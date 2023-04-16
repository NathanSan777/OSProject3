import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * This is the Main class of the Resource Calculator.
 * Using the Matrix class I wrote, it is able to read in files containing
 * information about matrices, and is capable of multiplying them and printing the 
 * result to the user.
 * @author Nathan Sanchez
 * For: CSC428 Operating Systems Project #3
 * Instructor: Dr. Thede
 *
 */
public class Main {
	
	/**
	 * A method that takes in an ArrayList as input,
	 * and prints out the contents of it.
	 * @param input: ArrayList that the user would like to print.
	 */
	public static void printArrayList(ArrayList<Integer> input) {
		for (Integer i : input) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	/**
	 * Method for multiplying two matrices concurrently. 
	 * @param base: First matrix that we will multiply.
	 * @param other: Second matrix that we will multiply the base with.
	 * @return: a newly formed matrix containing the sum of products of the two matrices.
	 */
	public static Matrix multiplyMatrices(Matrix base, Matrix other){
		/*In the event  that the first matrix's columns do not
		 * have the same length as the second matrix's rows,
		 * we cannot multiply them. Prints an error message and returns null.
		 */
		if (base.getColumns() != other.getRows()) {
			System.out.println("We can't multiply these two matrices!");
			System.out.println("The number of columns in this matrix doesn't match the number of rows in the other matrix!");
			return null;
		}
		/*
		 * Create an array of RowCalculators, which we will use threads to
		 * concurrently multiply each row of the matrices.
		 */
		RowCalculator[] multiplyThreads = new RowCalculator[base.getRows()];
		//Creates a new matrix with matrix A's rows and matrix B's columns
		Matrix c = new Matrix(base.getRows(), other.getColumns());
		for (int i = 0; i < base.getRows(); i++) {
			/*
			 * For every row in the base matrix, we create a new RowCalculator object
			 * and put it into our array of RowCalculators. We then start the thread.
			 */
			RowCalculator r = new RowCalculator(base, other, c, i);
			multiplyThreads[i] = r;
			r.start();
		}
		/*
		 * For every thread we created, we call .join() on them to get the 
		 * result of the thread. We also check for an interruption.
		 */
		for (int i = 0; i < multiplyThreads.length; i++) {
			try {
			multiplyThreads[i].join();
			}
			catch (InterruptedException e){
				System.out.println("Thread was interrupted!");
			}
		}
		//Returns a newly-formed matrix 
		return c;
	}
	
	public static void main(String[] args) {
		//Create a scanner for user input
		Scanner toScan = new Scanner(System.in); 
		//Create an ArrayList of Integers to hold the values read from input.
		ArrayList<Integer> matrixValues = new ArrayList<Integer>();
		//Initialize an index so we can keep track of how many numbers we have used
		//in matrixValues
		int index = 0;
		System.out.println("Hello! Welcome to the Resource Calculator!");
		System.out.println("Please type in the name of the file you'd like to load in: ");
		String fileName = toScan.nextLine();
		/*
		 * Loads in a file from the user's input. Concatenate "src/" so that 
		 * the file can be accessed from src. Then, read in all the values from the 
		 * file.
		 */
		try {
			File information = new File("src/" + fileName);
			Scanner reader = new Scanner(information);
			while (reader.hasNextInt()) {
				int temp = reader.nextInt();
				matrixValues.add(temp);
			}
			System.out.println("File loaded!");
			System.out.println();
			reader.close();
		} 
		//Otherwise, a file was not found. Catch the exception and print an error message.
		catch (FileNotFoundException e) {
			System.out.println("An error occurred. Did you type in the file correctly?");
			toScan.close();
			return;
		}
		/*
		 * The first two values of the file determine the m and n values of 
		 * the Matrix A, where m is the number of rows and n is the number of
		 * columns.
		 */
		Matrix a = new Matrix(matrixValues.get(0), matrixValues.get(1));
		/*
		 * The second and third values of the file determine the n and p values
		 * of Matrix B, where n is the number of rows and p is the number of
		 * columns. 
		 */
		Matrix b = new Matrix(matrixValues.get(1), matrixValues.get(2));
		/*Increment our index by 3 so we know that we have used the first 
		 * 3 values of our matrixValues ArrayList.
		 */
	    index += 3;
	    /*
	     * Fill Matrix A entirely, with regards to the index.
	     * The index is used as the offset for the fillMatrix method.
	     */
	    a.fillMatrix(matrixValues, index);
	    System.out.println("Matrix A:");
	    a.printMatrix();
	    System.out.println();
	    /*
	     * To ensure that we don't use previous values from 
	     * matrixValues, we add the total number of cells in 
	     * Matrix A to update the index we should be using.
	     */
	    index += a.getTotalCells();
	    System.out.println("Matrix B:");
	    /*
	     * Fill Matrix B entirely, with regards to the index.
	     * The index is used as the offset for the fillMatrix method.
	     */
	    b.fillMatrix(matrixValues, index);
	    b.printMatrix();
	    System.out.println();
	    /*
	     * To ensure that we don't use previous values from 
	     * matrixValues, we add the total number of cells in 
	     * Matrix B to update the index we should be using.
	     */
	    index += b.getTotalCells();
	    /*
	     * Create Matrix C, which is the resultant matrix
	     * of multiplying A and B.
	     */
		Matrix c = multiplyMatrices(a, b);
		System.out.println("Matrix C:");
		System.out.println();
		//If we couldn't make Matrix C, return null and inform the user.
		if (c.equals(null)) {
			System.out.println("Matrix c couldn't be produced!");
			toScan.close();
			return;
		}
		c.printMatrix();
		System.out.println();
		System.out.println("Vector to perform matrix multiplication on Matrix C:");
		/*
		 * Create Matrix v, which is the final matrix from matrixValues.
		 * Matrix v is m rows by 1 column, where m is how many unused values
		 * remain in matrixValues. 
		 */
		Matrix v = new Matrix(matrixValues.size() - index, 1);
		/*
		 * Fill Matrix v with the last unused values of matrixValues, 
		 * with regards to our index.
		 */
		v.fillMatrix(matrixValues, matrixValues.size() - v.getRows());
		System.out.println();
		v.printMatrix();
		System.out.println();
		System.out.println("Final Matrix:");
		/*
		 * To find how many basic resources we need, 
		 * we multiply Matrix c and Matrix v together.
		 */
		Matrix basicResources = multiplyMatrices(c, v);
		//Print the final result to the user.
		basicResources.printMatrix();
		System.out.println("Thank you for using the Resource Calculator! Have a great day!");
		//Close scanner to avoid memory leaks.
		toScan.close();

	}

}
