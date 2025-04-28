package com.BinaryFileHandler;

import com.config.ConfigReader;
import java.io.*;
import java.util.*;

public class BinaryFileHandlerTest {

    public static void main(String[] args) {
        try {
            // Load configuration
            ConfigReader config = new ConfigReader("AddressRecords"); 
            BinaryFileHandler handler = new BinaryFileHandler(config);

            String filePath = "data/AddressRecords/binary/test.dat"; // Test file
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");

            // 1. Create a sample record
            Map<String, Object> sampleRecord = new LinkedHashMap<>();
            sampleRecord.put("ID", 101);
            sampleRecord.put("Name", "Alice Wonderland");
            sampleRecord.put("Phone", "9876543210");
            sampleRecord.put("Street", "456 Elm Street");
            sampleRecord.put("Locality", "Midtown");
            sampleRecord.put("City", "Springfield");
            sampleRecord.put("State", "Illinois");
            sampleRecord.put("Pincode", 62704);
            sampleRecord.put("Country", "USA");

            // 2. Write record
            file.seek(0); // Start of file
            handler.writeRecord(file, sampleRecord);
            file.close();

            // 3. Read record
            RandomAccessFile readFile = new RandomAccessFile(filePath, "r");
            readFile.seek(0); // Start of file
            Map<String, Object> readRecord = handler.readRecord(readFile);
            readFile.close();

            // 4. Compare and Print
            System.out.println("Written Record: " + sampleRecord);
            System.out.println("Read Record:    " + readRecord);

            if (compareRecords(sampleRecord, readRecord)) {
                System.out.println("\n✅ Test Passed: Written and Read records match!");
            } else {
                System.out.println("\n❌ Test Failed: Written and Read records do not match!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean compareRecords(Map<String, Object> original, Map<String, Object> readBack) {
        for (String key : original.keySet()) {
            Object originalValue = original.get(key);
            Object readValue = readBack.get(key);

            if (originalValue instanceof String && readValue instanceof String) {
                if (!((String) originalValue).trim().equals(((String) readValue).trim())) {
                    return false;
                }
            } else if (!Objects.equals(originalValue, readValue)) {
                return false;
            }
        }
        return true;
    }
}
