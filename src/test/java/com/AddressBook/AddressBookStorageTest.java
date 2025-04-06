package com.AddressBook;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class AddressBookStorageTest {
    private AddressBookStorage storage;
    private final String testFileName = "address_book0.dat";
    private AddressBook testRecord1;
    private AddressBook testRecord2;

    @Before
    public void setUp() throws IOException {
        // Check if test file is accessible
        File file = new File(testFileName);
        assertTrue("Test file should exist and be accessible", file.exists() && file.canRead() && file.canWrite());

        // Initialize storage
        storage = new AddressBookStorage(testFileName);

        // Create test records
        testRecord1 = new AddressBook(1, "John Doe", "9876543210", "123 Main St", "Downtown",
                "New York", "NY", 10001, "USA");

        testRecord2 = new AddressBook(2, "Jane Doe", "1234567890", "456 Elm St", "Uptown",
                "Los Angeles", "CA", 90001, "USA");
    }

    @Test
    public void testAddAndGetRecord() throws IOException {
        storage.addRecord(testRecord1);
        AddressBook retrieved = storage.getRecord(0);

        assertNotNull("Retrieved record should not be null", retrieved);
        assertEquals("ID should match", testRecord1.id, retrieved.id);
        assertEquals("Name should match", testRecord1.name.trim(), retrieved.name.trim());
        assertEquals("Phone should match", testRecord1.phone.trim(), retrieved.phone.trim());
        assertEquals("City should match", testRecord1.city.trim(), retrieved.city.trim());
        assertEquals("Pincode should match", testRecord1.pincode, retrieved.pincode);
    }

    @Test
    public void testUpdateRecord() throws IOException {
        storage.addRecord(testRecord1);
        storage.updateRecord(0, testRecord2);
        AddressBook updated = storage.getRecord(0);

        assertNotNull("Updated record should not be null", updated);
        assertEquals("ID should be updated", testRecord2.id, updated.id);
        assertEquals("Name should be updated", testRecord2.name.trim(), updated.name.trim());
        assertEquals("City should be updated", testRecord2.city.trim(), updated.city.trim());
    }

    @Test
    public void testDeleteRecord() throws IOException {
        storage.addRecord(testRecord1);
        storage.addRecord(testRecord2);

        storage.hardDeleteRecord(0);  // Delete the first record

        AddressBook remaining = storage.getRecord(0);

        assertNotNull("Remaining record should not be null", remaining);
        assertEquals("Remaining record should be Jane Doe", testRecord2.name.trim(), remaining.name.trim());
        assertEquals("Remaining record ID should match", testRecord2.id, remaining.id);
    }
}
