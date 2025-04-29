package com.BinaryFileHandler;

import com.config.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class BinaryFileHandler {

    private final File binaryFile;
    private final ConfigReader configReader;
    private final int recordSize;


    public BinaryFileHandler(String dbName, String filename) {
        String basePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + dbName + File.separator + "binary";
        binaryFile = new File(basePath + File.separator +  filename); // currently points to one particular binary-file
        // NOTE: to change this fixed pointer to slider in the directory once mulitple files are established

        if (!binaryFile.getParentFile().exists()) {
            binaryFile.getParentFile().mkdirs(); // Ensure directory exists
        }
        configReader = new ConfigReader(dbName);
        this.recordSize = configReader.getRecordSize();
    }
    
    public BinaryFileHandler(String dbName) {
        String basePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + dbName + File.separator + "binary";
        binaryFile = new File(basePath + File.separator +  "records.data"); // currently points to one particular binary-file
        // NOTE: to change this fixed pointer to slider in the directory once mulitple files are established

        if (!binaryFile.getParentFile().exists()) {
            binaryFile.getParentFile().mkdirs(); // Ensure directory exists
        }
        configReader = new ConfigReader(dbName);
        this.recordSize = configReader.getRecordSize();
    }

    public void writeRecord(BinaryRecord record) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(binaryFile, "rw")) {
            raf.seek(raf.length()); // Go to end for append
            raf.write(record.toByteArray());
        }
    }

    public List<BinaryRecord> readAllRecords() throws IOException {
        List<BinaryRecord> records = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(binaryFile, "r")) {
            long numRecords = raf.length() / recordSize;
            for (int i = 0; i < numRecords; i++) {
                byte[] buffer = new byte[recordSize];
                raf.readFully(buffer);
                records.add(BinaryRecord.fromByteArray(buffer, configReader));
            }
        }
        return records;
    }

    public BinaryRecord readRecordAt(int index) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(binaryFile, "r")) {
            raf.seek((long) index * recordSize);
            byte[] buffer = new byte[recordSize];
            raf.readFully(buffer);
            return BinaryRecord.fromByteArray(buffer, configReader);
        }
    }

    public void updateRecordAt(int index, BinaryRecord newRecord) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(binaryFile, "rw")) {
            raf.seek((long) index * recordSize);
            raf.write(newRecord.toByteArray());
        }
    }

    public void deleteRecordAt(int index) throws IOException {
        File tempFile = new File(binaryFile.getParent(), "temp.data");
        try (RandomAccessFile raf = new RandomAccessFile(binaryFile, "r");
             RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rw")) {

            long numRecords = raf.length() / recordSize;
            for (int i = 0; i < numRecords; i++) {
                byte[] buffer = new byte[recordSize];
                raf.readFully(buffer);
                if (i != index) {
                    tempRaf.write(buffer);
                }
            }
        }

        if (binaryFile.delete() && tempFile.renameTo(binaryFile)) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Failed to delete record.");
        }
    }
}
