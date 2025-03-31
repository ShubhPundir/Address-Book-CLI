package com.Indexing;

class BPlusTreeNode {
    int[] keys;
    long[] offsets; // File pointer offsets
    int degree;
    BPlusTreeNode[] children;
    boolean isLeaf;
    int numKeys;
    BPlusTreeNode next;

    public BPlusTreeNode(int degree, boolean isLeaf) {
        this.degree = degree;
        this.isLeaf = isLeaf;
        this.keys = new int[degree];
        this.offsets = new long[degree]; // Storing offsets
        this.children = new BPlusTreeNode[degree + 1];
        this.numKeys = 0;
        this.next = null;
    }
}