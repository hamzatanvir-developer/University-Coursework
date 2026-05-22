package com.muhammadhamza.algoquest.levels;

public class HashMapLevel {

    private static final int CAPACITY = 5;
    private final Entry[] buckets;

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

    public HashMapLevel() {
        buckets = new Entry[CAPACITY];

        put("name", "Ali");
        put("age", "20");
        put("course", "DSA");
    }

    private int hash(String key) {
        int hashCode = Math.abs(key.hashCode());
        return hashCode % CAPACITY;
    }

    public void put(String key, String value) {
        int bucketIndex = hash(key);
        Entry current = buckets[bucketIndex];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }

            current = current.next;
        }

        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[bucketIndex];
        buckets[bucketIndex] = newEntry;
    }

    public String get(String key) {
        int bucketIndex = hash(key);
        Entry current = buckets[bucketIndex];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }

            current = current.next;
        }

        return null;
    }

    public boolean remove(String key) {
        int bucketIndex = hash(key);
        Entry current = buckets[bucketIndex];
        Entry previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[bucketIndex] = current.next;
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
        return hash(key);
    }

    public String getConceptExplanation() {
        return """
                HASHMAP CONCEPT

                Simple Meaning:
                HashMap stores data in key-value pairs.

                Example:
                name → Ali
                age → 20
                course → DSA

                Key:
                The identifier used to find data.

                Value:
                The actual data stored against the key.

                Hash Function:
                Converts a key into a bucket index.

                Bucket:
                Internal storage location where entries are stored.

                Collision:
                When two different keys go to the same bucket.

                Time Complexity:
                Average put() = O(1)
                Average get() = O(1)
                Average remove() = O(1)

                Worst Case:
                O(n), if many keys collide in the same bucket.
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT HASHMAP POINTS

                • HashMap stores key-value pairs.
                • Key is used to find the value.
                • Value is the actual data stored.
                • Hash function converts key into bucket index.
                • Buckets store entries internally.
                • put() stores or updates data.
                • get() retrieves value using key.
                • remove() deletes data using key.
                • Collision means two keys go to same bucket.
                • Collision can be handled using chaining.
                • Average put/get/remove is O(1).
                • Worst case can become O(n) if many collisions happen.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE HASHMAP EXAMPLES

                1. Phone contacts:
                   name → phone number

                2. Student record:
                   roll number → student details

                3. Login system:
                   username → password hash

                4. Dictionary:
                   word → meaning

                5. Product catalog:
                   product ID → product details

                6. Country codes:
                   country code → country name
                """;
    }

    public String getPutCode() {
        return """
                PUT OPERATION

                Meaning:
                put() stores a key-value pair.

                Example:
                map.put("name", "Ali");

                Result:
                name → Ali

                Built-in Java:

                HashMap<String, String> map = new HashMap<>();
                map.put("name", "Ali");

                Manual put() Logic:

                public void put(String key, String value) {
                    int bucketIndex = hash(key);
                    Entry current = buckets[bucketIndex];

                    while (current != null) {
                        if (current.key.equals(key)) {
                            current.value = value;
                            return;
                        }

                        current = current.next;
                    }

                    Entry newEntry = new Entry(key, value);
                    newEntry.next = buckets[bucketIndex];
                    buckets[bucketIndex] = newEntry;
                }

                Step-by-step:
                1. Hash key to get bucket index.
                2. Check if key already exists.
                3. If key exists, update value.
                4. If key does not exist, create new entry.
                5. Add entry inside bucket.

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
                name → Ali

                map.get("name") returns:
                Ali

                Built-in Java:

                String value = map.get("name");

                Manual get() Logic:

                public String get(String key) {
                    int bucketIndex = hash(key);
                    Entry current = buckets[bucketIndex];

                    while (current != null) {
                        if (current.key.equals(key)) {
                            return current.value;
                        }

                        current = current.next;
                    }

                    return null;
                }

                Step-by-step:
                1. Hash key to find bucket.
                2. Search inside bucket chain.
                3. If key matches, return value.
                4. If key is not found, return null.

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
                remove() deletes a key-value pair using key.

                Example:
                Before:
                name → Ali
                age → 20

                remove("age")

                After:
                name → Ali

                Built-in Java:

                map.remove("age");

                Manual remove() Logic:

                public boolean remove(String key) {
                    int bucketIndex = hash(key);
                    Entry current = buckets[bucketIndex];
                    Entry previous = null;

                    while (current != null) {
                        if (current.key.equals(key)) {
                            if (previous == null) {
                                buckets[bucketIndex] = current.next;
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
                1. Hash key to find bucket.
                2. Search matching key.
                3. If first entry matches, move bucket head.
                4. If middle entry matches, skip that entry.
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
                3. % CAPACITY keeps index inside bucket range.
                4. Returned value is bucket index.

                Example:
                key = "name"
                bucketIndex = hash("name")

                Why hash function?
                It helps HashMap quickly decide where to store or search data.
                """;
    }

    public String getCollisionCode() {
        return """
                COLLISION HANDLING

                Collision means:
                Two different keys produce the same bucket index.

                Example:
                Bucket 2:
                name → Ali
                city → Lahore

                Manual Collision Handling:
                We can use chaining.

                Chaining means:
                Each bucket can store a linked list of entries.

                Entry structure:
                key
                value
                next

                Why collision happens:
                Bucket count is limited, but possible keys are many.

                Effect:
                Too many collisions can slow down HashMap operations.
                """;
    }

    public String getEdgeCases() {
        return """
                HASHMAP EDGE CASES

                1. Missing key:
                   get() returns null if key does not exist.

                2. Duplicate key:
                   put() with same key updates old value.

                3. Collision:
                   Two keys may go to the same bucket.

                4. Empty bucket:
                   If bucket has no entry, search returns null.

                5. Remove missing key:
                   remove() returns false or does nothing.

                6. Many collisions:
                   Performance can become O(n).

                7. Null key:
                   Java HashMap allows one null key.

                8. Null values:
                   Java HashMap allows multiple null values.

                9. Same hash but different key:
                   equals() check is needed to confirm actual key match.

                10. Resizing:
                    Real Java HashMap resizes when load factor becomes high.
                """;
    }
}