/*CS321: P2
 * @Author Irene Galca
 */
public class PQueue {
	MaxHeap heap;

	/*******************************************
	 *Constructor
	 *******************************************/
	public PQueue() {
		heap= new MaxHeap();
	}

	/********************************************************
	 * Enqueues job from the heap
	 * @return enqueued job.
	 ********************************************************/
	public void enPQueue(Process p) {
		heap.insert(p);
		heap.buildHeap();
	}

	/********************************************************
	 * Dequeues job from the heap
	 * @return dequeued job.
	 ********************************************************/
	public Process dePQueue() {
		return heap.extractMax();
	}

	/********************************************************
	 * Checks if queue is empty.
	 * @return true
	 * @return false (otherwise)
	 ********************************************************/
	public boolean isEmpty() {
		if(heap.getSize() == 0) {
			return true;
		}
		return false;
	}

	/********************************************************
	 * Updates priority level for every time it isn't processed.
	 * @param int timeToIncrementLevel (priority level incremented)
	 * @param in maxLevel (maximum priority level)
	 ********************************************************/
	public void update(int timeToIncrementLevel, int maxLevel) {

		for( int i = 0; i<heap.getSize(); i++) {
			Process p = heap.getProcess()[i];
			p.setTimeNotProcessed(p.getTimeNotProcessed() + 1);

			if(p.getTimeNotProcessed() >= timeToIncrementLevel) {
				if(p.getPriority() < maxLevel) {
					p.setPriority(p.getPriority() + 1) ;
					heap.heapifyUp(i);
				}
				p.resetTimeNotProcessed();
			}
		}
	}
}