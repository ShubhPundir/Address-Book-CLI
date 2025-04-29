package com.BinaryFileHandler;

import com.config.ConfigReader;
import java.io.File;
import java.io.RandomAccessFile;

public class ReadTest {
    public static void main(String[] args) {
        try {
            ConfigReader config = new ConfigReader(); // Default AddressRecords config
            File file = new File("data/Employee/binary/testData.bin");

            if (!file.exists()) {
                System.out.println("Data file not found!");
                return;
            }
            System.out.println("Data file found");

            RandomAccessFile raf = new RandomAccessFile(file, "r");

            long recordSize = 4 + config.getRecordSize() + 4; 
            // id (4 bytes) + fields + pincode (4 bytes)
            
            long numRecords = raf.length() / recordSize;

            for (int i = 0; i < numRecords; i++) {
                BinaryDataStructure record = BinaryDataStructure.readFromFile(raf, config);
                System.out.println(record);
            }

            raf.close();
        } catch (Exception e) {
            System.out.println("Exception called");
            e.printStackTrace();
        }
    }
}
