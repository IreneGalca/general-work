/*CS321: P2
 * @Author Irene Galca
 */
import java.util.Random;

public class ProcessGenerator {
	private Random random;
	private double probability;

	/*******************************************
	 *Constructor
	 *******************************************/
	public ProcessGenerator(double probability) {
		random = new Random();
		setProbability(probability);
	}

	/********************************************************
	 * Gets a new process with its current time,
	 * Maximum process time, and maximum level.
	 * @param currentTime (current time)
	 * @param maxProcessTime (maximum process time)
	 * @param maxLevel (maximum priority level)
	 * @return p (the new Process)
	 ********************************************************/
	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		Process p = new Process(currentTime, random.nextInt(maxProcessTime) + 1, random.nextInt(maxLevel) + 1);
		return p;
	}

	/********************************************************
	 * Provides a new process generator if query is provided.
	 * @return true if query provided 
	 * @return false if no query provided
	 ********************************************************/
	public boolean query() {
		if(random.nextDouble() <= probability) {
			return true;
		}
		return false;
	}
	
	/********************************************************
	 * Sets the probability when given.
	 ********************************************************/
	public void setProbability(double probability) {
		this.probability = probability;
	}
}