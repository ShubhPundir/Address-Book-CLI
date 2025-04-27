package com.AddressBook;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;

public class AddressBookStorageTest {
    private static final String TEST_FILE_PATH = 
        "C:\\AB_cli\\Address-Book-CLI\\src\\test\\resources\\address_book-4.dat";
    private static final int RECORD_SIZE = 256; // Set your actual record size here
    
    private AddressBookStorage storage;
    
    @Before
    public void setUp() throws Exception {
        // Initialize with test-specific constructor
        storage = new AddressBookStorage(TEST_FILE_PATH, RECORD_SIZE);
    }
    
    
    
    @Test
    public void testStorageInitialization() {
        assertNotNull("Storage should be initialized", storage);
    }
    
    
    
    @Test(expected = IOException.class)
    public void testInvalidIndex() throws IOException {
        storage.getRecord(Integer.MAX_VALUE);
    }
}