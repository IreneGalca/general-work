/*CS321: P2
 * @Author Irene Galca
 */
public class MaxHeap {
	private Process Heap[];
	private int size;

	/*********************************************
	 * Constructor
	 *********************************************/
	public MaxHeap() {
		this.size = 0;
		Heap = new Process[200];

	}
	/********************************************************
	 * Builds a new heap every time a top process is removed
	 ********************************************************/
	public void buildHeap() {
		int startIndex = (size / 2) -1;
		for(int i = startIndex; i >= 0; i--) {
			heapifyDown(i);
		}

	}
	/********************************************************
	 * gets a new process
	 * @return Heap
	 ********************************************************/	
	public Process[] getProcess() {
		return Heap;
	}

	/********************************************************
	 * Finds the position of parent.
	 * @param int pos (current position)
	 * @return position of Parent
	 ********************************************************/
	public int parent(int pos) {
		return (int)Math.ceil((double)pos/ 2) -1;
	}
	/********************************************************
	 *Finds position of left child.
	 *@param int pos (current position)
	 * @return 1 if parent is 0.
	 * @return position of left child otherwise.
	 ********************************************************/
	public int leftChild(int pos) {
		if(pos == 0) {
			return 1;
		}else
			return ((2 * pos) + 1);
	}	
	/********************************************************
	 * Finds position of right child.
	 * @param int pos (current position)
	 * @return 2 if parent is 0.
	 * @return position of right child otherwise.
	 ********************************************************/
	public int rightChild(int pos) {
		if(pos == 0) {
			return 2;
		}
		return (2 * pos) + 2;
	}
	/********************************************************
	 * Swaps with the highest job value in the heap.
	 * @param int xpos (job to be swapped)
	 * @param int ypos (job to be swapped with)
	 ********************************************************/
	private void swap(int xpos, int ypos) {
		Process tmp; //temporary node
		tmp = Heap[xpos];
		Heap[xpos] = Heap[ypos];
		Heap[ypos] = tmp;

	}
	/********************************************************
	 * Compares the current job with a parent job.
	 * If the current job has a higher value than parent,
	 * swap positions upwards.
	 * @param int pos (current position)
	 ********************************************************/
	public void heapifyUp(int pos) {
		while(pos >= 1 && Heap[parent(pos)].compareTo(Heap[pos]) < 0){
			swap(pos, parent(pos));
			pos = parent(pos);
		}

	}
	/********************************************************
	 * Checks if left child exists and/or right child exists
	 * Compares current job with left child or right child
	 * before comparing with parent.
	 * swaps the current job with a child that has a higher job value.
	 * @param int pos (current position)
	 * @return 
	 ********************************************************/
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
	/********************************************************
	 * Inserts a new job in the heap.
	 * @param Process p (new process/job being added).
	 ********************************************************/
	public void insert(Process p) {
		Heap[size] = p;
		size++;
		heapifyUp(size - 1);
	}
	/********************************************************
	 * Traverses upwards and fixes changes to other jobs/processes.
	 * @param int pos (current position)
	 ********************************************************/
	public void traverseUp(int pos) {
		int current = size;
		while( Heap[current].compareTo(Heap[parent(current)]) > 0) {
			swap (current, parent(current));
			current = parent(current);
		}
	}
	/********************************************************
	 * Extracts the job of maximum value from the heap
	 * @return Process popped (job removed from heap)
	 ********************************************************/
	public Process extractMax() {
		Process popped = Heap[0];

		if(size>=  1) {
			Heap[0] = Heap[size-1];
			Heap[size - 1] = null;
			size--;
		}
		heapifyDown(0);
		return popped;
	}

	/********************************************************
	 * gets the current size of heap.
	 * @return size
	 ********************************************************/
	public int getSize() {
		return size;
	}
}