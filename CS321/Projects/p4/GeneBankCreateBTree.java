/*************************************************
 *CS321:P4
 *Btree Project
 *@authors EmmaLytle, IreneGalca, AdriannaMickle 
 *************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileDescriptor;

public class GeneBankCreateBTree {
	
	@SuppressWarnings("rawtypes")
	BTreeNode rootNode;
	@SuppressWarnings("rawtypes")
	private static BTree btree;
	//private static int totalUniqueKeys, degree; (may not need)
	private static int sequenceLen;
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
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
			degree = BTreeDiskAccess.getOptimalDegree(); //around 65.
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

		try {			
			//1. Write Root to disk.
			BTreeNode rootNode = btree.getRoot();
			BTreeDiskAccess.diskWrite(rootNode.getFilePointer(),rootNode);

			//2. Write Meta to disk.
			BTreeDiskAccess.diskWriteBTreeMetaData(degree, sequenceLen, rootNode.getFilePointer());

		} catch (Exception e) {
		}

		
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
	

	@SuppressWarnings("resource")
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
					print.write(s);//print seq occurrences to file
					print.write(occurs);//print occurrences to file
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}

}
