package com.BinaryFileHandler;

import java.util.List;


public class BinaryFileHandlerReadAll {
    public static void main(String[] args) {
        String dbName = "AddressRecords";
        BinaryFileHandler bfh = new BinaryFileHandler(dbName, "address_book-0.dat");
        // ConfigReader reader = new ConfigReader(dbName);
        
        try {
            System.out.println("\nAll Records:");
            List<BinaryRecord> all = bfh.readAllRecords();
            all.forEach(BinaryRecord::printRecord);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
}
