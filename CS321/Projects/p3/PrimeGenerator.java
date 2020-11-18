import java.util.Random;
import java.lang.Math;

/*********************************
 * CS321 Project #3: Hash Table
 * @Author Irene Galca
 *********************************/

public class PrimeGenerator {
	Random rand = new Random();
	public int GenPrimeNum(int min, int max) {
		int j = min;
		int k = rand.nextInt(j-2)+2;
		double doubNum = 0;
		int last = 0;

		for ( j = min; j < max; j++) { 

			doubNum = k;
			String binary = Integer.toBinaryString(j-1);

			//loops through binary numbers
			for (int i = 1; i < binary.length(); i++) { 
				if ((binary.charAt(i)) == '0') {
					doubNum = Math.pow(doubNum,2);
					doubNum %= j;
				} else if ((binary.charAt(i)) == '1') { 
					doubNum = Math.pow(doubNum,2);
					doubNum *= k;
					doubNum %= j;
				}
			}

			//if prime satisfies both conditions
			if (doubNum == 1) {
				if (last == (j - 2)) { 
					return j;           
				} else { //if prime only satisfies one	
					last = j;
				}
			}
		}
		return 0;
	}
	
}
