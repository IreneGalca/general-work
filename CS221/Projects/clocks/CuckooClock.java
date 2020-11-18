/*
 * @author Irene Galca
 */
public class CuckooClock extends Clock {

	public CuckooClock() {
		/*
		 * @param ClockType.mecahnical: sets its actual type.
		 * @param 0.000694444: the value of its drift.
		 */
		super(ClockType.mechanical, 0.000694444);
	}
	public void display() {
		System.out.println("Mechanical, Cuckoo Clock, time [" +time.formattedTime()+ "], drift = " + time.getTotalDrift());

	}
}
