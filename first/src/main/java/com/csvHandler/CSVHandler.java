package com.csvHandler;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVHandler {

    // Method to read from a CSV file
    public void readCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                System.out.println(String.join(", ", row));
            }
        } catch (IOException | com.opencsv.exceptions.CsvException e) {
            e.printStackTrace();
        }
    }
}

