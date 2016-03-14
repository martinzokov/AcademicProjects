
package sudoku.app.model;

/**
 * @author Martin Zokov
 * The Cell class represents a single cell in the grid of the sudoku.
 */
public class Cell {

	/** The value of the cell. It is a space character ' ' if there is no value set. */
	private char value;
	
	/** The candidates represented by a string. */
	private String candidates = "123456789";

	/**
	 * Instantiates a new cell.
	 */
	public Cell() {
		value = ' ';
	}

	/**
	 * Sets the value of a cell.
	 *
	 * @param val the new value
	 */
	public void setValue(char val) {
		value = val;
	}

	/**
	 * Gets the value of a cell.
	 *
	 * @return the value
	 */
	public char getValue() {
		return value;
	}

	/**
	 * Checks if is sets the.
	 *
	 * @return true, if is sets the
	 */
	public boolean isSet() {
		if (value != ' ') {
			return true;
		}
		return false;
	}

	/**
	 * Gets the candidates.
	 *
	 * @return the candidates
	 */
	public String getCandidates() {
		return candidates;
	}

	/**
	 * Gets the candidates array.
	 *
	 * @return the candidates array
	 */
	public char[] getCandidatesArray() {
		char[] chars = new char[2], string;
		int nextFree = 0;
		string = candidates.toCharArray();
		for (int i = 0; i < string.length; i++) {
			if (string[i] != ' ') {
				chars[nextFree] = string[i];
				nextFree++;
			}
		}
		
		return chars;
	}

	/**
	 * Gets one candidate from a specific position.
	 *
	 * @param position in the string (char array)
	 * @return the candidate in the position
	 */
	public char getOneCandidate(int pos) {
		char[] candidatesArr = candidates.toCharArray();
		return candidatesArr[pos];
	}

	/**
	 * Sets the only candidate.
	 */
	public void setOnlyCandidate() {
		if (getNumOfCandidates() == 1) {
			char[] candidatesArr = candidates.toCharArray();
			for (int i = 0; i < candidatesArr.length; i++) {
				if (candidatesArr[i] != ' ') {
					this.setValue(candidatesArr[i]);
					candidates = "         ";
				}
			}
		}
	}

	/**
	 * Gets the num of candidates.
	 *
	 * @return the num of candidates
	 */
	public int getNumOfCandidates() {
		char[] candidatesArr = candidates.toCharArray();
		int num = 0;
		for (int i = 0; i < candidatesArr.length; i++) {
			if (candidatesArr[i] != ' ') {
				num++;
			}
		}
		return num;
	}

	/**
	 * Removes the candidate.
	 *
	 * @param candidate the candidate
	 */
	public void removeCandidate(char candidate) {
		char[] chars = candidates.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == candidate) {
				chars[i] = ' ';
			}
		}
		candidates = String.valueOf(chars);
	}

	/**
	 * Removes the all candidates.
	 */
	public void removeAllCandidates() {
		candidates = "         ";
	}

	/**
	 * Prints the candidates.
	 */
	public void printCandidates() {
		System.out.print(candidates);
	}

	/**
	 * Find candidate.
	 *
	 * @param candidate the candidate
	 * @return true, if successful
	 */
	public boolean findCandidate(char candidate) {
		char found = ' ';
		for (int i = 0; i < candidates.length(); i++) {
			if (candidates.charAt(i) == candidate) {
				found = candidates.charAt(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the unit. Method is taken from a topic on stack overflow from user sth - 
	 * http://stackoverflow.com/questions/4718213/dividing-a-9x9-2d-array-into-9-sub-grids-like-in-sudoku-c
	 *
	 * @param row the row
	 * @param col the col
	 * @return the sub-grid
	 */
	public int getUnit(int row, int col) {
		return (row / 3) * 3 + (col / 3);
	}
}
