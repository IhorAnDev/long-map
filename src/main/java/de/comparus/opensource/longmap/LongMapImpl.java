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
     * @param  key   the key to be inserted
     * @param  value the value associated with the key
     * @return       the previous value associated with the key, or null if the key was not present
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
            index = (index + 1) % table.length;
            entry = table[index];
        }

        table[index] = new Entry<>(key, value);
        size++;

        return null;
    }

    /**
     * Retrieves the value associated with the specified key from the hash table.
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
            index = (index + 1) % table.length;
            entry = table[index];
        }

        return null;
    }

    /**
     * Removes the entry with the specified key from the hash table.
     *
     * @param  key  the key of the entry to be removed
     * @return      the value of the removed entry, or null if the key is not found
     */
    public V remove(long key) {
        int index = getIndex(key);
        Entry<V> entry = table[index];
        Entry<V> prev = null;

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
            index = (index + 1) % table.length;
            entry = table[index];
        }

        return null;
    }

    /**
     * Checks if the collection is empty.
     *
     * @return  true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the specified key is present in the map.
     *
     * @param  key  the key to be checked
     * @return      true if the key is present, false otherwise
     */
    public boolean containsKey(long key) {
        return get(key) != null;
    }

    /**
     * Checks if the given value is present in the table.
     *
     * @param  value the value to search for
     * @return true if the value is found, false otherwise
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
     * Returns an array of all the keys in the hash table.
     *
     * @return an array of type long[] containing all the keys in the hash table.
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
     * Returns an array containing all the values in the map.
     *
     * @return  an array containing all the values in the map
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
     * Clears the table by setting all elements to null and resetting the size.
     *
     * @return void  No return value.
     */
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    /**
     * Retrieves the index of a given key in the table.
     *
     * @param  key  the key whose index needs to be retrieved
     * @return      the index of the key in the table
     */
    private int getIndex(long key) {
        int index = (int) (key % table.length);
        if (index < 0) {
            index += table.length;
        }
        return index;
    }

    /**
     * Ensures that the capacity of the table is sufficient to accommodate new elements.
     * @return void  No return value.
     */
    private void ensureCapacity() {
        if (size >= table.length * LOAD_FACTOR) {
            rehash();
        }
    }

    /**
     * Rehashes the table by doubling its capacity and reassigning all the entries.
     *
     * @return void  No return value.
     */
    private void rehash() {
        Entry<V>[] oldTable = table;
        int newCapacity = table.length * 2;
        table = new Entry[newCapacity];
        size = 0;

        for (Entry<V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    private static class Entry<V> {
        long key;
        V value;
        Entry<V> next;

        Entry(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
