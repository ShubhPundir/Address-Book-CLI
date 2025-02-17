package com.binaryGenerator;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            AddressRecord record1 = new AddressRecord(1, "John Doe", "1234567890", "123 Main St", 
                                                      "Downtown", "New York", "NY", 10001, "USA");
            AddressRecord record2 = new AddressRecord(2, "Alice Smith", "9876543210", "456 Elm St", 
                                                      "Midtown", "Los Angeles", "CA", 90001, "USA");

            AddressBookStorage.addRecord(record1);
            AddressBookStorage.addRecord(record2);

            System.out.println("Fetching record at index 1:");
            System.out.println(AddressBookStorage.getRecord(1)); // Fetch second record

            System.out.println("\nAll records in file:");
            AddressBookStorage.displayAll(); // Display all records

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
