import java.util.ArrayList;

/**
 * This is the Matrix class that I wrote for the Resource Calculator.
 * It is a class that represents a Matrix, which can hold a 2D Array of integers.
 * It keeps track of its data, such as rows, columns, and total cells, and 
 * can fill itself with values.
 * This class has no relation to the Java Matrix package.
 * @author Nathan Sanchez
 * For: CSC428 Operating Systems Project #3
 * Instructor: Dr. Thede
 */
public class Matrix {
	private int[][] materials;
	private int rows;
	private int columns;
	private int totalCells;
	
	/**
	 * Constructor for the Matrix class. 
	 * It takes in two integer inputs: r and c.
	 * It also tracks how many cells it has.
	 * @param r: Number of rows that the Matrix has.
	 * @param c: Number of columns that the Matrix has.
	 */
	public Matrix(int r, int c) {
		this.materials = new int[r][c];
		this.rows = this.materials.length;
		this.columns = this.materials[0].length;
		this.totalCells = c * r;
	}
	/**
	 * Method to return how many rows a Matrix has.
	 * @return: number of rows.  
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Method to return how many columns a Matrix has.
	 * @return: number of columns.
	 */
	public int getColumns() {
		return this.columns;
	}
	/**
	 * A method to print out the contents of a Matrix.
	 */
	public void printMatrix() {
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.columns; c++) {
				System.out.print(this.materials[r][c] + " ");
			}
			System.out.println();
		}
	}
	/**
	 * A method to return a value from a Matrix at a certain index.
	 * @param r: The row of the value.
	 * @param c: The column of the value.
	 * @return: An integer from materials[r][c].
	 */
	public int getValue(int r, int c) {
		return this.materials[r][c];
	}
	/**
	 * A method to set a value at a certain index to another value.
	 * @param r: Row of the integer value to be changed.
	 * @param c: Column of the integer value to be changed.
	 * @param value: an integer.
	 */
	public void setValue(int r, int c, int value) {
		this.materials[r][c] = value;
	}
	
	/**
	 * A method that returns how many cells a Matrix has.
	 * @return: an integer representing the total amount of cells.
	 */
	public int getTotalCells() {
		return this.totalCells;
	}
	/**
	 * A method to fill a Matrix with values from an ArrayList. 
	 * It takes an offset, which determines the index of the value
	 * that is retrieved from the ArrayList.
	 * @param numbers: An ArrayList<Integer> containing the values of the Matrix.
	 * @param offset: An integer that determines the index of where we are retrieving 
	 * the value in the ArrayList.
	 */
	public void fillMatrix(ArrayList<Integer> numbers, int offset) {
		int count = offset;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.materials[i][j] = numbers.get(count);
				count++;
			}
		}
	}
}