package data_structures;

/*     Brittany Saunders
       cssc0954
       Assignment 2
*/

import java.util.Iterator;
import java.util.NoSuchElementException;


public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {


    private E[] array;
    private E temp;
    private int size, maxSize, currSize, loc;

    public UnorderedArrayPriorityQueue(int S) {
        size = 0;
        currSize = S;
        array = (E[]) new Comparable[DEFAULT_MAX_CAPACITY];
        maxSize = currSize;
    }

    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E object) {
        if(isFull() == true)
            return false;
        if(isEmpty() == true) {
            array[1] = object;
            size++;
        }
        else {
            array[size+1] = object;
            size++;
        }
        return true;
    }



    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove() {
        if(array[1] == null)
            return null;
        if(array[2] == null) {
            temp = array[1];
            array[1] = null;
            size = 0;
            return temp;
        }
        temp = array[1];
        loc = 1;
        for (int i = 2; i <= size; i++) {
            if ((temp).compareTo(array[i]) > 0) {
                temp = array[i];
                loc = i;
            }
            if (i == size) {
                if(loc == 1)
                    for (int j = loc; j < i; j++)
                        array[j] = array[j + 1];
                else
                    for (int j = loc; j < i; j++)
                        array[j] = array[j + 1];
                array[i] = null;
                size--;
                return temp;
            }
        }
        return null;
    }

    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it.
    //  Returns null if the PQ is empty.
    public E peek() {
        if(array[1] == null)
            return null;
        temp = array[1];
        for (int i = 2; i <= size; i++) {
            if ((temp).compareTo(array[i]) > 0)
                temp = array[i];
            }
        return temp;
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
        return new UnorderedArrayPriorityQueue.IteratorHelper();
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
