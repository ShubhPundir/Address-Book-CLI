package com.BinaryFileHandler;

import com.config.ConfigReader;
import java.io.*;
import java.util.*;

public class ReaderTest {

    public static void main(String[] args) {
        try {
            // Load configuration
            ConfigReader config = new ConfigReader("AddressRecords");
            BinaryFileHandler handler = new BinaryFileHandler(config);

            String filePath = "data/AddressRecords/binary/test.dat"; // Test file
            RandomAccessFile file = new RandomAccessFile(filePath, "r");

            int recordSize = config.getRecordSize();
            long totalRecords = file.length() / recordSize;
            int recordsToRead = (int) Math.min(10, totalRecords);

            System.out.println("Total Records in file: " + totalRecords);
            System.out.println("Printing first " + recordsToRead + " records:\n");

            file.seek(0); // Start of file
            for (int i = 0; i < recordsToRead; i++) {
                Map<String, Object> record = handler.readRecord(file);
                System.out.println("Record " + (i + 1) + ": " + record);
            }

            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
