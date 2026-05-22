package com.muhammadhamza.algoquest.levels;

public class LinkedListLevel {

    private Node head;

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedListLevel() {
        insertAtEnd(5);
        insertAtEnd(10);
        insertAtEnd(15);
    }

    public void insertAtEnd(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public boolean insertAfterValue(int target, int value) {
        Node current = head;

        while (current != null) {
            if (current.data == target) {
                Node newNode = new Node(value);
                newNode.next = current.next;
                current.next = newNode;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public boolean deleteValue(int value) {
        if (head == null) {
            return false;
        }

        if (head.data == value) {
            head = head.next;
            return true;
        }

        Node current = head;

        while (current.next != null) {
            if (current.next.data == value) {
                current.next = current.next.next;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public int indexOf(int value) {
        Node current = head;
        int index = 0;

        while (current != null) {
            if (current.data == value) {
                return index;
            }

            current = current.next;
            index++;
        }

        return -1;
    }

    public int[] getVisibleList() {
        int count = 0;
        Node current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        int[] values = new int[count];
        current = head;
        int index = 0;

        while (current != null) {
            values[index] = current.data;
            current = current.next;
            index++;
        }

        return values;
    }

    public String getConceptExplanation() {
        return """
                LINKED LIST CONCEPT

                Simple Meaning:
                A linked list stores data in connected nodes.

                Visual Example:
                HEAD → [5 | next] → [10 | next] → [15 | null]

                Important Parts:

                HEAD:
                Points to the first node.

                Node:
                A block that stores data and the address/reference of the next node.

                Data:
                The actual value inside the node.

                Next:
                A pointer/reference that connects one node to the next node.

                NULL:
                Means there is no next node. It marks the end of the list.

                Difference from Array:
                Array stores values in continuous memory and uses indexes.
                Linked list stores values as separate nodes connected through pointers.

                Time Complexity:
                Insert at head = O(1)
                Search = O(n)
                Traversal = O(n)
                Delete by value = O(n)
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT LINKED LIST POINTS

                • Linked list stores data using nodes.
                • Each node has data and next pointer.
                • HEAD points to the first node.
                • Last node points to NULL.
                • Linked list does not need continuous memory.
                • Linked list does not provide direct index access like arrays.
                • Searching starts from HEAD and moves node by node.
                • Insert at beginning is O(1).
                • Search is O(n).
                • Delete by value is O(n).
                • Linked list is useful when frequent insertion and deletion are needed.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE LINKED LIST EXAMPLES

                1. Music playlist:
                   Each song points to the next song.

                2. Browser history:
                   Each page can link to the next visited page.

                3. Train coaches:
                   Each coach is connected to the next coach.

                4. Image slideshow:
                   Each image points to the next image.

                5. Task chain:
                   Each task can point to the next task.

                6. Undo/redo systems:
                   Actions can be connected in a sequence.
                """;
    }

    public String getNodeCode() {
        return """
                NODE STRUCTURE

                Manual Node Class:

                private static class Node {
                    int data;
                    Node next;

                    Node(int data) {
                        this.data = data;
                        this.next = null;
                    }
                }

                Explanation:

                data:
                Stores the actual value.

                next:
                Stores the reference of the next node.

                Example Node:
                [5 | next]

                Full Linked List:
                HEAD → [5 | next] → [10 | next] → [15 | null]

                The final null means the list has ended.
                """;
    }

    public String getSearchCode() {
        return """
                SEARCH / TRAVERSE LINKED LIST

                Built-in Java:

                LinkedList<Integer> list = new LinkedList<>();
                list.add(5);
                list.add(10);
                list.add(15);

                boolean found = list.contains(10);

                Manual Search Logic:

                public int indexOf(int value) {
                    Node current = head;
                    int index = 0;

                    while (current != null) {
                        if (current.data == value) {
                            return index;
                        }

                        current = current.next;
                        index++;
                    }

                    return -1;
                }

                Step-by-step:
                1. Start from HEAD.
                2. Check current node data.
                3. If value matches, return index.
                4. Otherwise move to current.next.
                5. Stop when current becomes NULL.
                6. Return -1 if value is not found.

                Time Complexity:
                O(n)
                """;
    }

    public String getInsertAtHeadCode() {
        return """
                INSERT AT HEAD

                Meaning:
                Insert at head means adding a new node at the beginning.

                Before:
                HEAD → 5 → 10 → 15 → NULL

                Insert 3 at beginning.

                After:
                HEAD → 3 → 5 → 10 → 15 → NULL

                Manual Logic:

                public void insertAtBeginning(int value) {
                    Node newNode = new Node(value);
                    newNode.next = head;
                    head = newNode;
                }

                Step-by-step:
                1. Create a new node.
                2. Make newNode.next point to old HEAD.
                3. Move HEAD to newNode.

                Time Complexity:
                O(1)
                """;
    }

    public String getInsertAfterCode() {
        return """
                INSERT AFTER A NODE

                Example:
                Insert 8 after 5.

                Before:
                HEAD → 5 → 10 → 15 → NULL

                After:
                HEAD → 5 → 8 → 10 → 15 → NULL

                Manual Logic:

                public boolean insertAfterValue(int target, int value) {
                    Node current = head;

                    while (current != null) {
                        if (current.data == target) {
                            Node newNode = new Node(value);
                            newNode.next = current.next;
                            current.next = newNode;
                            return true;
                        }

                        current = current.next;
                    }

                    return false;
                }

                Step-by-step:
                1. Start from HEAD.
                2. Search for target node.
                3. Create new node.
                4. New node points to target's next node.
                5. Target node points to new node.

                Time Complexity:
                O(n)
                """;
    }

    public String getDeleteCode() {
        return """
                DELETE NODE BY VALUE

                Example:
                Delete 10.

                Before:
                HEAD → 5 → 8 → 10 → 15 → NULL

                After:
                HEAD → 5 → 8 → 15 → NULL

                Manual Logic:

                public boolean deleteValue(int value) {
                    if (head == null) {
                        return false;
                    }

                    if (head.data == value) {
                        head = head.next;
                        return true;
                    }

                    Node current = head;

                    while (current.next != null) {
                        if (current.next.data == value) {
                            current.next = current.next.next;
                            return true;
                        }

                        current = current.next;
                    }

                    return false;
                }

                Step-by-step:
                1. Check if list is empty.
                2. If HEAD contains value, move HEAD to next node.
                3. Otherwise search for previous node.
                4. Skip the node that should be deleted.
                5. Connect previous node to deleted node's next node.

                Time Complexity:
                O(n)
                """;
    }

    public String getTypesCode() {
        return """
                TYPES OF LINKED LIST

                1. Singly Linked List:
                   Each node points only to the next node.

                   Example:
                   5 → 10 → 15 → NULL

                2. Doubly Linked List:
                   Each node has previous and next pointers.

                   Example:
                   NULL ← 5 ↔ 10 ↔ 15 → NULL

                3. Circular Linked List:
                   Last node points back to the first node.

                   Example:
                   5 → 10 → 15
                   ↑         ↓
                   ← ← ← ← ←

                Difference:

                Singly:
                Less memory, forward movement only.

                Doubly:
                More memory, forward and backward movement.

                Circular:
                Useful when list should repeat continuously.
                """;
    }

    public String getEdgeCases() {
        return """
                LINKED LIST EDGE CASES

                1. Empty list:
                   If HEAD is NULL, the list has no nodes.

                2. Insert into empty list:
                   New node becomes HEAD.

                3. Delete HEAD node:
                   HEAD must move to the next node.

                4. Delete last node:
                   Previous node's next must become NULL.

                5. Search missing value:
                   If value is not found, return -1.

                6. Insert after missing value:
                   If target does not exist, insertion fails safely.

                7. Single-node list:
                   Deleting the only node makes HEAD NULL.

                8. Null pointer safety:
                   Always check current != null before accessing current.data or current.next.

                9. Duplicate values:
                   Search/delete usually affects the first matching value.

                10. Circular linked list warning:
                    Traversal needs a stopping condition, otherwise it may run forever.
                """;
    }
}