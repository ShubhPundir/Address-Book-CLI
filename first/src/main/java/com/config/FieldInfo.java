package com.config;

public class FieldInfo {
    private int size;
    private String dtype;

    public FieldInfo(int size, String dtype) {
        this.size = size;
        this.dtype = dtype;
    }

    public int getSize() {
        return size;
    }

    public String getDtype() {
        return dtype;
    }
}

