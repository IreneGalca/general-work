/*
 * @author Irene Galca
 */
public interface TimePiece {

	public static void reset() {
	/*
	 * Resets the timepiece to its starting time 
	 * (00:00:00- midnight by default)
	 */
	}
	
	public static void tick() {
	/*
	 * simulates one second of time passing.
	 */	
	}
	
	public static void display() {
	/*
	 * displays the current time.
	 */
	}
}
