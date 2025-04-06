package com.Indexing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

class BPlusTree implements Serializable {
    private BPlusTreeNode root;
    private final int order;
    
    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusTreeNode(true); // Start with a leaf node
    }
    
    public void insert(int key, long offset) {
        BPlusTreeNode splitNode = insertRecursive(root, key, offset);
        if (splitNode != null) {
            // Create a new root if split occurred
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.keys.add(splitNode.keys.get(0));
            newRoot.children.add(root);
            newRoot.children.add(splitNode);
            root = newRoot;
        }
    }
    
    private BPlusTreeNode insertRecursive(BPlusTreeNode node, int key, long offset) {
        if (node.isLeaf) {
            int pos = Collections.binarySearch(node.keys, key);
            if (pos < 0) pos = -(pos + 1);
            node.keys.add(pos, key);
            node.values.add(pos, offset);
            if (node.keys.size() < order) return null;
            return splitLeaf(node);
        }
        
        int pos = Collections.binarySearch(node.keys, key);
        if (pos < 0) pos = -(pos + 1);
        BPlusTreeNode splitNode = insertRecursive(node.children.get(pos), key, offset);
        
        if (splitNode != null) {
            node.keys.add(pos, splitNode.keys.get(0));
            node.children.add(pos + 1, splitNode);
            if (node.keys.size() < order) return null;
            return splitInternal(node);
        }
        return null;
    }
    
    private BPlusTreeNode splitLeaf(BPlusTreeNode node) {
        int mid = node.keys.size() / 2;
        BPlusTreeNode newLeaf = new BPlusTreeNode(true);
        newLeaf.keys.addAll(node.keys.subList(mid, node.keys.size()));
        newLeaf.values.addAll(node.values.subList(mid, node.values.size()));
        node.keys.subList(mid, node.keys.size()).clear();
        node.values.subList(mid, node.values.size()).clear();
        newLeaf.next = node.next;
        node.next = newLeaf;
        return newLeaf;
    }
    
    private BPlusTreeNode splitInternal(BPlusTreeNode node) {
        int mid = node.keys.size() / 2;
        BPlusTreeNode newInternal = new BPlusTreeNode(false);
        newInternal.keys.addAll(node.keys.subList(mid + 1, node.keys.size()));
        newInternal.children.addAll(node.children.subList(mid + 1, node.children.size()));
        node.keys.subList(mid, node.keys.size()).clear();
        node.children.subList(mid + 1, node.children.size()).clear();
        return newInternal;
    }
    
    public Long search(int key) {
        BPlusTreeNode node = root;
        while (!node.isLeaf) {
            int pos = Collections.binarySearch(node.keys, key);
            if (pos < 0) pos = -(pos + 1);
            node = node.children.get(pos);
        }
        int pos = Collections.binarySearch(node.keys, key);
        return (pos >= 0) ? node.values.get(pos) : null;
    }
    
    public void delete(int key) {
        deleteRecursive(root, key);
    }
    
    private void deleteRecursive(BPlusTreeNode node, int key) {
        if (node.isLeaf) {
            int pos = Collections.binarySearch(node.keys, key);
            if (pos >= 0) {
                node.keys.remove(pos);
                node.values.remove(pos);
            }
            return;
        }
        int pos = Collections.binarySearch(node.keys, key);
        if (pos < 0) pos = -(pos + 1);
        deleteRecursive(node.children.get(pos), key);
    }
    
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }
    
    public static BPlusTree loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (BPlusTree) ois.readObject();
        }
    }
    
    public void bulkInsert(Map<Integer, Long> records) {
        for (Map.Entry<Integer, Long> entry : records.entrySet()) {
            insert(entry.getKey(), entry.getValue());
        }
    }
}
