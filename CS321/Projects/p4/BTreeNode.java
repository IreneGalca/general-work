/*************************************************
 *CS321:P4
 *Btree Project
 *@authors EmmaLytle, IreneGalca, AdriannaMickle 
 *************************************************/
import java.io.Serializable;

@SuppressWarnings("serial")
public class BTreeNode<T> implements Serializable {
	
	private Long[] BTreeKeys;
	//private BTreeNode<T>[] BTreePointers;
	private Integer[] BTreePointers;  ///coming soon.  Not yet though.
	private int[] dupCounts;
	private int probeCount=0;
	private int keyCount = -1;
	//private int maxKeys=-1; (may not need)
	private boolean isLeaf = true;
	public int childrenCount = 0;
	//private int filePointer;
	private int dataBlockNumber;

	BTreeNode(int t){
		//this.maxKeys = (2*t)-1; (may not need)
		this.BTreeKeys = new Long[(2* t)-1];
		this.dupCounts = new int[(2* t)-1];
		//this.BTreePointers = new BTreeNode[2* t];
		this.BTreePointers = new Integer[2* t];
		this.dataBlockNumber = BTreeDiskAccess.getNextDataBlockNumber();
		this.keyCount = 0;
	}

	
	

	public int getKeyArrayLen() {
		return BTreeKeys.length;
	}
	public Long getKey(int i) {
		return BTreeKeys[i];
	}
	public int getdup(int i) {
		return dupCounts[i];
	}
	
	public void setDup(int addTo, int index) {
		this.dupCounts[index]=addTo;
	}
	public void upDup(int addTo, int index) {
		this.dupCounts[index]+=addTo;
	}
	public int getFilePointer() {
		return this.dataBlockNumber;
	}
	public void setFilePointer(int newPointer) {
		this.dataBlockNumber = newPointer;
	}
	public void upProbe() {
		this.probeCount++;
	}
	public void setLeaf(boolean tof) {
		this.isLeaf = tof;
	}
	public boolean isLeaf() {
		return this.isLeaf;
	}
	public void upKeyCount() {
		this.keyCount++;
	}
	public void setKeyCount(int i) {
		this.keyCount = i;
	}
	public void downKeyCount() {
		this.keyCount--;
	}
	public int getKeyCount() {
		return keyCount;
	}
	public void insertKey(Long key, int index) {
		this.BTreeKeys[index] = key;
	}
	@SuppressWarnings("unchecked")
	public BTreeNode<T> getPointer(int i) {
		try {
			int dataBlockNumber = this.BTreePointers[i];
			BTreeNode<T> nodeFromDisk = BTreeDiskAccess.disk_read(dataBlockNumber);
			return nodeFromDisk;
		} catch (Exception e) {
			return null;
		}

		//return BTreePointers[i];
	}
	@SuppressWarnings("rawtypes")
	public void insertPointer(BTreeNode pointer, int i) {
		if(pointer == null)
		{
			this.BTreePointers[i] = null;
		}
		else{
			this.BTreePointers[i] = pointer.dataBlockNumber;
			
		}
		// BTreeDiskAccess.removeDataBlockNumber(new Integer(pointerDataBlockNumber));  //free up block to be reused.				
		// BTreeDiskAccess.diskWrite(pointerDataBlockNumber,null); //clear out pointer
		// BTreeDiskAccess.diskWrite(this.dataBlockNumber,this); //write parent
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public void deleteKeys(){//this is for the split node feature, deletes all keys left of middle node (inclusive)
		int middleIndex = (getKeyCount()/2)+1;//find index of middle element to promote
		int k = 0;
		//now loop through right node and remove all old elements
		for(int i = middleIndex-1; i < BTreeKeys.length; i++) {
			BTreeKeys[i] = null;
			keyCount--;			
		}
		for(int i = middleIndex-1; i < BTreePointers.length; i++) {//now loop through right node and remove all old elements
			 if(BTreePointers[i] != null) {
				 try {
					
					int nextDataBlockNumber = BTreePointers[i];
					BTreeDiskAccess.removeDataBlockNumber(new Integer(nextDataBlockNumber));  //free up block to be reused.
			
					BTreeDiskAccess.diskWrite(i,null);
					BTreePointers[i] = null;
					childrenCount--;		
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
		
				}
			}
		}
	}
	public void fillNodeToMax()
	{
		// Use to calculate max nodesize.
		for (int i = 0; i < BTreeKeys.length; i++) {
			BTreeKeys[i] = Long.MAX_VALUE;
		}
		for (int i = 0; i < dupCounts.length; i++) {
			dupCounts[i] = Integer.MAX_VALUE;
		}
		for (int i = 0; i < BTreePointers.length; i++) {
			BTreePointers[i] = Integer.MAX_VALUE;
		}

	}

}


