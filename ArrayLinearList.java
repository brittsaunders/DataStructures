/*		Brittany Saunders
 *		cssc0954
 *	    Assignment 1
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {

    private E[] array;
    private int size, maxSize;

    public ArrayLinearList() {
        size = 0;
        array = (E[]) new Object[DEFAULT_MAX_CAPACITY];
        maxSize = DEFAULT_MAX_CAPACITY - 1;
    }

//  Adds the Object obj to the end of list.

    public void addLast(E obj) {
        if(size == maxSize)
            grow();
        array[size+1] = obj;
        size++;
    }

    //  Adds the Object obj to the beginning of list.
    public void addFirst(E obj) {
        insert(obj,1);
    }

    //  Inserts the Object obj at the position indicated.  If there is an element at
//  that location, all elements from that location to the end of the list are
//  shifted down to make room for the new insertion.  The location is one based.
//  If the location > size()+1 then a RuntimeException is thrown. List elements
//  must be contiguous.
    public void insert(E obj, int location) {
        if (location > size + 1 || location <= 0)
            throw new RuntimeException("Location or object invalid.");
        if(size == maxSize)
            grow();
        for (int i = size+1; i > location; i--)
            array[i] = array[i-1];
        array[location] = obj;
        size++;
    }

    //  Removes the object located at the parameter location (one based).
//  Throws a RuntimeException if the location does not map to a valid position within the list.
    public E remove(int location) {
        if (location > size())
            throw new RuntimeException("No element to remove at this location.");

        for (int i = location; i <= (size() - 1); i++)
            array[i] = array[i + 1];
        size--;

        if (size < (maxSize*.25))
            shrink();

        return null;
    }

    //  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of32 the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
    public E remove(E obj) {
        for (int i = 1; i <= size; i++) {
            if (((Comparable<E>) obj).compareTo(array[i]) == 0) {
                E temp = array[i];
                remove(i);
                return temp;
            }
        }
        return null;
    }

    //  Removes and returns the parameter object obj in first position in list if the list is not empty,
//  null if the list is empty. The ordering of the list is preserved.
    public E removeFirst() {

        return remove(array[1]);
    }

    //  Removes and returns the parameter object obj in last position in list if the list is not empty,
//  null if the list is empty. The ordering of the list is preserved.
    public E removeLast() {

        return remove(array[size()]);
    }

    //  Returns the parameter object located at the parameter location position (one based).
//  Throws a RuntimeException if the location does not map to a valid position within the list.
    public E get(int location) {
        location++;
        if (location > size)
            throw new RuntimeException("There is no object at this location.");
        else
            return array[location];
    }

    public void grow() {
        E[] temp = (E[]) new Object[maxSize*2-1];
        for(int i = 1; i <= size; i++)
            temp[i] = array[i];
        array = temp;
        maxSize = maxSize * 2 -1;
    }

    public void shrink() {
        E[] temp = (E[]) new Object[maxSize/2];
        for(int i = 1; i <= size; i++)
            temp[i] = array[i];
        array = temp;
        maxSize = maxSize / 2 - 1;
    }

    //  Returns true if the parameter object obj is in the list, false otherwise.
    public boolean contains(E obj) {
        for(int i=1; i <= size; i++)
            if(((Comparable<E>)obj).compareTo(array[i]) == 0)
                return true;
        return false;
    }

    //  Returns the one based location of the parameter object obj if it is in the list, -1 otherwise.
//  In the case of duplicates, this method returns the element closest to position #1.
    public int locate(E obj) {
        int i = 1;
        while (i != (size + 1)) {
            if (array[i] == obj)
                return 1;
            i++;
        }
        return -1;
    }

    //  The list is returned to an empty state.
    public void clear() {
        size = 0;
    }

    //  Returns true if the list is empty, otherwise false
    public boolean isEmpty() {
        return size == 0;
    }

    //  Returns the number of Objects currently in the list.
    public int size() {
        return size;
    }

    //  Returns an Iterator of the values in the list, presented in
//  the same order as the underlying order of the list. (position #1 first)
    public Iterator<E> iterator() {
        return new IteratorHelper();
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