/*
 * @author Irene Galca
 */
public class ClockSimulation {
private static final long SEC_PER_DAY= 86400;
private static final long SEC_PER_WEEK= 604800;
private static final long SEC_PER_MONTH= 2592000;
private static final long SEC_PER_YEAR= 31536000;

	public static void main (String[]args) {
	Bag<Clock> clocks= new Bag<Clock>();
	/*
	 * here is where we add each of our clock subclasses to the bag.
	 */
	clocks.add(new Sundial());
	clocks.add(new CuckooClock());
	clocks.add(new GrandfatherClock());
	clocks.add(new WristWatch());
	clocks.add(new AtomicClock());
	
	/*
	 * Shows us the value for each current clock.
	 * Starting time: ___
	 * After one day: ___
	 * After one week: ___
	 * After one month: ___
	 * After on year: ___
	 */
	System.out.println("Starting time: ");
	clockSim(clocks, 0);
	System.out.println(" ");
	
	
	System.out.println("After one day: ");
	clockSim(clocks, SEC_PER_DAY);
	System.out.println(" ");
	
	System.out.println("After one week: ");
	clockSim(clocks, SEC_PER_WEEK);
	System.out.println(" ");
	
	System.out.println("After one month: ");
	clockSim(clocks, SEC_PER_MONTH);
	System.out.println(" ");
	
	System.out.println("After one Year: ");
	clockSim(clocks, SEC_PER_YEAR);
	System.out.println(" ");
	}
	
	/*
	 * Runs the simulation for each clock that is called.
	 * 1. calls specific clock
	 * 2. ticks the clock (increments seconds)
	 * @param Bag<Clock> clocks: Contains our clocks.
	 * @param long seconds: number of seconds we want to simulate.
	 */
	private static void clockSim(Bag<Clock> clocks, long seconds) {
		for(int i=0; i<clocks.getSize(); i++) {
			//runs tick several thousand times for current clock
			Clock thisClock= clocks.get(i);
			thisClock.tick();
			for(int j = 0; j<seconds; j++) {
				thisClock.tick();
			}
			thisClock.display();
			thisClock.reset();
		}
	}
	
	
}
