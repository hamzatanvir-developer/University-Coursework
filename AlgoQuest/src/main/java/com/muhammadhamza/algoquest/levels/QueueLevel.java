package com.muhammadhamza.algoquest.levels;

public class QueueLevel {

    private final int[] queue;
    private int front;
    private int rear;
    private int size;

    public QueueLevel() {
        queue = new int[10];
        front = 0;
        rear = -1;
        size = 0;

        enqueue(5);
        enqueue(10);
        enqueue(15);
    }

    public boolean enqueue(int value) {
        if (size == queue.length) {
            return false;
        }

        rear++;
        queue[rear] = value;
        size++;
        return true;
    }

    public Integer dequeue() {
        if (size == 0) {
            return null;
        }

        int removed = queue[front];
        front++;
        size--;
        return removed;
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }

        return queue[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }

    public int[] getVisibleQueueFrontToRear() {
        int[] visible = new int[size];

        for (int i = 0; i < size; i++) {
            visible[i] = queue[front + i];
        }

        return visible;
    }

    public String getConceptExplanation() {
        return """
                QUEUE CONCEPT

                Simple Meaning:
                A queue stores data in FIFO order.

                FIFO means:
                First In, First Out.

                Visual Example:
                FRONT → [5] [10] [15] ← REAR

                The first value inserted is 5.
                So 5 will be removed first.

                Main Parts:

                FRONT:
                Points to the value that will be removed next.

                REAR:
                Points to the value where new items are added.

                enqueue():
                Adds a new value at REAR.

                dequeue():
                Removes the value from FRONT.

                peek():
                Reads the FRONT value without removing it.

                Manual Implementation:
                We can implement a queue using an array with front, rear, and size variables.

                Time Complexity:
                enqueue() = O(1)
                dequeue() = O(1)
                peek() = O(1)
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT QUEUE POINTS

                • Queue is a linear data structure.
                • Queue follows FIFO: First In, First Out.
                • The first inserted element is removed first.
                • FRONT points to the element that will be removed next.
                • REAR points to the position where new elements are inserted.
                • enqueue() inserts a value at REAR.
                • dequeue() removes a value from FRONT.
                • peek() reads the FRONT value without removing it.
                • Queue is useful for waiting lines, printer jobs, CPU scheduling, and customer service systems.
                • Enqueue, dequeue, and peek usually take O(1) time.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE QUEUE EXAMPLES

                1. Ticket counter:
                   The first person in line is served first.

                2. Printer jobs:
                   First print request is printed first.

                3. Customer support calls:
                   First caller is answered first.

                4. Food order line:
                   First order is prepared first.

                5. CPU task scheduling:
                   Tasks may be processed in arrival order.

                6. Message queue:
                   Messages are processed in the order they arrive.
                """;
    }

    public String getEnqueueCode() {
        return """
                ENQUEUE OPERATION

                Meaning:
                enqueue() adds a new value at the REAR of the queue.

                Before enqueue 20:
                FRONT → [5] [10] [15] ← REAR

                After enqueue 20:
                FRONT → [5] [10] [15] [20] ← REAR

                Built-in Java:

                Queue<Integer> queue = new LinkedList<>();
                queue.add(5);
                queue.add(10);
                queue.add(15);
                queue.add(20);

                Manual Logic:

                public boolean enqueue(int value) {
                    if (size == queue.length) {
                        return false;
                    }

                    rear++;
                    queue[rear] = value;
                    size++;
                    return true;
                }

                Step-by-step:
                1. Check if queue is full.
                2. Move rear one step forward.
                3. Store value at queue[rear].
                4. Increase size by 1.

                Time Complexity:
                O(1)
                """;
    }

    public String getDequeueCode() {
        return """
                DEQUEUE OPERATION

                Meaning:
                dequeue() removes the value from FRONT.

                Before dequeue:
                FRONT → [5] [10] [15] ← REAR

                After dequeue:
                FRONT → [10] [15] ← REAR

                Removed value:
                5

                Built-in Java:

                int removed = queue.remove();

                Manual Logic:

                public Integer dequeue() {
                    if (size == 0) {
                        return null;
                    }

                    int removed = queue[front];
                    front++;
                    size--;
                    return removed;
                }

                Step-by-step:
                1. Check if queue is empty.
                2. Store queue[front] in removed variable.
                3. Move front one step forward.
                4. Decrease size by 1.
                5. Return removed value.

                Time Complexity:
                O(1)
                """;
    }

    public String getPeekCode() {
        return """
                PEEK OPERATION

                Meaning:
                peek() reads the FRONT value without removing it.

                Queue:
                FRONT → [5] [10] [15] ← REAR

                peek() returns:
                5

                Queue after peek:
                FRONT → [5] [10] [15] ← REAR

                Nothing is removed.

                Built-in Java:

                int value = queue.peek();

                Manual Logic:

                public Integer peek() {
                    if (size == 0) {
                        return null;
                    }

                    return queue[front];
                }

                Time Complexity:
                O(1)
                """;
    }

    public String getDisplayCode() {
        return """
                DISPLAY QUEUE

                Meaning:
                Display means showing queue values from FRONT to REAR.

                Queue:
                FRONT → [5] [10] [15] ← REAR

                Manual Logic:

                for (int i = 0; i < size; i++) {
                    System.out.println(queue[front + i]);
                }

                Output:
                5
                10
                15

                Step-by-step:
                1. Start from FRONT.
                2. Print current value.
                3. Move forward.
                4. Stop at REAR.

                Time Complexity:
                O(n)

                Why O(n)?
                Every queue element is visited once.
                """;
    }

    public String getCircularQueueCode() {
        return """
                CIRCULAR QUEUE

                Problem in simple queue:
                After many dequeue operations, empty spaces at the start may be wasted.

                Circular queue solution:
                FRONT and REAR wrap around using modulo.

                Formula:
                rear = (rear + 1) % capacity;
                front = (front + 1) % capacity;

                Example:
                capacity = 5

                If rear is at index 4,
                next rear becomes:

                (4 + 1) % 5 = 0

                Benefit:
                Empty spaces are reused.

                Visual Idea:
                [30] [40] [ ] [10] [20]
                       REAR      FRONT

                Time Complexity:
                enqueue = O(1)
                dequeue = O(1)
                peek = O(1)
                """;
    }

    public String getEdgeCases() {
        return """
                QUEUE EDGE CASES

                1. Enqueue into full queue:
                   If size == capacity, queue has no free space.

                2. Dequeue from empty queue:
                   If size == 0, no element can be removed.
                   This is queue underflow.

                3. Peek from empty queue:
                   If queue is empty, there is no FRONT value.

                4. Single element queue:
                   After one dequeue, queue becomes empty.

                5. Front and rear update:
                   Front should move on dequeue.
                   Rear should move on enqueue.

                6. Circular queue wrap-around:
                   Use modulo to move front/rear back to index 0.

                7. Display empty queue:
                   Program should show empty queue instead of crashing.

                8. Fixed capacity:
                   Normal array-based queue cannot grow automatically.
                """;
    }
}