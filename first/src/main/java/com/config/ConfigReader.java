package com.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private Map<String, Integer> fieldSizes = new HashMap<>();
    private boolean configLoaded = false; 

    public ConfigReader(String filePath) {
        loadConfig(filePath);
    }

    public ConfigReader() {
        this(System.getProperty("user.dir") + "/config.xml");
    }

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

    public boolean isConfigLoaded(){
        return configLoaded;
    }

    public int getFieldSize(String fieldName) {
        return fieldSizes.getOrDefault(fieldName, -1);
    }

    public int getRecordSize() {
        return fieldSizes.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<String, Integer> getFields() {
        return fieldSizes;
    }
}
