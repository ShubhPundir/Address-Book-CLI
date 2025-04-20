package com.BPlusTree;

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
