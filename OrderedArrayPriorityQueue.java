package data_structures;

/*     Brittany Saunders
       cssc0954
       Assignment 2
*/

import java.util.Iterator;
import java.util.NoSuchElementException;


public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {


    private E[] array;
    private int size, maxSize, currSize;

    public OrderedArrayPriorityQueue(int S) {
        size = 0;
        currSize = S;
        array = (E[]) new Comparable[DEFAULT_MAX_CAPACITY];
        maxSize = currSize;
    }

    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E object) {
        if (isFull() == true)
            return false;
        int pos = binary(object, 0, size-1);
        for (int i = size-1; i >= pos; i--)
            array[i + 1] = array[i];
        array[pos] = object;
        size++;
        return true;
    }

    private int binary (E obj, int lo, int hi) {
        if(hi < lo)
            return lo;
        int mid = (lo+hi) >> 1;
        if((obj).compareTo(array[mid]) >= 0)
            return binary(obj, lo, mid-1);
        return binary(obj, mid+1, hi);
    }

    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove() {
        if(isEmpty())
            return null;
        else {
            E temp = array[size-1];
            size--;
            return temp;
        }
    }

    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it.
    //  Returns null if the PQ is empty.
    public E peek() {
        if(isEmpty())
            return null;
        else
            return array[size-1];
    }

    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
    public boolean contains(E obj) {
        for(int i=1; i <= size; i++)
            if((obj).compareTo(array[i]) == 0)
                return true;
        return false;
    }

    //  Returns the number of objects currently in the PQ.
    public int size() {
        return size;
    }

    //  Returns the PQ to an empty state.
    public void clear() {
        size = 0;
    }

    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty() {
        return size == 0;
    }

    //  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
    public boolean isFull() {
        return size == maxSize;
    }

    //  Returns an iterator of the objects in the PQ, in no particular
    //  order.  The iterator must be fail-fast.
    public Iterator<E> iterator() {
        return new OrderedArrayPriorityQueue.IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {
        int iterIndex;

        public IteratorHelper() {
            iterIndex = 1;
        }

        public boolean hasNext() {
            return iterIndex <= size;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return array[iterIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
