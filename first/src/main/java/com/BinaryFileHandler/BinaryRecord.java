package com.BinaryFileHandler;

import com.config.ConfigReader;
import com.config.FieldInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class BinaryRecord {

    private final LinkedHashMap<String, FieldInfo> schema;
    private final LinkedHashMap<String, Object> data;

    public BinaryRecord(LinkedHashMap<String, FieldInfo> schema) {
        this.schema = schema;
        this.data = new LinkedHashMap<>();
    }
    
    public BinaryRecord(ConfigReader conf){
        this.schema = conf.getFieldInfoMap();
        this.data = new LinkedHashMap<>();
    }


    // Adds a field value with validation
    public void addFieldValue(String fieldName, Object value) throws IllegalArgumentException {
        FieldInfo info = schema.get(fieldName);
        if (info == null) {
            throw new IllegalArgumentException("Field '" + fieldName + "' not found in schema.");
        }

        String dtype = info.getDtype().toUpperCase();

        switch (dtype) {
            case "INT":
                if (!(value instanceof Integer)) {
                    throw new IllegalArgumentException("Expected INT for field '" + fieldName + "'.");
                }
                break;

            case "FLOAT":
                if (!(value instanceof Float)) {
                    throw new IllegalArgumentException("Expected FLOAT for field '" + fieldName + "'.");
                }
                break;

            case "STRING":
                if (!(value instanceof String)) {
                    throw new IllegalArgumentException("Expected STRING for field '" + fieldName + "'.");
                }
                String str = (String) value;
                if (str.length() > info.getSize()) {
                    throw new IllegalArgumentException("STRING too long for field '" + fieldName + "', max size: " + info.getSize());
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported dtype: " + dtype);
        }

        data.put(fieldName, value);
    }

    // Get data value
    public Object getFieldValue(String fieldName) {
        return data.get(fieldName);
    }

    // For debugging: Print the record
    public void printRecord() {
        data.forEach((key, value) -> {
            System.out.printf("%-15s : %s%n", key, value);
        });
    }

    public LinkedHashMap<String, Object> getData() {
        return data;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            FieldInfo info = schema.get(fieldName);
            String dtype = info.getDtype().toUpperCase();
            int size = info.getSize();

            switch (dtype) {
                case "INT" -> dos.writeInt((Integer) value);
                case "FLOAT" -> dos.writeFloat((Float) value);
                case "STRING" -> {
                    String str = (String) value;
                    byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
                    if (strBytes.length > size) {
                        strBytes = Arrays.copyOf(strBytes, size);
                    } else if (strBytes.length < size) {
                        strBytes = Arrays.copyOf(strBytes, size); // zero padding
                    }
                    dos.write(strBytes);
                }
            }
        }

        return baos.toByteArray();
    }
    
    public static BinaryRecord fromByteArray(byte[] bytes, ConfigReader schema) throws IOException {
        BinaryRecord record = new BinaryRecord(schema);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        for (Map.Entry<String, FieldInfo> field : record.schema.entrySet()) {
            String fieldName = field.getKey();
            String dtype = field.getValue().getDtype().toUpperCase();
            int size = field.getValue().getSize();

            switch (dtype) {
                case "INT" -> record.addFieldValue(fieldName, dis.readInt());
                case "FLOAT" -> record.addFieldValue(fieldName, dis.readFloat());
                case "STRING" -> {
                    byte[] strBytes = new byte[size];
                    dis.readFully(strBytes);
                    String value = new String(strBytes, StandardCharsets.UTF_8).trim();
                    record.addFieldValue(fieldName, value);
                }
            }
        }

        return record;
    }
    
     


}
