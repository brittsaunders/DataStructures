/*  Brittany Saunders
    cssc0954
    Assignment 1
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedLinearList<E> implements LinearListADT<E> {

    private Node <E> head;
    private Node <E> tail;
    private int currentSize;
    public void LinkedList(){
        head = null;
        tail = null;
        currentSize = 0;
    }

    class Node <E> {
        E data;
        Node <E> next;
        public Node(E obj) {
            data = obj;
            next = null;
        }
    }

    //  Adds the Object obj to the end of list.
    public void addLast(E obj){
        Node <E> node = new Node<E>(obj);
        if(head == null) {
            head = tail = node;
            currentSize++;
            return;
        }
        Node <E> temp = head;
        while(temp.next != null)
            temp = temp.next;
        temp.next = node;
        tail = temp.next;
        currentSize++;
    }

    //  Adds the Object obj to the beginning of list.
    public void addFirst(E obj){
        Node <E> node = new Node<E>(obj);
        node.next = head;
        head = node;
        currentSize++;

        tail = head;
        while(tail.next != null)
            tail = tail.next;
    }

    //  Inserts the Object obj at the position indicated.  If there is an element at
//  that location, all elements from that location to the end of the list are 
//  shifted down to make room for the new insertion.  The location is one based.
//  If the location > size()+1 then a RuntimeException is thrown. List elements 
//  must be contiguous.
    public void insert(E obj, int location){
        Node <E> prev = null, current = head, temp = null;
        if(location == 1) {
            addFirst(obj);
            return;
        }
        if(current.next == null) {
            addLast(obj);
            return;
        }
        int i = 1;
        while(current != null) {
            if(i == location) {
                temp = current;
                break;
            }
            prev = current;
            current = current.next;
            i++;
        }
        Node <E> node = new Node<E>(obj);
        prev.next = node;
        node.next = current;
        currentSize++;
    }

    //  Removes the object located at the parameter location (one based).
//  Throws a RuntimeException if the location does not map to a valid position within the list.
    public E remove(int location){
        Node <E> prev = null, current = head;
        int i = 1;
        while(current != null) {
            if (i == location) {
                if(i == 1)
                    return removeFirst();
                E temp = current.data;
                prev.next = current.next;
                currentSize--;
                return temp;
            }
            prev = current;
            current = current.next;
            i++;
        }
        throw new RuntimeException("No element to remove at this location.");
    }

    //  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
    public E remove(E obj){
        Node <E> current = head, prev = null;
        while(current != null) {
            if(((Comparable<E>)obj).compareTo(current.data) == 0) {
                if(current == head)
                    return removeFirst();
                if(current == tail)
                    return removeLast();
                prev.next = current.next;
                currentSize--;
                return current.data;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    //  Removes and returns the parameter object obj in first position in list if the list is not empty,
//  null if the list is empty. The ordering of the list is preserved.
    public E removeFirst(){
        E temp = head.data;
        Node<E> tmp = head;
        if(head == null)
            throw new RuntimeException("No element to remove at this location.");
        if(head == tail)
            head = tail = null;
        head = tmp.next;
        currentSize--;
        return temp;
    }

    //  Removes and returns the parameter object obj in last position in list if the list is not empty,
//  null if the list is empty. The ordering of the list is preserved.
    public E removeLast(){
        if(head == null)
            throw new RuntimeException("No element to remove at this location.");
        if(head == tail)
            return removeFirst();
        Node <E> current = head, prev = null;
        while(current != tail) {
            prev = current;
            current = current.next;
        }
        prev.next = null;
        tail = prev;
        currentSize--;
        return current.data;
    }

    //  Returns the parameter object located at the parameter location position (one based).
//  Throws a RuntimeException if the location does not map to a valid position within the list.
    public E get(int location){
        Node <E> prev = null, current = head, temp;
        for(int i = 1; i <= location; i++) {
            if(i == location) {
                prev.next = current.next;
                return current.data;
            }
            prev = current;
            current = current.next;
        }
        throw new RuntimeException("No element at this location.");
    }

    //  Returns true if the parameter object obj is in the list, false otherwise.
    public boolean contains(E obj){
        Node <E> current = head;
        while(current != null) {
            if(((Comparable<E>)obj).compareTo(current.data) == 0)
                return true;
            current = current.next;
        }
        return false;
    }

    //  Returns the one based location of the parameter object obj if it is in the list, -1 otherwise.
//  In the case of duplicates, this method returns the element closest to position #1.
    public int locate(E obj){
        Node <E> current = head;
        int i = 1;
        while(current != null) {
            if(((Comparable<E>)obj).compareTo(current.data) == 0)
                return i;
            current = current.next;
            i++;
        }
        return -1;
    }

    //  The list is returned to an empty state.
    public void clear(){
        head = tail = null;
        currentSize = 0;
    }

    //  Returns true if the list is empty, otherwise false
    public boolean isEmpty(){
        if(head == null)
            return true;
        return false;
    }

    //  Returns the number of Objects currently in the list.
    public int size(){
        return currentSize;
    }
    //  Returns an Iterator of the values in the list, presented in
//  the same order as the underlying order of the list. (position #1 first)
    public Iterator<E> iterator(){
        return new IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {

        Node <E> index;
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