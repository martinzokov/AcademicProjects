/**
 * 
 * The Board class holds the array in which is drawn onto the grid
 * 
 * @author Mariya Blagoeva
 * 
 */
public class Board {
	private char grid[][];
	private int size;

	/**
	 * Constructor for objects of class Board
	 * 
	 * @param gridSize
	 *            is the size of the grid
	 */
	public Board(int gridSize) {
		size = gridSize;
		if (size < 1 || size > 100) {
			throw new IllegalArgumentException(
					"Incorrect Value! Must be a positive number between 0 and 100.");
		} else {
			grid = new char[size][size];

			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					grid[x][y] = 'b';
				}
			}
		}
	}
	

	/**
	 * Returns the position of a single block
	 * 
	 * @param row
	 *            is the row position of the block in the array
	 * @param column
	 *            is the column position of the block in the array
	 * @return char[][]
	 */
	public char getBlock(int row, int column) {
		return grid[row][column];
	}

	/**
	 * Method that returns the value of the size
	 * 
	 * @return int size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Method that sets the value of size
	 * 
	 * @param size
	 *            requires an int
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Method that gives the values of newBoard to the grid
	 * 
	 * @param newBoard
	 *            requires a char[][]
	 */
	public void setBoard(char[][] newBoard) {
		grid = newBoard;
	}

	/**
	 * Method that gives a char to a specific block position in the grid
	 * 
	 * @param row
	 *            requires an int
	 * @param column
	 *            requires an int
	 * @param c
	 *            requires a char
	 */
	public void setChar(int row, int column, char c) {
		grid[row][column] = c;
	}

	/**
	 * Method that returns the position of a block
	 * 
	 * @param x
	 *            requires int
	 * @param y
	 *            requires int
	 * @return char[][] from a specific position in the 2D array
	 */
	public char getChar(int x, int y) {
		return grid[x][y];
	}
}