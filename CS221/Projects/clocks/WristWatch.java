/*
 * @author Irene Galca
 */
public class WristWatch extends Clock {

	/*
	 * @param ClockType.digital: sets its actual type.
	 * @param 0.00003472: the value of its drift.
	 */
	public WristWatch() {
		super(ClockType.digital, 0.00003472);
	}
	public void display() {
		System.out.println("Digital, Wristwatch, time [" +time.formattedTime()+ "], drift = " + time.getTotalDrift());

	}
}
