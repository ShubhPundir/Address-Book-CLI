package com.config;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class ConfigReader {
    private Map<String, Integer> fieldSizes = new LinkedHashMap<>(); // Use LinkedHashMap to preserve field order
    private boolean configLoaded = false;

    // Constructor to load the config from a specific database
    public ConfigReader(String database) {
        loadConfig(System.getProperty("user.dir") + File.separator + "data" + File.separator + database + File.separator + "config.xml");
    }

    // Default constructor for AddressRecords database
    public ConfigReader() {
        this("AddressRecords");
    }

    // Load the configuration from XML
    private void loadConfig(String filePath) {
        try {
            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) {
                throw new RuntimeException("Config file not found: " + filePath);
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("field");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getAttribute("name");
                    int size = Integer.parseInt(element.getAttribute("size"));
                    fieldSizes.put(name, size);
                }
            }
            configLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading XML config!");
        }
    }

    public boolean isConfigLoaded() {
        return configLoaded;
    }

    public Map<String, Integer> getFields() {
        return fieldSizes;
    }

    public int getFieldSize(String fieldName) {
        return fieldSizes.getOrDefault(fieldName, -1);
    }

    public int getRecordSize() {
        return fieldSizes.values().stream().mapToInt(Integer::intValue).sum();
    }

    public List<String> getFieldNames() {
        return new ArrayList<>(fieldSizes.keySet());
    }
}
