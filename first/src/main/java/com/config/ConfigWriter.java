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

    private final Map<String, Integer> fieldConfig = new LinkedHashMap<>();

    public void addField(String name, int size) {
        fieldConfig.put(name, size);
    }

    public void writeToFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();
            Element root = doc.createElement("record");
            doc.appendChild(root);

            for (Map.Entry<String, Integer> entry : fieldConfig.entrySet()) {
                Element field = doc.createElement("field");
                field.setAttribute("name", entry.getKey());
                field.setAttribute("size", String.valueOf(entry.getValue()));
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
            writer.addField(name, size);
        }
        scanner.close();
        
        String defaultPath = System.getProperty("user.dir") + "/config.xml";
        writer.writeToFile(defaultPath);
    }
}
