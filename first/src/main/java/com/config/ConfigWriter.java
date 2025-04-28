package com.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.LinkedHashMap;

public class ConfigWriter {

    private static class FieldInfo {
        int size;
        String dtype;

        FieldInfo(int size, String dtype) {
            this.size = size;
            this.dtype = dtype;
        }
    }

    private final Map<String, FieldInfo> fieldConfig = new LinkedHashMap<>();

    public void addField(String name, int size, String dtype) {
        fieldConfig.put(name, new FieldInfo(size, dtype));
    }

    private static boolean isValidDtype(String dtype) {
        return dtype.equalsIgnoreCase("INT") ||
               dtype.equalsIgnoreCase("FLOAT") ||
               dtype.equalsIgnoreCase("STRING");
    }

    public void writeToFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();
            Element root = doc.createElement("record");
            doc.appendChild(root);

            for (Map.Entry<String, FieldInfo> entry : fieldConfig.entrySet()) {
                Element field = doc.createElement("field");
                field.setAttribute("name", entry.getKey());
                field.setAttribute("size", String.valueOf(entry.getValue().size));
                field.setAttribute("dtype", entry.getValue().dtype.toUpperCase());
                root.appendChild(field);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Configuration written successfully to: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error writing configuration file.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConfigWriter writer = new ConfigWriter();

        System.out.println("Enter fields for your database schema.");
        while (true) {
            System.out.print("Field name (or type 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            System.out.print("Field size (integer): ");
            int size = Integer.parseInt(scanner.nextLine());

            String dtype;
            while (true) {
                System.out.print("Field data type (INT, FLOAT, STRING): ");
                dtype = scanner.nextLine();
                if (isValidDtype(dtype)) {
                    break;
                } else {
                    System.out.println("Invalid data type! Only INT, FLOAT, STRING are allowed.");
                }
            }

            writer.addField(name, size, dtype.toUpperCase());
        }
        scanner.close();

        String defaultPath = System.getProperty("user.dir") + "/config.xml";
        writer.writeToFile(defaultPath);
    }
}
