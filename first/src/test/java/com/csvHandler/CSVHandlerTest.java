package com.csvHandler;

import org.junit.Before;
import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import java.io.File;

public class CSVHandlerTest {
    private CSVHandler csvHandler;
    private static final String TEST_CSV_FILE = "test.csv";

    @Before
    public void setUp() throws IOException {
        csvHandler = new CSVHandler();
        
        // Create a sample CSV file for testing
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_CSV_FILE))) {
            writer.println("Name,Age,City");
            writer.println("Alice,25,New York");
            writer.println("Bob,30,Los Angeles");
        }
    }

    @Test
    public void testReadCSV() throws IOException {
        System.out.println("Reading CSV:");
        csvHandler.readCSV(TEST_CSV_FILE); // Should print CSV content
        assertTrue("File should exist", new File(TEST_CSV_FILE).exists());
    }

    @Test
    public void testReadEmptyCSV() throws IOException {
        String emptyFile = "empty.csv";
        Files.createFile(Paths.get(emptyFile));

        System.out.println("Reading Empty CSV:");
        csvHandler.readCSV(emptyFile); // Should print nothing

        Files.delete(Paths.get(emptyFile));
    }

    @Test
    public void testReadMissingCSV() {
        String missingFile = "missing.csv";
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
