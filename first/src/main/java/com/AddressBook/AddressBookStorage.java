package com.AddressBook;
import java.io.*;
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
    
    // Append a new record to the file
    public void addRecord(AddressBook record) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(file.length()); // Move to end for appending
            record.writeToFile(file);
        }
    }

    // Read a record by index (assuming fixed-size records)
    public AddressBook getRecord(int index) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            file.seek(index * RECORD_SIZE); // Jump to the record position
            return AddressBook.readFromFile(file);
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
}