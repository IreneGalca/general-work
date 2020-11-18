
import java.util.LinkedList;

/**
 * A cache representation that uses the linked list adt to store data.
 *   
 * @EmmaLytle
 * 
 * @param <T> type to store
 */
public class Cache<T> {
	private LinkedList<T> cache1, cache2;
	private double HR, HR1, HR2;// Hit Ratio
	private int NH, NH1, NH2;// number of cache hits
	private int NR, NR1, NR2;// references to cache
	private int cache1Size;
	private int cache2Size;
	private boolean enableDoubleLayer = false;

	/**
	 * Constructor taking just one size
	 * 
	 * @param int size
	 */
	public Cache(int size) {
		cache1 = new LinkedList<T>();
		cache1Size = size;
	}

	/**
	 * Constructor taking just both cache layer sizes
	 * 
	 * @param int layer1Size, int layer2Size 
	 */
	public Cache(int layer1Size, int layer2Size) {
		cache1 = new LinkedList<T>();
		cache1Size = layer1Size;
		cache2 = new LinkedList<T>();
		cache2Size = layer2Size;
		enableDoubleLayer = true;
	}
	/**
	 * gets the desired obj from either of the caches
	 * 
	 * @param T obj
	 */
	public T getObject(T obj) {
		NR1++;

		int indexCache1 = cache1.indexOf(obj);
		if (indexCache1 >= 0) // Found
		{
			cache1.remove(indexCache1);
			cache1.addFirst(obj); // MRU

			NH1++;

		} else { // Not Found
			cache1.addFirst(obj);
			trimCache1();
			NR2++; // layer1 miss.
		}

		if (enableDoubleLayer) {

			int indexCache2 = cache2.indexOf(obj);
			if (indexCache2 >= 0) // Found
			{
				cache2.remove(indexCache2);
				cache2.addFirst(obj); // MRU
				if (indexCache1 < 0) {
					NH2++;
				}

			} else { // Not Found
				cache2.addFirst(obj);
				trimCache2();

			}

		}
		return obj;
	}
	/**
	 * adds the desired obj from either of the caches
	 * 
	 * @param T obj
	 */
	public void addObject(T obj) {

		if (cache1.contains(obj)) {
			cache1.remove(obj);
			cache1.addFirst(obj);

		} else {
			cache1.addFirst(obj);
			trimCache1();
		}

		if (enableDoubleLayer) {

			if (cache2.contains(obj)) {
				cache2.remove(obj);
				cache2.addFirst(obj);
			} else {
				cache2.addFirst(obj);
				trimCache2();
			}

		}

	}
	/**
	 * removes the desired obj from either of the caches
	 * 
	 * @param T obj
	 */
	public boolean removeObject(T obj) {

		boolean removed = false;
		if (enableDoubleLayer) {
			if (cache2.remove(obj)) {
				removed = true;
			}
		}

		if (cache1.remove(obj)) {

			removed = true;
		}

		return removed;
	}
	/**
	 * clears the caches
	 * 
	 * @param T obj
	 */
	public void clearCache() {
		cache1.clear();
		cache2.clear();
	}
	/**
	 * 
	 * Ensures that the first cache wont go over the given size
	 * 
	 */
	private void trimCache1() {
		if (cache1.size() > cache1Size) {
			cache1.removeLast();
		}
	}
	/**
	 * 
	 * Ensures that the second cache wont go over the given size
	 * 
	 */
	private void trimCache2() {
		if (cache2.size() > cache2Size) {
			cache2.removeLast();
		}
	}
	/**
	 * 
	 * Calculats and prints out the results 
	 * 
	 */
	public void cacheResults() {
		
		if (enableDoubleLayer) {
			NH = NH1 + NH2; // total number of cache hits
			NR = NR1; // total number of references to cache.
			if (NR1 > 0 && NR2 > 0) {
				HR1 = (double) NH1 / NR1;
				HR2 = (double) NH2 / NR2;
				HR = ((double) NH1 + (double) NH2) / NR1;
			}
		} else {
			// Zero out, second level cache not used.
			NH2 = 0;
			NR2 = 0;

			NH = NH1; // total number of cache hits
			NR = NR1; // total number of references to cache.
			if (NR > 0) {
				HR = (double) NH / NR;
			}

		}

		System.out.println("First level cache with " + cache1Size + " entries has been created");
		System.out.println("Second level cache with " + cache2Size + " entries has been created");
		System.out.println();
		System.out.println("The number of global references: " + NR);
		System.out.println("The number of global cache hits: " + NH);
		System.out.println("The global hit ratio: " + HR);
		System.out.println();
		System.out.println("The number of 1st-level references: " + NR1);
		System.out.println("The number of 1st-level cache hits: " + NH1);
		System.out.println("The 1st-level cache hit ratio: " + HR1);
		System.out.println();

		System.out.println("The number of 2nd-level references: " + NR2);
		System.out.println("The number of 2nd-level cache hits: " + NH2);
		System.out.println("The 2nd-level cache hit ratio: " + HR2);

		System.out.println("---------------------------------------------------------------------------");

	}
}
