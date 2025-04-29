package com.config;

public class WriteConfigTest {

    public static void main(String[] args) {
        // Create a ConfigWriter instance
        ConfigWriter writer = new ConfigWriter();

        // Add some test fields
        writer.addField("Id", 4, "INT");
        writer.addField("Name", 30, "STRING");
        writer.addField("Phone", 15, "STRING");
        writer.addField("Pincode", 6, "INT");

        // Write the configuration to the file
        String database = "Normal";
        writer.writeToFile(database);

        System.out.println("Config file was successfully created at: " + database);
    }
}
