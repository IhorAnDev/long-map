# long-map <br>

**Finish development of class LongMapImpl, which implements a map with keys of type long. It has to be a hash table (like HashMap). Requirements: <br>**

* it should not use any known Map implementations; <br>
* it should use as less memory as possible and have adequate performance;<br>
*  the main aim is to see your codestyle and test-style.
<hr>

# Basic implementation<br>
This implementation is placed in basic branch. <br>
This implementation uses separate chaining to handle collisions, 
and it dynamically resizes the table when the load factor exceeds a certain threshold.<br> 
It also handles the special case where Long.MIN_VALUE is used as a key separately, 
as it cannot be negated. <br>

## Main features
Collision Handling: Separate Chaining.
* When a collision occurs (two keys hashing to the same index), a linked list of entries was used at that index to store multiple key-value pairs.<br>
* This approach allows multiple entries with the same index to coexist, making it easier to handle collisions. <br>

Resizing: Dynamic Resizing with Separate Chaining.
* The table is  dynamically resized when the load factor (the ratio of the number of entries to the table size) exceeded a certain threshold.
The linked lists within the buckets remained relatively constant.

Storage Overhead:
* Separate chaining can introduce some overhead due to the linked lists, especially when there are many collisions.

## Testing
In this implementation I used **_Junit 4_** for testing methods.
<hr>

# Medium implementation
This implementation is placed in medium branch. <br>
This implementation using linear probing is designed for better performance in scenarios with frequent collisions and aims for better memory efficiency due to the absence of linked lists.<br>
It dynamically resizes the table when needed, similar to the previous implementation.

## Main features
Collision Handling: Linear Probing.
* When a collision occurs, the code searches for the next available slot (by incrementing the index) until an empty slot is found.<br>
* This approach directly stores key-value pairs in the array without using linked lists, and it ensures that the table remains compact.<br>
  
Resizing: Dynamic Resizing with Linear Probing.
* The table is also dynamically resized when the load factor exceeds a threshold. 
However, the resizing process is slightly different. 
All entries are rehashed and placed in a new table with a larger size.

Storage Overhead:
* Linear probing avoids the overhead of linked lists, which can lead to better memory efficiency in scenarios with frequent collisions.



## Testing
In this implementation I used _Junit 5_ for testing methods.




