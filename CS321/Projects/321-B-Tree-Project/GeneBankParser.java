import java.io.File;
import java.util.Scanner;

public class GeneBankParser {


    public static void parseFileIntoBtree(String filename, int sequenceLen, BTree btree){
        try {
            //One step, parse and insert into btree.
            
            //parse through the file to find the dna strings we need to traslate to binary
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
									continue;//Not sure if we should ignore and continue parsing or treat this like //
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
								//System.out.println("total keys entered is now " + totalUniqueKeys);
								//System.out.println("Inserted new bnode into tree with dna string " + genes + " and binary : " + binary + " and long : " + binaryL);
								binary = binary.substring(2);//empty both strings for next sequence
								genes = genes.substring(1);
							}
						}
					 }					
				}		
			}
				
			fileScan.close();
			
			// System.out.println("BTree creations complete, total dups " + btree.totalDups() +
			// 		" \ntotal nodes inserted: " + totalUniqueKeys + " gene search resulsts: "+
			// 		//for sequences of 3
			// 		  " \nkey aaa 0: " + btree.GeneSearch(btree.getRoot(), (long) 0) + " "+
			// 		  " \nkey aac 1: " + btree.GeneSearch(btree.getRoot(), Long.parseLong("1")) +
			// 		  " \nkey acc 5: "+ btree.GeneSearch(btree.getRoot(), Long.parseLong("5")) + 
			// 		  " \nkey aca 4: " + btree.GeneSearch(btree.getRoot(), Long.parseLong("4"))+ 
			// 		  " \nkey aag 2: " + btree.GeneSearch(btree.getRoot(), Long.parseLong("2")) +
			// 		  " \nkey aga 8: " + btree.GeneSearch(btree.getRoot(), Long.parseLong("8")) +
			// 		  " \nkey agg 10: " + btree.GeneSearch(btree.getRoot(), Long.parseLong("10")) +
					  
					  
			// 		  " \nkey caa 16:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("16")) +
			// 		  " \nkey cca 20: "+btree.GeneSearch(btree.getRoot(), Long.parseLong("20"))+ 
			// 		  " \nkey cac 17: " +btree.GeneSearch(btree.getRoot(), Long.parseLong("17"))+ 
			// 		  " \nkey ccc 21: " +btree.GeneSearch(btree.getRoot(), Long.parseLong("21"))+ 
			// 		  " \nkey ccg 22:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("22"))+
			// 		  " \nkey cgc 25:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("25"))+
			// 		  " \nkey cgg 26: " + btree.GeneSearch(btree.getRoot(), Long.parseLong("26"))+
					  
			// 		  " \nkey ggg 42:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("42"))+
			// 		  " \nkey gga 40:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("40"))+
			// 		  " \nkey gag 34:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("34"))+
			// 		  " \nkey gaa 32:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("32"))+
			// 		  " \nkey ggc 41:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("41"))+
			// 		  " \nkey gcg 38:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("38"))+
			// 		  " \nkey gcc 37:" + btree.GeneSearch(btree.getRoot(), Long.parseLong("37")));
				
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