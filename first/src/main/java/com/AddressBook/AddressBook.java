package com.AddressBook;

import com.config.ConfigReader;
import java.io.*;

public class AddressBook {
    private int id;
    private String name;
    private String phone;
    private String street;
    private String locality;
    private String city;
    private String state;
    private int pincode;
    private String country;

    // Load field sizes dynamically from ConfigReader
    private static final ConfigReader conf = new ConfigReader();
    private static final int NAME_SIZE = conf.getFieldSize("Name");
    private static final int PHONE_SIZE = conf.getFieldSize("Phone");
    private static final int STREET_SIZE = conf.getFieldSize("Street");
    private static final int LOCALITY_SIZE = conf.getFieldSize("Locality");
    private static final int CITY_SIZE = conf.getFieldSize("City");
    private static final int STATE_SIZE = conf.getFieldSize("State");
    private static final int COUNTRY_SIZE = conf.getFieldSize("Country");

    // Constructor
    public AddressBook(int id, String name, String phone, String street, String locality,
            String city, String state, int pincode, String country) {
        this.id = id;
        this.name = padString(name, NAME_SIZE);
        this.phone = padString(phone, PHONE_SIZE);
        this.street = padString(street, STREET_SIZE);
        this.locality = padString(locality, LOCALITY_SIZE);
        this.city = padString(city, CITY_SIZE);
        this.state = padString(state, STATE_SIZE);
        this.pincode = pincode;
        this.country = padString(country, COUNTRY_SIZE);
    }

    // Padding strings to fixed length
    private String padString(String input, int length) {
        return String.format("%-" + length + "s", input).substring(0, length);
    }

    // Writing the record to a binary file
    public void writeToFile(RandomAccessFile file) throws IOException {
        file.writeInt(id);
        file.writeBytes(name);
        file.writeBytes(phone);
        file.writeBytes(street);
        file.writeBytes(locality);
        file.writeBytes(city);
        file.writeBytes(state);
        file.writeInt(pincode);
        file.writeBytes(country);
    }

    // Reading a record from a binary file
    public static AddressBook readFromFile(RandomAccessFile file) throws IOException {
        int id = file.readInt();
        String name = readFixedString(file, NAME_SIZE);
        String phone = readFixedString(file, PHONE_SIZE);
        String street = readFixedString(file, STREET_SIZE);
        String locality = readFixedString(file, LOCALITY_SIZE);
        String city = readFixedString(file, CITY_SIZE);
        String state = readFixedString(file, STATE_SIZE);
        int pincode = file.readInt();
        String country = readFixedString(file, COUNTRY_SIZE);

        return new AddressBook(id, name, phone, street, locality, city, state, pincode, country);
    }

    private static String readFixedString(RandomAccessFile file, int length) throws IOException {
        byte[] bytes = new byte[length];
        file.readFully(bytes);
        return new String(bytes).trim(); // Trim extra spaces
    }

    @Override
    public String toString() {
        return id + ", " + name.trim() + ", " + phone.trim() + ", " + street.trim() + ", " + locality.trim() + ", " + city.trim() + ", " + state.trim() + ", " + pincode + ", " + country.trim();
    }
}
