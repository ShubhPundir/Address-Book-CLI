package com.BPlusTree;

import java.util.ArrayList;
import java.util.List;

public class DeleteTest {
    public static void main(String[] args) {
        List<RecordEntry> bulkData = new ArrayList<>(List.of(
            new RecordEntry(10, 1000L),
            new RecordEntry(20, 2000L),
            new RecordEntry(5, 500L),
            new RecordEntry(6, 600L),
            new RecordEntry(12, 1200L),
            new RecordEntry(30, 3000L),
            new RecordEntry(7, 700L),
            new RecordEntry(17, 1700L),
            new RecordEntry(120, 1000L),
            new RecordEntry(220, 2000L),
            new RecordEntry(52, 500L),
            new RecordEntry(62, 600L),
            new RecordEntry(122, 1200L),
            new RecordEntry(320, 3000L),
            new RecordEntry(72, 700L),
            new RecordEntry(127, 1700L)
        ));

        BPlusTree bulkTree = new BPlusTree(3);
        bulkTree.bulkLoad(bulkData);
        bulkTree.printTree();
        bulkTree.delete(72);

        Long result = bulkTree.search(72);
        System.out.println("Offset for key " + 72 + ": " + (result != null ? result : "Not Found"));

        System.out.println("-------------------------");
        bulkTree.search(72);
        bulkTree.printTree();

    }
}
