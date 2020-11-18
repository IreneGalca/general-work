/*********************************
 * CS321 Project #3: Hash Table
 * @Author Irene Galca
 *********************************/

public class HashTable<T> {

	private HashObject<T> table[];
	public double load;
	public int tableSize; 
	public int debugLev;

	private int linProbeCount = 0;
	private int doubProbeCount = 0;
	private int linEntry;
	private int doubEntry;
	private int dupLinCount;
	private int dupDoubCount;
	static PrimeGenerator p = new PrimeGenerator();

	//Constructor//
	@SuppressWarnings("unchecked")
	public HashTable(double loadfactor, int debug) {
		loadfactor = this.load;
		debug = this.debugLev;
		tableSize = p.GenPrimeNum(95500, 96000);
		table = new HashObject[tableSize];
	}
	/********************
	 * Get methods
	 ********************/
	public int getSize() {
		return this.tableSize;
	}
	
	public double getLoad() {
		return (double) linEntry / tableSize;
	}
	
	public int getLinearEntry() {
		return linEntry;
	}

	public int getDoubleEntry() {
		return doubEntry;
	}
	
	public int getLinearDuplicates() {
		return dupLinCount;
	}
	
	public int getDoubleDuplicates() {
		return dupDoubCount;
	}

	public int getlinProbes() {
		return linProbeCount;
	}

	public int getDoubleProbes() {
		return doubProbeCount;
	}

	/******************
	 * Hash functions
	 ******************/
	public int linearHashValue(int key, int l) {// finds the hash values
		int retval = (key + l) % tableSize; 
		if (retval < 0) {
			retval += tableSize;
		}
		return retval;
	}

	public int doubleHashValue(int key) {
		int retval = (1 + key) % (tableSize - 2);
		if (retval < 0) {
			retval += tableSize - 2;
		}
		return retval;
	}

	public int linProbe(HashObject<T> obj) { 
		int itr = 0; 
		int objKey = obj.hashCode() % tableSize;
		if (objKey < 0) {
			objKey += tableSize;
		}
		int index = linearHashValue(objKey, 0);
		int temp = linProbeCount;

		while (itr < this.tableSize) {
			itr++;
			
			linProbeCount++;
			if (table[index] != null && table[index].isEqual(obj)) {
				dupLinCount++;
				table[index].incDupProbes();
				linProbeCount = temp;
				return index;
				
			} else if (table[index] == null) { // add obj
				table[index] = obj;
				linEntry++;
				table[index].incProbeInserts(); 
				return index;
			}
			table[index].incProbeInserts(); 
			index = linearHashValue(objKey, itr);
		}
		return index;
	}

	public void doubleHash(HashObject<T> obj) {
		int objKey = obj.hashCode() % tableSize;
		if (objKey < 0) {
			objKey += tableSize;
		}
		int itr = 0;
		int index = linearHashValue(objKey, 0);
		int temp = doubProbeCount;
		while (itr < tableSize) {
			itr++;
			
			if (table[index] != null && table[index].isEqual(obj)) { 
				dupDoubCount++; 
				table[index].incDupProbes();
				doubProbeCount = temp;
				return;
				
			} else if (table[index] == null) { // add obj
				table[index] = obj;
				doubEntry++;
				doubProbeCount++; 
				table[index].incProbeInserts();
				return;
			}
			table[index].incProbeInserts(); 
			index = (objKey + (itr * doubleHashValue(objKey))) % tableSize;
			doubProbeCount++; 
		}
		return;

	}

	/**************
	 * Averaging
	 **************/
	public double avgLinearProbes() {
		return (double) linProbeCount / linEntry;
	}

	public double avgDoubleProbes() {
		return (double) doubProbeCount / doubEntry;
	}

	public String viewTables() {
		String result = "";
		
		for (int index = 0; index < table.length; index++) {
			HashObject<T> obj = table[index];
			
			if (table[index] != null) {
				result += ("table[" + index + "]: " + table[index].toString() + "\n"); 
			}
		}
		return result;
	}

}