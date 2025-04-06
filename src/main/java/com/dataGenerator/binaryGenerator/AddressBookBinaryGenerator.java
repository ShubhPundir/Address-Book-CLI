package com.dataGenerator.binaryGenerator;

import com.config.ConfigReader;
import com.github.javafaker.Faker;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class AddressBookBinaryGenerator {
    // private static int RECORD_SIZE = 0;

    public static void main(String[] args) {
        // Load configuration
        ConfigReader conf = new ConfigReader();

        // Compute total record size
        // RECORD_SIZE = conf.getFieldSize("ID") +
        //               conf.getFieldSize("Name") +
        //               conf.getFieldSize("Phone") +
        //               conf.getFieldSize("Street") +
        //               conf.getFieldSize("Locality") +
        //               conf.getFieldSize("City") +
        //               conf.getFieldSize("State") +
        //               conf.getFieldSize("Pincode") +
        //               conf.getFieldSize("Country");

        // Define directory and file path
        String directoryPath = System.getProperty("user.dir") + "/data/binary";
        String binaryBaseFileName = directoryPath + "/address_book";

        // Create directory if not exists
        File binaryDir = new File(directoryPath);
        if (binaryDir.exists() && binaryDir.isDirectory() && binaryDir.list().length > 0) {
            System.out.println("Directory is not empty. Aborting File. Clear the folder to continue.");
            System.exit(0);
        }

        // Initialize Faker with Indian locale
        Faker faker = new Faker(new Locale.Builder().setLanguage("en").setRegion("IN").build());

        // Number of records per file
        int numRecords = 100_000;
        int num_files = 10;
        int ID = 0; // Start ID from 0

        // Generate 10 Binary files
        for (int i = 0; i < num_files; i++) {
            String fileName = binaryBaseFileName + "-" + i + ".dat";

            try (RandomAccessFile file = new RandomAccessFile(fileName, "rw")) {
                // Generate fake data
                for (int j = 0; j < numRecords; j++) {
                    file.writeInt(ID); // Write ID
                    writeFixedString(file, faker.name().fullName(), conf.getFieldSize("Name"));
                    writeFixedString(file, faker.phoneNumber().cellPhone(), conf.getFieldSize("Phone"));
                    writeFixedString(file, faker.address().streetAddress(), conf.getFieldSize("Street"));
                    writeFixedString(file, faker.address().cityName(), conf.getFieldSize("Locality"));
                    writeFixedString(file, faker.address().city(), conf.getFieldSize("City"));
                    writeFixedString(file, faker.address().state(), conf.getFieldSize("State"));
                    
                    String pincode = faker.address().zipCode().replaceAll("[^0-9]", ""); // Remove non-numeric chars
                    int parsedPincode = pincode.isEmpty() ? 0 : Integer.parseInt(pincode); // Handle empty case
                    file.writeInt(parsedPincode);
                    // file.writeInt(Integer.parseInt(faker.address().zipCode())); // Write Pincode
                    
                    writeFixedString(file, "India", conf.getFieldSize("Country"));

                    ++ID; // Increment ID
                }

                System.out.println("Binary file '" + fileName + "' generated successfully with " + numRecords + " records.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Utility function to write a fixed-size string to the file
    private static void writeFixedString(RandomAccessFile file, String data, int length) throws IOException {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > length) {
            file.write(bytes, 0, length); // Truncate if too long
        } else {
            file.write(bytes); // Write actual data
            file.write(new byte[length - bytes.length]); // Pad with null bytes
        }
    }
}
