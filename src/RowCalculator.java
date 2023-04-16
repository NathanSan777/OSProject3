/**
 * This is the RowCalculator class of the Resource Calculator. 
 * It multiplies a row of one Matrix with the column of the other.
 * It is capable of being run with concurrency.
 * @author Nathan Sanchez
 * For: CSC428 Operating Systems Project #3
 * Instructor: Dr. Thede
 */
public class RowCalculator extends Thread{
	
	private Matrix ownerMatrix;
	private Matrix paramMatrix;
	private Matrix result;
	private int rowIndex;
	private int total;
	
	@Override
	public void run() {
		multiplyRow();
		returnResult();
	}
	/**
	 * Constructor for the RowCalculator class. It takes in 3 Matrices as parameters, 
	 * as well as the current index of the row.
	 * @param first: Matrix A.
	 * @param second: Matrix B.
	 * @param third: Matrix C, the resultant Matrix of A and B.
	 * @param currIndex: Current index of Matrix A's row.
	 */
	public RowCalculator(Matrix first, Matrix second, Matrix third, int currIndex) {
		this.ownerMatrix = first;
		this.paramMatrix = second;
		this.result = third;
		this.rowIndex = currIndex;
		this.total = 0;
	}
	/**
	 * A method to return the newly filled row of the Matrix.
	 * @return: A matrix.
	 */
	public Matrix returnResult() {
		return this.result;
	}
	/**
	 * A method for multiplying the row of a Matrix. 
	 * This method goes through every value in Matrix A's row and multiplies it
	 * with the column of Matrix B. It adds the product to the total, and sets it 
	 * into Matrix C. The total is then reset when it is done.
	 */
	public void multiplyRow() {
		for (int j = 0; j < this.paramMatrix.getColumns(); j++) {
			for (int k = 0; k < this.paramMatrix.getRows(); k++) {
				total += ownerMatrix.getValue(this.rowIndex, k) * paramMatrix.getValue(k, j);
			}
			result.setValue(rowIndex, j, total);
			this.total = 0;
		}
		
	}
	
	
	
	
	
	

}
