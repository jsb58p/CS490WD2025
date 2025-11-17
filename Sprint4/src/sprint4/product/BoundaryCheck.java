package sprint4.product;

import sprint4.product.SOSGame.Bound;

/**
*Represents the SOS boundary checks.
*Ensures that array out of bounds errors do not occur when placing a letter ('S' or 'O').
*/
public class BoundaryCheck {
	
	int boardSize;
	
	/**
	 * Creates a new boundary checker with the specified settings.
	 * 
	 * @param size the size of the board (e.g. 3 for 3x3)
	 */
	public BoundaryCheck(int size) {
		this.boardSize = size;
	}
	
	/**
	 * Performs horizontal and diagonal boundary checks for detecting SOS patterns.
	 * 
	 * @param boundary the side and letter to check
	 * @param row the row the letter is placed
	 * @param col the column the letter is placed
	 * @param i the horizontal/diagonal offset
	 * @return the boundary conditions to be checked
	 */
	public boolean boundsCheck(Bound boundary, int row, int col, int i) {
		switch (boundary) {
		case OHORIZONTALBOUND:
			return (col + 1 < boardSize && col - 1 >= 0 && row - i >= 0 && row - i < boardSize && row + i >= 0 && row + i < boardSize);
		case SLEFTBOUND:
			return (row + (2*i) >= 0 && row + (2*i) < boardSize && col - 2 >= 0);
		case SRIGHTBOUND:
			return (row - (2*i) >= 0 && row - (2*i) < boardSize && col + 2 < boardSize);
		default:
			return false;
		}
	}
	
	/**
	 * Performs vertical boundary checks for detecting SOS patterns.
	 * 
	 * @param boundary the side and letter to check
	 * @param row the row the letter is placed
	 * @param col the column the letter is placed
	 * @return the boundary conditions to be checked
	 */
	public boolean boundsCheck(Bound boundary, int row, int col) {
		switch(boundary) {
		case OVERTICALBOUND:
			return (row - 1 >= 0 && row + 1 < boardSize);
		case SUPBOUND:
			return (row + 2 < boardSize);
		case SDOWNBOUND:
			return (row - 2 >= 0);
		default:
			return false;
		}
	}
}