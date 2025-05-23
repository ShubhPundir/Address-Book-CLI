package com.config;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class ConfigReader {
    private final LinkedHashMap<String, FieldInfo> fieldInfoMap = new LinkedHashMap<>();
    private boolean configLoaded = false;

    // Constructor to load from specific database
    public ConfigReader(String database) {
        loadConfig(System.getProperty("user.dir") + File.separator + "data" + File.separator + database + File.separator + "config.xml");
    }

    // Default constructor
    public ConfigReader() {
        this("AddressRecords");
    }

    // Load the configuration
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
                    String dtype = element.getAttribute("dtype");

                    FieldInfo fieldInfo = new FieldInfo(size, dtype);
                    fieldInfoMap.put(name, fieldInfo);
                }
            }
            configLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading XML config!");
        }
    }

    // Public Getters
    public boolean isConfigLoaded() {
        return configLoaded;
    }

    public LinkedHashMap<String, FieldInfo> getFieldInfoMap() {
        return fieldInfoMap;
    }

    public int getFieldSize(String fieldName) {
        FieldInfo info = fieldInfoMap.get(fieldName);
        return info != null ? info.getSize() : -1;
    }

    public String getFieldDtype(String fieldName) {
        FieldInfo info = fieldInfoMap.get(fieldName);
        return info != null ? info.getDtype() : null;
    }

    public int getRecordSize() {
        return fieldInfoMap.values().stream().mapToInt(FieldInfo::getSize).sum();
    }

    public List<String> getFieldNames() {
        return new ArrayList<>(fieldInfoMap.keySet());
    }
}
