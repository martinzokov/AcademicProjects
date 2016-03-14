
package sudoku.app.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Martin Zokov
 * The Class Grid. Represents the sudoku 9 x 9 grid with a 2D array of Cell objects.
 */
public class Grid {

	/** The grid. A 2D array of cell objects which hold the data for one cell */
	private Cell[][] grid;
	
	/** The cell. A single cell object. */
	private Cell cell;
	
	/** A boolean variable to show if the puzzle is solved or not. Also used for pausing the solver thread. */
	public boolean solved = false;
	
	/** Boolean to check if the file is loaded correctly. */
	private boolean fileLoaded;

	/**
	 * Instantiates a new grid. Creates the grid array and fills it with Cell objects.
	 */
	public Grid() {
		grid = new Cell[9][9];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				cell = new Cell();
				grid[i][j] = cell;
			}
		}
		fileLoaded = false;
	}

	/**
	 * Gets the grid. Used to pass it on to the GUI.
	 *
	 * @return Returns the whole grid.
	 */
	public Cell[][] getGrid() {
		return grid;
	}

	/**
	 * The solve method uses 3 algorithms to solve the grid. Goes through all 3 of them and refreshes the candidates list for every cell. 
	 * Pauses the solver thread while running.
	 */
	public void solve() {
		nakedSingles();
		findCandidates();
		hiddenSingles();
		findCandidates();
		nakedPairs();
		findCandidates();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the puzzle is solved. Goes through the grid and counts the solved cells.
	 *
	 * @return  integer with the total number of solved cells.
	 */
	public int isSolved() {
		int solvedCells = 0;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				if (grid[x][y].getValue() != ' ') {
					solvedCells++;
				}
			}
		}
		return solvedCells;
	}

	/**
	 * Prints the grid to the console. Used for debugging while implementing algorithms.
	 */
	public void printGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j].getValue());
				System.out.print("|");
			}
			System.out.println("");
		}
	}

	/**
	 * Prints the candidates grid to the console. Used for debugging while implementing algorithms.
	 */
	public void printCandidatesGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j].getCandidates());
				System.out.print("|");
			}
			System.out.println("");
		}
	}

	/**
	 * Naked pairs algorithm. Goes through the cells on the board, looks for two cells that have the same pair of candidates.
	 * If it finds such cells, it then eliminates those two candidates from all cells in the same row/column/sub-grid, 
	 * depending on where the cells are
	 */
	public void nakedPairs() {
		char[] pairChars;
		int pairOccur = 0;
																												//loop through all cells on the grid
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {

																												// loop through the same column of the grid as the current cell
				for (int i = 0; i < grid.length; i++) {
					pairOccur = 0;																				//reset counter for number of pairs
																												
																												
					if (grid[row][col].getNumOfCandidates() == 2 && grid[i][col].getNumOfCandidates() == 2) {	//check if the number of candidates of two cells on 
																												//the same column have only two candidates
						if (row != i && col != i) {																//check if it is not the same cell being looked at twice
							pairOccur++;  																		 //increment pair counter
						}											
						if (grid[row][col].getCandidates().equals(grid[i][col].getCandidates())) {				//checks if the two cells have the same candidates
							if (pairOccur == 1) {
								
								pairChars = grid[row][col].getCandidatesArray();								//if a pair is found get the 2 candidates in an array
								for (int yRemove = 0; yRemove < grid.length; yRemove++) {									//loop through the same column again to remove the pair from
									if (grid[row][col] != grid[yRemove][col] && grid[i][col] != grid[yRemove][col]) {		//all other cells except the 2 in which the pair was found and removes the numbers
										for (int remChar = 0; remChar < pairChars.length; remChar++) {
											grid[yRemove][col].removeCandidate(pairChars[remChar]);
											findCandidates();
										}
									}
								}
							}
						}
					}
				}

				
				for (int i = 0; i < grid.length; i++) {																			// loop through the same row of the grid as the current cell
					pairOccur = 0;																								//reset counter for number of pairs
					if (grid[row][col].getNumOfCandidates() == 2 && grid[row][i].getNumOfCandidates() == 2) {					//check if the number of candidates of two cells
																																//on the same column have only two candidates
						
						if (row != i && col != i) {																				//check if it is not the same cell being looked at twice
							pairOccur++;																						//increment pair counter
						}
						
						if (grid[row][col].getCandidates().equals(grid[row][i].getCandidates())) {								//checks if the two cells have the same candidates
							if (pairOccur == 1) {
								pairChars = grid[row][col].getCandidatesArray();												//if a pair is found get the 2 candidates in an array.
								for (int xRemove = 0; xRemove < grid.length; xRemove++) {										//loop through the same row again to remove the pair from
									if (grid[row][col] != grid[row][xRemove] && grid[row][i] != grid[row][xRemove]) {			//all other cells except the 2 in which 
										for (int remChar = 0; remChar < pairChars.length; remChar++) {							//the pair was found and removes the numbers
											grid[row][xRemove].removeCandidate(pairChars[remChar]);
											findCandidates();
										}
									}
								}

							}
						}
					}
				}

				if (grid[row][col].getNumOfCandidates() == 2) {																			//check if the current cell has only two candidates

					for (int x = 0; x < grid.length; x++) {																				//start another loop through the whole grid
						for (int y = 0; y < grid.length; y++) {
							pairOccur = 0;																								//reset pair counter
							
							if (grid[x][y].getUnit(x, y) == grid[row][col].getUnit(row, col)) {											//check if the two cells that are being compared are in the same box(sub-grid)
								if (grid[x][y].getNumOfCandidates() == 2) {																//check if the second cell has only 2 candidates and the candidate lists are equal
									if (grid[row][col].getCandidates().equals(grid[x][y].getCandidates())) {
										if (row != x || col != y) {
											pairOccur++;																				//increment pair counter
										}
										if (pairOccur == 1) {
											pairChars = grid[x][y].getCandidatesArray();												//if a pair was found get the 2 candidates in an array
											
											for (int boxX = 0; boxX < grid.length; boxX++) {											//go through the whole grid again, check for cells in the same
												for (int boxY = 0; boxY < grid.length; boxY++) {										//sub-grid and check if they are different then the 2 with a pair
													if (grid[boxX][boxY].getUnit(boxX, boxY) == grid[row][col].getUnit(row, col)) {
														if (grid[boxX][boxY] != grid[row][col] && grid[boxX][boxY] != grid[x][y]) {
															for (int remChar = 0; remChar < pairChars.length; remChar++) {				//remove the pair from all other cells in the sub-grid
																grid[boxX][boxY].removeCandidate(pairChars[remChar]);
																findCandidates();
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * Hidden singles algorithm which checks a cell and it's candidates. If one of the candidates occurs only once on a row/column/sub-grid,
	 * then this candidate must be placed there.
	 */
	public void hiddenSingles() {
		char currentCandidate, compareCandidate;										//local char variables for comparing cell values
		int charOccur = 0;																//counter for occurance of a candidate
												
		for (int row = 0; row < grid.length; row++) {									//loop through the whole grid
			for (int col = 0; col < grid.length; col++) {
				
				if (!grid[row][col].isSet()) {											//if a cell is already set, skip it
					for (int cand = 0; cand < 9; cand++) {								//loop through all candidates 
						
						charOccur = 0;													//reset char counter
						currentCandidate = grid[row][col].getOneCandidate(cand);		//gets a candidate from the current cell
																																				
						if (currentCandidate != ' ') {									// checks the whole row if the candidate of the 
							for (int i = 0; i < grid.length; i++) {						//current cell is different than a space character
								compareCandidate = grid[row][i].getOneCandidate(cand);  //check if any of the cells has the same candidate
								if (compareCandidate == currentCandidate && currentCandidate != ' ') {
									charOccur++;										//if both candidates are equal and different than a space increment the counter
								}
							}
							if (charOccur == 1) {										//check if a char was found only once on the row
								grid[row][col].setValue(currentCandidate);				//remove it from the column as well
								grid[row][col].removeAllCandidates();
								for (int i = 0; i < grid.length; i++) {
									grid[i][col].removeCandidate(currentCandidate);
								}
								findCandidates();										//update the candidates list for the whole grid
							}
						}
					}

					for (int cand = 0; cand < 9; cand++) {													//loop through all candidates 

						charOccur = 0;																		//reset char counter
						currentCandidate = grid[row][col].getOneCandidate(cand);							//gets a candidate from the current cell
						
						if (currentCandidate != ' ') {														// checks the whole column if the candidate of the 
							for (int i = 0; i < grid.length; i++) {											//current cell is different than a space character
								compareCandidate = grid[i][col].getOneCandidate(cand);
								if (compareCandidate == currentCandidate && currentCandidate != ' ') {		//check if any of the cells has the same candidate
									charOccur++;															//if both candidates are equal and different than a space increment the counter
								}
							}
							if (charOccur == 1) {															
								grid[row][col].setValue(currentCandidate);									
								grid[row][col].removeAllCandidates();										//check if a char was found only once on the row
								for (int i = 0; i < grid.length; i++) {										//remove it from the row as well
									grid[row][i].removeCandidate(currentCandidate);
								}	
								findCandidates();															//update the candidates list for the whole grid
							}
						}

					}

					for (int cand = 0; cand < 8; cand++) {															//loop through all candidates 

						charOccur = 0;																				//reset char counter
						currentCandidate = grid[row][col].getOneCandidate(cand);									//gets a candidate from the current cell
						if (currentCandidate != ' ') {																// checks if the candidate is a space
							for (int x = 0; x < grid.length; x++) {													//loops again through the grid
								for (int y = 0; y < grid.length; y++) {												//searches for cells in the same sub-grid
									if (grid[x][y].getUnit(x, y) == grid[row][col].getUnit(row, col)) {
										compareCandidate = grid[x][y].getOneCandidate(cand);						
										if (compareCandidate == currentCandidate && currentCandidate != ' ') {		//if a cell with the same candidate is found
											charOccur++;															// in the sub-grid increment counter
										}
									}
								}
							}

							if (charOccur == 1) {																	// if a character was found only once
								grid[row][col].setValue(currentCandidate);											//set the cell to that candidate
								grid[row][col].removeAllCandidates();												//remove all other candidates
								for (int x = 0; x < grid.length; x++) {												
									for (int y = 0; y < grid.length; y++) {
										if (grid[x][y].getUnit(x, y) == grid[row][col].getUnit(row, col)) {			//and remove it from all other cells 
											grid[x][y].removeCandidate(currentCandidate);							//in the same sub-grid as a candidate
										}
									}
								}
								findCandidates();
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * Naked singles algorithm simply looks for cells which have only one possible candidate and sets it as the value.
	 */
	public void nakedSingles() {
		for (int row = 0; row < grid.length; row++) {						//loop through the whole grid
			for (int column = 0; column < grid.length; column++) {
				grid[row][column].setOnlyCandidate();						//if a cell has only one candidate, set it as the value
			}
		}
	}

	/**
	 * Method which goes through the whole grid and eliminates candidates which can't be the value of the cell
	 */
	public void findCandidates() {
		char  compareCell;	
		for (int row = 0; row < grid.length; row++) {							//loop through the grid
			for (int column = 0; column < grid.length; column++) {

																				// scan column for same number
				for (int i = 0; i < grid.length; i++) {
					compareCell = grid[i][column].getValue();					//takes a candidate from the column
					if (grid[row][column].findCandidate(compareCell)) {			//checks if the current cell has the same candidate as the second one
						if (grid[row][column].isSet()) {						//check if the cell already has a value
							grid[row][column].removeAllCandidates();			//and removes all it's candidates
						} else {
							grid[row][column].removeCandidate(compareCell);		//if the current cell doesn't have a value, remove the second cells value
						}														//from the candidates list
					}
				}

																				// scan row for same number
				for (int j = 0; j < grid.length; j++) {							
					compareCell = grid[row][j].getValue();						//takes a candidate from the row
					if (grid[row][column].findCandidate(compareCell)) {			//checks if the current cell has the same candidate as the second one
						if (grid[row][column].isSet()) {						//check if the cell already has a value
							grid[row][column].removeAllCandidates();			//and removes all it's candidates
						} else {
							grid[row][column].removeCandidate(compareCell);		//if the current cell doesn't have a value, remove the second cells value
						}														//from the candidates list
					}
				}

																										// scan box(sub-grid) for same number
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid.length; j++) {
						compareCell = grid[i][j].getValue();											//takes a candidate from the box
						if (grid[i][j].getUnit(i, j) == grid[row][column].getUnit(row, column)) {		//checks if both cells are in the same sub-grid
							if (grid[row][column].isSet()) {											//check if the cell already has a value
								grid[row][column].removeAllCandidates();								//and removes all it's candidates
							} else {
								grid[row][column].removeCandidate(compareCell);							//if the current cell doesn't have a value, remove the second cells value
							}																			//from the candidates list
						}
					}
				}
			}
		}
	}

	/**
	 * Loads a grid file. Reads the lines as strings, makes them a char array and puts them into the grid cells.
	 *
	 * @param file A string with the file path for the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadGrid(String file) throws IOException {

		BufferedReader br = null;
		char[] charBuffer;
		String read;
		try {
			br = new BufferedReader(new FileReader(file));

			for (int i = 0; i < grid.length; i++) {
				read = br.readLine();
				charBuffer = read.toCharArray();
				for (int j = 0; j < grid.length; j++) {
					grid[i][j].setValue(charBuffer[j]);
				}
			}
			br.close();
			fileLoaded = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if is loaded.
	 *
	 * @return true, if a file is loaded
	 */
	public boolean isLoaded() {
		return fileLoaded;
	}


	/**
	 * Stop method to change the boolean solved so that it can be used as a pause button for the solver thread.
	 */
	public void stop() {
		solved = true;
	}

}
