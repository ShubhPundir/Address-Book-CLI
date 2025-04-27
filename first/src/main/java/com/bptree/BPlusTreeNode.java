package com.bptree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BPlusTreeNode implements Serializable {
    boolean isLeaf;
    List<Integer> keys;
    List<Long> values; // File offsets for leaf nodes
    List<BPlusTreeNode> children;
    BPlusTreeNode next; // For leaf node chaining
    
    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        keys = new ArrayList<>();
        values = isLeaf ? new ArrayList<>() : null;
        children = isLeaf ? null : new ArrayList<>();
        next = null;
    }
}