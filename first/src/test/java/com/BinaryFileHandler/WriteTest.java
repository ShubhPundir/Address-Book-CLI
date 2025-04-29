package com.BinaryFileHandler;

import com.config.ConfigReader;

import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;

public class WriteTest {
    public static void main(String[] args) {
        try {
            // Load configuration
            ConfigReader config = new ConfigReader("Employee");

            // Prepare sample record data
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("Id", "1");
            fields.put("Name", "John Doe");
            fields.put("Age", "4");
            fields.put("Salary", "1234");
            fields.put("DepartmentID", "2");

            BinaryDataStructure record = new BinaryDataStructure(config, fields);

            // Write to binary file
            RandomAccessFile file = new RandomAccessFile("datafile.bin", "rw");
            file.seek(file.length()); // Append at the end
            record.writeToFile(file);

            file.close();
            System.out.println("Record written successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
