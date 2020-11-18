/*************************************************
 *CS321:P4
 *Btree Project
 *@authors EmmaLytle, IreneGalca, AdriannaMickle 
 *************************************************/
import java.io.File;
import java.util.Scanner;

public class GeneBankParser {

	//parse through the file to find the dna strings we need to traslate to binary
    @SuppressWarnings({ "unused", "unchecked" })
	public static void parseFileIntoBtree(String filename, int sequenceLen, @SuppressWarnings("rawtypes") BTree btree){
        try {
            //One step, parse and insert into btree.
            
			String binary = "";//holds the final dna string translated to binary
			String genes = "";//holds the final dna string in gene format
			File file = new File(filename);
			Scanner fileScan = new Scanner(file);
			String binaryTranslation = "";
            int totalUniqueKeys = 0;

			while(fileScan.hasNext()) {
				if(fileScan.next().equals("ORIGIN")){
                    //reset sequence
                    binaryTranslation = "";
                    genes = "";
                    binary = "";
					boolean yes = true;
					while(yes) {//when a gene is found, translate each valid char to a binary number. Ignor invalid chars
						String dnaLine = fileScan.next();
						
						for(int i = 0; i < dnaLine.length(); i++) {
							char dnaLetterC = dnaLine.charAt(i);
							String dnaLetter = String.valueOf(dnaLetterC);
							if(dnaLetter.equals("/")){//stop translating genes when // is encountered
								yes = false;
								break;
							}
							else if(dnaLetter.equalsIgnoreCase("N")){
                                //reset sequence
                                binaryTranslation = "";
                                genes = "";
                                binary = "";
									continue;
							}
							else if(dnaLetter.equalsIgnoreCase("A")) {
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
								continue;
							}													
							binary = binary.concat(binaryTranslation);
                            genes = genes.concat(dnaLetter);

							if(genes.length() >= sequenceLen) {
								//insert genes and binary into query tree
                                Long binaryL = Long.parseLong(binary, 2);	                                	
								totalUniqueKeys += btree.insert(binaryL, btree.getRoot());								
								binary = binary.substring(2);//empty both strings for next sequence
								genes = genes.substring(1);
							}
						}
					 }					
				}		
			}
			fileScan.close();
		}	
		catch (Exception e) {
			System.out.print(e);
		}
	}
    // Given a key (long), convert back to Gene Sequence.
	public static String convertToGeneSequence(long key, int sequenceLen) {
		String binaryString = Long.toBinaryString(key);  //ex:  "1001"
		String binaryStringWithPaddedZeroes = "";
		String retVal = "";
		for (int i = 0; i < (sequenceLen * 2) - (binaryString.length()); i++) {
			binaryStringWithPaddedZeroes += "0";
		}
		binaryStringWithPaddedZeroes += binaryString;
		for (int i = 0; i <= binaryStringWithPaddedZeroes.length() - 2; i += 2) {
			if (binaryStringWithPaddedZeroes.substring(i, i + 2).equals("00")) {
				retVal += "a";
			} else if (binaryStringWithPaddedZeroes.substring(i, i + 2).equals("01")) {
				retVal += "c";
			} else if (binaryStringWithPaddedZeroes.substring(i, i + 2).equals("11")) {
				retVal += "t";
			} else if (binaryStringWithPaddedZeroes.substring(i, i + 2).equals("10")) {
				retVal += "g";
			}
		}
		return retVal;
	}


}