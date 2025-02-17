package com.config;

import com.config.ConfigReader;

public class ConfigReaderTest {
    public static void main(String[] args) {
        ConfigReader.loadConfig();
        
        System.out.println("ID size: " + ConfigReader.getFieldSize("ID"));
        System.out.println("Name size: " + ConfigReader.getFieldSize("Name"));
        System.out.println("Phone size: " + ConfigReader.getFieldSize("Phone"));
        System.out.println("Total size: " + ConfigReader.getFieldSize("Total"));
    }
}