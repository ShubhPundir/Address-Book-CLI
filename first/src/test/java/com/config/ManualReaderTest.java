package com.config;

public class ManualReaderTest {
    public static void main(String[] args) {
        // Create a ConfigReader instance
        ConfigReader configReader = new ConfigReader("Employee");

        // Check if config loaded
        if (configReader.isConfigLoaded()) {
            System.out.println("Config loaded successfully!");

            // Print all fields and their sizes
            System.out.println("Fields and sizes:");
            for (var entry : configReader.getFields().entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            // Test getFieldSize for an existing field
            String testField = "Name"; // Assuming "Name" exists in config.xml
            int size = configReader.getFieldSize(testField);
            if (size != -1) {
                System.out.println("Size of '" + testField + "': " + size);
            } else {
                System.out.println("Field '" + testField + "' not found.");
            }

            // Test getRecordSize
            System.out.println("Total record size: " + configReader.getRecordSize());
        } else {
            System.out.println("Failed to load config.");
        }
    }
}
