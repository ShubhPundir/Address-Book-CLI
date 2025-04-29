package com.BPlusTree;

import com.BinaryFileHandler.BinaryFileHandler;
import com.BinaryFileHandler.BinaryRecord;
import com.config.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BPlusTreeHandler {
    private final BPlusTree tree;
    private final BinaryFileHandler binaryFileHandler;

    public BPlusTreeHandler(String dbName, int degree) throws IOException {
        this.tree = new BPlusTree(degree);
        this.binaryFileHandler = new BinaryFileHandler(dbName);
        buildIndex();
    }

    private void buildIndex() throws IOException {
        List<BinaryRecord> records = binaryFileHandler.readAllRecords();
        List<RecordEntry> entries = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            BinaryRecord record = records.get(i);
            int id = Integer.parseInt(record.getField("id"));  // assumes "id" is the field name for primary key
            long offset = (long) i * binaryFileHandler.getRecordSize();
            entries.add(new RecordEntry(id, offset));
        }

        tree.bulkLoad(entries);
    }

    public Long searchOffsetById(int id) {
        return tree.search(id);
    }

    public void printTree() {
        tree.printTree();
    }

    public void printLeaves() {
        tree.printLeaves();
    }

    public void printMetrics() {
        tree.printMetrics();
    }

    public void saveTree(String path) throws IOException {
        tree.saveToFile(path);
    }

    public static BPlusTree loadTree(String path) throws IOException, ClassNotFoundException {
        return BPlusTree.loadFromFile(path);
    }

    public BPlusTree getTree() {
        return tree;
    }
    
    public int getRecordSize() {
        return this.recordSize;
    }
    
}
