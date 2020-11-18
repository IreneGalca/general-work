/*CS321: P2
 * @Author Irene Galca
 */
public class MaxHeap {
	private Process Heap[];
	private int size;

	//Constructor
	public MaxHeap() {
		this.size = 0;
		Heap = new Process[200];

	}

	public void buildHeap() {
		int startIndex = (size / 2) -1;
		for(int i = startIndex; i >= 0; i--) {
			heapifyDown(i);
		}

	}
	public Process[] getProcess() {
		return Heap;
	}

	//Position of Parent Node
	public int parent(int pos) {
//		if(pos == 0) {
//			return -1;
//		}
		return (int)Math.ceil((double)pos/ 2) -1;
	}

	//Returns position of left child
	public int leftChild(int pos) {
		if(pos == 0) {
			return 1;
		}else
			return ((2 * pos) + 1);
	}	
	//returns position of right child
	public int rightChild(int pos) {
		if(pos == 0) {
			return 2;
		}
		return (2 * pos) + 2;
	}
	//Checks if given node is a leaf
	//	public boolean isLeaf(int pos) {
	//		if (pos >= (size/2) && pos <= size) {
	//			return true;
	//		}
	//		return false;
	//	}
	private void swap(int xpos, int ypos) {
		Process tmp; //temporary node
		tmp = Heap[xpos];
		Heap[xpos] = Heap[ypos];
		Heap[ypos] = tmp;

	}
	public void heapifyUp(int pos) {
		while(pos >= 1 && Heap[parent(pos)].compareTo(Heap[pos]) < 0){
			swap(pos, parent(pos));
			pos = parent(pos);
		}
//		for (int i = pos; i > 0; i = parent(i)) {
//			if(Heap[i].compareTo(Heap[parent(i)]) == 1) {
//				swap(i, parent(i));
//				
//			}
//		}
	}
	public void heapifyDown(int pos) {
		if(Heap[leftChild(pos)] != null) {
			if(Heap[rightChild(pos)] != null) {
				//both left and right exist, compare left and right before comparing either against parent
				if (Heap[leftChild(pos)].compareTo(Heap[rightChild(pos)]) > 0) {
					if(Heap[leftChild(pos)].compareTo(Heap[pos]) > 0) {
						swap (pos, leftChild(pos));
						heapifyDown(leftChild(pos));
					}
				}else {
					if(Heap[rightChild(pos)].compareTo(Heap[pos]) > 0) {
						swap (pos, rightChild(pos));
						heapifyDown(rightChild(pos));
					}
				}
				return;
			}
			//no right child exists
			if(Heap[leftChild(pos)].compareTo(Heap[pos]) > 0) {
				swap (pos, leftChild(pos));
				heapifyDown(leftChild(pos));
			}
			return;
		}
	}
	//inserts a new element into the heap
	public void insert(Process p) {
		Heap[size] = p;
		size++;
			heapifyUp(size - 1);
	}
	//traverse upwards and fix changes to other nodes/elements
	public void traverseUp(int pos) {
		int current = size;
		while( Heap[current].compareTo(Heap[parent(current)]) > 0) {
			swap (current, parent(current));
			current = parent(current);
		}
	}
	//removes the element from the heap
	public Process extractMax() {
		Process popped = Heap[0];
		
		if(size>=  1) {
			Heap[0] = Heap[size-1];
			Heap[size - 1] = null;
			size--;
		}
		//System.out.println(Heap[0]);
		heapifyDown(0);
		return popped;
	}
	public int getSize() {
		return size;
	}

	//increases key value
	//	public void increaseValue(int i) {
	//		int parent = i/2;
	//		if (parent > i ) {
	//			swap(parent, i);
	//		}
	//		Heap[i] = value;
	//		while(i > 1 && Heap[i/2] < Heap[i]) {
	//			swap(i, value);
	//			i = i/2;

}



