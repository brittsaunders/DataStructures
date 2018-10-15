package data_structures;

/*  Brittany Saunders
    cssc0954
    Professor Riggins
    5/2/17
    Program 3 - Hash Table
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class HashTable<K extends Comparable<K>, V> implements DictionaryADT<K,V> {
    class HashElement<K,V> implements Comparable<HashElement<K,V>> {
        K key;
        V value;
        public HashElement(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public int compareTo(HashElement<K,V> h) {
            return ((Comparable<K>)h.key).compareTo((this.key));
        }
    }

    UnorderedList<HashElement<K,V>> [] hash_list;
    int tableSize, size, maxSize;
    long count;

    public HashTable(int s) {
        maxSize = s;
        size=0;
        tableSize = (int) (maxSize * 1.3f);
        count = 0;
        hash_list = (UnorderedList<HashElement<K, V>>[]) new UnorderedList[tableSize];
        for (int i = 0; i < tableSize; i++)
            hash_list[i] = new UnorderedList<>();
    }

    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key){
        int index = (key.hashCode() & 0x7FFFFFFF) % tableSize;
        UnorderedList<HashElement<K,V>> list = hash_list[index];
        for(HashElement<K,V> hashEl : list) {
            if(hashEl.key.equals(key))
                return true;
        }
        return false;
    }

    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean add(K key, V value){
        HashElement<K,V> hashEl = new HashElement<>(key, value);
        int index = (key.hashCode() & 0x7FFFFFFF) % tableSize; //index of linked list in array position
        if(isFull() || contains(key))
            return false;
        hash_list[index].add(hashEl);
        size++;
        return true;
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean delete(K key){
        int index = (key.hashCode() & 0x7FFFFFFF) % tableSize;
        for(HashElement<K,V> hashEl : hash_list[index]) {
            if(hashEl.key.equals(key)) {
                hash_list[index].remove(hashEl);
                size--;
                return true;
            }
        }
        return false;
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key){
        int index = (key.hashCode() &0x7FFFFFFF) % tableSize;
        for(HashElement<K,V> hashEl : hash_list[index])
            if((key).compareTo(hashEl.key) == 0)
                return hashEl.value;
        return null;
    }

    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value){
        for(int i = 0; i < tableSize; i++) {
            for(HashElement<K,V> hashEl : hash_list[i]) {
                if ((((Comparable<V>)hashEl.value).compareTo(value)) == 0)
                    return hashEl.key;
            }
        }
        return null;
    }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size(){
        return size;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        return size == tableSize;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        return size == 0;
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        for(int i=0; i<tableSize; i++)
            hash_list[i].clear();
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
        HashElement<K, V>[] hsh;
        int index;
        long modifications;
        public IteratorHelper() {
            hsh = new HashElement[size];
            index = 0;
            int p = 0;
            modifications = count;
            for (int i = 0; i < tableSize; i++)
                if(hash_list[i] != null)
                    for (HashElement h : hash_list[i])
                        if(h != null)
                            hsh[p++] = h;
            hsh = shellSort(hsh);
        }

        public boolean hasNext() {
            if (modifications != count)
                throw new ConcurrentModificationException();
            return index < size;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private HashElement<K, V>[] shellSort(HashElement<K, V>[] array) {
            HashElement<K, V>[] n = array;
            int in, out, h = 1;
            HashElement<K,V> tmp;
            size = n.length;
            while (h <= size / 3) //calculate gaps
                h = h * 3 + 1;
            while (h > 0) {
                for (out = h; out < size; out++) {
                    tmp = n[out];
                    in = out;
                    while (in > h - 1 && n[in - h].compareTo(tmp) <= 0) {
                        n[in] = n[in - h];
                        in -= h;
                    }
                    n[in] = tmp;

                }// end for
                h = (h - 1) / 3;
            } //end while
            return n;
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K>{
        public KeyIteratorHelper() {
            super();
        }
        public K next(){
            return (K) hsh[index++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V>{
        public ValueIteratorHelper() {
            super();
        }
        public V next(){
            return (V) hsh[index++].value;
        }
    }
}
