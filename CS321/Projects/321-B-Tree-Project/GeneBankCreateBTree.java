import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileDescriptor;

/*
*
*@authors EmmaLytle, IreneGalca, AdriannaMickle 
*/
public class GeneBankCreateBTree {
	
	BTreeNode rootNode;
	private static BTree btree;
	private static int totalUniqueKeys, sequenceLen, degree;
	
	public static void main(String[] args)
    {
		int cacheSize = -1;
		int debugLevel = -1;
	    if ((args.length > 6)||(args.length < 4))
	    {
		    System.out.println("Usage: GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length>[<cache size>] [<debug level>]");
		    System.exit(1);
		}
	    //check if cache is needed
		boolean withCache = ((1 == Integer.parseInt(args[0])));//if args 0 is 1, then with cache is true 	
		//calculate degree
		int degree = Integer.parseInt(args[1]);
		if ((degree < 0)|| (degree == 1)) {
		    throw new IllegalArgumentException ("Illegal argument: degree must >= 0.");
		}
		else if(degree == 0) {
			//degree is = to optimal degree based on a disk block size of 4069 bytes and size of your btree node on disk
		}
		//get gbk file
		String gbkFile = args[2];
		
		//get sequence length
		sequenceLen = Integer.parseInt(args[3]);
		if ((sequenceLen <= 0) || (sequenceLen >= 32)) {
		    throw new IllegalArgumentException ("Illegal argument: time-to-increment-level must >= 1.");
		}
		//get cache size and debug value
	
		if((args.length == 5)) {
			if(withCache) {//if cache yes is specified
				cacheSize = Integer.parseInt(args[4]);
				debugLevel = Integer.parseInt(args[5]);
			}
			else {
				debugLevel = Integer.parseInt(args[4]);
			}
			
		}
		else if(args.length == 4){
			if(withCache){
				 throw new IllegalArgumentException ("Illegal argument: time-to-increment-level must >= 1.");	
		    }
		}

		Path path = Paths.get(gbkFile);   
		Path gbkFileName = path.getFileName(); //Get Actual filename, from the full path+ filename.

		
		String dumpFileName = String.format("%s.btree.dump.%s", gbkFileName,sequenceLen);
		String btreeFileName = String.format("%s.btree.data.%s.%s", gbkFileName,sequenceLen,degree);

		btree = new BTree(degree,btreeFileName,sequenceLen);
		GeneBankParser.parseFileIntoBtree(gbkFile,sequenceLen,btree);

		// Write text dump file, 
		if(debugLevel == 1){
			PrintStream dumpFile = null;
			
			try {
				dumpFile = new PrintStream(new FileOutputStream(dumpFileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.setOut(dumpFile);
			btree.printInorder(btree.getRoot());

			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));





		}
	}
	

	public void createQuery(String query) {
	
		File qFile = new File(query);
		File newQFile = new File("QueryResults.txt");
		String seq = "";//the gene sequence
		
		int occurs = 0; //number of times sequences occur
		try {
			PrintWriter print = new PrintWriter(newQFile);
			Scanner qScan = new Scanner(qFile);
			
			while(qScan.hasNextLine()) { //while file still has sequences in it
				
				String s = qScan.nextLine();
				if(seq.length() == sequenceLen) { //if the seq matches the sequenceLength
					occurs ++;
					print.write(s);//print seq occurrances to file
					print.write(occurs);//print occurrances to file
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}

	// public void calcDegree() {
	
	// 	/**********************************************************
	// 	 * Cannot assign actual values until BTree Class is done!!
	// 	 * 4096 = metadata + nodePtr + n(2t-1)[keys] + m(2t) [nodes]
	// 	 * 
	// 	 * Notes:
	// 	 * - when doing division cast to double, then cast result back to INT,
	// 	 *   or else you get the wrong result!!!
	// 	 *   
	// 	 * - Initialized each variable to 0 temporarily!
	// 	 **********************************************************/
		
	// 	int blockSize = 4096;
	// 	int metadata = 0; //m
	// 	int objSize = 0; //o
	// 	int diskSize = 0; //d
	// 	int pointer = 0; //p
	// 	int degree = 0; //t
		
	// 	/**************************************************************
	// 	 * m + o(2t-1) + p(2t-1) <= d
	// 	 * m + 2to - o + 2tp -p <= d
	// 	 * ------------------T degree formula below--------------------
	// 	 * t = ((double)(d + o - m - p)) / ((double)(2o + 2p))
	// 	 **************************************************************/
		
	// }

	// public static void printUsage() {
	// 	System.err.println(
	// 			"java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length>[<cache size>] [<debug level>]");
	// }
}
