
public class Process implements Comparable<Process> {
	//private int pLevel;
	//private int timeRemaining;
	//private int arrivalTime;
	//private int maxPLevel;
	//private int timeNotProcessed;

	private int procTime;
	private int priLevel;
	private int timeNotProc;
	private int arivTime;

	// constructor
	public Process(int arivTime, int procTime, int priLevel) {
		this.procTime = procTime;
		this.priLevel = priLevel;
		this.arivTime = arivTime;
	}

	public int getTimeRemaining() {
		return procTime;

	}

	public int getPriority() {
		return priLevel;
	}

	public void setPriority(int priLevel) {
		this.priLevel = priLevel;
	}

	public int getArrivalTime() {
		return arivTime;

	}
	public void setTimeNotProcessed(int timeNotProcessed) {
		this.timeNotProc = timeNotProcessed;

	}

	public int getTimeNotProcessed() {
		return timeNotProc;
	}

	public void resetTimeNotProcessed() {
		timeNotProc = 0;
	}
	
	public void increaseTimeNotProcessed() {
		timeNotProc++;
	}

	public boolean finish() {
		if(procTime <= 0) {
			return true;
		}
		return false;

	}

	@Override
	public int compareTo(Process proc) {
		// TODO Auto-generated method stub
		if (this.getPriority() == proc.getPriority()) {
			if (this.getArrivalTime() > proc.getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		} else if(this.getPriority() < proc.getPriority()) {
			return -1;
		}else {
			return 1;
		}
	}

	public void reduceTimeRemaining() {
		// TODO Auto-generated method stub
		//resetTimeNotProcessed();
		procTime--;

	}


}

