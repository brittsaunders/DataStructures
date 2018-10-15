package data_structures;

/*     Brittany Saunders
       cssc0954
       Assignment 2
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private OrderedList<E> list;
    private Node<E> head;
    private Node<E> tail;
    private int currentSize, loc;
    E temp;
    public OrderedListPriorityQueue(){
        list = new OrderedList<E>();

    }

    class Node <E> {
        E data;
        Node<E> next;
        public Node(E obj) {
            data = obj;
            next = null;
        }
    }

    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E object) {
        list.insert(object);
        return true;
    }

    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove() {
        if(list.size() == 0)
            return null;
        return list.removeFirst();
    }

    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it.
    //  Returns null if the PQ is empty.
    public E peek() {
        if(list.size() == 0)
            return null;
        return list.get(1);
    }

    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
    public boolean contains(E obj) {
        return list.contains(obj);
    }

    //  Returns the number of objects currently in the PQ.
    public int size() {
        return list.size();
    }

    //  Returns the PQ to an empty state.
    public void clear() {
        if(list != null)
            list.clear();
    }

    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty() {
        return list.isEmpty();
    }

    //  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
    public boolean isFull() {
        return false;
    }

    //  Returns an iterator of the objects in the PQ, in no particular
    //  order.  The iterator must be fail-fast.
    public Iterator<E> iterator(){
        return new OrderedListPriorityQueue.IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {

        Node<E> index;
        public IteratorHelper() {
            index = head;
        }
        public boolean hasNext() {
            return (index != null);
        }
        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();
            E value = index.data;
            index = index.next;
            return value;
        }
    }

}

