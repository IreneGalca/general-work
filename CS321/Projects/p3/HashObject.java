import java.util.Random;
/*********************************
 * CS321 Project #3: Hash Table
 * @Author Irene Galca
 *********************************/

public class HashObject<T> {

	private T key;
	private int probeInserts =0;
	private int dupProbes = 0;
	Random rand = new Random();

	public HashObject(T obj) {
		key = obj; 
	}
	
	/**************************
	 * Increment methods
	 **************************/
	public void incProbeInserts() {
		probeInserts++;
	}
	
	public void incDupProbes() {
		dupProbes++;
	}
	/****************************
	 * Hash object functionality
	 ****************************/
	public int hashCode() {//determines value of key
		return key.hashCode();
	}
	
	public T getKey() {
		return key;
	}
	
	public int getProbeInserts() {
		return probeInserts;
	}

	public int getDupProbes() {
		return dupProbes;
	}
	
	public boolean isEqual(HashObject<T> obj) {
		if(obj.getKey().equals(key)) {
			return true;
		}else {
			return false;
		}
	}
	
	

	

	public String toString() {// counting twice
		return " " + getKey() + " " + getDupProbes()/2 + " " + getProbeInserts()/2;
	}
	
}