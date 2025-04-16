package com.Indexing;

import java.util.ArrayList;

class BPlusTreeNode {
    int t; // Minimum degree
    boolean leaf;
    ArrayList<Integer> keys;
    ArrayList<BPlusTreeNode> children;

    BPlusTreeNode(boolean leaf, int t) {
        this.leaf = leaf;
        this.t = t;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }
}

class BPT {
    int t;
    BPlusTreeNode root;

    BPT(int t) {
        this.t = t;
        this.root = new BPlusTreeNode(true, t);
    }

    public void insert(int key) {
        BPlusTreeNode r = root;
        if (r.keys.size() == (2 * t) - 1) {
            BPlusTreeNode s = new BPlusTreeNode(false, t);
            s.children.add(r);
            splitChild(s, 0);
            root = s;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(BPlusTreeNode x, int key) {
        int i = x.keys.size() - 1;
        if (x.leaf) {
            x.keys.add(0);
            while (i >= 0 && key < x.keys.get(i)) {
                x.keys.set(i + 1, x.keys.get(i));
                i--;
            }
            x.keys.set(i + 1, key);
        } else {
            while (i >= 0 && key < x.keys.get(i)) {
                i--;
            }
            i++;
            if (x.children.get(i).keys.size() == (2 * t) - 1) {
                splitChild(x, i);
                if (key > x.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(x.children.get(i), key);
        }
    }

    private void splitChild(BPlusTreeNode x, int i) {
        BPlusTreeNode y = x.children.get(i);
        BPlusTreeNode z = new BPlusTreeNode(y.leaf, t);
        x.children.add(i + 1, z);
        x.keys.add(i, y.keys.get(t - 1));

        for (int j = 0; j < t - 1; j++) {
            z.keys.add(y.keys.get(j + t));
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(y.children.get(j + t));
            }
        }
        y.keys.subList(t - 1, y.keys.size()).clear();
        if (!y.leaf) {
            y.children.subList(t, y.children.size()).clear();
        }
    }

    public void traverse() {
        traverse(root);
    }

    private void traverse(BPlusTreeNode node) {
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.leaf) {
                traverse(node.children.get(i));
            }
            System.out.print(" " + node.keys.get(i));
        }
        if (!node.leaf) {
            traverse(node.children.get(node.keys.size()));
        }
    }

    public static void main(String[] args) {
        BPT bpt = new BPT(3);
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int key : keys) {
            bpt.insert(key);
        }
        System.out.println("Traversal of the B+ Tree:");
        bpt.traverse();
    }
}