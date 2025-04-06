package com.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigReaderTest {
    private ConfigReader configReader;

    private static final String TEST_XML_FILE = "data/config/test_config.xml";
    private static final String TEST_DAT_FILE = "data/config/records.dat";

    @Before
    public void setUp() throws Exception {
        createTestXML();
        configReader = new ConfigReader(TEST_XML_FILE);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(TEST_XML_FILE));
        Files.deleteIfExists(Path.of(TEST_DAT_FILE));
    }

    private void createTestXML() throws Exception {
        File file = new File(TEST_XML_FILE);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<config>");
            writer.println("    <field name=\"username\" size=\"10\"/>");
            writer.println("    <field name=\"password\" size=\"15\"/>");
            writer.println("    <field name=\"email\" size=\"25\"/>");
            writer.println("</config>");
        }
    }

    @Test
    public void shouldLoadFieldSizesFromXML() {
        Map<String, Integer> fields = configReader.getFields();
        assertFalse("Field sizes should be loaded from XML.", fields.isEmpty());
    }

    @Test
    public void shouldReturnCorrectSizeForValidField() {
        assertEquals("Size should match XML value for 'username'.", 10, configReader.getFieldSize("username"));
    }

    @Test
    public void shouldReturnNegativeOneForInvalidField() {
        assertEquals("Invalid field should return -1.", -1, configReader.getFieldSize("invalidField"));
    }

    @Test
    public void shouldCalculateTotalRecordSizeCorrectly() {
        int expectedTotalSize = 10 + 15 + 25;
        assertEquals("Total record size should be sum of all field sizes.", expectedTotalSize, configReader.getRecordSize());
    }

    @Test
    public void shouldValidateBinaryFileHasConstantRecordSize() throws Exception {
        // Ensure the binary file exists
        File datFile = new File(TEST_DAT_FILE);
        datFile.getParentFile().mkdirs();
        if (!datFile.exists()) {
            Files.createFile(datFile.toPath());
        }

        // Write dummy record
        try (FileWriter writer = new FileWriter(datFile)) {
            byte[] record = new byte[configReader.getRecordSize()];
            writer.write(new String(record));
        }

        long fileSize = Files.size(datFile.toPath());
        int recordSize = configReader.getRecordSize();

        assertTrue("Binary file should have constant record size.", fileSize % recordSize == 0);
    }
}
