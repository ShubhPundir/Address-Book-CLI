package com.find.addressbook;

public class AddressBookRecord {
    String id;
    String name;
    String phone;
    String street;
    String locality;
    String city;
    String state;
    String pincode;
    String country;

    public AddressBookRecord(String id, String name, String phone, String street, String locality,
            String city, String state, String pincode, String country) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.locality = locality;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Phone: " + phone + ", Address: " + street + ", " + locality + ", "
                + city + ", " + state + " - " + pincode + ", " + country;
    }

}
