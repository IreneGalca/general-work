import java.util.Random;

public class ProcessGenerator {
private Random random;
private double probability;

	//constructor
	public ProcessGenerator(double probability) {
		random = new Random();
		setProbability(probability);
	}

	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		// TODO Auto-generated method stub
		Process p = new Process(currentTime, random.nextInt(maxProcessTime) + 1, random.nextInt(maxLevel) + 1);
		//System.out.println("process + 1");
		return p;
	}

	public boolean query() {
		// TODO Auto-generated method stub
		if(random.nextDouble() <= probability) {
			return true;
		}
		return false;
	}
	
	public void setProbability(double probability) {
		this.probability = probability;
	}


}

