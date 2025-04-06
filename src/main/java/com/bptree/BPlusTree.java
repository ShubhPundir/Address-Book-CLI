package com.bptree;

import java.util.ArrayList;
import java.util.List;

class BPlusTree<K extends Comparable<K>, V> {
    private int order;
    private Node<K, V> root;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new Node<>(true);
    }

    public void insert(K key, V value) {
        Node<K, V> newNode = root.insert(key, value, order);
        if (newNode != null) {
            Node<K, V> newRoot = new Node<>(false);
            newRoot.keys.add(newNode.keys.get(0));
            newRoot.children.add(root);
            newRoot.children.add(newNode);
            root = newRoot;
        }
    }

    public V search(K key) {
        return root.search(key);
    }

    public void inOrderTraversal() {
        Node<K, V> current = root.getFirstLeaf();
        while (current != null) {
            System.out.print(current.keys + " ");
            current = current.next;
        }
        System.out.println();
    }
}

class Node<K extends Comparable<K>, V> {
    List<K> keys = new ArrayList<>();
    List<V> values = new ArrayList<>();
    List<Node<K, V>> children = new ArrayList<>();
    Node<K, V> next;
    boolean isLeaf;

    Node(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    Node<K, V> insert(K key, V value, int order) {
        if (isLeaf) {
            int pos = 0;
            while (pos < keys.size() && keys.get(pos).compareTo(key) < 0) pos++;
            keys.add(pos, key);
            values.add(pos, value);

            if (keys.size() > order) return splitLeaf();
        } else {
            int pos = 0;
            while (pos < keys.size() && keys.get(pos).compareTo(key) < 0) pos++;

            Node<K, V> newNode = children.get(pos).insert(key, value, order);
            if (newNode != null) {
                keys.add(pos, newNode.keys.get(0));
                children.add(pos + 1, newNode);

                if (keys.size() > order) return splitInternal();
            }
        }
        return null;
    }

    V search(K key) {
        if (isLeaf) {
            int pos = keys.indexOf(key);
            return pos != -1 ? values.get(pos) : null;
        }
        int pos = 0;
        while (pos < keys.size() && key.compareTo(keys.get(pos)) > 0) pos++;
        return children.get(pos).search(key);
    }

    Node<K, V> getFirstLeaf() {
        return isLeaf ? this : children.get(0).getFirstLeaf();
    }

    private Node<K, V> splitLeaf() {
        int mid = keys.size() / 2;
        Node<K, V> newLeaf = new Node<>(true);

        newLeaf.keys.addAll(keys.subList(mid, keys.size()));
        newLeaf.values.addAll(values.subList(mid, values.size()));

        keys.subList(mid, keys.size()).clear();
        values.subList(mid, values.size()).clear();

        newLeaf.next = this.next;
        this.next = newLeaf;

        return newLeaf;
    }

    private Node<K, V> splitInternal() {
        int mid = keys.size() / 2;
        Node<K, V> newInternal = new Node<>(false);

        newInternal.keys.addAll(keys.subList(mid + 1, keys.size()));
        newInternal.children.addAll(children.subList(mid + 1, children.size()));

        keys.subList(mid, keys.size()).clear();
        children.subList(mid + 1, children.size()).clear();

        return newInternal;
    }
}
