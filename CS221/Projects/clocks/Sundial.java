/*
 * @author Irene Galca
 */
public class Sundial extends Clock {

	/*
	 * @param ClockType.natural: sets its actual type.
	 * @param 0.00: the value of its drift.
	 */
	public Sundial() {
		super(ClockType.natural, 0.00);
		
	}
	public void display() {
		System.out.println("Natural, Sundial, time [" +time.formattedTime()+ "], drift = " + time.getTotalDrift());
	}

}
