package com.muhammadhamza.algoquest.levels;

public class ArrayLevel {

    private final int[] array;
    private int size;

    public ArrayLevel() {
        array = new int[10];
        array[0] = 10;
        array[1] = 20;
        array[2] = 30;
        array[3] = 40;
        size = 4;
    }

    public int[] getVisibleArray() {
        int[] visible = new int[size];

        for (int i = 0; i < size; i++) {
            visible[i] = array[i];
        }

        return visible;
    }

    public int accessByIndex(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }

        return array[index];
    }

    public boolean insertAt(int index, int value) {
        if (size == array.length) {
            return false;
        }

        if (index < 0 || index > size) {
            return false;
        }

        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }

        array[index] = value;
        size++;
        return true;
    }

    public boolean deleteAt(int index) {
        if (size == 0) {
            return false;
        }

        if (index < 0 || index >= size) {
            return false;
        }

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        size--;
        return true;
    }

    public int linearSearch(int target) {
        for (int i = 0; i < size; i++) {
            if (array[i] == target) {
                return i;
            }
        }

        return -1;
    }

    public String getConceptExplanation() {
        return """
                ARRAY CONCEPT

                Simple Meaning:
                An array stores multiple values under one variable name.

                Visual Example:
                VALUE:    [10] [20] [30] [40]
                INDEX:      0    1    2    3
                POSITION: First Second Third Fourth
                SIZE: 4 elements

                Java Example:
                int[] numbers = {10, 20, 30, 40};

                Important:
                Array index starts from 0, not 1.

                Example:
                numbers[0] = 10
                numbers[1] = 20
                numbers[2] = 30
                numbers[3] = 40

                Why arrays are useful:
                Arrays allow us to store related values together and access them using index numbers.

                Time Complexity:
                Access by index = O(1)
                Search in unsorted array = O(n)
                Insert at middle/start = O(n)
                Delete from middle/start = O(n)
                """;
    }

    public String getAccessExplanation() {
        return """
                ACCESS BY INDEX

                Meaning:
                Access means reading a value directly from a specific index.

                Example:
                Array: [10, 20, 30, 40]
                Index:   0   1   2   3

                Access index 2:
                array[2] = 30

                Built-in Java:
                int[] numbers = {10, 20, 30, 40};
                int value = numbers[2];

                Manual Logic:

                public int accessByIndex(int index) {
                    if (index < 0 || index >= size) {
                        return -1;
                    }

                    return array[index];
                }

                Step-by-step:
                1. Check if index is valid.
                2. If index is invalid, return -1.
                3. If index is valid, directly return array[index].

                Time Complexity:
                O(1)

                Why O(1)?
                Array can directly jump to the required index.
                """;
    }

    public String getInsertEndExplanation() {
        return """
                INSERT AT END

                Meaning:
                Insert at end means adding a new value after the last active element.

                Before:
                [10] [20] [30]

                Insert 50 at end.

                After:
                [10] [20] [30] [50]

                Manual Logic:
                array[size] = value;
                size++;

                Step-by-step:
                1. Check if array has free space.
                2. Place value at index size.
                3. Increase size by 1.

                Time Complexity:
                O(1), if free space is available.

                Important:
                In a fixed-size array, insertion fails if the array is already full.
                """;
    }

    public String getInsertMiddleExplanation() {
        return """
                INSERT AT MIDDLE

                Meaning:
                Insert at middle means placing a value at a specific index and shifting existing values right.

                Before:
                [10] [20] [30]
                  0    1    2

                Insert 25 at index 1.

                Step 1:
                Shift 30 right.

                Step 2:
                Shift 20 right.

                Step 3:
                Put 25 at index 1.

                After:
                [10] [25] [20] [30]

                Manual Logic:

                for (int i = size; i > index; i--) {
                    array[i] = array[i - 1];
                }

                array[index] = value;
                size++;

                Time Complexity:
                O(n)

                Why O(n)?
                Because many elements may need to shift.
                """;
    }

    public String getInsertStartExplanation() {
        return """
                INSERT AT START

                Meaning:
                Insert at start means adding a value at index 0.

                Before:
                [10] [20] [30]

                Insert 5 at index 0.

                After:
                [5] [10] [20] [30]

                What happens internally:
                1. 30 shifts right.
                2. 20 shifts right.
                3. 10 shifts right.
                4. 5 is placed at index 0.

                Time Complexity:
                O(n)

                Why O(n)?
                Every existing element must move one step right.

                Important:
                Insert at start is slower than insert at end.
                """;
    }

    public String getDeleteExplanation() {
        return """
                DELETE OPERATION

                Meaning:
                Delete means removing an element from the array.

                Example:
                Array: [10, 20, 30, 40]
                Delete index 1.

                Value at index 1 is 20.

                After deleting 20:
                [10, 30, 40]

                What happens internally:
                1. Remove value at index 1.
                2. Shift 30 left.
                3. Shift 40 left.
                4. Decrease size.

                Manual Logic:

                for (int i = index; i < size - 1; i++) {
                    array[i] = array[i + 1];
                }

                size--;

                Time Complexity:
                Delete from end = O(1)
                Delete from start/middle = O(n)
                """;
    }

    public String getFindExplanation() {
        return """
                FIND / LINEAR SEARCH

                Meaning:
                Search means finding whether a value exists in the array.

                Example:
                Array: [10, 20, 30, 40]
                Target: 30

                Search process:
                Check index 0 → 10, not match.
                Check index 1 → 20, not match.
                Check index 2 → 30, found.

                Result:
                30 is found at index 2.

                Manual Logic:

                public int linearSearch(int target) {
                    for (int i = 0; i < size; i++) {
                        if (array[i] == target) {
                            return i;
                        }
                    }

                    return -1;
                }

                Time Complexity:
                Best case = O(1)
                Worst case = O(n)

                If value is missing:
                return -1
                """;
    }

    public String getEdgeCases() {
        return """
                ARRAY EDGE CASES

                1. Access invalid index:
                   If index < 0 or index >= size, the index is invalid.

                2. Insert when array is full:
                   Fixed-size array cannot accept new values when full.

                3. Delete from empty array:
                   If size is 0, no element exists to delete.

                4. Search missing value:
                   If value does not exist, linear search returns -1.

                5. Insert at start:
                   All elements shift right, so it takes O(n).

                6. Delete at start:
                   All remaining elements shift left, so it takes O(n).

                7. Insert at end:
                   If free space exists, it takes O(1).

                8. Delete at end:
                   Only size decreases, so it takes O(1).

                9. Duplicate values:
                   Arrays can store duplicate values.

                10. Fixed capacity:
                    Normal arrays have limited capacity.
                    If more space is needed, a bigger array must be created.
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT ARRAY POINTS

                • Array stores multiple values under one name.
                • Index starts from 0.
                • Access by index is O(1).
                • Linear search is O(n).
                • Insert at start or middle is O(n).
                • Delete from start or middle is O(n).
                • Insert at end is O(1) if space is available.
                • Fixed-size arrays do not grow automatically.
                • ArrayList is used in Java when dynamic size is needed.
                """;
    }
}