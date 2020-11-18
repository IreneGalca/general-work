/*CS 321: P1
 * This test class should create two caches 
 * and test their functionality.
 * @Author Irene Galca
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Test {
	private static int nH = 0; // total number of hits.
	private static int nH1 = 0; // number of hits in 1st cache.
	private static int nH2 = 0; // number of hits in 2nd cache.
	private static int nR = 0; // total number of references.
	private static int nR1 = 0 ; // number of references in 1st cache.
	private static int nR2 = 0; // number of references in 2nd cache.
	private static double hR = 0; //total hit ratio.
	private static double hR1 = 0; //cache1 hit ratio.
	private static double hR2 = 0; //cache2 hit ratio.
	private static String c1Item; // item to be found in cache

	public static void main(String[] args) {
		Cache<String> c1; // cache 1
		Cache<String> c2; // cache 2

		if (args[0].equals("1")) {// 1st level cache
			c1 = new Cache<String>(Integer.parseInt(args[1])); // creates cache 1 of given size
			c2 = new Cache<String>(0);
			System.out.println("\n*************************************************************");
			System.out.println("1st level cache with " + args[1] + " entries has been created.");
			System.out.println("*************************************************************");

		} else if (args[0].equals("2")) {// 2nd level cache
			c1 = new Cache<String>(Integer.parseInt(args[1])); // creates cache 1 of given size
			c2 = new Cache<String>(Integer.parseInt(args[2])); // creates cache 2 of given size
			System.out.println("\n*************************************************************");
			System.out.println("1st level cache with " + args[1] + " entries has been created.");
			System.out.println("2nd level cache with " + args[2] + " entries has been created.");
			System.out.println("*************************************************************");
		} else { // if cache level input is invalid
			System.out.println("\nError: Size invalid or improper format.");
			System.out.println("Please enter correct format. (example):");
			System.out.println("Test 1#: java Test <cache_level> <size_input> <textfile>");
			System.out.println("\n or");
			System.out.println("Test 2#: java Test <2_cache_level> <1st_cache_size_input> <2nd_cache_size_input> <textfile>");
			return;
		}
		File txt = new File(args[3]);
		try {
			Scanner wordScn = new Scanner(txt);
			while (wordScn.hasNextLine()) {
				String Line = wordScn.nextLine();
				StringTokenizer st =new StringTokenizer (Line, " \t");
				//Scanner wordScn = new Scanner(lineScn.next());
				while (st.hasMoreTokens()) {
					nR++;
					c1Item = st.nextToken();
					if (args[0].equals("1")) {
						nR1++;
						if (c1.getObject(c1Item) != null) { // 1st level hit
							c1.removeObject(c1Item);
							c1.addObject(c1Item);
							nH1++;

						}else{
							c1.addObject(c1Item);
						}
					} else if (args[0].equals("2")) {
						nR1++;
					
						if (c1.getObject(c1Item) != null) { // 1st level hit
							c1.removeObject(c1Item);
							c1.addObject(c1Item);
							c2.removeObject(c1Item);
							c2.addObject(c1Item);
							nH1++;
							
						} else if (c2.getObject(c1Item) != null) { // 2nd level hit
							c1.addObject(c1Item);
							//nH1++;
							nH2++;
							nR2++;
							c2.removeObject(c1Item);
							c2.addObject(c1Item);
							
							
						} else {
							c1.addObject(c1Item);
							c2.addObject(c1Item);
							nR2++;
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nH = nH1 + nH2; // total number of hits 
		//nR = nR1 + nR2; // total number of references
		hR1 = (double) nH1 / (double) nR1; //hit ratio in cache 1
		hR2 = (double) nH2 / (double) nR2; //hit ratio in cache 2
		hR = (double) nH / (double) nR; //total hit ratio
		System.out.println("\nGlobal references: " + nR);
		System.out.println("Global Cache Hits: " + nH);
		System.out.println("Global hit ratio: " + hR);
		System.out.println("\nReferences in 1st-level Cache: " + nR1);
		System.out.println("Hits in 1st-level Cache: " + nH1);
		System.out.println("Hit ratio in 1st-level Cache: " + hR1);
		System.out.println("\nReferences in 2nd-level Cache: " + nR2);
		System.out.println("Hits in 2nd-level Cache: " + nH2);
		System.out.println("Hit ratio in 2nd-level Cache: " + hR2);
		
	}
}
