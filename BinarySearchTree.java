package data_structures;

/*  Brittany Saunders
    cssc0954
    Professor Riggins
    5/2/17
    Program 3 - Binary Search Tree
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree<K extends Comparable<K>,V> implements DictionaryADT<K,V> {
    public class binaryNode<K, V> {
        K key;
        V value;
        binaryNode<K, V> leftChild, rightChild;

        public binaryNode(K key, V value) {
            this.key = key;
            this.value = value;
            leftChild = rightChild = null;
        }
    }

    binaryNode<K, V> root;
    int size;
    long count;

    public BinarySearchTree() {
        root = null;
        size = 0;
        count = 0;
    }

    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key) {
        if (isEmpty())
            return false;
        return contains(key, root);
    }

    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    boolean contains(K key, binaryNode<K, V> node) {
        if(node == null)
            return false;
        if ((key).compareTo(node.key) == 0)
            return true;
        if ((key).compareTo(node.key) < 0)
            return contains(key, node.leftChild);
        else if ((key).compareTo(node.key) > 0)
            return contains(key, node.rightChild);
        else
            return false;
    }


    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean add(K key, V value) {
        if (contains(key))
            return false;
        if (root == null)
            root = new binaryNode<>(key, value);
        else
            insert(key, value, root, null, false);
        size++;
        count++;
        return true;
    }

    public void insert(K key, V value, binaryNode<K, V> node, binaryNode<K, V> parent, boolean wasLeft) {
        if (node == null)
            if (wasLeft)
                parent.leftChild = new binaryNode<>(key, value);
            else
                parent.rightChild = new binaryNode<>(key, value);
        else if ((key).compareTo(node.key) < 0)
            insert(key, value, node.leftChild, node, true);
        else if ((key).compareTo(node.key) > 0)
            insert(key, value, node.rightChild, node, false);
        else
            return;
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean delete(K key) {
        if (isEmpty())
            return false;
        if (!delete(key, root, null, false))
            return false;
        size--;
        count++;
        return true;
    }

    //Delete conditions to test for null tree, no children, one child, or two children
    private boolean delete(K key, binaryNode<K, V> node, binaryNode<K, V> parent, boolean wasLeft) {
        if (node == null) return false;
        else if ((key).compareTo(node.key) < 0)
            return delete(key, node.leftChild, node, true);
        else if ((key).compareTo(node.key) > 0)
            return delete(key, node.rightChild, node, false);
        // testing case for no children
        else {
            if (node.leftChild == null && node.rightChild == null) {
                if(node == root)
                    root = null;
                else if (wasLeft)
                    parent.leftChild = null;
                else parent.rightChild = null;
            }
            // testing case for one child
            else if (node.rightChild == null) {
                if(node == root)
                    root = node.leftChild;
                else if (wasLeft)
                    parent.leftChild = node.leftChild;
                else
                    parent.rightChild = node.leftChild;
            }
            else if (node.leftChild == null) {
                if(node == root)
                    root = node.rightChild;
                else if(wasLeft)
                    parent.leftChild = node.rightChild;
                else
                    parent.leftChild = node.rightChild;
            }
            // testing case for two children
            else {
                binaryNode<K, V> tmp = getSuccessor(node.rightChild);
                node.key = tmp.key;
                node.value = tmp.value;
                tmp.key = key;
                return delete(key,node.rightChild,node,false);
            }
        }
        return true;
    }

    //delete helper method to traverse down the tree
    private binaryNode<K, V> getSuccessor(binaryNode<K, V> node) {
        if(node.leftChild == null)
            return node;
        return getSuccessor(node.leftChild);
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key) {
        return findValue(key, root);
    }

    //traverses down tree to get and return value associated with key
    public V findValue(K key, binaryNode<K, V> node) {
        if (node == null)
            return null;
        if ((key).compareTo(node.key) < 0)
            return findValue(key, node.leftChild);  //go to the left
        if ((key).compareTo(node.key) > 0)
            return findValue(key, node.rightChild); //go to the right
        return node.value;
    }

    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    K keyFinder;
    public K getKey(V value) {
        keyFinder = null;
        findKey(root,value);
        return keyFinder;
    }

    //traverses down tree, comparing values until specified one is found then returns key associated with that value
    public void findKey(binaryNode<K, V> node, V value) {
        if (node == null) return;
            findKey(node.leftChild, value);
        if (((Comparable<V>) value).compareTo(node.value) == 0) {
            keyFinder = node.key;
            return;
        }
        findKey(node.rightChild, value);
    }



    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size(){
        return size;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        return false;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        if(size == 0 || root == null)
            return true;
        return false;
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        size = 0;
        count = 0;
        root = null;
    }
    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order.  The iterator must be fail-fast.
    public Iterator<K> keys(){
        return new KeyHelper<>();
    }

    // Returns an Iterator of the values in the dictionary.  The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    public Iterator<V> values(){
        return new ValueHelper<>();
    }

    abstract class IteratorHelper<E> implements Iterator<E>{
        binaryNode<K,V> [] nodes ;
        int indx;
        long modification;

        public IteratorHelper(){
            nodes = new binaryNode[size];
            indx = 0;
            modification = count;
            inOrder(root);
        }

        int i = 0;
        private void inOrder(binaryNode<K,V> n){
            if(n == null)
                return;
            inOrder(n.leftChild);
            nodes[i++] = n;
            inOrder(n.rightChild);
        }

        public boolean hasNext(){
            if(modification != count)
                throw new ConcurrentModificationException();
            return indx < size;
        }
    }

    class KeyHelper<K> extends IteratorHelper<K>{
        public KeyHelper() {
            super();
        }
        public K next(){
            return (K) nodes[indx++].key;
        }
    }

    class ValueHelper<V> extends IteratorHelper<V>{
        public ValueHelper() {
            super();
        }
        public V next(){
            return (V) nodes[indx++].value;
        }
    }

}