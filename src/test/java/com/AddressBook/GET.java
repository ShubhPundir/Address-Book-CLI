package com.AddressBook;

public class GET {
    public static void main(String[] args) {
        AddressBookStorage storage = new AddressBookStorage("data/binary/address_book-0.dat");
        System.out.println("Printing first Record");

        try {
            AddressBook record = storage.getRecord(266);
            System.out.println(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
