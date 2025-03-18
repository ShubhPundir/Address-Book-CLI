package com.find.addressbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// import com.find.addressbook.AddressBookRecord;;

public class AddressBook {
    private List<AddressBookRecord> records;

    public AddressBook(String csvFile) {
        records = new ArrayList<>();
        loadCSV(csvFile);
    }

    private void loadCSV(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true; // Flag to skip header
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header line
                    continue;
                }

                // Regular expression to handle quoted commas and split by commas
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (values.length == 9) { // Match with number of fields in AddressBookRecord
                    AddressBookRecord record = new AddressBookRecord(values[0], values[1], values[2],
                            values[3], values[4], values[5],
                            values[6], values[7], values[8]);
                    records.add(record);
                    // System.out.println("Loaded Record: " + record); // Debugging output
                }
            }
            System.out.println("CSV Loaded");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("OOPSIE DAISY T_T --> WRONG PATH");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Search for a record by ID (O(n) time complexity)
    public AddressBookRecord searchById(String id) {
        id = id.trim(); // Trim the search ID to avoid leading/trailing spaces
        for (AddressBookRecord record : records) {
            if (record.getId().trim().equals(id)) { // Trim the record ID as well before comparing
                return record;
            }
        }
        return null; // Not found
    }

    // Display first 5 records
    public void displayFirstFiveRecords() {
        int count = 0;
        for (AddressBookRecord record : records) {
            if (count >= 5) {
                break;
            }
            System.out.println(record);
            count++;
        }
    }

    private void printFirstFiveLines(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < 5) {
                System.out.println("Reading line: " + line); // Print the raw line
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Make sure to give the relative path to your CSV in the testing folder
        AddressBook addressBook = new AddressBook("data\\address_book-0.csv");

        addressBook.printFirstFiveLines("data\\address_book-0.csv");

        // Display first 5 records
        System.out.println("First 5 records:");
        addressBook.displayFirstFiveRecords();

        // Example search
        String searchId = "10";
        AddressBookRecord record = addressBook.searchById(searchId);

        if (record != null) {
            System.out.println("Record found: " + record);
        } else {
            System.out.println("Record not found.");
        }
    }
}
