package com.AddressBook;
import java.io.*;
import java.util.List;

import com.config.ConfigReader;

public class AddressBookStorage {
    private ConfigReader conf;
    private final int RECORD_SIZE;
    private String FILE_NAME;

    public AddressBookStorage(String file_name) {
        this.conf = new ConfigReader();
        this.RECORD_SIZE = conf.getRecordSize();
        this.FILE_NAME = file_name;
        
        // Ensure the file exists
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // CRUD OPERATIONS


    // CREATE/Append a new record to the file
    public void addRecord(AddressBook record) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(file.length()); // Move to end for appending
            record.writeToFile(file);
        }
    }

    // READ a record by index (assuming fixed-size records)
    public AddressBook getRecord(int index) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            file.seek(index * RECORD_SIZE); // Jump to the record position
            return AddressBook.readFromFile(file);
        }
    }

    // UPDATE: Modify a record by index
    public void updateRecord(int index, AddressBook updatedRecord) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(index * RECORD_SIZE);
            updatedRecord.writeToFile(file);
        }
    }

    // DELETE: Soft delete a record
    // Hard delete: Remove record permanently by compacting the file
    public void hardDeleteRecord(int index) throws IOException {
        File originalFile = new File(FILE_NAME);
        File tempFile = new File(FILE_NAME + ".tmp");

        try (RandomAccessFile file = new RandomAccessFile(originalFile, "r");
            RandomAccessFile temp = new RandomAccessFile(tempFile, "rw")) {
            
            int recordIndex = 0;

            while (file.getFilePointer() < file.length()) {
                long currentPosition = file.getFilePointer();
                AddressBook record = AddressBook.readFromFile(file);
                System.out.println(record.toString());

                System.exit(0);
                System.out.println("THIS SHOULD NOT GET PRINTED");

                // Skip the deleted record, copy all others
                if (recordIndex != index) {
                    file.seek(currentPosition);  // Reposition to re-read
                    AddressBook validRecord = AddressBook.readFromFile(file);
                    validRecord.writeToFile(temp);
                }
                System.out.println("Record " + recordIndex + " deleted.");
                recordIndex++;
            }
        }

        // Replace old file with the compacted file
        if (originalFile.delete()) {
            tempFile.renameTo(originalFile);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Error: Could not delete record.");
        }
    }

    // Display all records
    public void displayAll() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            while (file.getFilePointer() < file.length()) {
                AddressBook record = AddressBook.readFromFile(file);
                System.out.println(record);
            }
        }
    }

    public long getTotalRecords() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalRecords'");
    }

    public void addAllRecords(List<AddressBook> sampleRecords) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAllRecords'");
    }
}