package com.BplusTree;

import java.util.*;

class RecordEntry {
    int key;
    long offset;

    RecordEntry(int key, long offset) {
        this.key = key;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "(" + key + " -> " + offset + ")";
    }
}

class BPlusTreeNode {
    int t;
    boolean leaf;
    ArrayList<Integer> keys; // used only for navigation
    ArrayList<BPlusTreeNode> children;
    ArrayList<RecordEntry> records; // used only in leaf nodes
    BPlusTreeNode next;

    BPlusTreeNode(boolean leaf, int t) {
        this.leaf = leaf;
        this.t = t;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.records = leaf ? new ArrayList<>() : null;
        this.next = null;
    }
}

class BPlusTree {
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
            while (i < node.keys.size() && key > node.keys.get(i)) i++;
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
            while (i < node.keys.size() && key > node.keys.get(i)) i++;
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
    /*
    private void splitChild(BPlusTreeNode parent, int i) {
        BPlusTreeNode child = parent.children.get(i);
        BPlusTreeNode newChild = new BPlusTreeNode(child.leaf, t);

        // Move the middle part of keys and children to the new child node
        int midIndex = t - 1;  // Middle index
        parent.keys.add(i, child.keys.get(midIndex));  // Promote the middle key to the parent

        // Move the second half of the keys from the child node to the new child
        for (int j = midIndex + 1; j < child.keys.size(); j++) {
            newChild.keys.add(child.keys.get(j));
        }

        // If it's not a leaf node, also move children pointers to the new child
        if (!child.leaf) {
            for (int j = midIndex + 1; j < child.children.size(); j++) {
                newChild.children.add(child.children.get(j));
            }
        }

        // Remove the moved keys and children from the original child node
        child.keys.subList(midIndex, child.keys.size()).clear();
        if (!child.leaf) {
            child.children.subList(midIndex + 1, child.children.size()).clear();
        }

        // Add the new child to the parent
        parent.children.add(i + 1, newChild);
    }

     */

    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(3);

        // Simulating key-offset insertions (offset as a file pointer)
        tree.insert(10, 1000L);
        tree.insert(20, 2000L);
        tree.insert(5, 500L);
        tree.insert(6, 600L);
        tree.insert(12, 1200L);
        tree.insert(30, 3000L);
        tree.insert(7, 700L);
        tree.insert(17, 1700L);
        tree.insert(120, 1000L);
        tree.insert(220, 2000L);
        tree.insert(52, 500L);
        tree.insert(62, 600L);
        tree.insert(122, 1200L);
        tree.insert(320, 3000L);
        tree.insert(72, 700L);
        tree.insert(127, 1700L);

        tree.printTree();
        System.out.println();
        // tree.printLeaves();

        int searchKey = 12;
        Long result = tree.search(searchKey);
        System.out.println("Offset for key " + searchKey + ": " + (result != null ? result : "Not Found"));

        searchKey = 17;
        result = tree.search(searchKey);
        System.out.println("Offset for key " + searchKey + ": " + (result != null ? result : "Not Found"));
    }
}
