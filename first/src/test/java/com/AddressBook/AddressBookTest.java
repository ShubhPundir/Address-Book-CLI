package com.AddressBook;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressBookTest {

    private static final int NAME_SIZE = 20; // Use static final for constants
    private AddressBook addressBook;

    @Before
    public void setUp() {
        // Initialize the AddressBook instance before each test
        addressBook = new AddressBook(1, "John", "12345", "Street", "Locality", "City", "State", 123456, "Country");
    }

    @Test
    public void testPadString_ShortInput() {
        String input = "John";
        String expected = String.format("%-" + NAME_SIZE + "s", input);
        String actual = addressBook.padString(input, NAME_SIZE);
        assertEquals("Should pad short input with spaces", expected, actual);
    }

    @Test
    public void testPadString_LongInput() {
        String input = "ThisIsAVeryLongNameExceedingTheLimit";
        String expected = input.substring(0, NAME_SIZE);
        String actual = addressBook.padString(input, NAME_SIZE);
        assertEquals("Should truncate long input to fit the specified length", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPadString_InvalidLength() {
        addressBook.padString("Test", -1);
    }
}
