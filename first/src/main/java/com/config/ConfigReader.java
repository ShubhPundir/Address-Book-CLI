package com.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private static final String CONFIG_FILE = System.getProperty("user.dir") + "/config.xml";
    private static Map<String, Integer> fieldSizes = new HashMap<>();

    public ConfigReader() {
        try {
            File xmlFile = new File(CONFIG_FILE);
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading XML config!");
        }
    }

    public int getFieldSize(String fieldName) {
        return fieldSizes.getOrDefault(fieldName, -1);
    }

    public int getRecordSize() {
        int totalSize = 0;
        for (int size : fieldSizes.values()) {
            totalSize += size;
        }
        return totalSize;
    }

    public Map<String, Integer> getFields(){
        return fieldSizes;
    }
}
