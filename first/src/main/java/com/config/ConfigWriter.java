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
import java.util.LinkedHashMap;

public class ConfigWriter {

    // Store field names, sizes, and types in LinkedHashMap to maintain the insertion order
    private final Map<String, FieldInfo> fieldConfig = new LinkedHashMap<>();

    // Method to add a field to the fieldConfig map
    public void addField(String name, int size, String dtype) {
        // Validate data type before adding the field
        if (!isValidDtype(dtype)) {
            System.out.println("Invalid data type for field " + name + ". It must be one of: INT, FLOAT, STRING.");
            return;
        }
        fieldConfig.put(name, new FieldInfo(size, dtype));
    }

    // Validates the data type input
    public static boolean isValidDtype(String dtype) {
        return dtype.equalsIgnoreCase("INT") ||
               dtype.equalsIgnoreCase("FLOAT") ||
               dtype.equalsIgnoreCase("STRING");
    }

    // Method to write the configuration to an XML file
    public void writeToFile(String database) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Root element for the XML document
            Element root = doc.createElement("schema");
            doc.appendChild(root);

            // Add each field as a "field" element in the XML
            for (Map.Entry<String, FieldInfo> entry : fieldConfig.entrySet()) {
                Element field = doc.createElement("field");
                field.setAttribute("name", entry.getKey());
                field.setAttribute("size", String.valueOf(entry.getValue().getSize()));
                field.setAttribute("dtype", entry.getValue().getDtype().toUpperCase());
                root.appendChild(field);
            }

            // Prepare the XML document for output
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + database + File.separator + "config.xml";
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Configuration written successfully to: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error writing configuration file.");
        }
    }

}
