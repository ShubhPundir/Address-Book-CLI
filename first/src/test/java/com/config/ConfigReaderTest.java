package com.config;

public class ConfigReaderTest {
    public static void main(String[] args) {
        ConfigReader conf = new ConfigReader();
        
        System.out.println("ID size: " + conf.getFieldSize("ID"));
        System.out.println("Name size: " + conf.getFieldSize("Name"));
        System.out.println("Phone size: " + conf.getFieldSize("Phone"));
        System.out.println("Total size: " + conf.getFieldSize("Total"));
    }
}