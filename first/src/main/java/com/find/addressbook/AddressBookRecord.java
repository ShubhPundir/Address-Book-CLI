package com.find.addressbook;

public class AddressBookRecord {
    String id;
    private String name;
    private String phone;
    private String street;
    private String locality;
    private String city;
    private String state;
    private String pincode;
    private String country;

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

    // Add Getter Methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Phone: " + phone + ", Address: " + street + ", " + locality + ", "
                + city + ", " + state + " - " + pincode + ", " + country;
    }
}
