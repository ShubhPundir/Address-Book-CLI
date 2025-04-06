package com.csvHandler;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CSVHandlerTest {
    private CSVHandler csvHandler;

    private static final String EXISTING_CSV_FILE = "Address-Book-CLI/data/csv/data.csv";
    private static final String EMPTY_CSV_FILE = "Address-Book-CLI/data/csv/empty.csv";
    private static final String MISSING_CSV_FILE = "Address-Book-CLI/data/csv/missing.csv";

    @Before
    public void setUp() {
        csvHandler = new CSVHandler();
    }

    @Test
    public void testReadExistingCSV() throws IOException {
        System.out.println("\nReading Existing CSV:");

        File file = new File(EXISTING_CSV_FILE);
        assertTrue("File should exist at the specified path", file.exists());

        csvHandler.readCSV(EXISTING_CSV_FILE); // Read the existing CSV file
    }

    @Test
    public void testReadEmptyCSV() throws IOException {
        Path emptyFilePath = Paths.get(EMPTY_CSV_FILE);

        // Ensure the empty CSV file exists
        if (!Files.exists(emptyFilePath)) {
            Files.createDirectories(emptyFilePath.getParent()); // Ensure parent directories exist
            Files.createFile(emptyFilePath);
        }

        System.out.println("\nReading Empty CSV:");
        csvHandler.readCSV(EMPTY_CSV_FILE); // Should handle empty file gracefully

        Files.deleteIfExists(emptyFilePath); // Clean up
    }

    @Test
    public void testReadMissingCSV() {
        System.out.println("\nReading Missing CSV:");

        try {
            csvHandler.readCSV(MISSING_CSV_FILE);
            fail("Expected an IOException for missing file.");
        } catch (IOException e) {
            assertTrue("Exception message should contain the missing filename",
                    e.getMessage().contains("missing.csv"));
        }
    }
}
