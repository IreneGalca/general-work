/*
 * @author Irene Galca
 */
public abstract class Clock implements TimePiece{
	private ClockType clockType;
	protected Time time;
	public enum ClockType{natural, digital, mechanical, quantum}
	/*
	 * We are setting our actual clock involving type
	 * and drift
	 * @param ClockType type: sets the type of clock (natural, digital, etc...).
	 * @param double drift: inputs the drift values for each clock type.
	 */
	public Clock(ClockType type, double drift) {
	clockType = type;
	time = new Time(0, 0, 0, drift);
	}
	/*
	 * gets the type of clock from ClockType
	 */
	public ClockType getClockType() {
		return clockType;

	}
	/*
	 * sets the ClockType
	 */
	public void setClockType(ClockType type) {

	}
	/*
	 * resets clock to its starting time [00:00:00].
	 */
	public void reset()
	{
		time.resetToStartTime();
	}
	/*
	 * tick adds seconds onto the clock.
	 */
	public void tick()
	{
		time.incrementTime();
	}
	/*
	 * displays the current time on the clock.
	 */
	public abstract void display();

}


