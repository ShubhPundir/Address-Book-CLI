package com.AddressBook;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AddressBookTest {

    private AddressBook address;

    @Before
    public void setUp() {
        address = new AddressBook(
                1,
                "John Doe",
                "1234567890",
                "123 Main Street",
                "Downtown",
                "Metropolis",
                "StateX",
                123456,
                "CountryY"
        );
    }

    @Test
    public void testPadString() {
        String padded = address.padString("Test", 10);
        assertEquals(10, padded.length());
        assertTrue(padded.startsWith("Test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPadStringInvalidLength() {
        address.padString("Test", -1);
    }

    @Test
    public void testWriteAndReadFromFile() throws IOException {
        String tempFileName = "addressbook_test.dat";
        File tempFile = new File(tempFileName);

        // Write record
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            address.writeToFile(raf);
        }

        AddressBook readAddress;

        // Read record
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            readAddress = AddressBook.readFromFile(raf);
        }

        // Compare fields
        assertEquals(address.getId(), readAddress.getId());
        assertEquals(address.getName(), readAddress.getName());
        assertEquals(address.getPhone(), readAddress.getPhone());
        assertEquals(address.getStreet(), readAddress.getStreet());
        assertEquals(address.getLocality(), readAddress.getLocality());
        assertEquals(address.getCity(), readAddress.getCity());
        assertEquals(address.getState(), readAddress.getState());
        assertEquals(address.getPincode(), readAddress.getPincode());
        assertEquals(address.getCountry(), readAddress.getCountry());

        // Clean up
        tempFile.delete();
    }

    @Test
    public void testToString() {
        String expectedStart = "1, John Doe";
        assertTrue(address.toString().startsWith(expectedStart));
    }

    @Test
    public void testGetRecordSize() {
        int recordSize = AddressBook.getRecordSize();
        assertTrue(recordSize > 0);
    }
}
