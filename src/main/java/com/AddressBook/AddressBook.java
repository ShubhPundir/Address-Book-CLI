package com.AddressBook;

import com.config.ConfigReader;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class AddressBook {
    public int id;
    public String name;
    public String phone;
    public String street;
    public String locality;
    public String city;
    public String state;
    public int pincode;
    public String country;

    public static final ConfigReader conf = new ConfigReader();
    public static final int NAME_SIZE = conf.getFieldSize("Name");
    public static final int PHONE_SIZE = conf.getFieldSize("Phone");
    public static final int STREET_SIZE = conf.getFieldSize("Street");
    public static final int LOCALITY_SIZE = conf.getFieldSize("Locality");
    public static final int CITY_SIZE = conf.getFieldSize("City");
    public static final int STATE_SIZE = conf.getFieldSize("State");
    public static final int COUNTRY_SIZE = conf.getFieldSize("Country");

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

    String padString(String input, int length) {
        if (input == null) input = "";
        if (input.length() > length) {
            return input.substring(0, length);  // Ensure fixed-size
        }
        return String.format("%-" + length + "s", input); // Left-align and pad
    }

    public void writeToFile(RandomAccessFile file) throws IOException {
        file.writeInt(id);
        file.write(name.getBytes(StandardCharsets.UTF_8), 0, NAME_SIZE);
        file.write(phone.getBytes(StandardCharsets.UTF_8), 0, PHONE_SIZE);
        file.write(street.getBytes(StandardCharsets.UTF_8), 0, STREET_SIZE);
        file.write(locality.getBytes(StandardCharsets.UTF_8), 0, LOCALITY_SIZE);
        file.write(city.getBytes(StandardCharsets.UTF_8), 0, CITY_SIZE);
        file.write(state.getBytes(StandardCharsets.UTF_8), 0, STATE_SIZE);
        file.writeInt(pincode);
        file.write(country.getBytes(StandardCharsets.UTF_8), 0, COUNTRY_SIZE);
    }

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
        return new String(bytes, StandardCharsets.UTF_8).trim();
    }

    @Override
    public String toString() {
        return id + ", " + name.trim() + ", " + phone.trim() + ", " + street.trim() + ", " + locality.trim() + ", " + city.trim() + ", " + state.trim() + ", " + pincode + ", " + country.trim();
    }

    public static int getRecordSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRecordSize'");
    }

    public Object getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    public String getStreet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStreet'");
    }

    public String getPhone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhone'");
    }

    public String getLocality() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLocality'");
    }

    public String getCity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCity'");
    }

    public Object getPincode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPincode'");
    }

    public String getState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    public String getCountry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCountry'");
    }
}
