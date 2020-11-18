/*CS321: P2
 * @Author Irene Galca
 */
public class Process implements Comparable<Process> {

	private int procTime;
	private int priLevel;
	private int timeNotProc;
	private int arivTime;

	/*******************************************
	 *Constructor
	 *******************************************/
	public Process(int arivTime, int procTime, int priLevel) {
		this.procTime = procTime;
		this.priLevel = priLevel;
		this.arivTime = arivTime;
	}

	/********************************************************
	 * Gets time remaining
	 * @return procTime (Process time remaining)
	 ********************************************************/
	public int getTimeRemaining() {
		return procTime;

	}

	/********************************************************
	 * Gets priority of job
	 * @return priLevel (priority level of job)
	 ********************************************************/
	public int getPriority() {
		return priLevel;
	}

	/********************************************************
	 * Sets the priority level of a job.
	 * @param int priLevel (priority level)
	 ********************************************************/
	public void setPriority(int priLevel) {
		this.priLevel = priLevel;
	}

	/********************************************************
	 * Get arrival time of job
	 * @return arivTime (Arrival time of job)
	 ********************************************************/
	public int getArrivalTime() {
		return arivTime;

	}
	
	/********************************************************
	 * Sets the time not processed.
	 * @param int timeNotProcessed (time not processed)
	 ********************************************************/
	public void setTimeNotProcessed(int timeNotProcessed) {
		this.timeNotProc = timeNotProcessed;

	}

	/********************************************************
	 * Gets time not processed.
	 * @return timeNotProc
	 ********************************************************/
	public int getTimeNotProcessed() {
		return timeNotProc;
	}

	/********************************************************
	 * Resets timeNotProc to 0.
	 ********************************************************/
	public void resetTimeNotProcessed() {
		timeNotProc = 0;
	}
	
	/********************************************************
	 * increases timeNotProc.
	 ********************************************************/
	public void increaseTimeNotProcessed() {
		timeNotProc++;
	}
	
	/********************************************************
	 * Finishes a job if there is no time remaining.
	 * @return true if time is out.
	 * @return false if there is still time remaining.
	 ********************************************************/
	public boolean finish() {
		if(procTime <= 0) {
			return true;
		}
		return false;

	}

	/********************************************************
	 * Compares a child process to a parent process to determine
	 * which job is higher priority.
	 ********************************************************/
	public int compareTo(Process proc) {
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

	/********************************************************
	 * Reduces time remaining for each time the job is processed.
	 ********************************************************/
	public void reduceTimeRemaining() {
		procTime--;

	}
}