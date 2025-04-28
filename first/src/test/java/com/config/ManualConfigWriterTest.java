package com.config;

public class ManualConfigWriterTest {
    public static void main(String[] args) {
        ConfigWriter writer = new ConfigWriter();

        // Add some fields manually
        writer.addField("Age", 4, "INT");
        writer.addField("Name", 20, "STRING");
        writer.addField("Salary", 10, "FLOAT");
        writer.addField("DepartmentID", 4, "INT");

        // Define the output file path
        String outputPath = System.getProperty("user.dir") + "/data/Employee/config.xml";

        // Write the fields to the XML file
        writer.writeToFile(outputPath);

        System.out.println("Testing complete. Config written to: " + outputPath);
    }
}
