package com.AddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;

public class AddressBookStorageValidationTest {

    private final String testFileName = "data/binary/address_book-1.dat";  // Your existing file
    private AddressBookStorage storage;

    @Before
    public void setUp() throws IOException {
        // Initialize storage with the existing file
        storage = new AddressBookStorage(testFileName);
    }

    @After
    public void tearDown() {
        storage = null;  // Clean up after the test
    }

    @Test
    public void testAllFieldsAreProperlyStored() throws IOException {
        File file = new File(testFileName);
        assertTrue("File should exist", file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int recordCount = 0;
            int expectedFieldCount = 9;  // The number of fields in your AddressBook class

            while ((line = reader.readLine()) != null) {
                recordCount++;

                // Split the line by comma
                String[] fields = line.split(",", -1);  // -1 keeps trailing empty fields
                assertEquals("Each record should have " + expectedFieldCount + " fields", 
                             expectedFieldCount, fields.length);

                // Validate individual fields
                assertFalse("ID should not be empty", fields[0].trim().isEmpty());
                assertFalse("Name should not be empty", fields[1].trim().isEmpty());
                assertFalse("Phone should not be empty", fields[2].trim().isEmpty());
                assertFalse("Address should not be empty", fields[3].trim().isEmpty());
                assertFalse("City should not be empty", fields[4].trim().isEmpty());
                assertFalse("State should not be empty", fields[5].trim().isEmpty());
                assertFalse("Pincode should not be empty", fields[6].trim().isEmpty());
                assertFalse("Country should not be empty", fields[7].trim().isEmpty());

                // Validate data types
                assertTrue("ID should be numeric", fields[0].trim().matches("\\d+"));
                assertTrue("Phone should be numeric", fields[2].trim().matches("\\d{10}"));
                assertTrue("Pincode should be numeric", fields[6].trim().matches("\\d+"));
            }

            assertTrue("At least one record should be present", recordCount > 0);
        }
    }
}
