import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//Required arguments
//java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>][<debug level>]
public class GeneBankSearch {
	public static int debugLevel = 0;

		BTreeNode rootNode;
		private static BTree btree;
	
	public static void main(String args[]) {

		int cacheSize = -1;
		String BFile;
		boolean withCache = (1 == Integer.parseInt(args[0])); //if args[0] has an int then cache is used
		try {
			BFile = args[1];
			String QFile = args[2];
			//arguments for GeneBankSearch
			if (withCache) {
				cacheSize = Integer.parseInt(args[4]);
				if((args.length == 5)){
					if(debugLevel > 0 || debugLevel < 0) { //check later
						System.out.println("Invalid Debug level");
					}
					debugLevel = Integer.parseInt(args[5]);
				}
			}
			else {
				if (args.length == 4) {;
					System.out.print("Qfile = " + QFile);
					if(debugLevel > 0 || debugLevel < 0) { //check later
						System.out.println("Invalid Debug level");
					}
					debugLevel = Integer.parseInt(args[3]);
				}

			}
			System.out.println("query file is " + QFile);
			try {
				parseFile(QFile);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		//Path path = Paths.get(QFile);   
		//Path qFileName = path.getFileName(); //Get Actual filename, from the full path+ filename.
		//btree = new BTree(degree,btreeFileName);
	}

	private static void parseFile(String filename) throws ClassNotFoundException, IOException{
		//open btree file 
		//add root node to main memory
		try {//parse through the file to find the dna strings we need to traslate to binary
			String binary = "";//holds the final dna string translated to binary
			File file = new File(filename);
			Scanner fileScan = new Scanner(file);
			String binaryTranslation = "";
			//fileScan.useDelimiter("\r\n");
			while(fileScan.hasNext()) {
				String dnaLine = fileScan.next();
				for(int i = 0; i < dnaLine.length(); i++) {
					char dnaLetterC = dnaLine.charAt(i);
					String dnaLetter = String.valueOf(dnaLetterC);
					if(dnaLetter.equalsIgnoreCase("A")) {
						binaryTranslation = "00";
					}
					else if(dnaLetter.equalsIgnoreCase("T")) {
						binaryTranslation = "11";
					}
					else if(dnaLetter.equalsIgnoreCase("C")) {
						binaryTranslation = "01";
					}
					else if(dnaLetter.equalsIgnoreCase("G")) {
						binaryTranslation = "10";
					}
					else {
						System.out.println("invalid char found");
						continue;
					}
						binary = binary.concat(binaryTranslation);
				}
				Long binaryL = Long.parseLong(binary, 2);
				BTreeNode root = btree.getRoot();
				int dupCount = btree.GeneSearch(root, binaryL);
				System.out.println(dnaLine + " : "+ dupCount);
				binary = "";//empty both strings for next sequence
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}

