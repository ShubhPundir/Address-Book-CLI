package com.config;

import java.util.LinkedHashMap;

public class ReadConfigTest {

    public static void main(String[] args) {
        // Initialize the config reader with the default config XML ("AddressRecords")
        ConfigReader configReader = new ConfigReader("Employee");

        // Check if config is loaded
        if (configReader.isConfigLoaded()) {
            System.out.println("Configuration loaded successfully.");
        } else {
            System.out.println("Failed to load configuration.");
            return;
        }

        // Print field info map contents
        System.out.println("Field information map:");
        LinkedHashMap<String, FieldInfo> fieldInfoMap = configReader.getFieldInfoMap();

        for (String fieldName : fieldInfoMap.keySet()) {
            FieldInfo fieldInfo = fieldInfoMap.get(fieldName);
            System.out.println("Field: " + fieldName + " | Dtype: " + fieldInfo.getDtype() + " | Size: " + fieldInfo.getSize());
        }
    }
}
