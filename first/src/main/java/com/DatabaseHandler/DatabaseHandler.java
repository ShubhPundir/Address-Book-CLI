package com.DatabaseHandler;

import com.config.ConfigReader;
import com.config.ConfigWriter;
import com.config.FieldInfo;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class DatabaseHandler {

    private static final String BASE_PATH = System.getProperty("user.dir") + File.separator + "data";

    // 1. List all directories (databases) under /data
    public void listDatabases() {
        File baseDir = new File(BASE_PATH);
        File[] dirs = baseDir.listFiles(File::isDirectory);

        System.out.println("Available Databases:");
        if (dirs != null && dirs.length > 0) {
            for (File dir : dirs) {
                System.out.println("- " + dir.getName());
            }
        } else {
            System.out.println("No databases found.");
        }
    }

    // 2. Load config.xml from chosen database and display schema
    

    public void showDatabaseSchema(String dbName) {
        try {
            // Properly create the full path
            ConfigReader reader = new ConfigReader(dbName);
            LinkedHashMap<String, FieldInfo> schema = reader.getFieldInfoMap();

            System.out.println("=== Schema for database: " + dbName + " ===");
            schema.forEach((field, info) ->
                System.out.printf("Field: %-15s Size: %-5d Type: %-7s%n",
                        field, info.getSize(), info.getDtype().toUpperCase()));
        } catch (Exception e) {
            System.out.println("Failed to read schema for database '" + dbName + "'.");
        }
    }
    

    // 3. Create new database directory and write config.xml
    public void createNewDatabase(String dbName) {
        Scanner scanner = new Scanner(System.in);
        String dbPath = BASE_PATH + File.separator + dbName;
        File newDir = new File(dbPath);

        if (newDir.exists()) {
            System.out.println("Database already exists.");
            scanner.close();
            return;
        }

        if (newDir.mkdirs()) {
            ConfigWriter writer = new ConfigWriter();
            System.out.println("Creating schema for database: " + dbName);

            while (true) {
                System.out.print("Enter field name (or 'done'): ");
                String name = scanner.nextLine();
                if ("done".equalsIgnoreCase(name)) break;

                System.out.print("Enter size: ");
                int size = Integer.parseInt(scanner.nextLine());

                String dtype;
                while (true) {
                    System.out.print("Enter dtype (INT, FLOAT, STRING): ");
                    dtype = scanner.nextLine();
                    if (ConfigWriter.isValidDtype(dtype)) break;
                    System.out.println("Invalid data type.");
                }

                writer.addField(name, size, dtype.toUpperCase());
            }

            writer.writeToFile(dbName);
            System.out.println("Database '" + dbName + "' created successfully.");
        } else {
            System.out.println("Failed to create database directory.");
        }
        scanner.close();
    }

}
