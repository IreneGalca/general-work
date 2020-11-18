/*
 * @author Irene Galca
 */
public class GrandfatherClock extends Clock{

	/*
	 * @param ClockType.mecahanical: sets its actual type.
	 * @param 0.000347222: the value of its drift.
	 */
	public GrandfatherClock() {
		super(ClockType.mechanical, 0.000347222);


	}
	public void display() {
		System.out.println("Mechanical, Grandfather Clock, time [" +time.formattedTime()+ "], drift = " + time.getTotalDrift());



	}
}

