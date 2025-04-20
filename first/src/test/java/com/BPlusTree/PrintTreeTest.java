package com.BPlusTree;

public class PrintTreeTest {
    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(3);

        // Simulating key-offset insertions (offset as a file pointer)
        tree.insert(10, 1000L);
        tree.insert(20, 2000L);
        tree.insert(5, 500L);
        tree.insert(6, 600L);
        tree.insert(12, 1200L);
        tree.insert(30, 3000L);
        tree.insert(7, 700L);
        tree.insert(17, 1700L);
        tree.insert(120, 1000L);
        tree.insert(220, 2000L);
        tree.insert(52, 500L);
        tree.insert(62, 600L);
        tree.insert(122, 1200L);
        tree.insert(320, 3000L);
        tree.insert(72, 700L);
        tree.insert(127, 1700L);

        tree.printTree();
        System.out.println();
        // tree.printLeaves();

        int searchKey = 12;
        Long result = tree.search(searchKey);
        System.out.println("Offset for key " + searchKey + ": " + (result != null ? result : "Not Found"));

        searchKey = 17;
        result = tree.search(searchKey);
        System.out.println("Offset for key " + searchKey + ": " + (result != null ? result : "Not Found"));
    }
}
