package com.csvHandler;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVHandler {

    public void readCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                System.out.println(String.join(", ", row));
            }
        } catch (IOException e) {
            throw e;
        } catch (com.opencsv.exceptions.CsvException e) {
            e.printStackTrace();
        }
    }
}
