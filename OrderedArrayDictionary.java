package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/*  Brittany Saunders
    cssc0954
    Professor Riggins
    5/2/17
    Program 3 - Ordered Array Dictionary
 */

public class OrderedArrayDictionary<K extends Comparable<K>,V> implements DictionaryADT<K , V> {

    class ArrayElement<K, V> implements Comparable<ArrayElement<K, V>> {
        K key;
        V value;
        public ArrayElement (K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(ArrayElement<K, V> h) {
            return (((Comparable<K>)h.key).compareTo(this.key));
        }
    }

    ArrayElement<K, V> [] array;
    int size, tableSize;
    long count;

    public OrderedArrayDictionary(int s) {
        size = 0;
        tableSize = s;
        array = new ArrayElement[tableSize];
    }

    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key) {
        for(int i=0; i < size; i++)
            if((key).compareTo(array[i].key) == 0)
                return true;
        return false;    }

    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean add(K key, V value) {
        if(contains(key) || isFull())
            return false;
        if (isEmpty() == true) {
            array[0] = new ArrayElement<>(key, value);
        }
        int pos = binary(key, 0, size-1);
        for (int i = size-1; i >= pos; i--)
            array[i+1] = array[i];
        array[pos] = new ArrayElement<>(key, value);
        size++;
        return true;
    }

    private int binary (K key, int lo, int hi) {
        if(hi < lo)
            return lo;
        int mid = (lo+hi) >> 1;
        if((key).compareTo(array[mid].key) <= 0)
            return binary(key, lo, mid-1);
        return binary(key, mid+1, hi);
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean delete(K key) {
        if(isEmpty())
            return false;
        if(contains(key) == false)
            return false;
        else {
            int pos = binary(key, 0, size-1);
            array[pos].key = null;
            array[pos].value = null;
            for (int i = pos; i < size; i++)
                array[i] = array[i+1];
            size--;
            return true;
        }
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key) {
        if(isEmpty())
            return null;
        if(contains(key)) {
            int pos = binary(key, 0, size - 1);
            return array[pos].value;
        }
        return null;
    }

    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value) {
        for(int i = 0; i <= size; i++) {
            if((array[i].value).equals(value))
             return array[i].key;
        }
        return null;
    }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size() {
        return size;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull() {
        return size == tableSize;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the Dictionary object to an empty state.
    public void clear() {
        size = 0;
    }

    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order.  The iterator must be fail-fast.
    public Iterator<K> keys() {return new KeyIteratorHelper<K>();}

    // Returns an Iterator of the values in the dictionary.  The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    public Iterator<V> values() { return new ValueIteratorHelper<V>();}

    abstract class IteratorHelper<E> implements Iterator<E> {
        ArrayElement<K, V>[] arr;
        int index;
        long modification;
        int j;

        public IteratorHelper() {
            arr = new ArrayElement[tableSize];
            index = 0;
            j = 0;
            modification = count;
            for (int i = 0; i < tableSize; i++)
                    arr[j++] = array[i];
        }

        public boolean hasNext() {
            if (modification != count)
                throw new ConcurrentModificationException();
            return index < size;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K>{
        public KeyIteratorHelper() {
            super();
        }
        public K next(){
            return (K) array[index++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V>{
        public ValueIteratorHelper() {
            super();
        }
        public V next(){
            return (V) array[index++].value;
        }
    }

}