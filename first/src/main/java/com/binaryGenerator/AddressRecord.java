package com.binaryGenerator;

import java.io.*;

public class AddressRecord {
    private int id;
    private String name;
    private String phone;
    private String street;
    private String locality;
    private String city;
    private String state;
    private int pincode;
    private String country;

    // Constructor
    public AddressRecord(int id, String name, String phone, String street, String locality, 
                         String city, String state, int pincode, String country) {
        this.id = id;
        this.name = padString(name, 50);
        this.phone = padString(phone, 15);
        this.street = padString(street, 50);
        this.locality = padString(locality, 50);
        this.city = padString(city, 30);
        this.state = padString(state, 30);
        this.pincode = pincode;
        this.country = padString(country, 30);
    }

    // Padding strings to fixed length
    private String padString(String input, int length) {
        return String.format("%-" + length + "s", input).substring(0, length);
    }
    // If input is shorter, it fills the remaining space with spaces (" ").
    // If longer, it truncates the string to the exact length.
    // Ensures that if the string is longer than length, it gets truncated to the exact length

    // Writing the record to a binary file
    public void writeToFile(RandomAccessFile file) throws IOException {
        // all of it writes in binary format
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
    public static AddressRecord readFromFile(RandomAccessFile file) throws IOException {
        int id = file.readInt();
        String name = readFixedString(file, 50);
        String phone = readFixedString(file, 15);
        String street = readFixedString(file, 50);
        String locality = readFixedString(file, 50);
        String city = readFixedString(file, 30);
        String state = readFixedString(file, 30);
        int pincode = file.readInt();
        String country = readFixedString(file, 30);

        return new AddressRecord(id, name, phone, street, locality, city, state, pincode, country);
    }

    private static String readFixedString(RandomAccessFile file, int length) throws IOException {
        byte[] bytes = new byte[length];
        file.readFully(bytes);
        return new String(bytes).trim(); // Trim extra spaces
    }

    @Override
    public String toString() {
        return id + ", " + name.trim() + ", " + phone.trim() + ", " + street.trim() + ", " + 
               locality.trim() + ", " + city.trim() + ", " + state.trim() + ", " + pincode + ", " + country.trim();
    }
}
