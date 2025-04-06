package com.csvHandler;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class CSVHandlerTest {
    private CSVHandler csvHandler;
    // Use the existing file path
    private static final String EXISTING_CSV_FILE = "C:/AB_cli/Address-Book-CLI/data/csv/address_book-4.csv";

    @Before
    public void setUp() {
        csvHandler = new CSVHandler();
    }

    @Test
    public void testReadExistingCSV() throws IOException {
        System.out.println("Reading Existing CSV:");
        File file = new File(EXISTING_CSV_FILE);
        assertTrue("CSV file should exist", file.exists());

        csvHandler.readCSV(EXISTING_CSV_FILE); // Should print CSV content
    }

    @Test
    public void testReadMissingCSV() {
        String missingFile = "C:/AB_cli/Address-Book-CLI/data/csv/missing.csv";
        System.out.println("Reading Missing CSV:");

        try {
            csvHandler.readCSV(missingFile);
            fail("Expected an IOException for missing file.");
        } catch (IOException e) {
            assertTrue("Exception message should contain missing filename", 
                       e.getMessage().contains("missing.csv"));
        }
    }
}
