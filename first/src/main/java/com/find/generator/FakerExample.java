package com.find.generator;

import com.github.javafaker.Faker;

public class FakerExample {
    public static void main(String[] args) {
        Faker faker = new Faker();

        System.out.println("Name: " + faker.name().fullName());
        System.out.println("Address: " + faker.address().fullAddress());
        System.out.println("Phone Number: " + faker.phoneNumber().cellPhone());
        System.out.println("Company: " + faker.company().name());
    }
}
