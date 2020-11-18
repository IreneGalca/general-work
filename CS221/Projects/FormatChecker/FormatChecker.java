/*
 * @author Irene Galca
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FormatChecker {

	//Check if the foratting of the file is correct.
	//should return true if valid. False otherwise.
	//Parameter s is the filename.
	public static boolean checkFormat(String s) {
		System.out.println(s); 
		Scanner filescan=null;
		int row=0;
		int col=0;
		//if it contains file
		try {
			filescan= new Scanner(new File(s));
		} catch (FileNotFoundException e) {
			System.out.println( e.toString());
			return false;
		}
		//read first line and next line
		String line1= filescan.nextLine();

		String[] line1Split= line1.split("\\s+");
		if(line1Split.length != 2)
		{
			System.out.println("first line: Number of arguments does not match.");
			return false;
		}
		//checks if arguments are 0 or 1
		try {
			row= Integer.parseInt(line1Split[0]);
			col= Integer.parseInt(line1Split[1]);
		}
		catch(Exception e) {
			System.out.println(e.toString());
			return false;
		}
		//reads  number of rows
		int numRows = 0;
		while (filescan.hasNext())
		{
			if(numRows == row && filescan.nextLine().length()==0)
			{

			}
			else 
			{
				if(numRows == row)
				{
					System.out.println("Wrong number of rows.");
					return false;
				}
				String rowline = filescan.nextLine();

				String[] rowSplit = rowline.split("\\s+");
				//checks if given numCols is equal to col.
				if(rowSplit.length != col)
				{
					System.out.println("Wrong number of columns in row: " + numRows);
					return false;
				}
				try 
				{
					for(String item : rowSplit)
						Float.parseFloat(item);
				}catch(Exception e) {
					System.out.println(e.toString());
					return false;
				}
			}
			numRows++;
		}
		return true;
	}
	//main method: makes sure there is an argument when it reads the file.
	//0 means it doesn't hold an argument, therefore the file doesn't exist.
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("File not Found! (Specified file could not be found in the system.) ");
			System.exit(1);
		}
		//states if file is valid or invalid.
		for(String s : args) 
		{
			System.out.println( (checkFormat(s) ? "VALID" : "INVALID"));
		}
	}
}
