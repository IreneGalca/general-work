import java.io.Serializable;

/*
*
*@authors EmmaLytle, IreneGalca, AdriannaMickle 
*/
public class BTreeNode<T> implements Serializable {
	
	private Long[] BTreeKeys;
	private BTreeNode<T>[] BTreePointers;
	//private Long[] BTreePointers;  ///coming soon.  Not yet though.
	private int[] dupCounts;
	private int probeCount=0;
	private int keyCount = -1;
	private int maxKeys=-1;
	private boolean isLeaf = true;
	public int childrenCount = 0;
	private int filePointer;
	
	BTreeNode(int t){
		this.maxKeys = (2*t)-1;
		this.BTreeKeys = new Long[(2* t)-1];
		this.dupCounts = new int[(2* t)-1];
		this.BTreePointers = new BTreeNode[2* t];
		//this.BTreePointers = new Long[2* t];
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
	public BTreeNode<T> getPointer(int i) {
		// try {
			
		// 	BTreeNode<T> fromDisk = BTreeDiskAccess.disk_read(i);
		// 	return fromDisk;
		// } catch (Exception e) {
		// 	return null;
		// }

		return BTreePointers[i];
	}
	public void setDup(int addTo, int index) {
		this.dupCounts[index]=addTo;
	}
	public void upDup(int addTo, int index) {
		this.dupCounts[index]+=addTo;
	}
	public int getFilePointer() {
		return this.filePointer;
	}
	public void setFilePointer(int newPointer) {
		this.filePointer = newPointer;
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
	public void insertPointer(BTreeNode pointer, int index) {

		// try {
			
		// 	BTreeDiskAccess.diskWrite(index,pointer);
			
		// } catch (Exception e) {
		// 	System.out.println(e.getMessage());
		// }

		this.BTreePointers[index] = pointer;
	}
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

				// try {
			
				// 	BTreeDiskAccess.diskWrite(i,null);
					
				// } catch (Exception e) {
				// 	System.out.println(e.getMessage());
		
				// }
				BTreePointers[i] = null;		
				childrenCount--;		
			}
		}
	}


}


