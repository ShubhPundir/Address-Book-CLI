package com.BPlusTree;

public class SerializableTest {
    public static void main(String[] args) throws Exception {
        BPlusTree tree = new BPlusTree(3);
        tree.insert(10, 1000);
        tree.insert(20, 2000);
        tree.insert(30, 3000);

        // Save to disk
        tree.saveToFile("bplustree.dat");

        // Load from disk
        BPlusTree loadedTree = BPlusTree.loadFromFile("bplustree.dat");
        loadedTree.printTree();
        long n = loadedTree.search(10);
        System.out.println(n);
    }
}
