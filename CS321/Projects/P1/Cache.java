/*CS 321: P1 
 * This class creates the cache and the main functions of
 * the cache class.
 * @Author Irene Galca
 */
import java.util.LinkedList;

public class Cache<T> {
	private LinkedList<T> cache;
	private int cSize; //the given size of cache
	
	public Cache(int cSize) {//constructor
		cache = new LinkedList<T>();
		this.cSize = cSize;
		
	}
	/*********************************************
	 * Finds the object in the cache and gets it.
	 * @param target Object in caches
	 * @return null if object doesn't exist
	 *********************************************/
	public T getObject(T target) {
		if (cache.contains(target)) {
			return target;
		}
		return null;
	}
	/*********************************************
	 * Adds the object to the top of the cache.
	 * @param target
	 *********************************************/
	public void addObject(T target) {
		if (cache.size() >= cSize ) {
			cache.removeLast();
		}
		cache.addFirst(target);
	}
	/*********************************************
	 * Removes the object from the cache
	 * @param target
	 *********************************************/
	public void removeObject(T target) {
		
		cache.remove(target);
	}
}
