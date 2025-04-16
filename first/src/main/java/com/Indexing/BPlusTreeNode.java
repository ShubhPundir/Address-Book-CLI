package com.Indexing;

class BPlusTreeNode {
    int degree;
    boolean isLeaf;
    int[] keys;
    long[] offsets; // File pointer offsets
    BPlusTreeNode[] children;
    int numKeys;
    BPlusTreeNode next;

    public BPlusTreeNode(int degree, boolean isLeaf) {
        this.degree = degree;
        this.isLeaf = isLeaf;
        this.keys = new int[degree - 1]; // Adjusted size
        this.offsets = new long[degree - 1]; // Adjusted size
        this.children = new BPlusTreeNode[degree]; // Adjusted size
        this.numKeys = 0;
        this.next = null;
    }
}
