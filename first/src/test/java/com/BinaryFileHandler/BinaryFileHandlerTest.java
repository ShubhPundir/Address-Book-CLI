package com.BinaryFileHandler;

import com.config.ConfigReader;

import java.io.IOException;
import java.util.List;

public class BinaryFileHandlerTest {

    public static void main(String[] args) {
        String dbName = "TestDB";

        // Step 1: Ensure config exists
        // Normally, ConfigReader reads config.xml — you should already have config.xml generated.
        // We'll assume it defines fields: ID(INT), Name(STRING, size=20), Salary(FLOAT)

        try {
            BinaryFileHandler handler = new BinaryFileHandler(dbName);

            // Step 2: Create a sample record
            ConfigReader reader = new ConfigReader(dbName);
            BinaryRecord record1 = new BinaryRecord(reader);
            record1.addFieldValue("ID", 1);
            record1.addFieldValue("Name", "Alice");
            record1.addFieldValue("Salary", 50000.0f);

            handler.writeRecord(record1);

            BinaryRecord record2 = new BinaryRecord(reader);
            record2.addFieldValue("ID", 2);
            record2.addFieldValue("Name", "Bob");
            record2.addFieldValue("Salary", 60000.0f);

            handler.writeRecord(record2);

            // Step 3: Read all records
            System.out.println("\nAll Records:");
            List<BinaryRecord> all = handler.readAllRecords();
            all.forEach(BinaryRecord::printRecord);

            // Step 4: Read record at index 1
            System.out.println("\nRecord at Index 1:");
            try {
                BinaryRecord r = handler.readRecordAt(1);
                r.printRecord();
                
            } catch (Exception e) {
                System.out.println("No Record at index 1");
                e.printStackTrace();
            }
            // Step 5: Update record at index 0
            BinaryRecord updated = new BinaryRecord(reader);
            updated.addFieldValue("ID", 1);
            updated.addFieldValue("Name", "AliceUpdated");
            updated.addFieldValue("Salary", 70000.0f);

            handler.updateRecordAt(0, updated);
            System.out.println("\nAfter Update at Index 0:");
            handler.readAllRecords().forEach(BinaryRecord::printRecord);

            // Step 6: Delete record at index 1
            handler.deleteRecordAt(1);
            System.out.println("\nAfter Deletion at Index 1:");
            handler.readAllRecords().forEach(BinaryRecord::printRecord);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
