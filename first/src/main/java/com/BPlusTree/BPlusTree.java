package com.BPlusTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class BPlusTree implements Serializable{
    int t;
    BPlusTreeNode root;

    BPlusTree(int t) {
        this.t = t;
        this.root = new BPlusTreeNode(true, t);
    }

    public void insert(int key, long offset) {
        BPlusTreeNode r = root;
        if ((r.leaf && r.records.size() == 2 * t - 1) || (!r.leaf && r.keys.size() == 2 * t - 1)) {
            BPlusTreeNode s = new BPlusTreeNode(false, t);
            s.children.add(r);
            splitChild(s, 0);
            root = s;
        }
        insertNonFull(root, key, offset);
    }

    private void insertNonFull(BPlusTreeNode node, int key, long offset) {
        if (node.leaf) {
            int i = 0;
            while (i < node.records.size() && node.records.get(i).key < key) i++;
            node.records.add(i, new RecordEntry(key, offset));
        } else {
            int i = 0;
            while (i < node.keys.size() && key >= node.keys.get(i)) i++;
            BPlusTreeNode child = node.children.get(i);
            if ((child.leaf && child.records.size() == 2 * t - 1) ||
                (!child.leaf && child.keys.size() == 2 * t - 1)) {
                splitChild(node, i);
                if (key > node.keys.get(i)) i++;
            }
            insertNonFull(node.children.get(i), key, offset);
        }
    }

    private void splitChild(BPlusTreeNode parent, int index) {
        BPlusTreeNode y = parent.children.get(index);
        BPlusTreeNode z = new BPlusTreeNode(y.leaf, t);

        parent.children.add(index + 1, z);

        if (y.leaf) {
            for (int j = t - 1; j < y.records.size(); j++) {
                z.records.add(y.records.get(j));
            }
            y.records.subList(t - 1, y.records.size()).clear();
            z.next = y.next;
            y.next = z;

            parent.keys.add(index, z.records.get(0).key);
        } else {
            for (int j = t; j < y.keys.size(); j++) {
                z.keys.add(y.keys.get(j));
            }
            for (int j = t; j < y.children.size(); j++) {
                z.children.add(y.children.get(j));
            }

            int midKey = y.keys.get(t - 1);
            parent.keys.add(index, midKey);

            y.keys.subList(t - 1, y.keys.size()).clear();
            y.children.subList(t, y.children.size()).clear();
        }
    }

    public Long search(int key) {
        return search(root, key);
    }

    private Long search(BPlusTreeNode node, int key) {
        if (node.leaf) {
            for (RecordEntry record : node.records) {
                if (record.key == key) {
                    return record.offset;
                }
            }
            return null;
        } else {
            int i = 0;
            while (i < node.keys.size() && key >= node.keys.get(i)) i++;
            return search(node.children.get(i), key);
        }
    }

    public void printLeaves() {
        System.out.println("Leaf node records (linked):");
        BPlusTreeNode node = root;
        while (!node.leaf) node = node.children.get(0);

        while (node != null) {
            for (RecordEntry entry : node.records) {
                System.out.print(entry + "  ");
            }
            node = node.next;
        }
        System.out.println();
    }

    public void delete(int key) {
        deleteRecursive(root, key);
    
        // Shrink tree height if root is internal with 1 child
        if (!root.leaf && root.children.size() == 1) {
            root = root.children.get(0);
        }
    }
    
    private void deleteRecursive(BPlusTreeNode node, int key) {
        if (node.leaf) {
            node.records.removeIf(r -> r.key == key);
            return;
        }
    
        int idx = 0;
        while (idx < node.keys.size() && key >= node.keys.get(idx)) idx++;
    
        BPlusTreeNode child = node.children.get(idx);
        deleteRecursive(child, key);
    
        // Update internal key if needed
        if (child.leaf && !child.records.isEmpty()) {
            int newKey = child.records.get(0).key;
            if (idx < node.keys.size()) {
                node.keys.set(idx, newKey);
            } else if (idx > 0) {
                node.keys.set(idx - 1, newKey);
            }
        } else if (child.leaf && child.records.isEmpty()) {
            // Remove empty child and its corresponding key
            node.children.remove(idx);
            if (idx < node.keys.size()) {
                node.keys.remove(idx);
            } else if (idx > 0) {
                node.keys.remove(idx - 1);
            }
        }
    
        // Rebalance if needed
        if (child.leaf && child.records.size() < t - 1) {
            rebalance(node, idx);
        }
    }
    
    
    private void rebalance(BPlusTreeNode parent, int index) {
        BPlusTreeNode node = parent.children.get(index);
    
        // Try borrowing from left sibling
        if (index > 0) {
            BPlusTreeNode leftSibling = parent.children.get(index - 1);
            if (leftSibling.records.size() > t - 1) {
                // Borrow the last record from left sibling
                node.records.add(0, leftSibling.records.remove(leftSibling.records.size() - 1));
                parent.keys.set(index - 1, node.records.get(0).key);
                return;
            }
        }
    
        // Try borrowing from right sibling
        if (index < parent.children.size() - 1) {
            BPlusTreeNode rightSibling = parent.children.get(index + 1);
            if (rightSibling.records.size() > t - 1) {
                // Borrow the first record from right sibling
                node.records.add(rightSibling.records.remove(0));
                parent.keys.set(index, rightSibling.records.get(0).key);
                return;
            }
        }
    
        // Merge with left sibling
        if (index > 0) {
            BPlusTreeNode leftSibling = parent.children.get(index - 1);
            leftSibling.records.addAll(node.records);
            parent.children.remove(index);
            parent.keys.remove(index - 1);
        }
        // Merge with right sibling
        else if (index < parent.children.size() - 1) {
            BPlusTreeNode rightSibling = parent.children.get(index + 1);
            node.records.addAll(rightSibling.records);
            parent.children.remove(index + 1);
            parent.keys.remove(index);
        }
    }
    
    

    public void printTree() {
        System.out.println("B+ Tree structure:");
        Queue<BPlusTreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int levelSize = q.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < levelSize; i++) {
                BPlusTreeNode node = q.poll();
                if (node.leaf) {
                    sb.append("Leaf ").append(node.records).append(" | ");
                } else {
                    sb.append("Node ").append(node.keys).append(" | ");
                    q.addAll(node.children);
                }
            }
            System.out.println(sb.toString());
        }
    }

    public void bulkLoad(List<RecordEntry> entries) {
        if (entries.isEmpty()) return;
        entries.sort(Comparator.comparingInt(e -> e.key));
        
        // Step 1: Build leaf level
        List<BPlusTreeNode> leaves = new ArrayList<>();
        for (int i = 0; i < entries.size(); i += 2 * t - 1) {
            BPlusTreeNode leaf = new BPlusTreeNode(true, t);
            int end = Math.min(i + (2 * t - 1), entries.size());
            leaf.records.addAll(entries.subList(i, end));
            if (!leaves.isEmpty()) {
                leaves.get(leaves.size() - 1).next = leaf;
            }
            leaves.add(leaf);
        }
    
        // Step 2: Build internal nodes level-by-level
        List<BPlusTreeNode> currentLevel = leaves;
        while (currentLevel.size() > 1) {
            List<BPlusTreeNode> parents = new ArrayList<>();
            for (int i = 0; i < currentLevel.size(); i += 2 * t) {
                BPlusTreeNode parent = new BPlusTreeNode(false, t);
                int end = Math.min(i + 2 * t, currentLevel.size());
                parent.children.addAll(currentLevel.subList(i, end));
                for (int j = i + 1; j < end; j++) {
                    parent.keys.add(currentLevel.get(j).leaf ? 
                                    currentLevel.get(j).records.get(0).key : 
                                    currentLevel.get(j).keys.get(0));
                }
                parents.add(parent);
            }
            currentLevel = parents;
        }
    
        // Final root
        this.root = currentLevel.get(0);
    }

    public int getHeight() {
        int height = 0;
        BPlusTreeNode node = root;
        while (!node.leaf) {
            node = node.children.get(0);
            height++;
        }
        return height + 1;
    }
    
    public int getLeafCount() {
        int count = 0;
        BPlusTreeNode node = root;
        while (!node.leaf) node = node.children.get(0);
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }
    
    public int getTotalRecords() {
        int count = 0;
        BPlusTreeNode node = root;
        while (!node.leaf) node = node.children.get(0);
        while (node != null) {
            count += node.records.size();
            node = node.next;
        }
        return count;
    }
    
    public void printMetrics() {
        System.out.println("===== B+ Tree Metrics =====");
        System.out.println("Tree Height: " + getHeight());
        System.out.println("Leaf Node Count: " + getLeafCount());
        System.out.println("Total Records: " + getTotalRecords());
        System.out.println("===========================");
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
    

    
}
