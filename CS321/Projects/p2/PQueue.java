/*CS321: P2
 * @Author Irene Galca
 */
public class PQueue {
	MaxHeap heap;

	public PQueue() {
		heap= new MaxHeap();
	}
	public void enPQueue(Process p) {
		heap.insert(p);
		heap.buildHeap();
	}
	public Process dePQueue() {
		return heap.extractMax();
	}

	public boolean isEmpty() {
		if(heap.getSize() == 0) {
			return true;
		}
		return false;
	}

	public void update(int timeToIncrementLevel, int maxLevel) {
		// TODO Auto-generated method stub

		//for(int i=(heap.getSize()-1); i >= 0 ; i--) {
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
			//heap.heapifyDown(i);
			//heap.increaseValue(timeToIncrementLevel);
		}

	}

}