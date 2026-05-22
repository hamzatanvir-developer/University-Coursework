package com.muhammadhamza.algoquest.levels;

import java.util.ArrayList;

public class HeapsLevel {

    private final ArrayList<Integer> heap;

    public HeapsLevel() {
        heap = new ArrayList<>();
        insert(90);
        insert(50);
        insert(70);
        insert(40);
    }

    public void insert(int value) {
        heap.add(value);
        bubbleUp(heap.size() - 1);
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            if (heap.get(parentIndex) >= heap.get(index)) {
                break;
            }

            swap(parentIndex, index);
            index = parentIndex;
        }
    }

    public Integer extractMax() {
        if (heap.isEmpty()) {
            return null;
        }

        int max = heap.get(0);
        int last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            bubbleDown(0);
        }

        return max;
    }

    private void bubbleDown(int index) {
        int size = heap.size();

        while (index < size) {
            int leftChild = (2 * index) + 1;
            int rightChild = (2 * index) + 2;
            int largest = index;

            if (leftChild < size && heap.get(leftChild) > heap.get(largest)) {
                largest = leftChild;
            }

            if (rightChild < size && heap.get(rightChild) > heap.get(largest)) {
                largest = rightChild;
            }

            if (largest == index) {
                break;
            }

            swap(index, largest);
            index = largest;
        }
    }

    public Integer peekMax() {
        if (heap.isEmpty()) {
            return null;
        }

        return heap.get(0);
    }

    private void swap(int first, int second) {
        int temp = heap.get(first);
        heap.set(first, heap.get(second));
        heap.set(second, temp);
    }

    public String getConceptExplanation() {
        return """
                HEAP CONCEPT

                Simple Meaning:
                A heap is a complete binary tree used for priority.

                Complete Binary Tree:
                All levels are filled from left to right.

                Max Heap:
                The largest value stays at the root.

                Min Heap:
                The smallest value stays at the root.

                Visual Max Heap:

                        90
                       /  \\
                     50    70

                Array Form:
                [90, 50, 70]

                Important:
                Heap looks like a tree, but it is usually stored in an array or ArrayList.

                Time Complexity:
                peek root = O(1)
                insert = O(log n)
                extract root = O(log n)
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT HEAP POINTS

                • Heap is a complete binary tree.
                • Heap is usually stored in array/ArrayList form.
                • Max Heap keeps the largest value at root.
                • Min Heap keeps the smallest value at root.
                • Insert operation uses bubble up.
                • Extract operation uses bubble down.
                • Peek root takes O(1) time.
                • Insert takes O(log n) time.
                • Extract root takes O(log n) time.
                • Heap is useful when priority matters.
                • Java PriorityQueue works like a Min Heap by default.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE HEAP EXAMPLES

                1. Emergency room:
                   Patient with highest priority is treated first.

                2. CPU scheduling:
                   Higher priority tasks can run first.

                3. Game leaderboard:
                   Highest score can be accessed quickly.

                4. Priority queue:
                   Most important item is served first.

                5. Dijkstra shortest path:
                   Uses priority queue to select shortest distance.

                6. Event simulation:
                   Next important or earliest event can be processed first.
                """;
    }

    public String getMaxMinHeapCode() {
        return """
                MAX HEAP VS MIN HEAP

                Max Heap:
                • Root contains maximum value.
                • Parent is greater than or equal to children.

                Example:

                        90
                       /  \\
                     50    70

                Min Heap:
                • Root contains minimum value.
                • Parent is smaller than or equal to children.

                Example:

                        10
                       /  \\
                     30    20

                Java PriorityQueue:
                By default, PriorityQueue works like Min Heap.

                PriorityQueue<Integer> pq = new PriorityQueue<>();

                For Max Heap in Java:
                PriorityQueue<Integer> maxHeap =
                    new PriorityQueue<>((a, b) -> b - a);
                """;
    }

    public String getInsertCode() {
        return """
                INSERT / BUBBLE UP

                Meaning:
                Insert adds a new value at the end, then moves it upward if heap rule breaks.

                Example:
                Max Heap before inserting 95:

                        90
                       /  \\
                     50    70

                Add 95 at the end:

                        90
                       /  \\
                     50    70
                    /
                  95

                Since 95 is greater than 50, swap.
                Since 95 is greater than 90, swap again.

                Final:

                        95
                       /  \\
                     90    70
                    /
                  50

                Manual Logic:

                public void insert(int value) {
                    heap.add(value);
                    bubbleUp(heap.size() - 1);
                }

                private void bubbleUp(int index) {
                    while (index > 0) {
                        int parentIndex = (index - 1) / 2;

                        if (heap.get(parentIndex) >= heap.get(index)) {
                            break;
                        }

                        swap(parentIndex, index);
                        index = parentIndex;
                    }
                }

                Time Complexity:
                O(log n)
                """;
    }

    public String getExtractCode() {
        return """
                EXTRACT MAX / BUBBLE DOWN

                Meaning:
                extractMax removes the root value from Max Heap.

                Example:
                Before extract:

                        90
                       /  \\
                     50    70

                Step 1:
                Save root 90.

                Step 2:
                Move last value to root.

                Step 3:
                Bubble down to restore heap rule.

                Manual Logic:

                public Integer extractMax() {
                    if (heap.isEmpty()) {
                        return null;
                    }

                    int max = heap.get(0);
                    int last = heap.remove(heap.size() - 1);

                    if (!heap.isEmpty()) {
                        heap.set(0, last);
                        bubbleDown(0);
                    }

                    return max;
                }

                Time Complexity:
                O(log n)
                """;
    }

    public String getPeekCode() {
        return """
                PEEK ROOT

                Meaning:
                Peek reads the root value without removing it.

                Max Heap:

                        90
                       /  \\
                     50    70

                peekMax() returns:
                90

                Heap after peek:
                Same as before. Nothing is removed.

                Manual Logic:

                public Integer peekMax() {
                    if (heap.isEmpty()) {
                        return null;
                    }

                    return heap.get(0);
                }

                Time Complexity:
                O(1)
                """;
    }

    public String getArrayFormulaCode() {
        return """
                HEAP ARRAY FORMULAS

                Heap is stored level by level in an array.

                If current node index = i:

                Parent index:
                (i - 1) / 2

                Left child index:
                (2 * i) + 1

                Right child index:
                (2 * i) + 2

                Example:
                Array: [90, 50, 70]

                index 0 = 90
                left child of 90 = index 1 = 50
                right child of 90 = index 2 = 70

                Why use array?
                Heap does not need explicit left/right pointers.
                Index formulas are enough.
                """;
    }

    public String getEdgeCases() {
        return """
                HEAP EDGE CASES

                1. Extract from empty heap:
                   No root value exists, so return null.

                2. Peek from empty heap:
                   There is no maximum/minimum value.

                3. Insert duplicate values:
                   Heap can allow duplicate values.

                4. Single element heap:
                   Extract removes the only value and heap becomes empty.

                5. Bubble up stops early:
                   If parent already follows heap rule, no more swaps are needed.

                6. Bubble down with one child:
                   Compare with existing child only.

                7. Broken heap rule:
                   In Max Heap, if parent is smaller than child, swapping is needed.

                8. Array index safety:
                   Always check child index is inside heap size before accessing it.

                9. Min Heap difference:
                   In Min Heap, comparisons are reversed.

                10. Priority note:
                    Heap is not fully sorted.
                    It only guarantees that root has highest or lowest priority.
                """;
    }
}