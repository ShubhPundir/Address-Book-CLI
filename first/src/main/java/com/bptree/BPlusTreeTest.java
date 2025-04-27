package com.bptree;

import com.bloock.sdk.record.entity.Record;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BPlusTreeTest {
    public static void main(String[] args) throws CsvValidationException {
        String csvFile = "data/address_book-0.csv";

        int numRecords = 20;
        List<String[]> records = new ArrayList<>();
        
        // Step 1: Read the first 20 records
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            String[] record;
            int count = 0;
            
            while ((record = csvReader.readNext()) != null && count < numRecords) {
                records.add(record);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Step 2: Insert into B+ Tree
        BPlusTree<Integer, String[]> bptree = new BPlusTree<>(3); // Order 3 B+ Tree
        for (String[] record : records) {
            int key = Integer.parseInt(record[0]); // Assuming the first column is an Integer ID
            bptree.insert(key, record);
        }

        // Step 3: Test B+ Tree Operations
        // Search for a key
        int searchKey = 5;
        String[] result = bptree.search(searchKey);
        if (result != null) {
            System.out.println("Record Found for Key " + searchKey + ": " + String.join(", ", result));
        } else {
            System.out.println("No record found for Key " + searchKey);
        }

        // Display in-order traversal
        System.out.println("In-order Traversal of B+ Tree:");
        bptree.inOrderTraversal();
    }

    public void insert(int i, long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    public Object search(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    public void saveToFile(String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveToFile'");
    }

    public void bulkLoad(List<Object> entries) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bulkLoad'");
    }

    public void delete(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public void printTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printTree'");
    }
}
