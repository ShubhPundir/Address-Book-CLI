package com.BinaryFileHandler;
import com.config.ConfigReader;

import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class UpdateTest {
    public static void main(String[] args) {
        String databaseName = "AddressRecords"; // Change if your database is different
        String dataFilePath = System.getProperty("user.dir") + "/data/" + databaseName + "/binary/test.dat";

        try (RandomAccessFile file = new RandomAccessFile(dataFilePath, "rw")) {
            ConfigReader configReader = new ConfigReader(databaseName);
            BinaryFileHandler handler = new BinaryFileHandler(configReader);

            System.out.println("File size before update: " + file.length() + " bytes");

            // Fields you want to update
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", "Anna");
            updates.put("city", "New City");

            long recordIndexToUpdate = 0; // 0-based index: 0 -> first record

            // Perform update (this will hard delete the old record and update with new data)
            handler.updateRecordByIndex(file, recordIndexToUpdate, updates);

            System.out.println("Record at index " + recordIndexToUpdate + " updated successfully!");

            // 3. Read the updated record
            file.seek(0); // Start of file
            Map<String, Object> updatedRecord = handler.readRecord(file);

            System.out.println("Updated Record: " + updatedRecord);
            System.out.println("File size after update: " + file.length() + " bytes");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

