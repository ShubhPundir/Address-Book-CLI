package com.AddressBook;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

public class AddressBookTest {
    private AddressBook testEntry;
    private final int testId = 1;
    private final String testName = "John Doe";
    private final String testPhone = "9876543210";
    private final String testStreet = "123 Main St";
    private final String testLocality = "Downtown";
    private final String testCity = "New York";
    private final String testState = "NY";
    private final int testPincode = 10001;
    private final String testCountry = "USA";
    private File testFile;

    @Before
    public void setUp() throws IOException {
        testEntry = new AddressBook(testId, testName, testPhone, testStreet, testLocality, testCity, testState, testPincode, testCountry);
        testFile = File.createTempFile("testAddressBook", ".bin");
        testFile.deleteOnExit();
    }

    @Test
    public void testWriteAndReadFromFile() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(testFile, "rw")) {
            testEntry.writeToFile(file);
            file.seek(0);
            AddressBook readEntry = AddressBook.readFromFile(file);

            assertEquals("ID should match", testId, readEntry.id);
            assertEquals("Name should match", testName.trim(), readEntry.name.trim());
            assertEquals("Phone should match", testPhone.trim(), readEntry.phone.trim());
            assertEquals("Street should match", testStreet.trim(), readEntry.street.trim());
            assertEquals("Locality should match", testLocality.trim(), readEntry.locality.trim());
            assertEquals("City should match", testCity.trim(), readEntry.city.trim());
            assertEquals("State should match", testState.trim(), readEntry.state.trim());
            assertEquals("Pincode should match", testPincode, readEntry.pincode);
            assertEquals("Country should match", testCountry.trim(), readEntry.country.trim());
        }
    }

    @Test
    public void testPaddingFunctionality() {
        String padded = testEntry.toString();
        assertFalse("Padded name should not exceed allowed size", padded.length() > 100);
        assertFalse("Padded phone should not exceed allowed size", testEntry.phone.length() > 15);
    }

    @Test
    public void testFieldSizeInitialization() {
        assertTrue("Name size should be greater than 0", AddressBook.NAME_SIZE > 0);
        assertTrue("Phone size should be greater than 0", AddressBook.PHONE_SIZE > 0);
    }
}
