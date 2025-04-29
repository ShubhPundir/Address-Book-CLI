package com.DatabaseHandler;

public class DatabaseHandlerTest {
    public static void main(String[] args) {
        DatabaseHandler handler = new DatabaseHandler();

        // 1. List all databases
        System.out.println("=== Listing All Databases ===");
        handler.listDatabases();

        // 2. Show schema of an existing database (e.g., "Normal")
        System.out.println("\n=== Showing Schema for 'Normal' Database ===");
        handler.showDatabaseSchema("Normal");

        // 3. Create a new database with hardcoded fields
        System.out.println("\n=== Creating New Database 'TestDB' ===");
        handler.createNewDatabase("TestDB");
    }
}
