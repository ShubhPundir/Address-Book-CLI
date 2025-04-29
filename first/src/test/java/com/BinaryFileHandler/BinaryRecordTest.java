package com.BinaryFileHandler;

import com.config.ConfigReader;
import com.config.FieldInfo;

import java.util.LinkedHashMap;

public class BinaryRecordTest {

    public static void main(String[] args) {
        try {
            // 1. Load schema from ConfigReader (simulate schema loading from XML)
            String database = "Normal";  // Change this to your actual path
            ConfigReader reader = new ConfigReader(database);
            LinkedHashMap<String, FieldInfo> schema = reader.getFieldInfoMap();

            // 2. Create BinaryRecord instance with the schema
            BinaryRecord record = new BinaryRecord(schema);

            // 3. Add some field values
            record.addFieldValue("ID", 101);
            record.addFieldValue("Name", "Alice");
            record.addFieldValue("Age", 25);

            // Print the record before conversion
            System.out.println("Original Record:");
            record.printRecord();

            // 4. Convert the record to byte array
            byte[] bytes = record.toByteArray();

            // 5. Create a new BinaryRecord instance (simulate reading from file)
            BinaryRecord newRecord = new BinaryRecord(schema);
            newRecord.fromByteArray(bytes);

            // Print the restored record after conversion
            System.out.println("\nRestored Record:");
            newRecord.printRecord();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
