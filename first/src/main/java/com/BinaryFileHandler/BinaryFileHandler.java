package com.BinaryFileHandler;

import com.config.ConfigReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BinaryFileHandler {
    private ConfigReader config;

    public BinaryFileHandler(ConfigReader config) {
        this.config = config;
    }

    public void writeRecord(RandomAccessFile file, Map<String, Object> record) throws IOException {
        for (String fieldName : config.getFieldNames()) {
            int size = config.getFieldSize(fieldName);
            Object value = record.getOrDefault(fieldName, ""); // default empty if missing
    
            if (value instanceof Integer) {
                file.writeInt((Integer) value);
            } else if (value instanceof String) {
                String str = (String) value;
                str = padString(str, size);
                file.write(str.getBytes(StandardCharsets.UTF_8), 0, size);
            } else {
                throw new IllegalArgumentException("Unsupported data type for field: " + fieldName);
            }
        }
    }
    

    public Map<String, Object> readRecord(RandomAccessFile file) throws IOException {
        Map<String, Object> record = new LinkedHashMap<>(); // Maintain field order
        for (String fieldName : config.getFieldNames()) {
            int size = config.getFieldSize(fieldName);
            if (fieldName.equalsIgnoreCase("id") || fieldName.equalsIgnoreCase("pincode")) {
                int value = file.readInt();
                record.put(fieldName, value);
            } else {
                byte[] bytes = new byte[size];
                file.readFully(bytes);
                String value = new String(bytes, StandardCharsets.UTF_8).trim();
                record.put(fieldName, value);
            }
        }
        return record;
    }

    public void updateRecordByIndex(RandomAccessFile file, long index, Map<String, Object> updatedValues) throws IOException {
        long recordSize = config.getRecordSize();
        long positionToUpdate = index * recordSize;
    
        // Ensure the record is within bounds
        if (positionToUpdate >= file.length()) {
            throw new IOException("Record index out of bounds");
        }
    
        // Step 1: Move the file pointer to the record to be updated
        file.seek(positionToUpdate);
    
        // Step 2: Read the record and delete (skip it)
        Map<String, Object> oldRecord = readRecord(file);
        
        // Step 3: Now write the updated record
        writeRecord(file, updatedValues);
    
        // You don’t need a separate "delete" method, because when writing the new record, 
        // we are effectively overwriting the old one.
        
        // No need for any other record compaction here; the file will be overwritten in place.
    }
    

    public void updateRecordByCondition(RandomAccessFile file, String fieldName, Object targetValue, Map<String, Object> updatedFields) throws IOException {
        long recordSize = config.getRecordSize();
        long totalRecords = file.length() / recordSize;

        for (long i = 0; i < totalRecords; i++) {
            file.seek(i * recordSize);
            Map<String, Object> record = readRecord(file);

            Object fieldValue = record.get(fieldName);
            if (fieldValue != null && fieldValue.equals(targetValue)) {
                file.seek(i * recordSize);
                for (Map.Entry<String, Object> entry : updatedFields.entrySet()) {
                    record.put(entry.getKey(), entry.getValue());
                }
                writeRecord(file, record);
            }
        }
    }

    private String padString(String input, int length) {
        if (input == null) input = "";
        if (input.length() > length) {
            return input.substring(0, length);
        }
        return String.format("%-" + length + "s", input); // Left align and pad with spaces
    }
}
