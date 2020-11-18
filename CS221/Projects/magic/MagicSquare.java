import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * @Author Irene Galca
 */

public class MagicSquare {
	/*
	 * readMatrix, reads the values of the the matrix created,
	 * both the indexes and the values created. It reads the contents
	 * of the file.
	 * @param filename : name of the file created.
	 */
	public static int [][]matrix;

	public static void readMatrix(String filename)
	{
		File file = new File (filename);
		try {
			Scanner input= new Scanner(file);
			int size = input.nextInt(); //size of the matrix
			matrix = new int[size][size];
			while (input.hasNextInt())
			{
				for(int i=0; i<=(size-1); i++) {
					for(int j=0; j<=(size-1); j++) {
						matrix[i][j]= input.nextInt();
					}
				}
			}
			input.close();
		}catch(FileNotFoundException e) {
			System.out.println("File not found/is invalid. Please eneter a valid file.");
		}

	}


	/*
	 * checkMatrix checks the contents of the file created and determines
	 * whether or not they are valid, based on the given formula ((n^2)+1)/2).
	 * @param name : Takes the name of the file as a String.
	 * 
	 */
	public static boolean checkMatrix(String name){
		readMatrix(name);
		System.out.println(matrix.toString());
		int n = matrix.length;
		int row;
		int col;
		int total;
		int i;
		int [][]square= matrix;
		//equation (n (n^2)+1))/2
		int formula = (n*(n*n+1))/2;
		System.out.println("\n sizeber: " + n);

		for(row = 0; row<n; row++) //determines the sum of all values of a row.
		{
			int rowtotal = 0;
			for(col = 0; col<n; col++)
			{
				rowtotal= rowtotal + square [row][col];
			}
			if (rowtotal != formula)
				System.out.println("Input value is invalid for row: " + row + "\nThe sum for this row is: " + rowtotal);
			return false;
		}

		row=0;
		col=0;
		total=0;
		for(col =0; col<n; col++) //determines the sum of all values in a column.
		{
			int coltotal = 0;
			for(row = 0; row<0; row++)
			{
				coltotal= coltotal + square [row][col];
			}
			if(coltotal != formula)
				System.out.println("Input value is invalid for column: " + col + "\nThe sum of this column is: " + coltotal);
			return false;
		}
		row = 0;
		col = 0-1;
		total = 0;
		for(i = 0; i<n; i++) //determines sum of all values in a diagonal.
		{
			total = total + square[row][col];
			row++;
			col--;

		}
		if(total != formula)
		{
			System.out.println("\n The input value is invalid at the second diagonal. The sum at this diagonal is: " + total);
			return false;
		}
		System.out.println("\n The value is valid: " + formula);
		return true;
	}
	/*
	 * createMagicSquare will create the square in the file based on 
	 * the size parameters the user inpputs
	 * @param int size : the size input of the user.
	 * @param String filename : the file that is created by the user.
	 */
	public static void createMagicSquare(String filename, int size)
	{
		int n= size;
		int [][]square= new int [n][n];
		int row= n-1;
		int col= n/2;

		int oldRow= row;
		int oldCol= col;
		for(int i = 1; i<=n*n; i++)
		{
			square[row][col]= i;
			oldRow = row;
			oldCol = col;
			row++;
			col++;

			if(row==n)
			{
				row= 0; 
			}
			if(col==n)
			{
				col=0;
			}
			if(square[row][col] !=0)
			{
				row= oldRow;
				col= oldCol;
				row= row-1;	
			}
		}
		for(int r = 0; r< size; r++)
		{
			System.out.println();
			for(int c = 0; c< size; c++)
			{
				System.out.println(" " + square[r][c]);
				if(square[r][c]<1)
				{
					System.out.println();
				}
			}
		}
		writeMatrix(new File (filename), size, square);

	}
	/*
	 * Writes the 2D array matrix to the file
	 * @param 
	 */
	private static void writeMatrix(File file, int size, int[][]square) {
		PrintWriter outFile= null;
		try {

			outFile= new PrintWriter(new FileWriter(file));
			outFile.println(size);
			for(int row = 0; row<size; row++)
			{
				for(int col = 0; col<size; col++)
				{
					outFile.print(square[row][col]);
					outFile.print(" ");
				}
				outFile.print("\n");
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		outFile.close();
	}
	public void printMatrix(int size, int[][]square)
	{
		System.out.println(size + "\n");
		for (int row = 0; row<size; row++) {

			for (int col = 0; col<size; col++) {

				System.out.print(square[row][col]);
				System.out.print(" ");

				// write files
				// console into file
			}
			System.out.println("\n");
		}
	}
}