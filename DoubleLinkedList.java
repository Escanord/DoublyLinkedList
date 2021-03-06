import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.*;

/**
 * A double linked list.
 * @author Escanord Le
 */
public class DoubleLinkedList<T> implements Iterable<T> {
  /** a reference to the first node of the double linked list */
  private DLNode<T> front;
  
  /** a reference to the last node of a double linked list */
  private DLNode<T> back;
  
  /** Create an empty double linked list. */
  public DoubleLinkedList() {
    front = back = null;
  }
  
  /** 
   * Returns true if the list is empty.
   * @return  true if the list has no nodes
   */
  public boolean isEmpty() {
    return (getFront() == null);
  }
  
  /**
   * Returns the reference to the first node of the linked list.
   * @return the first node of the linked list
   */
  protected DLNode<T> getFront() {
    return front;
  }
  
  /**
   * Sets the first node of the linked list.
   * @param node  the node that will be the head of the linked list.
   */
  protected void setFront(DLNode<T> node) {
    front = node;
  }
  
  /**
   * Returns the reference to the last node of the linked list.
   * @return the last node of the linked list
   */
  protected DLNode<T> getBack() {
    return back;
  }
  
  /**
   * Sets the last node of the linked list.
   * @param node the node that will be the last node of the linked list
   */
  protected void setBack(DLNode<T> node) {
    back = node;
  }
  
  /*----------------------------------------*/
  /* METHODS TO BE ADDED DURING LAB SESSION */
  /*----------------------------------------*/
  
  /**
   * Add an element to the head of the linked list.
   * @param element  the element to add to the front of the linked list
   */
  public void addToFront(T element) {
    DLNode<T> newNode = new DLNode<T> (element, null, this.getFront());
    this.setFront(newNode);
    if (this.getBack() == null) 
    {
      this.setBack(newNode);
    }
  }
  
  /**
   * Add an element to the tail of the linked list.
   * @param element  the element to add to the tail of the linked list
   */
  public void addToBack(T element) {
    DLNode<T> newNode = new DLNode<T> (element, this.getBack(), null);
    this.setBack(newNode);
    if (this.getFront() == null) this.setFront(newNode);
  }
  
  /**
   * Remove and return the element at the front of the linked list.
   * @return the element that was at the front of the linked list
   * @throws NoSuchElementException if attempting to remove from an empty list
   */
  public T removeFromFront() throws NoSuchElementException {
    T element;
    if(this.getFront() != null) {
      element = this.getFront().getElement();
    }
    else {
      element = null;
      throw new NoSuchElementException();
    }
    if(this.getFront().getNext() != null && this.getFront().getNext().getElement() != null) {
      this.getFront().getNext().setPrevious(null);
      this.setFront(this.getFront().getNext()); 
    }
    else {
      this.setFront(null);
      this.setBack(null);
    }
    return element;
  }
  
  /**
   * Remove and return the element at the back of the linked list.
   * @return the element that was at the back of the linked list
   * @throws NoSuchElementException if attempting to remove from an empty list
   */
  public T removeFromBack() throws NoSuchElementException {
    T element;
    if(this.getBack() != null) {
      element = this.getBack().getElement();
    }
    else {
      element = null;
      throw new NoSuchElementException();
    }
    if(this.getBack().getPrevious() != null && this.getBack().getPrevious().getElement() != null) {
      this.getBack().getPrevious().setNext(null);
      this.setBack(this.getBack().getPrevious()); 
    }
    else {
      this.setFront(null);
      this.setBack(null);
    }
    return element;
  }
  
  /**
   * Returns an interator for the linked list that starts at the head of the list and runs to the tail.
   * @return  the iterator that runs through the list from the head to the tail
   */
  @Override
  public Iterator<T> iterator() {
    return new DLLIterator(this.getFront(), this);
  }
  
  /**
   * Remove every other node from the input list of nodes
   * @param list1 the first node of a list of nodes
   * @return the first node of the list of nodes that were removed
   */
  public static <S> DLNode<S> split(DLNode<S> list1) {
    DLNode<S> list2 = list1.getNext();
    list2.setPrevious(null);
    DLNode<S> nodeptr = list2;
    DLNode<S> prevptr = list1;
    
    while (nodeptr != null) 
    {
      prevptr.setNext(nodeptr.getNext());
      if (prevptr.getNext() != null) 
        prevptr.getNext().setPrevious(prevptr);
      prevptr = nodeptr;
      nodeptr = nodeptr.getNext();
    }
    
    return list2;
  }
  
  /**
   * Code to merge two lists of sorted nodes into one list
   * @param list1  the first node of a sorted list of nodes
   * @param list2  the second node of a sorted list of nodes
   * @return the first node of the sorted list
   */
  private static <S extends Comparable<? super S>> DLNode<S> merge(DLNode<S> list1, DLNode<S> list2, Comparator<S> comp) {
    DLNode<S> finalList;
    
    // determine the first node of final list
    if (comp.compare(list1.getElement(), list2.getElement()) < 0) {
      finalList = list1;
      list1 = list1.getNext();
    }
    else {
      finalList = list2;
      list2 = list2.getNext();
    }
    
    // repeat choosing the node with the smallest value to add to the end of the list
    DLNode<S> endptr = finalList;
    while (list1 != null && list2 != null) {
      if (comp.compare(list1.getElement(), list2.getElement()) < 0) {
        endptr.setNext(list1);
        list1.setPrevious(endptr);
        list1 = list1.getNext();
      }
      else {
        endptr.setNext(list2);
        list2.setPrevious(endptr);
        list2 = list2.getNext();
      }
      endptr = endptr.getNext();
    }
    
    // one of list1 or list2 is empty, so stick the other on the end
    if (list1 == null)
    {
      endptr.setNext(list2);
      list2.setPrevious(endptr);
    }
    else
    {
      endptr.setNext(list1);
      list1.setPrevious(endptr);
    }
    
    return finalList;
  }
  
  /**
   * take the first node of a list of nodes, sort, and return the first node of the sorted list
   * @param list  the first node of a list of nodes to sort
   * @return the first node of the sorted list
   */
  private static <S extends Comparable<? super S>> DLNode<S> mergeSort(DLNode<S> list, Comparator<S> comp) {
    if (list.getNext() == null)
      return list;
    
    // 1. split the list
    DLNode<S> list2 = split(list);
    
    // 2. sort each half
    list = mergeSort(list, comp);
    list2 = mergeSort(list2, comp);
    
    // 3. combine the sorted lists and return
    return merge(list, list2, comp);
  }
  
  /**
   * merge sort the linked list
   * @param list the linked list to sort
   * @param comp the comparator using to compare two S
   */
  public static <S extends Comparable<? super S>> void mergeSort(DoubleLinkedList<S> list, Comparator<S> comp) {
    if (list.getFront().getPrevious() != null) 
    {
      list.getFront().getPrevious().setNext(null);
      list.getFront().setPrevious(null);
    }
    if (list.getBack().getNext() != null) 
    {
      list.getBack().getNext().setPrevious(null);
      list.getBack().setNext(null);
    }
    // stores the front node of sorted list
    DLNode<S> frontNode = mergeSort(list.getFront(), comp);
    list.setFront(frontNode);
    while (frontNode.getNext() != null) 
      frontNode = frontNode.getNext();
    list.setBack(frontNode);
  }
  
  /** 
   * An interator for the linked list that starts at the head of the list and runs to the tail.
   * @author Escanord Le */
  private class DLLIterator implements ListIterator<T>
  {
    /*********** FIELDS ************/
    // stores the current position of iterator on the list
    private DLNode<T> nodeptr;
    
    // stores the id of last method of the iterator called
    private int callRemove;
    
    // stores the list of the iterator
    private DoubleLinkedList<T> list;
    
    // stores the first position of iterator on the list
    private DLNode<T> firstNode;
    
    // stores the last position of iterator on the list
    private DLNode<T> lastNode;
    
    /********* CONSTRUCTOR *********/
    /** Creates an interator for DoubleLinkedList object
      * @param nodeptr the starting place of the iterator
      * @param list    the list of the iterator
      */
    public DLLIterator (DLNode<T> nodeptr, DoubleLinkedList<T> list)
    {
      this.nodeptr = nodeptr;
      this.list = list;
      callRemove = 4;
      lastNode = new DLNode<T> (null, list.getBack(), null);
      firstNode = new DLNode<T> (null, null, list.getFront());
    }
    
    /***** NON-STATIC METHODS ******/
    /** Returns the first position of iterator on the list 
      * @return the first position of iterator on the list */
    private DLNode<T> getFirstNode()
    {
      return this.firstNode;
    }
    
    /** Returns the last position of iterator on the list
      * @return the last position of iterator on the list */
    private DLNode<T> getLastNode()
    {
      return this.lastNode;
    }
    
    /** Returns the list of the iterator
      * @return the list of the iterator */
    private DoubleLinkedList<T> getList()
    {
      return this.list;
    }
    
    /** Add a new element to the current place of iterator in the list. The element is added immediately before the element 
      * that would be returned by next(), if any, and after the element that would be returned by previous(), if any.
      * 
      * @param element the value of the new element */
    public void add (T element)
    {
      if (nodeptr == null)
      {
        // stores the new node
        DLNode<T> node = new DLNode<T> (element, this.getFirstNode(), this.getLastNode());
        this.getList().setFront(node);
        this.getList().setBack(node);
        nodeptr = node;
      }
      else
      {
        // stores the new node
        DLNode<T> node = new DLNode<T> (element, nodeptr.getPrevious(), nodeptr);
        if (nodeptr == this.getList().getFront()) this.getList().setFront(node);
        if (nodeptr == this.getLastNode()) this.getList().setBack(node);
      }
      setCallRemove(3);
    }
    
    /** return if there is any other node after this current place of iterator 
      * @return <p> <code> true </code>  if there is another node after this iterator </p>
                <p> <code> false </code> if the iterator is at the end of the list </p> */
    public boolean hasNext()
    {
      return nodeptr != null && nodeptr != this.getLastNode();
    }
    
    /** return if there is any other node before this current place of iterator
      * @return <p> <code> true </code>  if there is another node before this iterator </p>
      <p> <code> false </code> if the iterator is at the start of the list */
    public boolean hasPrevious()
    {
      return nodeptr != null && nodeptr.getPrevious() != this.getFirstNode();
    }
    
    /** returns the next element in the list and move the iterator up
      * @return the next element in the list
      * @throws NoSuchElementException if there is no next element in the list (the iterator is at the end of the list) */
    public T next() throws NoSuchElementException
    {
      if (!this.hasNext()) 
        throw new NoSuchElementException();
      // stores the return value
      T save = nodeptr.getElement();
      
      nodeptr = nodeptr.getNext();
      setCallRemove(1);
      return save;
    }
    
    /** returns the previous element in the list and move the iterator backwards
      * @return the previous element in the list
      * @throws NoSuchElementException if there is no previous element in the list (the iterator is at the start of the list) */
    public T previous() throws NoSuchElementException
    {
      if (!this.hasPrevious()) 
        throw new NoSuchElementException();
      nodeptr = nodeptr.getPrevious();
      setCallRemove(2);
      return nodeptr.getElement();
    }
    
    /** sets the last method that was called
      * @param callRemove the id of the last method that was called */
    private void setCallRemove(int callRemove)
    {
      this.callRemove = callRemove;
    }
    
    /** returns the id of the last method that was called
      * @return the id of the last method that was called */
    private int getCallRemove()
    {
      return this.callRemove;
    }
    
    
    /** remove the list the last element that was returned by next() or previous()
      * @throws IllegalStateException if the last method that was called is not next() or previous() */
    public void remove() throws IllegalStateException
    {
      if (this.getCallRemove() > 2) throw new IllegalStateException();
      if (this.getCallRemove() == 1)
      {
        if (!this.hasNext()) 
        {
          this.getList().removeFromBack();
          this.getLastNode().setPrevious(this.getList().getBack());
        }
        else
        {
          nodeptr.getPrevious().getPrevious().setNext(nodeptr);
          if (nodeptr.getPrevious() == this.getList().getFront()) this.getList().setFront(nodeptr);
          nodeptr.setPrevious(nodeptr.getPrevious().getPrevious());
        }
      }
      else
      {
        if (!this.hasPrevious()) 
        {
          this.getList().removeFromFront();
          this.getFirstNode().setNext(this.getList().getFront());
        }
        else
        {
          nodeptr = nodeptr.getPrevious();
          if (nodeptr.getNext() == this.getList().getBack()) this.getList().setBack(nodeptr);
          nodeptr.setNext(nodeptr.getNext().getNext());
          nodeptr.getNext().setPrevious(nodeptr);
        }
      }
      setCallRemove(4);
    }
    
    /** is not supported by this list iterator
      * @throws UnsupportedOperationException this method is not supported by this list iterator */
    public void set(T element) throws UnsupportedOperationException
    {
      throw new UnsupportedOperationException();
    }
    
    /** is not supported by this list iterator
      * @throws UnsupportedOperationException this method is not supported by this list iterator */
    public int previousIndex() throws UnsupportedOperationException
    {
      throw new UnsupportedOperationException();
    }
    
    /** is not supported by this list iterator
      * @throws UnsupportedOperationException this method is not supported by this list iterator */
    public int nextIndex() throws UnsupportedOperationException
    {
      throw new UnsupportedOperationException();
    }
  }
}
