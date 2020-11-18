/*
 * @author IreneGalca
 * This code is supposed to test the validity of the user inputs,
 * and the validity of the MagicSquare indexes and values.
 */

import java.io.IOException;


/*@param
 * This checks the args parameters used, and determines if the users
 * have entered a valid input.
 */
public class MagicSquareTester {
	static int size = 0;
	static String filename= "";
	static boolean choice = false;
	
	public static void main(String args[]) throws IOException {

		if( args.length > 0 && (args[0].equals("-check") || args[0].equals("-create"))) {

			if (args[0].equals("-check")) {

				if (args.length != 2) {
					System.out.println("Incorrect command line args.");
					System.out.println("Correct format: -check fileName");
					System.exit(0);
				}

				choice = true;
				filename = args[1];

			}else{

				if (args.length != 3) {
					System.out.println("Incorrect command line args.");
					System.out.println("Correct format: -create fileName size");
					System.exit(0);
				}
				
				filename = args[1];

				try {
					size = Integer.parseInt(args[2]);
				}  
				catch (NumberFormatException nfe){
					System.out.println("Incorrect type");
					System.out.println("Size should be of type int");
					System.exit(0);
				}

				if (size%2 != 1 || size < 3) {
					System.out.println("Incorrect odd number size."
							+ " Must input size >1. ");
					System.exit(0);
				}
			}

		}else {
			System.out.println("Incorrect command line args.");
			System.out.println("Correct format: (-check / -create) fileName size)");
			System.exit(0);
		}
		
		if (choice == true) {
			
			MagicSquare.checkMatrix(filename);
			
		}else {
			
			MagicSquare.createMagicSquare(filename,size);
			
			
		}



	}

}