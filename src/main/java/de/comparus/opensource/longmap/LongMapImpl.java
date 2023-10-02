package de.comparus.opensource.longmap;

import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<V>[] table;
    private int size;

    public LongMapImpl() {
        this(DEFAULT_CAPACITY);
    }

    public LongMapImpl(int capacity) {
        table = new Entry[capacity];
    }
    /**
     * Puts a key-value pair into the map.
     *
     * @param  key    the key to be inserted
     * @param  value  the value to be associated with the key
     * @return        the previous value associated with the key, or null if the key was not present
     */
    public V put(long key, V value) {
        ensureCapacity();

        int index = getIndex(key);
        Entry<V> entry = table[index];
        while (entry != null) {
            if (entry.key == key) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            entry = entry.next;
        }

        table[index] = new Entry<>(key, value, table[index]);
        size++;
        return null;
    }
    /**
     * Retrieves the value associated with the specified key from this map.
     *
     * @param  key  the key whose associated value is to be retrieved
     * @return      the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(long key) {

        int index = getIndex(key);
        Entry<V> entry = table[index];
        while (entry != null) {
            if (entry.key == key) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }
    /**
     * Removes the entry with the specified key from the hash table.
     *
     * @param  key  the key of the entry to be removed
     * @return      the value associated with the removed entry, or null if the key is not found
     */
    public V remove(long key) {
        int index = getIndex(key);
        Entry<V> prev = null;
        Entry<V> entry = table[index];
        while (entry != null) {
            if (entry.key == key) {
                if (prev != null) {
                    prev.next = entry.next;
                } else {
                    table[index] = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }
        return null;
    }
    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Check if the given key exists in the data structure.
     *
     * @param  key  the key to check
     * @return      true if the key exists, false otherwise
     */
    public boolean containsKey(long key) {
        return get(key) != null;
    }

    /**
     * Checks if the given value is contained in the map.
     *
     * @param  value  the value to be checked
     * @return        true if the value is found, false otherwise
     */
    public boolean containsValue(V value) {
        for (Entry<V> entry : table) {
            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * Generates an array of long keys representing the keys in the hash table.
     *
     * @return         	an array of long keys
     */
    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;
        for (Entry<V> entry : table) {
            while (entry != null) {
                keys[index++] = entry.key;
                entry = entry.next;
            }
        }
        return keys;
    }

    /**
     * Retrieves all the values in the hash table and returns them as an array.
     *
     * @return         	an array containing all the values in the hash table
     */
    public V[] values() {
        V[] values = (V[]) new Object[size];
        int index = 0;
        for (Entry<V> entry : table) {
            while (entry != null) {
                values[index++] = entry.value;
                entry = entry.next;
            }
        }
        return values;
    }

    /**
     * Returns the size of the object.
     *
     * @return the size of the object
     */
    public long size() {
        return size;
    }

    /**
     * Clears the table by setting all elements to null and resetting the size to 0.
     *
     * @return void		This function does not return any value.
     */
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    /**
     * Returns the index in the table for the given key.
     *
     * @param  key  the key to calculate the index for
     * @return      the index in the table for the given key
     */
    private int getIndex(long key) {
        return (int) (key % table.length);
    }

    /**
     * Ensures that the capacity of the table is sufficient to accommodate
     * the current number of elements. If the size of the table exceeds
     * the load factor threshold, the table is resized and the elements
     * are rehashed.
     * This method is called internally and is not intended to be called
     * directly by the user.
     *
     * @return void This function does not return any value.
     */
    private void ensureCapacity() {
        if (size >= table.length * LOAD_FACTOR) {
            int newCapacity = table.length * 2;
            rehash(newCapacity);
        }
    }

    /**
     * Rehashes the hash table with a new capacity.
     *
     * @param  newCapacity   the new capacity of the hash table
     */
    private void rehash(int newCapacity) {
        Entry<V>[] newTable = new Entry[newCapacity];
        for (Entry<V> entry : table) {
            while (entry != null) {
                int newIndex = getIndex(entry.key, newCapacity);
                Entry<V> next = entry.next;
                entry.next = newTable[newIndex];
                newTable[newIndex] = entry;
                entry = next;
            }
        }
        table = newTable;
    }

    /**
     * Computes the index of a key in a hash table based on the given capacity.
     *
     * @param  key       the key to compute the index for
     * @param  capacity  the capacity of the hash table
     * @return           the index of the key in the hash table
     */
    private int getIndex(long key, int capacity) {
        return (int) (key % capacity);
    }

    private static class Entry<V> {
        long key;
        V value;
        Entry<V> next;

        Entry(long key, V value, Entry<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
