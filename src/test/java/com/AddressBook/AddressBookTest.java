package com.AddressBook;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

public class AddressBookTest {

    private AddressBook addressBook;
    private RandomAccessFile file;

    @Before
    public void setUp() throws Exception {
        // Mock data for AddressBook
        addressBook = new AddressBook(1, "John Doe", "1234567890", "Main St", "Downtown", "City", "State", 123456, "Country");

        // Create a temporary file to test file operations
        File tempFile = File.createTempFile("addressbook", ".dat");
        tempFile.deleteOnExit();  // Ensure the file is deleted after the test
        file = new RandomAccessFile(tempFile, "rw");
    }

    @Test
    public void testWriteToFile() throws IOException {
        // Write the address book to the file
        addressBook.writeToFile(file);

        // Move the file pointer back to the start to check the file content
        file.seek(0);

        // Verify that the file has the expected size
        long expectedFileSize = AddressBook.NAME_SIZE + AddressBook.PHONE_SIZE + AddressBook.STREET_SIZE + AddressBook.LOCALITY_SIZE
                + AddressBook.CITY_SIZE + AddressBook.STATE_SIZE + AddressBook.COUNTRY_SIZE + 8;  // id (int) + pincode (int)
        
        assertEquals(expectedFileSize, file.length());
    }

    @Test
    public void testReadFromFile() throws IOException {
        // Write the address book to the file
        addressBook.writeToFile(file);

        // Move the file pointer back to the beginning of the file to read
        file.seek(0);

        // Read the address book from the file
        AddressBook readAddressBook = AddressBook.readFromFile(file);

        // Verify the content read from the file
        assertNotNull(readAddressBook);
        assertEquals(addressBook.id, readAddressBook.id);
        assertEquals(addressBook.name.trim(), readAddressBook.name.trim());
        assertEquals(addressBook.phone.trim(), readAddressBook.phone.trim());
        assertEquals(addressBook.street.trim(), readAddressBook.street.trim());
        assertEquals(addressBook.locality.trim(), readAddressBook.locality.trim());
        assertEquals(addressBook.city.trim(), readAddressBook.city.trim());
        assertEquals(addressBook.state.trim(), readAddressBook.state.trim());
        assertEquals(addressBook.pincode, readAddressBook.pincode);
        assertEquals(addressBook.country.trim(), readAddressBook.country.trim());
    }

    @Test
    public void testToString() {
        String expected = "1, John Doe, 1234567890, Main St, Downtown, City, State, 123456, Country";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void testPadString() {
        // Test padding to the correct length
        String input = "Hello";
        int length = 10;
        String padded = addressBook.padString(input, length);

        assertEquals("Hello     ", padded);  // Ensure padding to 10 characters
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetRecordSize() {
        addressBook.getRecordSize();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetId() {
        addressBook.getId();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetName() {
        addressBook.getName();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetStreet() {
        addressBook.getStreet();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetPhone() {
        addressBook.getPhone();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetLocality() {
        addressBook.getLocality();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCity() {
        addressBook.getCity();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetPincode() {
        addressBook.getPincode();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetState() {
        addressBook.getState();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCountry() {
        addressBook.getCountry();
    }
}
