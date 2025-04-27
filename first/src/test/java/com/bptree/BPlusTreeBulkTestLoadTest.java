package com.bptree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BPlusTreeBulkTestLoadTest {

    private BPlusTree<Integer, String> bPlusTree;

    @Before
    public void setUp() {
        // Let's use order 3 for this test (small, to cause splits easily)
        bPlusTree = new BPlusTree<>(3);
    }

    @Test
    public void testInsertionAndSearch() {
        bPlusTree.insert(20, "Twenty");
        bPlusTree.insert(5, "Five");
        bPlusTree.insert(6, "Six");
        bPlusTree.insert(12, "Twelve");

        // Search tests
        assertEquals("Twenty", bPlusTree.search(20));
        assertEquals("Five", bPlusTree.search(5));
        assertEquals("Six", bPlusTree.search(6));
        assertEquals("Twelve", bPlusTree.search(12));

        // Negative test: searching for non-existent key
        assertNull(bPlusTree.search(99));
    }

    @Test
    public void testInOrderTraversal() {
        bPlusTree.insert(15, "Fifteen");
        bPlusTree.insert(5, "Five");
        bPlusTree.insert(25, "TwentyFive");
        bPlusTree.insert(10, "Ten");
        bPlusTree.insert(20, "Twenty");

        System.out.println("In-order traversal after insertion:");
        bPlusTree.inOrderTraversal();  // Just visually confirm keys are sorted
    }
}
