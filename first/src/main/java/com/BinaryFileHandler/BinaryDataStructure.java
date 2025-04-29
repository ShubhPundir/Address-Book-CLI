package com.BinaryFileHandler;

import com.config.ConfigReader;

import java.io.IOException;
import java.io.RandomAccessFile;
// import java.io.;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class BinaryDataStructure {
    @SuppressWarnings("unused")
    private final ConfigReader config;
    private final LinkedHashMap<String, Integer> fieldSizes;
    private final LinkedHashMap<String, String> fields;

    public BinaryDataStructure(ConfigReader config, Map<String, String> inputFields) {
        this.config = config;
        this.fieldSizes = config.getFieldSizes();
        this.fields = new LinkedHashMap<>();

        for (String key : fieldSizes.keySet()) {
            String value = inputFields.getOrDefault(key, "");
            this.fields.put(key, padString(value, fieldSizes.get(key)));
        }
    }

    // Padding helper
    private String padString(String input, int length) {
        if (input == null) input = "";
        if (input.length() > length) {
            return input.substring(0, length);
        }
        return String.format("%-" + length + "s", input);
    }

    // Write one record to file
    public void writeToFile(RandomAccessFile file) throws IOException {
        for (Map.Entry<String, Integer> entry : fieldSizes.entrySet()) {
            String value = fields.get(entry.getKey());
            file.write(value.getBytes(StandardCharsets.UTF_8), 0, entry.getValue());
        }
    }

    // Read one record from file
    public static BinaryDataStructure readFromFile(RandomAccessFile file, ConfigReader config) throws IOException {
        LinkedHashMap<String, Integer> fieldSizes = config.getFieldSizes();
        LinkedHashMap<String, String> readFields = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : fieldSizes.entrySet()) {
            String value = readFixedString(file, entry.getValue());
            readFields.put(entry.getKey(), value.trim());
        }

        return new BinaryDataStructure(config, readFields);
    }

    private static String readFixedString(RandomAccessFile file, int length) throws IOException {
        byte[] bytes = new byte[length];
        file.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // Getters
    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String value : fields.values()) {
            sb.append(value.trim()).append(", ");
        }
        if (!fields.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove last comma and space
        }
        return sb.toString();
    }
}
