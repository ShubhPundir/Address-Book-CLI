package com.BPlusTree;

import java.io.Serializable;
import java.util.ArrayList;

class BPlusTreeNode implements Serializable{
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

