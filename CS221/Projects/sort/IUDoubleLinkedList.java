import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * CS221-2
 * Double Linked List
 * A class that implements the IndexedUnsortedList interface
 * and creates and uses a DLLIterator to iterate through all elements 
 * of the list.
 * 
 * @Author Irene Galca
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private DLLNode<T> head; //first node in the list
	private DLLNode<T> tail; //last node on list
	private int count; //number of elements in list
	private int modCount; //modified number of elements in list

	/*
	 * Default constructor
	 */
	public IUDoubleLinkedList() {
		head = null; //beginning of list
		tail = null; //end of list
		count = 0;	 //number of element/nodes in the list
		modCount = 0; //number of changes made to the list
	}

	/*
	 * Adds an element to the front of the list
	 * @param T element is the element to be added
	 */
	public void addToFront(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element); //new node to be added
		if(isEmpty()) {
			head = tail = newNode;

		}else{
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
		}

		count++;
		modCount++;
	}

	/*
	 * Adds an element to the end of the list
	 * @param T element is the element to be added
	 */
	public void addToRear(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element);
		if(isEmpty()) {
			head = tail = newNode;
		}else{
			tail.setNext(newNode);
			newNode.setPrev(tail);
			tail = newNode;
		}

		count++;
		modCount++;

	}

	/*
	 * Adds an element to the end of the list
	 * @param T element is the element to be added
	 */
	public void add(T element) {
		addToRear(element);

	}


	/*
	 * Adds an element after a specified index
	 * @param T element is the element to be added.
	 * @param T target indicates the intended index
	 * @throws NoSuchElementException if element not found
	 */
	public void addAfter(T element, T target) {
		DLLNode<T> current = head;


		while(current != null && current.getElement() != target) {

			current = current.getNext();
		}
		if(current == null) {

			throw new NoSuchElementException("IUDoubleLinkedList - adAfter - element not found");
		}

		DLLNode<T> newNode = new DLLNode<T>(element);

		newNode.setNext(current.getNext());
		newNode.setPrev(current);
		current.setNext(newNode);

		if(current == tail) {
			tail = newNode;
		}else {
			newNode.getNext().setPrev(newNode);
		}
		count++;
		modCount++;

	}

	/*
	 * Adds an element at a specified index
	 * @param T element is the element to be added
	 * @param int index is the specified index
	 * @throws IndexOutOfBoundsException if index not found
	 */
	public void add(int index, T element) {
		DLLNode<T> current = head; //current node we are at
		DLLNode<T> newNode = new DLLNode<T>(element); 

		if(index < 0 || index > count ) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList - add(index, element) - index out of range");
		}
		if(isEmpty()) {
			head = tail = newNode;
			count++;
			modCount++;
		}

		//adds at the head
		else if(index == 0) {
			addToFront(element);
		}
		//adds at the tail
		else if (index == count) {
			addToRear(element);
			//adds at the specified index
		}else {

			for (int i = 0; i < index -1 ; i++) {

				current = current.getNext();

			}

			newNode.setNext(current.getNext());
			newNode.setPrev(current);
			current.setNext(newNode);
			newNode.getNext().setPrev(newNode);
			count++;
			modCount++;
		}

	}

	/*
	 * Removes the first element from the list and returns it
	 * @return item (removed element)
	 * @throws NoSuchElementException if element not found
	 */
	public T removeFirst() {
		if(head == null) {
			throw new NoSuchElementException("IUDoubleLinkedList - removeFirst - element not found");
		}
		T item = head.getElement(); //element to be returned

		//if only one node exists in list
		if(head == tail) {
			head = tail = null;
			//general case
		}else {
			head = head.getNext();
			head.setPrev(null);
		}
		count--;
		modCount++;

		return item;
	}

	/*
	 * Removes the last element from the list and returns it
	 * @return item (removed element)
	 * @throws NoSuchElementException if element not found
	 */
	public T removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException("IUDoubleLinkedList - removeLast - element not found");
		}
		T item = tail.getElement();

		//if only one node exists in list
		if( head == tail) {
			head = tail = null;
			//general case
		}else {
			tail = tail.getPrev();
			tail.setNext(null);
		}

		count--;
		modCount++;

		return item;
	}

	/*
	 * Removes the last element from the list and returns it
	 * @param T element is the element to be removed
	 * @return item (removed element)
	 * @throws NoSuchElementException if element not found
	 */
	public T remove(T element) {
		DLLNode<T> prev = null; //node before our current node
		DLLNode<T> current = head; //node we are actually at

		//iterates to the element we want
		while(current != null && current.getElement() != element) {
			prev=current;
			current = current.getNext();
		}
		//if list current node doesn't exist
		if(current == null) {
			throw new NoSuchElementException("IUDoubleLinkedList - remove(T element) - element not found");
		}
		T item = current.getElement(); //intended element to be returned
		//if current node is at head
		if(prev == null) {
			head = head.getNext();
		}else {
			prev.setNext(current.getNext());

			if(current.getNext() != null) {
				current.getNext().setPrev(prev);
			}
		}
		//if current is at tail
		if(current == tail) {
			tail = prev;
			if(tail != null) {
				tail.setNext(null);
			}
		}

		count--;
		modCount++;

		return item;
	}

	/*
	 * Removes the element at a specified index and returns it
	 * @param T element is the element to be removed
	 * @return item (removed element)
	 * @throws IndexOutOfBoundsException if index is out of range or empty
	 */
	public T remove(int index) {

		if(isEmpty()) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList - remove(index) - index does not exist");
		}
		if(index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList - remove(index) - index out of range");
		}

		//if index is at beginning
		if(index == 0) {
			return removeFirst();

			//if index is at end
		}else if(index == count-1) {
			return removeLast();

			//general case
		}else {
			DLLNode<T> current = head;
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
			T item = current.getElement();
			current = current.getNext();
			current.setPrev(current.getPrev().getPrev());
			current.getPrev().setNext(current);


			count--;
			modCount++;
			return item;
		}
	}

	/*
	 * Sets an element at a specific index
	 * @param int index is the specified index
	 * @param T element is the element to be set
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public void set(int index, T element) {

		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList - set - index out of range");
		}

		DLLNode<T> current = head;

		// iterate to node before one at index
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}

		current.setElement(element);
		modCount++;
	}

	/*
	 * Gets an element at a specific index and returns it
	 * @param int index is the specified index
	 * @param T element is the element to be set
	 * @return current.getElement()
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public T get(int index) {
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList - get - index out of range");
		}

		DLLNode<T> current = head;

		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}

		return current.getElement();
	}

	/*
	 * Finds the index of an element and returns it
	 * @param T element is the element to be found
	 * @return index
	 */
	public int indexOf(T element) {
		boolean found = false;
		int index = 0;
		DLLNode<T> current = head;

		//if element is found
		while(!found && index < count) {
			if(current.getElement().equals(element)) {
				found = true;
			}else {
				current = current.getNext();
				index++;
			}
		}
		//if element is not found
		if(!found) {
			index = -1;
		}
		return index;
	}

	/*
	 * Finds and returns element at first index
	 * @throws NoSuchElementException if element not found
	 * @return head.getElement() the element at head
	 */
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException("IUDoubleLinkedList - first - element not found");
		}
		return head.getElement();
	}

	/*
	 * Finds and returns element at last index
	 * @throws NoSuchElementException if element not found
	 * @return tail.getElement() the element at tail
	 */
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException("IUDoubleLinkedList - last - element not found");
		}
		return tail.getElement();
	}

	/*
	 * Determines if the list contains the specified target (element or index)
	 * @param T target is the target to be found
	 * @return found, a boolean value that confirms if the target is or isn't in the list
	 */
	public boolean contains(T target) {
		// true when element is in list
		boolean found = false;

		if (indexOf(target) == -1) {
			found = false;
		}

		else {
			found = true;
		}

		return found;
	}

	/*
	 * Determines if list is empty
	 * @return count == 0 if it is empty
	 */
	public boolean isEmpty() {

		return (count == 0);
	}

	/*
	 * Tells us the current size of the list
	 * @return count
	 */
	public int size() {

		return count;
	}


	public Iterator<T> iterator() {

		return new DLLIterator();
	}


	public ListIterator<T> listIterator() {

		return new DLLIterator();
	}

	/*
	 * Iterates through the list from startingIndex
	 * @return DLLIterator(startingIndex)
	 */
	public ListIterator<T> listIterator(int startingIndex) {

		return new DLLIterator(startingIndex);
	}

	/*
	 * Creates a DLLiterator
	 * 
	 */
	private class DLLIterator implements ListIterator<T>{
		private DLLNode<T>curr; //current node we are at
		private DLLNode<T>last; //last node the iterator just passed
		private int index; //index of the list
		private int itrModCount; //modified count of the iterator

		/*
		 * Default Constructor for 
		 * DLLIterator
		 */
		public DLLIterator() {
			curr = head;
			last = null;
			itrModCount= modCount;
			index = 0;
		}

		/*
		 * DEfault constructor of the DLLIterator at a starting index
		 * @throws IndexOutOfBoundsException if the startingIndex is invalid
		 */
		public DLLIterator(int startingIndex) {
			curr = head;
			last= null;
			index = startingIndex;
			itrModCount = modCount;
			last = null;

			if (startingIndex < 0 || startingIndex > count) {
				throw new IndexOutOfBoundsException("DLLIterator - DLLIterator(startingIndex) - invalid starting index");
			}


			for (int i = 0; i < startingIndex; i++) {
				curr = curr.getNext();
			}

		}

		/*
		 * Determines if there is an element after our current element
		 * @return curr != null
		 */
		public boolean hasNext() {
			
			modCheck();

			return curr != null;
		}

		/*
		 * Moves the iterator through the list if hasNext() precondition is met
		 * @return last.getElement(), element we just passed over
		 */
		public T next() {
			
			if(!hasNext())
			{
				throw new NoSuchElementException("DLLIterator - next() - element not found");
			}
			last= curr;
			curr= curr.getNext();
			index++;
			return last.getElement();
		}

		/*
		 * Determines if we have an element before our current element
		 * @return (curr != head) confirms that element is at head if the lement exists, and if there is no other 
		 * element prior to it.
		 */
		public boolean hasPrevious() {
			
			modCheck();

			return (curr != head);
		}

		/*
		 * Moves the iterator backwards through the list if the hasPrevious() pre condition is met
		 * @throws NoSuchElementException if there is no previous element before our current
		 * @return curr.getElement(), the current element 
		 */
		public T previous() {
			
			modCheck();

			if(!hasPrevious()) {
				throw new NoSuchElementException("DLLIterator - previous() - element not found");
			}


			if(curr != null) {

				curr = curr.getPrev();
			}else {
				curr = tail;
			}
			last = curr;
			index--;
			return curr.getElement();
		}

		/*
		 * Indicates what our next index is
		 * @return index
		 */
		public int nextIndex() {
			
			modCheck();

			return index;
		}

		/*
		 * Indicates what our previous index is
		 * @return index-1
		 */
		public int previousIndex() {
			
			modCheck();

			return index - 1;
		}

		/*
		 * removes the last element the iterator just called next() or previous() to
		 * @throws IllegalStateExcpetion if there is the last node doesn't exist
		 */
		public void remove() {
			
			modCheck();

			if(last == null) {
				throw new IllegalStateException("DLLIterator - remove() - last node doesn't exist");
			}

			//if only one element/node exists in the list
			if(count == 1) 
			{
				head = tail = null;

				//if the previous node accessed was at the head
			}else if(head == last) {
				head = head.getNext();
				head.setPrev(null);

				//if the previous node accessed was at the tail
			}else if(tail == last) {
				tail = tail.getPrev();
				tail.setNext(null);
			}

			//general case
			else {
				last.getNext().setPrev(last.getPrev());
				last.getPrev().setNext(last.getNext());
			}

			//if the previous node is the next node(implying the iterator went backwards)
			if(last == curr) {
				curr = curr.getNext();
			}else {
				index--;
			}
			last = null;
			count--;                        
			modCount++;
			itrModCount++;
		}

		/*
		 * sets an element at the last node we just passed
		 * @throws IllegalStateException if last node doesn't exist
		 */
		public void set(T e) {
			
			modCheck();

			if (last == null) {
				throw new IllegalStateException("DLLIterator - set(e) - last node doesn't exist");
			}

			last.setElement(e);
			modCount++;
			itrModCount++;
		}

		/*
		 * adds a node at the last node we just passed
		 */
		public void add(T e) {
			
			modCheck();

			DLLNode<T> newNode = new DLLNode<T>(e);

			//if adding at the head
			if(head == null) {
				head = tail = newNode;
			}

			//if adding at the tail
			else if(head == curr) {
				newNode.setNext(head);
				head.setPrev(newNode);;
				head = newNode;
			}

			else if(curr == null) {
				newNode.setPrev(tail);
				tail.setNext(newNode);
				tail = newNode;
			}

			else {
				newNode.setNext(curr);
				newNode.setPrev(curr.getPrev());
				curr.getPrev().setNext(newNode);
				curr.setPrev(newNode);

			}
			modCount++;
			itrModCount++;
			count++;
			index++;
			last = null;

		}
		/*
		 * checks if the values of the itrModCount and the modCount match
		 * @throws ConcurrentModificationException if the values do not match
		 */
		public void modCheck() {
			if(itrModCount != modCount) {
				throw new ConcurrentModificationException("DLLIterator - modification values do not match");
			}

		}
	}
}

