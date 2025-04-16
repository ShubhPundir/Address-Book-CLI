package com.Indexing;

import java.util.*;

class BPlusTree {
    private BPlusTreeNode root;
    private int degree;

    public BPlusTree(int degree) {
        this.degree = degree;
        this.root = new BPlusTreeNode(degree, true);
    }

    public void insert(int key, long offset) {
        BPlusTreeNode root = this.root;
        if (root.numKeys == degree - 1) {
            BPlusTreeNode newRoot = new BPlusTreeNode(degree, false);
            newRoot.children[0] = root;
            splitChild(newRoot, 0, root);
            this.root = newRoot;
        }
        insertNonFull(this.root, key, offset);
    }

    private void insertNonFull(BPlusTreeNode node, int key, long offset) {
        int i = node.numKeys - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                node.offsets[i + 1] = node.offsets[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.offsets[i + 1] = offset;
            node.numKeys++;
        } else {
            while (i >= 0 && key < node.keys[i]) i--;
            i++;
            if (node.children[i].numKeys == degree - 1) {
                splitChild(node, i, node.children[i]);
                if (key > node.keys[i]) i++;
            }
            insertNonFull(node.children[i], key, offset);
        }
    }

    private void splitChild(BPlusTreeNode parent, int i, BPlusTreeNode child) {
        int mid = (degree - 1) / 2;
        BPlusTreeNode newNode = new BPlusTreeNode(degree, child.isLeaf);

        // Copy the second half of keys and offsets to newNode
        int numKeysInNewNode = child.numKeys - mid;
        System.arraycopy(child.keys, mid, newNode.keys, 0, numKeysInNewNode);
        System.arraycopy(child.offsets, mid, newNode.offsets, 0, numKeysInNewNode);
        newNode.numKeys = numKeysInNewNode;

        if (!child.isLeaf) {
            System.arraycopy(child.children, mid, newNode.children, 0, numKeysInNewNode + 1);
        }

        // Update parent with new child reference
        for (int j = parent.numKeys; j > i; j--) {
            parent.children[j + 1] = parent.children[j];
            parent.keys[j] = parent.keys[j - 1];
            parent.offsets[j] = parent.offsets[j - 1];
        }
        parent.children[i + 1] = newNode;
        parent.keys[i] = child.keys[mid];  // Promote key
        parent.offsets[i] = child.offsets[mid];
        parent.numKeys++;

        child.numKeys = mid;

        if (child.isLeaf) {
            newNode.next = child.next;
            child.next = newNode;
        }
    }

    public Long search(int key) {
        return search(this.root, key);
    }

    private Long search(BPlusTreeNode node, int key) {
        int i = 0;
        while (i < node.numKeys && key > node.keys[i]) i++;
        if (i < node.numKeys && key == node.keys[i]) return node.offsets[i];
        if (node.isLeaf) return null;
        return search(node.children[i], key);
    }

    public void printTree() {
        Queue<BPlusTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BPlusTreeNode node = queue.poll();
            System.out.println(Arrays.toString(Arrays.copyOf(node.keys, node.numKeys)));
            if (!node.isLeaf) {
                for (int i = 0; i <= node.numKeys; i++) {
                    queue.add(node.children[i]);
                }
            }
        }
    }
}
