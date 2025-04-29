package com.config;

import java.util.LinkedHashMap;

public class GetFieldInfoMapTest {

    public static void main(String[] args) {
        // Initialize the config reader with the default config XML ("AddressRecords")
        ConfigReader configReader = new ConfigReader("Employee");
        
        // Retrieve the field information map
        LinkedHashMap<String, FieldInfo> fieldInfoMap = configReader.getFieldInfoMap();
        
        // Output the map contents to manually verify
        if (fieldInfoMap != null && !fieldInfoMap.isEmpty()) {
            System.out.println("Field Information Map:");
            for (String fieldName : fieldInfoMap.keySet()) {
                FieldInfo fieldInfo = fieldInfoMap.get(fieldName);
                System.out.println("Field: " + fieldName + " | Dtype: " + fieldInfo.getDtype() + " | Size: " + fieldInfo.getSize());
            }
        } else {
            System.out.println("No fields found in the config.");
        }
    }
}
