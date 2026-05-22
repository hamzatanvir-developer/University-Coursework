package com.muhammadhamza.algoquest.levels;

public class StackLevel {

    private final int[] stack;
    private int top;

    public StackLevel() {
        stack = new int[10];
        top = -1;

        push(10);
        push(20);
        push(30);
    }

    public boolean push(int value) {
        if (top == stack.length - 1) {
            return false;
        }

        top++;
        stack[top] = value;
        return true;
    }

    public Integer pop() {
        if (top == -1) {
            return null;
        }

        int removed = stack[top];
        top--;
        return removed;
    }

    public Integer peek() {
        if (top == -1) {
            return null;
        }

        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == stack.length - 1;
    }

    public int[] getVisibleStackTopToBottom() {
        int[] visible = new int[top + 1];

        int index = 0;
        for (int i = top; i >= 0; i--) {
            visible[index] = stack[i];
            index++;
        }

        return visible;
    }

    public String getConceptExplanation() {
        return """
                STACK CONCEPT

                Simple Meaning:
                A stack stores data in LIFO order.

                LIFO means:
                Last In, First Out.

                Visual Example:
                TOP → [30]
                      [20]
                      [10]

                The last value inserted is 30.
                So 30 will be removed first.

                Main Parts:

                TOP:
                Points to the latest inserted item.

                push():
                Adds a new item on TOP.

                pop():
                Removes the item from TOP.

                peek():
                Reads the TOP item without removing it.

                Manual Implementation:
                We can implement stack using an array and a top variable.

                top = -1 means stack is empty.

                Time Complexity:
                push() = O(1)
                pop() = O(1)
                peek() = O(1)
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT STACK POINTS

                • Stack is a linear data structure.
                • Stack follows LIFO: Last In, First Out.
                • The last inserted element is removed first.
                • TOP shows the current top element.
                • push() adds a new value at TOP.
                • pop() removes the value from TOP.
                • peek() reads TOP without removing it.
                • isEmpty() checks if stack has no elements.
                • isFull() checks if stack reached maximum capacity.
                • Push, pop, and peek usually take O(1) time.
                • Stack is useful in undo, browser back, recursion, and bracket matching.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE STACK EXAMPLES

                1. Plates in a pile:
                   The last plate placed on top is removed first.

                2. Undo feature:
                   The latest action is undone first.

                3. Browser back button:
                   The latest visited page is opened first when pressing back.

                4. Function calls:
                   The latest function call finishes first.

                5. Bracket matching:
                   Opening brackets are pushed and closing brackets pop.

                6. Recursion:
                   Recursive function calls are stored like a stack.
                """;
    }

    public String getPushCode() {
        return """
                PUSH OPERATION

                Meaning:
                push() adds a new value on TOP of the stack.

                Before push 40:
                TOP → [30]
                      [20]
                      [10]

                After push 40:
                TOP → [40]
                      [30]
                      [20]
                      [10]

                Built-in Java:

                Stack<Integer> stack = new Stack<>();
                stack.push(10);
                stack.push(20);
                stack.push(30);
                stack.push(40);

                Manual Logic:

                public boolean push(int value) {
                    if (top == stack.length - 1) {
                        return false;
                    }

                    top++;
                    stack[top] = value;
                    return true;
                }

                Step-by-step:
                1. Check if stack is full.
                2. Increase top by 1.
                3. Store value at stack[top].
                4. Return true.

                Time Complexity:
                O(1)
                """;
    }

    public String getPopCode() {
        return """
                POP OPERATION

                Meaning:
                pop() removes the value from TOP.

                Before pop:
                TOP → [30]
                      [20]
                      [10]

                After pop:
                TOP → [20]
                      [10]

                Removed value:
                30

                Built-in Java:

                int removed = stack.pop();

                Manual Logic:

                public Integer pop() {
                    if (top == -1) {
                        return null;
                    }

                    int removed = stack[top];
                    top--;
                    return removed;
                }

                Step-by-step:
                1. Check if stack is empty.
                2. Store stack[top] in removed variable.
                3. Decrease top by 1.
                4. Return removed value.

                Time Complexity:
                O(1)
                """;
    }

    public String getPeekCode() {
        return """
                PEEK OPERATION

                Meaning:
                peek() reads the TOP value without removing it.

                Stack:
                TOP → [30]
                      [20]
                      [10]

                peek() returns:
                30

                Stack after peek:
                TOP → [30]
                      [20]
                      [10]

                Nothing is removed.

                Built-in Java:

                int topValue = stack.peek();

                Manual Logic:

                public Integer peek() {
                    if (top == -1) {
                        return null;
                    }

                    return stack[top];
                }

                Time Complexity:
                O(1)
                """;
    }

    public String getDisplayCode() {
        return """
                DISPLAY STACK

                Meaning:
                Display means showing stack values from TOP to bottom.

                Stack internal array:
                [10, 20, 30]

                Stack visual:
                TOP → [30]
                      [20]
                      [10]

                Manual Logic:

                for (int i = top; i >= 0; i--) {
                    System.out.println(stack[i]);
                }

                Step-by-step:
                1. Start from top.
                2. Print current value.
                3. Move downward.
                4. Stop at index 0.

                Time Complexity:
                O(n)

                Why O(n)?
                Every stack element is visited once.
                """;
    }

    public String getBracketMatchingCode() {
        return """
                BRACKET MATCHING USING STACK

                Why stack is used:
                The last opened bracket must close first.

                Example:
                Expression: { [ ( ) ] }

                Process:
                1. Push opening bracket {
                2. Push opening bracket [
                3. Push opening bracket (
                4. Closing ) appears, pop (
                5. Closing ] appears, pop [
                6. Closing } appears, pop {

                If stack becomes empty at the end:
                Brackets are balanced.

                Manual Idea:

                for each character:
                    if character is opening bracket:
                        push it

                    if character is closing bracket:
                        if stack is empty:
                            expression is invalid

                        pop top bracket
                        check matching pair

                Time Complexity:
                O(n)
                """;
    }

    public String getUndoCode() {
        return """
                UNDO EXAMPLE USING STACK

                Actions:
                1. Type A
                2. Type B
                3. Type C

                Stack:
                TOP → [Type C]
                      [Type B]
                      [Type A]

                If user presses Undo:
                Type C is removed first.

                Built-in Java idea:

                Stack<String> actions = new Stack<>();
                actions.push("Type A");
                actions.push("Type B");
                actions.push("Type C");

                String lastAction = actions.pop();

                Output:
                Type C

                Why stack?
                Undo always removes the latest action first.
                """;
    }

    public String getEdgeCases() {
        return """
                STACK EDGE CASES

                1. Push into full stack:
                   If top == stack.length - 1, stack is full.
                   New value cannot be inserted.

                2. Pop from empty stack:
                   If top == -1, stack has no elements.
                   This is called stack underflow.

                3. Peek from empty stack:
                   There is no TOP value to read.

                4. Single element stack:
                   After one pop, stack becomes empty.

                5. Overflow:
                   Happens when fixed-size stack has no free space.

                6. Underflow:
                   Happens when pop or peek is called on empty stack.

                7. Display empty stack:
                   Program should show empty message instead of crashing.

                8. TOP safety:
                   top must always point to the latest inserted element.
                """;
    }
}