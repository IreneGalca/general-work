import java.util.Comparator;
import java.util.ListIterator;

/**
 * Sort version for students: 
 * 
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Quicksort algorithm.
 *
 * @author CS221
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		//TODO: replace with your IUDoubleLinkedList for extra-credit
		return new IUDoubleLinkedList<T>(); 
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		quicksort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		quicksort(list, c);
	}

	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <T extends Comparable<T>> void quicksort(IndexedUnsortedList<T> list)
	{
		// TODO: Implement recursive Quicksort algorithm 


		if(!list.isEmpty()) {
			T pivot = list.removeLast();
			IndexedUnsortedList<T> hi = newList();
			IndexedUnsortedList<T> lo = newList();

			while(!list.isEmpty()) {
				//if the element is less than the pivot
				T start = list.removeFirst();
				if(start.compareTo(pivot)<0) {
					lo.add(start);
				}
				//if the element is greater than the pivot
				else{
					hi.add(start);
				}
			}
			quicksort(hi);
			quicksort(lo);
			while(!lo.isEmpty()) {
				list.add(lo.removeFirst());
			}
			list.add(pivot);
			while(!hi.isEmpty()) {
				list.add(hi.removeFirst());
			}
		}
	}


	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void quicksort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		// TODO: Implement recursive Quicksort algorithm using Comparator
		if(!list.isEmpty()) {
			T pivot = list.removeLast();
			IndexedUnsortedList<T> hi = newList();
			IndexedUnsortedList<T> lo = newList();

			while(!list.isEmpty()) {
				//if the element is less than the pivot
				T start = list.removeFirst();
				if(c.compare(start, pivot)<0) {
					lo.add(start);
				}
				//if the element is greater than the pivot
				else{
					hi.add(start);
				}
			}
			quicksort(hi, c);
			quicksort(lo, c);
			while(!lo.isEmpty()) {
				list.add(lo.removeFirst());
			}
			list.add(pivot);
			while(!hi.isEmpty()) {
				list.add(hi.removeFirst());
				}
			}

		}
	}