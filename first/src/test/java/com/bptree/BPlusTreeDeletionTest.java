package com.bptree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BPlusTreeDeletionTest {

    private BPlusTree<Integer, String> tree;

    @Before
    public void setUp() {
        tree = new BPlusTree<>(3); // Order 3
    }

    @Test
    public void testInsertionAndDeletion() {
        // Insert elements
        tree.insert(20, "Twenty");
        tree.insert(5, "Five");
        tree.insert(6, "Six");
        tree.insert(12, "Twelve");

        System.out.println("\n=== Tree Before Deletion ===");
        tree.inOrderTraversal();

        // Search inserted elements
        assertEquals("Twenty", tree.search(20));
        assertEquals("Five", tree.search(5));
        assertEquals("Six", tree.search(6));
        assertEquals("Twelve", tree.search(12));

        // Perform deletion
        tree.delete(6);
        tree.delete(20);

        System.out.println("\n=== Tree After Deletion ===");
        tree.inOrderTraversal();

        // Search after deletion
        assertNull(tree.search(6)); // Deleted
        assertNull(tree.search(20)); // Deleted

        // Remaining elements should still be found
        
        assertEquals("Five", tree.search(5));
        assertEquals("Twelve", tree.search(12));
    }
}
