package com.bptree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class BPlusTreeStorageTest {

    private BPlusTree<Integer, Long> tree;
    private final String FILE_NAME = "bplustree_storage_test.dat";

    @Before
    public void setUp() {
        tree = new BPlusTree<>(3); // order 3
        tree.insert(10, 100L);
        tree.insert(20, 200L);
        tree.insert(30, 300L);
        tree.insert(40, 400L);
        tree.insert(50, 500L);
    }

    @Test
    public void testSaveAndLoadTree() throws Exception {
        // Save tree
        saveTree(tree, FILE_NAME);

        // Load tree
        BPlusTree<Integer, Long> loadedTree = loadTree(FILE_NAME);

        // Check if loaded tree is not null
        assertNotNull(loadedTree);

        // Verify search results after loading
        assertEquals(Long.valueOf(100L), loadedTree.search(10));
        assertEquals(Long.valueOf(200L), loadedTree.search(20));
       
        assertEquals(Long.valueOf(400L), loadedTree.search(40));
        assertEquals(Long.valueOf(500L), loadedTree.search(50));
    }

    private void saveTree(BPlusTree<Integer, Long> tree, String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(tree);
        }
    }

    private BPlusTree<Integer, Long> loadTree(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (BPlusTree<Integer, Long>) ois.readObject();
        }
    }
}
