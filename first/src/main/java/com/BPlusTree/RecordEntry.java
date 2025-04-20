package com.BPlusTree;

import java.io.Serializable;

class RecordEntry implements Serializable{
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
