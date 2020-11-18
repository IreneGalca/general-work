import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*Iterator class for a Single Linked List
 * @author Irene Galca
 * @param <T> generic type of elements stored in the list.
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T>{

	private int count; //number of elements in list
	private SLLNode<T> head, tail; //first and last nodes in the linked list

	public  IUSingleLinkedList(){
		count= 0;
		head= null;
		tail= null;
	}

	/*
	 * Adds an element to the front of the list
	 * @param T element is the element to be added.
	 */
	@Override
	public void addToFront(T element) {

		add(0,element);
	}

	/*
	 * Adds element to the end of the list
	 * @param T element is the element to be added
	 */
	@Override
	public void addToRear(T element) {

		add(element);

	}

	/*
	 * Adds element to the end of the list.
	 * @param T element is the element to be added. 
	 */
	@Override
	public void add(T element) {

		SLLNode<T> newNode = new SLLNode<T>(element); 
		// if list is empty 
		if(count == 0)
		{
			head = newNode; // if queue is empty 
		}
		else
		{
			tail.setNext(newNode);  // can't do if empty 
		}		
		tail = newNode;  
		count++; 
	}

	/*
	 * Adds a new element after a specific element.
	 * @param T element is the element to be added.
	 * @param T target is the element we are adding after.
	 * @throws NoSuchElementException if list is empty
	 */
	@Override
	public void addAfter(T element, T target) {

		if(isEmpty()) {
			throw new NoSuchElementException("Linked List - AddAfter() - element does not exist");

		}
		int index = indexOf(target);
		if (index == -1)
			throw new NoSuchElementException("Linked List - AddAfter() - element does not exist");

		SLLNode<T> current= head;
		SLLNode<T> newNode= new SLLNode<T>(element);
		boolean exit= false;
		//searches through the list for target element
		while(current != null && exit== false && current.getElement() != null) {
			if (current.getElement().equals(target)) {
				exit=true;
			}else{
				current = current.getNext();	
			}
		}
		newNode.setNext(current.getNext());
		current.setNext(newNode);
		count++;
	}


	/*
	 * Adds an element at a specific index
	 * @param int index refers to the index we want.
	 * @param T element is the element to be added at index.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	@Override
	public void add(int index, T element) {


		//check if index is valid.
		if(index<0 || index> count) {
			throw new IndexOutOfBoundsException("Linked List - add(int index, T element");
		}
		//if adding at the end of the list.
		if (index == count) {
			add(element);
		}

		//if adding to the front of the list.
		else if(index==0) 
		{
			SLLNode<T> newNode= new SLLNode<T> (element);
			newNode.setNext(head);
			head= newNode;
			count++;
		}
		else{
			SLLNode<T> current = head;
			// iterate to node before intended index   
			for(int i = 0; i < index-1; i++)
			{
				current = current.getNext(); 
			}
			SLLNode<T> newNode = new SLLNode<T>(element);  
			SLLNode<T> next = current.getNext(); //temp variable for node at index
			current.setNext(newNode);
			newNode.setNext(next);
			count++; 
		}
	}

	/*
	 * Removes and returns the first element of the list
	 * @returns element removed from the list
	 * @throws NoSuchElementException if list is empty or element cannot be found.
	 */
	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException("Linked List - removeFirst() - element not found");
		}
		T first= head.getElement(); //temp variable for element
		remove(first);
		return first;
	}

	/*
	 * Removes and returns the last element of the list
	 * @returns element removed from the list
	 * @throws NoSuchElementException if list is empty or element cannot be found.
	 */
	@Override
	public T removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException("Linked List - removeFirst() - element not found");
		}
		T last= tail.getElement(); //temp variable for element
		remove(last);
		return last;
	}

	/*
	 * Removes and returns specified element from the end of the list
	 * @param T element is the element to be removed
	 * @throws NoSuchElementException if element is not in list
	 */
	@Override
	public T remove(T element) {

		int index = indexOf(element); // find position of item, if in list 
		if(index == -1) // if element not in list 
		{
			throw new NoSuchElementException("Linked List - remove(element) - element not found"); 
		}

		return remove(index); 
	}

	/*
	 * Removes and returns element at a specific index
	 * @param int index is the specified index
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	@Override
	public T remove(int index) {

		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("List2 - remove(pos) - invalid index"); 
		}
		//if element is at head
		T item; 
		if(index == 0)
		{
			item = head.getElement(); 
			SLLNode<T> next = head.getNext(); 
			head.setNext(null);
			head = next; 

			// if element is at tail 
			if(count == 1)
			{
				tail = null; 
			}
		}
		else
		{
			SLLNode<T> current = head;

			// iterate to node before intended index 
			for(int i = 0; i < index-1; i++)
			{
				current = current.getNext(); 
			}
			// temp variable for node at index 
			SLLNode<T> next = current.getNext(); 
			current.setNext(next.getNext());
			next.setNext(null);
			if(index == count - 1){
				tail = current; 
			}
			item = next.getElement();
		}
		count--; 
		return item; 
	}

	/*
	 * Sets element at a specified index
	 * @param int index is the specified index
	 * @param T element is the element to be set
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	@Override
	public void set(int index, T element) {

		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("List2 - set - invalid index"); 
		}
		SLLNode<T> current = head;
		for(int i=0; i<index; i++) {
			current= current.getNext();
		}

		current.setElement(element);

	}

	/*
	 * Gets and returns element at a specified index
	 * @param int index is the specified index
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	@Override
	public T get(int index) {

		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("List2 - set - invalid index"); 
		}

		SLLNode<T> current = head;
		// iterate to node before intended index   
		for(int i = 0; i < index; i++)
		{
			current = current.getNext(); 
		}
		return current.getElement();
	}

	/*
	 * Finds the index of an element in the list
	 * @param T element element to be found
	 */
	@Override
	public int indexOf(T element) {

		boolean found = false;  //determines if item is found
		int index = 0;  // index of item in list 
		SLLNode<T> current = head;

		//searches for the item in the list
		while(!found && index < count)
		{
			if(current.getElement() == element)
			{
				found = true; 
			}
			else
			{
				index++; 
				current = current.getNext(); 
			}
		}

		// if element not in list 
		if(!found)
		{
			index = -1; 
		}
		return index;
	}

	/*
	 * Finds and returns the first element in the list
	 * @returns first the first element of list
	 * @throws NoSuchElementException if list is empty
	 */
	@Override
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException("Linked List - first()- element not found");
		}
		T first= head.getElement();
		return first;
	}

	/*
	 * Finds and returns the last element in the list
	 * @returns last the last element of list
	 * @throws NoSuchElementException if list is empty
	 */
	@Override
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException("Linked List - first()- element not found");
		}
		T last= tail.getElement();
		return last;
	}

	/*
	 * Checks if list contains a desired element.
	 * 
	 */
	@Override
	public boolean contains(T target) {

		return (indexOf(target) != -1); 
	}

	/*
	 * Checks if list is empty
	 * Returns true if list is empty, returns false otherwise
	 */
	@Override
	public boolean isEmpty() {

		if(head==null) {
			return true;
		}
		return false;
	}

	/*
	 * Refers to the size of our list
	 * @returns count at its current value
	 */
	@Override
	public int size() {

		return count;
	}

	/*
	 * Returns a list iterator for all the elements in the list
	 * @returns LinkedListIterator which iterates over all elements in list
	 */
	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}
	private class LinkedListIterator implements Iterator<T>
	{
		private int next; // position of next element to be returned
		@SuppressWarnings("unused")
		private boolean removeable; // whether remove pre-condition is met 
		private SLLNode<T> nextNode, current, prior;

		public LinkedListIterator() // constructor for iterator
		{
			next = 0;
			nextNode= head;
			removeable = false; 
			current= null;
			prior= null;
			
		}

		
		@Override
		public boolean hasNext()
		{
			return (next < count);
		}

		@Override
		public T next()
		{
			//checks if HasNext condition is met first
			if(!hasNext())
			{
				throw new NoSuchElementException("LinkedListIterator - next() - HasNext precondition not met"); 
			}
			current = nextNode;
			T result = get(next);
			next++;
			return result;

		}

		@Override
		public void remove()
		{
			boolean removeable= true;
			
			if(removeable) {
				
				if (prior != null) {
					prior.setNext(nextNode);
				}else {
					head= nextNode;
				}
				current.setNext(null);
				if(nextNode== null) {
					tail= prior;
				}
				current = null;
				count--;
				removeable= false;
			}else {
				throw new IllegalStateException("LinkedListIterator - remove()");
			}

		}
	}


	/*
	 * Method not used
	 * @throws UnsupportedOperationException
	 */
	@Override
	public ListIterator<T> listIterator() {

		throw new UnsupportedOperationException("Linked List - listIterator()");
	}
	/*
	 * Method not used
	 * @throws UnsupportedOperationException
	 */
	@Override
	public ListIterator<T> listIterator(int startingIndex) {

		throw new UnsupportedOperationException("Linked List - listIterator()");
	}

}