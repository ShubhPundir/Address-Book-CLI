package com.find;

public class CSVHandlerTest {
    public static void main(String[] args) {
        CSVHandler csvHandler = new CSVHandler();
        String filePath = "data/address_book-0.csv";  // Adjust this path if needed

        // Call the read method
        csvHandler.readCSV(filePath);
    }
}