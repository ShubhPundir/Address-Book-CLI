package com.bptree;

import java.util.ArrayList;
import java.util.List;

class BPlusTree<K extends Comparable<K>, V> {
    private int order;
    private Node<K, V> root;
    private LeafNode<K, V> firstLeaf;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new LeafNode<>();
        this.firstLeaf = (LeafNode<K, V>) root;
    }

    public void insert(K key, V value) {
        Node<K, V> newNode = root.insert(key, value);
        if (newNode != null) {
            InternalNode<K, V> newRoot = new InternalNode<>();
            newRoot.keys.add(newNode.getFirstLeafKey());
            newRoot.children.add(root);
            newRoot.children.add(newNode);
            root = newRoot;
        }
    }

    public V search(K key) {
        return root.search(key);
    }

    public void inOrderTraversal() {
        LeafNode<K, V> current = firstLeaf;
        while (current != null) {
            for (K key : current.keys) {
                System.out.print(key + " ");
            }
            current = current.next;
        }
        System.out.println();
    }
}
