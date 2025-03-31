package com.dataGenerator.csvGenerator;

import com.github.javafaker.Faker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class AddressBookGenerator {
    public static void main(String[] args) {
        // Define directory and base file path
        String directoryPath = System.getProperty("user.dir") + "/data";
        String csvBaseFileName = directoryPath + "/address_book";
        int numRecords = 100000; // Number of records per file
        int ID = 0; // Start ID from 0
        
        // Create file object for CSV directory
        File csvDir = new File(directoryPath);
        // System.out.println(csvDir);
        // System.exit(0);

        // Check if CSV directory exists and is not empty
        if (csvDir.exists() && csvDir.isDirectory() && csvDir.list().length > 0) {
            System.out.println("Directory is not empty. Aborting File. Clear the contents of the folder to carry on the script TEEE HEEE :)");
            System.exit(0);
        }

        // Initialize Faker with Indian locale
        Faker faker = new Faker(new Locale.Builder().setLanguage("en").setRegion("IN").build());


        // Generate 10 CSV files
        for (int i = 0; i < 10; i++) {
            String fileName = csvBaseFileName + "-" + i + ".csv";

            try (FileWriter writer = new FileWriter(fileName)) {
                // Write CSV header
                writer.append("ID,Name,Phone,Street,Locality,City,State,Pincode,Country\n");

                // Generate fake data
                for (int j = 0; j < numRecords; j++) {
                    writer.append(ID + ",");
                    writer.append(faker.name().fullName() + ",");
                    writer.append(faker.phoneNumber().cellPhone() + ",");
                    writer.append(faker.address().streetAddress() + ",");
                    writer.append(faker.address().cityName() + ","); // Locality substitute
                    writer.append(faker.address().city() + ",");
                    writer.append(faker.address().state() + ",");
                    writer.append(faker.address().zipCode() + ",");
                    writer.append("India\n");

                    ID++; // Increment ID
                }

                System.out.println("CSV file '" + fileName + "' generated successfully with " + numRecords + " records.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
