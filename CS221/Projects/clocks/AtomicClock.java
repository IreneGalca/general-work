/*
 * @author Irene Galca
 */
public class AtomicClock extends Clock{
	/*
	 * @param ClockType.quantum: sets its actual type.
	 * @param 0.0: the value of its drift.
	 */
	public AtomicClock() {
		super(ClockType.quantum, 0.0);
	}
	public void display() {
		System.out.println("Quantum, Atomic Clock, time [" +time.formattedTime()+ "], drift = " + time.getTotalDrift());

	}
}