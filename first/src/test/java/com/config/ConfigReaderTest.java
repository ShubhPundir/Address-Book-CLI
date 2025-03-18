package com.config;

import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigReaderTest {
    private ConfigReader configReader;
    private static final String TEST_XML_FILE = "test_config.xml";

    @Before
    public void setUp() throws Exception {
        createTestXML();
        configReader = new ConfigReader(TEST_XML_FILE);
    }

    private void createTestXML() throws Exception {
        File file = new File(TEST_XML_FILE);
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<config>");
        writer.println("    <field name=\"username\" size=\"10\"/>");
        writer.println("    <field name=\"password\" size=\"15\"/>");
        writer.println("    <field name=\"email\" size=\"25\"/>");
        writer.println("</config>");
        writer.close();
    }

    @Test
    public void testFieldSizesLoaded() {
        Map<String, Integer> fields = configReader.getFields();
        assertFalse("Field sizes should be loaded from XML.", fields.isEmpty());
    }

    @Test
    public void testGetFieldSize_ValidField() {
        int size = configReader.getFieldSize("username");
        assertEquals("Size should match XML value for 'username'.", 10, size);
    }

    @Test
    public void testGetFieldSize_InvalidField() {
        int size = configReader.getFieldSize("invalidField");
        assertEquals("Invalid field should return -1.", -1, size);
    }

    @Test
    public void testGetRecordSize() {
        int totalSize = configReader.getRecordSize();
        assertEquals("Total record size should be sum of all field sizes.", 10 + 15 + 25, totalSize);
    }
}
