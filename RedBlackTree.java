package data_structures;

/*  Brittany Saunders
    cssc0954
    Professor Riggins
    5/2/17
    Program 3 - Red Black Tree
 */

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RedBlackTree<K extends Comparable<K>,V> implements DictionaryADT<K , V> {

    class RBTree<K, V> implements Comparable<RBTree<K, V>> {
        K key;
        V value;
        public RBTree (K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(RBTree<K, V> h) {
            return (((Comparable<K>)h.key).compareTo(this.key));
        }
    }

    TreeMap<K,V> tree;
    int size;

    public RedBlackTree() {
        tree = new TreeMap<>();
        size = 0;
    }

    public boolean contains(K key) {
        return tree.containsKey(key);
    }

    public boolean add(K key, V value) {
        if(contains(key))
            return false;
        return tree.put(key, value) != null;
    }

    public boolean delete(K key) {
        return tree.remove(key) != null;
    }

    public V getValue(K key) {
        return tree.get(key);
    }

    public K getKey(V value) {
        Iterator<Map.Entry<K,V>> it = tree.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<K,V> temp = it.next();
                if (((Comparable<K>)temp.getValue()).compareTo((K) value) == 0)
                    return temp.getKey();
        }
        return null;
    }

    public int size() {
        return tree.size();
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return tree.size() == 0;
    }

    public void clear() {
        tree.clear();
    }

    public Iterator<K> keys() {
        return tree.keySet().iterator();
    }

    public Iterator<V> values() {
        return tree.values().iterator();
    }
}
