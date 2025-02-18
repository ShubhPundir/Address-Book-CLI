package com.AddressBook;
import java.io.IOException;

import com.config.ConfigReader;

public class AddressBookStorageTest {
    public static void main(String[] args) {
        try {
            // Create an instance of AddressBookStorage
            AddressBookStorage storage = new AddressBookStorage("data/address_book.dat");

            AddressBook record1 = new AddressBook(1, "John Doe", "1234567890", "123 Main St", 
                                                      "Downtown", "New York", "NY", 10001, "USA");
            AddressBook record2 = new AddressBook(2, "Alice Smith", "9876543210", "456 Elm St", 
                                                      "Midtown", "Los Angeles", "CA", 90001, "USA");

            storage.addRecord(record1);
            storage.addRecord(record2);

            System.out.println("Fetching record at index 1:");
            System.out.println(storage.getRecord(1)); // Fetch second record

            System.out.println("\nAll records in file:");
            storage.displayAll(); // Display all records

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
