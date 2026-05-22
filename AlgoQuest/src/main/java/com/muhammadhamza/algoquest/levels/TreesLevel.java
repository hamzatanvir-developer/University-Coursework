package com.muhammadhamza.algoquest.levels;

public class TreesLevel {

    private Node root;

    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public TreesLevel() {
        insert(10);
        insert(5);
        insert(15);
        insert(3);
        insert(7);
        insert(12);
        insert(20);
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.data) {
            current.left = insertRecursive(current.left, value);
        } else if (value > current.data) {
            current.right = insertRecursive(current.right, value);
        }

        return current;
    }

    public boolean search(int value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }

        if (current.data == value) {
            return true;
        }

        if (value < current.data) {
            return searchRecursive(current.left, value);
        }

        return searchRecursive(current.right, value);
    }

    public String inOrderTraversal() {
        StringBuilder result = new StringBuilder();
        inOrder(root, result);
        return result.toString().trim();
    }

    private void inOrder(Node current, StringBuilder result) {
        if (current == null) {
            return;
        }

        inOrder(current.left, result);
        result.append(current.data).append(" ");
        inOrder(current.right, result);
    }

    public String getConceptExplanation() {
        return """
                TREE CONCEPT

                Simple Meaning:
                A tree stores data in a hierarchy.

                Visual Example:

                        10
                       /  \\
                      5    15

                Main Parts:

                Root:
                The top node of the tree.

                Parent:
                A node that has child nodes.

                Child:
                A node connected below another node.

                Leaf:
                A node with no children.

                Edge:
                The connection between two nodes.

                Binary Tree:
                A tree where each node has at most two children.

                Binary Search Tree:
                A tree where smaller values go left and greater values go right.

                Time Complexity:
                Balanced BST search = O(log n)
                Skewed BST search = O(n)
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT TREE POINTS

                • Tree is a hierarchical data structure.
                • Root is the top node.
                • Parent node has child nodes.
                • Leaf node has no child.
                • Binary tree has at most two children per node.
                • BST means Binary Search Tree.
                • In BST, smaller values go left.
                • In BST, greater values go right.
                • Traversal means visiting all nodes.
                • In-order traversal of BST gives sorted order.
                • Balanced BST is faster than skewed BST.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE TREE EXAMPLES

                1. Family tree:
                   Parents and children form a hierarchy.

                2. Folder structure:
                   Main folder contains subfolders and files.

                3. Organization chart:
                   CEO, managers, and employees form levels.

                4. Website menu:
                   Main menu has submenus.

                5. Decision tree:
                   Choices lead to different results.

                6. Database indexing:
                   Tree structures help search data quickly.
                """;
    }

    public String getNodeCode() {
        return """
                TREE NODE STRUCTURE

                Manual Node Class:

                private static class Node {
                    int data;
                    Node left;
                    Node right;

                    Node(int data) {
                        this.data = data;
                        this.left = null;
                        this.right = null;
                    }
                }

                Explanation:

                data:
                Stores the actual value.

                left:
                Stores the left child reference.

                right:
                Stores the right child reference.

                In BST:
                left value < parent value
                right value > parent value
                """;
    }

    public String getInsertCode() {
        return """
                INSERT IN BINARY SEARCH TREE

                Meaning:
                Insert means placing a new value at the correct position using BST rule.

                BST Rule:
                Smaller value goes left.
                Greater value goes right.

                Example:
                Insert 7 into this tree:

                        10
                       /
                      5

                Step-by-step:
                1. Compare 7 with 10.
                2. 7 is smaller than 10, so go left.
                3. Compare 7 with 5.
                4. 7 is greater than 5, so go right of 5.

                Result:

                        10
                       /
                      5
                       \\
                        7

                Manual Logic:

                private Node insertRecursive(Node current, int value) {
                    if (current == null) {
                        return new Node(value);
                    }

                    if (value < current.data) {
                        current.left = insertRecursive(current.left, value);
                    } else if (value > current.data) {
                        current.right = insertRecursive(current.right, value);
                    }

                    return current;
                }

                Time Complexity:
                Balanced BST = O(log n)
                Skewed BST = O(n)
                """;
    }

    public String getSearchCode() {
        return """
                SEARCH IN BINARY SEARCH TREE

                Meaning:
                Search means finding whether a value exists in the tree.

                Example:
                Search 12:

                        10
                       /  \\
                      5    15
                          /
                         12

                Step-by-step:
                1. Start from root 10.
                2. 12 is greater than 10, so go right.
                3. Compare 12 with 15.
                4. 12 is smaller than 15, so go left.
                5. Found 12.

                Manual Logic:

                private boolean searchRecursive(Node current, int value) {
                    if (current == null) {
                        return false;
                    }

                    if (current.data == value) {
                        return true;
                    }

                    if (value < current.data) {
                        return searchRecursive(current.left, value);
                    }

                    return searchRecursive(current.right, value);
                }

                Time Complexity:
                Balanced BST = O(log n)
                Skewed BST = O(n)
                """;
    }

    public String getTraversalCode() {
        return """
                TREE TRAVERSALS

                Traversal means visiting all nodes in a tree.

                1. In-order:
                   left → root → right

                   For BST, in-order gives sorted order.

                2. Pre-order:
                   root → left → right

                   Useful for copying tree structure.

                3. Post-order:
                   left → right → root

                   Useful for deleting/freeing tree nodes.

                In-order Manual Logic:

                private void inOrder(Node current) {
                    if (current == null) {
                        return;
                    }

                    inOrder(current.left);
                    System.out.println(current.data);
                    inOrder(current.right);
                }

                Example:

                        10
                       /  \\
                      5    15

                In-order result:
                5 10 15

                Time Complexity:
                O(n)
                """;
    }

    public String getDeleteCode() {
        return """
                DELETE IDEA IN BST

                Delete has three main cases:

                1. Delete leaf node:
                   If node has no child, simply remove it.

                2. Delete node with one child:
                   Replace deleted node with its child.

                3. Delete node with two children:
                   Find inorder successor or predecessor.
                   Replace the value, then delete successor/predecessor.

                Example:
                Delete 5:

                        10
                       /  \\
                      5    15
                     / \\
                    3   7

                Since 5 has two children,
                we can replace 5 with inorder successor 7.

                Time Complexity:
                Balanced BST = O(log n)
                Skewed BST = O(n)
                """;
    }

    public String getEdgeCases() {
        return """
                TREE EDGE CASES

                1. Empty tree:
                   Root is null, so search should return false.

                2. Insert into empty tree:
                   New node becomes root.

                3. Duplicate values:
                   BST usually ignores duplicates or stores a count.

                4. Search missing value:
                   Search reaches null and returns false.

                5. Skewed tree:
                   If values are inserted in sorted order, tree can behave like a linked list.

                6. Delete root:
                   Root reference must be updated carefully.

                7. Delete leaf node:
                   Parent pointer should become null.

                8. Delete node with two children:
                   Inorder successor or predecessor is needed.

                9. Null check:
                   Always check current != null before accessing data.

                10. Balanced vs unbalanced:
                    Balanced tree is faster than skewed tree.
                """;
    }
}