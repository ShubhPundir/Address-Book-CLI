package com.Indexing;

public class Example {
    
    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(4);
        tree.insert(1001, 5000L);
        tree.insert(1002, 15000L);
        tree.insert(1003, 25000L);
        tree.insert(2001, 5L);
        tree.insert(2002, 15L);
        tree.insert(2003, 25L);
        tree.insert(3001, 3L);
        tree.insert(3002, 6L);
        tree.insert(3003, 9L);
        tree.insert(4001, 1L);
        tree.insert(4002, 10L);
        tree.insert(4003, 100L);
        tree.insert(5001, 50L);
        tree.insert(5002, 150L);
        tree.insert(5003, 250L);

        tree.printTree();
        System.out.println("Search 1002: " + tree.search(1002));
        System.out.println("Search 5003: " + tree.search(5003));
    }
}