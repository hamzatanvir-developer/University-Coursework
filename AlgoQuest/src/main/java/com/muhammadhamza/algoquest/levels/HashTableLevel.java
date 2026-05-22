package com.muhammadhamza.algoquest.levels;

public class HashTableLevel {

    private static final int CAPACITY = 5;
    private final Entry[] table;

    private static class Entry {
        String key;
        String value;
        Entry next;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public HashTableLevel() {
        table = new Entry[CAPACITY];

        put("id", "101");
        put("course", "DSA");
        put("name", "Ali");
    }

    private int hash(String key) {
        int hashCode = Math.abs(key.hashCode());
        return hashCode % CAPACITY;
    }

    public boolean put(String key, String value) {
        if (key == null || value == null) {
            return false;
        }

        int index = hash(key);
        Entry current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return true;
            }

            current = current.next;
        }

        Entry newEntry = new Entry(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;

        return true;
    }

    public String get(String key) {
        if (key == null) {
            return null;
        }

        int index = hash(key);
        Entry current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }

            current = current.next;
        }

        return null;
    }

    public boolean remove(String key) {
        if (key == null) {
            return false;
        }

        int index = hash(key);
        Entry current = table[index];
        Entry previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    public int getBucketIndex(String key) {
        if (key == null) {
            return -1;
        }

        return hash(key);
    }

    public String getConceptExplanation() {
        return """
                HASHTABLE CONCEPT

                Simple Meaning:
                Hashtable stores data in key-value pairs.

                Example:
                id → 101
                course → DSA
                name → Ali

                Key:
                Used to find the value.

                Value:
                The actual data stored against the key.

                Hash Function:
                Converts key into bucket/index.

                Bucket:
                Internal place where entries are stored.

                Important Java Difference:
                Hashtable is synchronized.
                Hashtable does not allow null key.
                Hashtable does not allow null value.

                Time Complexity:
                Average put() = O(1)
                Average get() = O(1)
                Average remove() = O(1)

                Worst Case:
                O(n), if many keys collide in same bucket.
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT HASHTABLE POINTS

                • Hashtable stores key-value pairs.
                • Key is used to find value.
                • Value is the actual stored data.
                • Hash function calculates bucket index.
                • put() stores or updates data.
                • get() retrieves data using key.
                • remove() deletes data using key.
                • Collision means two keys map to the same bucket.
                • Java Hashtable is synchronized.
                • Java Hashtable does not allow null key.
                • Java Hashtable does not allow null value.
                • Average put/get/remove is O(1).
                • Worst case can become O(n) if many collisions happen.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE HASHTABLE EXAMPLES

                1. Student database:
                   student ID → student information

                2. Employee system:
                   employee ID → employee details

                3. Product inventory:
                   product code → product stock

                4. Library system:
                   book ISBN → book details

                5. Login system:
                   username → stored password hash

                6. Bank account lookup:
                   account number → account details
                """;
    }

    public String getPutCode() {
        return """
                PUT OPERATION

                Meaning:
                put() stores a key-value pair.

                Example:
                table.put("course", "DSA");

                Result:
                course → DSA

                Built-in Java:

                Hashtable<String, String> table = new Hashtable<>();
                table.put("course", "DSA");

                Manual put() Logic:

                public boolean put(String key, String value) {
                    if (key == null || value == null) {
                        return false;
                    }

                    int index = hash(key);
                    Entry current = table[index];

                    while (current != null) {
                        if (current.key.equals(key)) {
                            current.value = value;
                            return true;
                        }

                        current = current.next;
                    }

                    Entry newEntry = new Entry(key, value);
                    newEntry.next = table[index];
                    table[index] = newEntry;

                    return true;
                }

                Step-by-step:
                1. Reject null key or null value.
                2. Hash key to find bucket.
                3. If key already exists, update value.
                4. Otherwise create new entry.
                5. Use chaining if bucket already has entries.

                Average Time:
                O(1)

                Worst Time:
                O(n)
                """;
    }

    public String getGetCode() {
        return """
                GET OPERATION

                Meaning:
                get() retrieves value using key.

                Example:
                course → DSA

                table.get("course") returns:
                DSA

                Built-in Java:

                String value = table.get("course");

                Manual get() Logic:

                public String get(String key) {
                    if (key == null) {
                        return null;
                    }

                    int index = hash(key);
                    Entry current = table[index];

                    while (current != null) {
                        if (current.key.equals(key)) {
                            return current.value;
                        }

                        current = current.next;
                    }

                    return null;
                }

                Step-by-step:
                1. Reject null key.
                2. Hash key to find bucket.
                3. Search entries inside bucket.
                4. Return value if key matches.
                5. Return null if key is missing.

                Average Time:
                O(1)

                Worst Time:
                O(n)
                """;
    }

    public String getRemoveCode() {
        return """
                REMOVE OPERATION

                Meaning:
                remove() deletes data using key.

                Example:
                Before:
                id → 101
                course → DSA

                remove("course")

                After:
                id → 101

                Built-in Java:

                table.remove("course");

                Manual remove() Logic:

                public boolean remove(String key) {
                    if (key == null) {
                        return false;
                    }

                    int index = hash(key);
                    Entry current = table[index];
                    Entry previous = null;

                    while (current != null) {
                        if (current.key.equals(key)) {
                            if (previous == null) {
                                table[index] = current.next;
                            } else {
                                previous.next = current.next;
                            }

                            return true;
                        }

                        previous = current;
                        current = current.next;
                    }

                    return false;
                }

                Step-by-step:
                1. Reject null key.
                2. Hash key to find bucket.
                3. Search matching entry.
                4. Remove entry by changing links.
                5. Return true if removed.
                6. Return false if key is missing.

                Average Time:
                O(1)

                Worst Time:
                O(n)
                """;
    }

    public String getHashFunctionCode() {
        return """
                HASH FUNCTION

                Meaning:
                Hash function converts key into bucket index.

                Manual Hash Function:

                private int hash(String key) {
                    int hashCode = Math.abs(key.hashCode());
                    return hashCode % CAPACITY;
                }

                Step-by-step:
                1. key.hashCode() converts key into an integer.
                2. Math.abs() makes it positive.
                3. % CAPACITY keeps it inside table range.
                4. Result becomes bucket index.

                Example:
                key = "course"
                bucket = hash("course")

                Purpose:
                Hash function decides where data should be stored.
                """;
    }

    public String getCollisionCode() {
        return """
                COLLISION HANDLING

                Collision means:
                Two different keys produce the same bucket index.

                Example:
                Bucket 1:
                id → 101
                code → SE101

                Manual Collision Handling:
                We use chaining.

                Chaining means:
                Each bucket can store multiple entries through next links.

                Entry structure:
                key
                value
                next

                Why collision matters:
                Too many collisions make operations slower.
                """;
    }

    public String getDifferenceCode() {
        return """
                HASHMAP VS HASHTABLE

                HashMap:
                • Not synchronized by default.
                • Allows one null key.
                • Allows multiple null values.
                • More commonly used in modern Java.

                Hashtable:
                • Synchronized.
                • Does not allow null key.
                • Does not allow null value.
                • Older Java class.

                Simple Meaning:
                Both store key-value pairs.

                Main Difference:
                Hashtable is synchronized and stricter.
                HashMap is usually preferred in modern Java for normal use.

                Beginner Tip:
                Use HashMap in most normal programs.
                Learn Hashtable to understand older synchronized key-value storage.
                """;
    }

    public String getEdgeCases() {
        return """
                HASHTABLE EDGE CASES

                1. Null key:
                   Java Hashtable does not allow null key.

                2. Null value:
                   Java Hashtable does not allow null value.

                3. Missing key:
                   get() returns null if key is not found.

                4. Duplicate key:
                   put() with same key updates old value.

                5. Collision:
                   Multiple keys may go to the same bucket.

                6. Remove missing key:
                   remove() returns false or does nothing.

                7. Empty table:
                   No key-value pair exists.

                8. Same bucket chain:
                   Search may need to move through multiple entries.

                9. Many collisions:
                   Worst-case operation time can become O(n).

                10. Synchronization:
                    Hashtable is synchronized, so it is thread-safe by default,
                    but it can be slower than HashMap in normal single-thread use.
                """;
    }
}