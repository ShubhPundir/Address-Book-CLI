package com.binaryGenerator;
import java.io.*;

public class AddressBookStorage {
    private static final String FILE_NAME = "data/address_book.dat";

    // Append a new record to the file
    public static void addRecord(AddressRecord record) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(file.length()); // Move to end for appending
            record.writeToFile(file);
        }
    }

    // Read a record by index (assuming fixed-size records)
    public static AddressRecord getRecord(int index) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            file.seek(index * 263); // Jump to the record position
            return AddressRecord.readFromFile(file);
        }
    }

    // Display all records
    public static void displayAll() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            while (file.getFilePointer() < file.length()) {
                AddressRecord record = AddressRecord.readFromFile(file);
                System.out.println(record);
            }
        }
    }
}
