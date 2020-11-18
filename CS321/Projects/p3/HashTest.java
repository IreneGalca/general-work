import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/*********************************
 * CS321 Project #3: Hash Table
 * @Author Irene Galca
 *********************************/

public class HashTest {
	private static double load;
	static Random rand = new Random();

	public static void main(String[] args) throws IOException {
		load = Double.parseDouble(args[1]);

		/*******************
		 * Debug level 0
		 *******************/
		if (args.length == 2 || Integer.parseInt(args[2]) == 0) {
			if (Integer.parseInt(args[0]) == 1) {
				debugZero_Type1();
				System.exit(1);
			} else if (Integer.parseInt(args[0]) == 2) {
				debugZero_Type2();
				System.exit(1);
			}else if(Integer.parseInt(args[0]) ==3){
				debugZero_Type3();
				System.exit(1);
			}
		}

		/*******************
		 * Debug Level 1
		 *******************/
		if (args.length == 3) { 
			if (Integer.parseInt(args[0]) == 1) {
				debugOne_Type1();
				System.exit(1);
			} else if (Integer.parseInt(args[0]) == 2) {
				debugOne_Type2();
				System.exit(1);
			} else if (Integer.parseInt(args[0]) == 3) {
				debugOne_Type3();
				System.exit(1);
			}
		}
	}
	/*****************************
	 * Debug level 0: 3 Types
	 *****************************/
	
	//If Integer//
	public static void debugZero_Type1() { 
		HashTable<Integer> Lin = new HashTable<Integer>(load, 0);
		HashTable<Integer> Doub = new HashTable<Integer>(load, 0);

		System.out.println("Proper table size found: " + Lin.getSize());
		System.out.println("Data Type: random integer\n\n");
		System.out.println("Using Linear Hashing...\n");

		while(Lin.getLoad() < load) {
			HashObject<Integer> entry = new HashObject<Integer>(Math.abs(rand.nextInt()));
			Lin.linProbe(entry);
			Doub.doubleHash(entry);
		}
		System.out.println("Input " + (Lin.getLinearEntry() + Lin.getLinearDuplicates()) + " elements, of which " + Lin.getLinearDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Lin.avgLinearProbes() + "\n\n");
		System.out.println("Using Double Hashing...\n");
		System.out.println("Input " + (Doub.getDoubleEntry() + Doub.getDoubleDuplicates()) + " elements, of which " + Doub.getDoubleDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Doub.avgDoubleProbes() + "\n\n");

	}

	//if Long//
	public static void debugZero_Type2() {
		HashTable<Long> Lin = new HashTable<Long>(load, 0);
		HashTable<Long> Doub = new HashTable<Long>(load, 0);

		System.out.println("Proper table size found: " + Lin.getSize());
		System.out.println("Data Type: random integer\n\n");
		System.out.println("Using Linear Hashing...\n");

		while(Lin.getLoad() < load) {
			HashObject<Long> entry = new HashObject<Long>(System.currentTimeMillis());
			Lin.linProbe(entry);
			Doub.doubleHash(entry);
		}
		System.out.println("Input " + (Lin.getLinearEntry() + Lin.getLinearDuplicates()) + " elements, of which " + Lin.getLinearDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Lin.avgLinearProbes() + "\n\n");
		System.out.println("Using Double Hashing...\n");
		System.out.println("Input " + (Doub.getDoubleEntry() + Doub.getDoubleDuplicates()) + " elements, of which " + Doub.getDoubleDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Doub.avgDoubleProbes() + "\n\n");
	}

	//if String//
	public static void debugZero_Type3() throws FileNotFoundException {
		HashTable<String> Lin = new HashTable<String>(load, 0);
		HashTable<String> Doub = new HashTable<String>(load, 0);

		System.out.println("Proper table size found: " + Lin.getSize());
		System.out.println("Data Type: random integer\n\n");
		System.out.println("Using Linear Hashing...\n");

		File file = new File("word-list");
		Scanner fScan = new Scanner(file);

		while(Lin.getLoad() < load) {
			HashObject<String> entry = new HashObject<String>(fScan.nextLine());
			Lin.linProbe(entry);
			Doub.doubleHash(entry);

		}
		System.out.println("Input " + (Lin.getLinearEntry() + Lin.getLinearDuplicates()) + " elements, of which " + Lin.getLinearDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Lin.avgLinearProbes() + "\n\n");
		System.out.println("Using Double Hashing...\n");
		System.out.println("Input " + (Doub.getDoubleEntry() + Doub.getDoubleDuplicates()) + " elements, of which " + Doub.getDoubleDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Doub.avgDoubleProbes() + "\n\n");
		fScan.close();
	}

	/*****************************
	 * Debug level 1: 3 Types
	 * @throws IOException 
	 *****************************/
	
	//if Integer//
	public static void debugOne_Type1() throws IOException {
		HashTable<Integer> Lin = new HashTable<Integer>(load, 0);
		HashTable<Integer> Doub = new HashTable<Integer>(load, 0);

		System.out.println("Proper table size found: " + Lin.getSize());
		System.out.println("Data Type: random integer\n\n");
		System.out.println("Using Linear Hashing...\n");

		while(Lin.getLoad() < load) {
			HashObject<Integer> entry = new HashObject<Integer>(Math.abs(rand.nextInt()));
			Lin.linProbe(entry);
			Doub.doubleHash(entry);
		}
		System.out.println("Input " + (Lin.getLinearEntry() + Lin.getLinearDuplicates()) + " elements, of which " + Lin.getLinearDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Lin.avgLinearProbes() + "\n\n");
		System.out.println("Using Double Hashing...\n");
		System.out.println("Input " + (Doub.getDoubleEntry() + Doub.getDoubleDuplicates()) + " elements, of which " + Doub.getDoubleDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Doub.avgDoubleProbes() + "\n\n");

		FileWriter LDfile = new FileWriter("liner-dump");
		LDfile.write(Lin.viewTables());
		LDfile.close();
		FileWriter DDfile = new FileWriter("double-dump");
		DDfile.write(Lin.viewTables());
		DDfile.close();
	}

	//If Long//
	public static void debugOne_Type2() throws IOException {
		HashTable<Long> Lin = new HashTable<Long>(load, 0);
		HashTable<Long> Doub = new HashTable<Long>(load, 0);

		System.out.println("Proper table size found: " + Lin.getSize());
		System.out.println("Data Type: random integer\n\n");
		System.out.println("Using Linear Hashing...\n");

		while(Lin.getLoad() < load) {
			HashObject<Long> entry = new HashObject<Long>(System.currentTimeMillis());
			Lin.linProbe(entry);
			Doub.doubleHash(entry);
		}
		System.out.println("Input " + (Lin.getLinearEntry() + Lin.getLinearDuplicates()) + " elements, of which " + Lin.getLinearDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Lin.avgLinearProbes() + "\n\n");
		System.out.println("Using Double Hashing...\n");
		System.out.println("Input " + (Doub.getDoubleEntry() + Doub.getDoubleDuplicates()) + " elements, of which " + Doub.getDoubleDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Doub.avgDoubleProbes() + "\n\n");

		FileWriter LDfile = new FileWriter("liner-dump");
		LDfile.write(Lin.viewTables());
		LDfile.close();
		FileWriter DDfile = new FileWriter("double-dump");
		DDfile.write(Lin.viewTables());
		DDfile.close();
	}

	//if String//
	public static void debugOne_Type3() throws IOException {
		HashTable<String> Lin = new HashTable<String>(load, 0);
		HashTable<String> Doub = new HashTable<String>(load, 0);

		System.out.println("Proper table size found: " + Lin.getSize());
		System.out.println("Data Type: random integer\n\n");
		System.out.println("Using Linear Hashing...\n");

		File file = new File("word-list");
		Scanner fScan = new Scanner(file);

		while(Lin.getLoad() < load) {
			HashObject<String> entry = new HashObject<String>(fScan.nextLine());
			Lin.linProbe(entry);
			Doub.doubleHash(entry);
		}
		System.out.println("Input " + (Lin.getLinearEntry() + Lin.getLinearDuplicates()) + " elements, of which " + Lin.getLinearDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Lin.avgLinearProbes() + "\n\n");
		System.out.println("Using Double Hashing...\n");
		System.out.println("Input " + (Doub.getDoubleEntry() + Doub.getDoubleDuplicates()) + " elements, of which " + Doub.getDoubleDuplicates() + " duplicates" );
		System.out.println("load factor = " + load + ", Avg. no. of probes " + Doub.avgDoubleProbes() + "\n\n");
		fScan.close();

		FileWriter LDfile = new FileWriter("liner-dump");
		LDfile.write(Lin.viewTables());
		LDfile.close();
		FileWriter DDfile = new FileWriter("double-dump");
		DDfile.write(Lin.viewTables());
		DDfile.close();
	}

}