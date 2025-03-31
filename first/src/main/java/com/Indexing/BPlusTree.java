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
        int mid = degree / 2;
        BPlusTreeNode newNode = new BPlusTreeNode(degree, child.isLeaf);
        parent.children[i + 1] = newNode;
        newNode.numKeys = child.numKeys - mid - 1;

        for (int j = 0; j < newNode.numKeys; j++) {
            newNode.keys[j] = child.keys[mid + 1 + j];
            newNode.offsets[j] = child.offsets[mid + 1 + j];
        }

        if (!child.isLeaf) {
            for (int j = 0; j <= newNode.numKeys; j++) {
                newNode.children[j] = child.children[mid + 1 + j];
            }
        }
        child.numKeys = mid;
        for (int j = parent.numKeys; j > i; j--) {
            parent.children[j + 1] = parent.children[j];
            parent.keys[j] = parent.keys[j - 1];
        }
        parent.keys[i] = child.keys[mid];
        parent.offsets[i] = child.offsets[mid];
        parent.numKeys++;
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