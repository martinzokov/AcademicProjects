package sudoku.app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import sudoku.app.model.Cell;
import sudoku.app.model.Grid;

/**
 * @author Martin Zokov
 * The Class SudokuFrame is the actual GUI for the program. It creates a grid and manages it.
 */
public class SudokuFrame extends JFrame implements ActionListener, Runnable {

	/** The sudoku grid is instantiated here. */
	private Grid sudoku = new Grid();
	
	/** A 2D cell grid which is taken from the actual grid to set JLabels in the GUI. */
	private Cell[][] grid;

	/** The buttons on the GUI. */
	public JButton solve, load, stop;
	
	/** The sudoku panel is the place where the grid is drawn. */
	private JPanel optionsPanel, sudokuPanel;
	
	/** A 2D array of JLabels to represent the cells. */
	private JLabel[][] cells;

	/**
	 * Instantiates a new sudoku frame.
	 */
	public SudokuFrame() {
		setTitle("Sudoku Solver 9001");	//It's over 9000
		
		//creates the buttons
		solve = new JButton("Start Solving");
		load = new JButton("Load Sudoku");
		stop = new JButton("Stop solving");
		
		//create a panel for the buttons and add action listener for them
		//also add the options panel to the frame
		optionsPanel = new JPanel();
		optionsPanel.add(load);
		optionsPanel.add(solve);
		optionsPanel.add(stop);
		load.addActionListener(this);
		solve.addActionListener(this);
		stop.addActionListener(this);
		optionsPanel.setLayout(new GridLayout(1, 5));
		setLayout(new BorderLayout());
		add(optionsPanel, BorderLayout.NORTH);
		
		//creates the panel for the sudoku grid, gives it a 9x9 layout and centers it in the frame
		sudokuPanel = new JPanel();
		sudokuPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		sudokuPanel.setLayout(new GridLayout(9, 9));
		add(sudokuPanel, BorderLayout.CENTER);
		
		//set sizes for the program's window
		setLocation(400, 150);
		setSize(400, 500);
		setResizable(false);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Sets the labels when the grid is loaded and gives thicker borders for some of the labels to represent sub-grids.
	 */
	public void setLabels() {
		cells = new JLabel[9][9];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j] = new JLabel();
				cells[i][j].setText(String.valueOf(grid[i][j].getValue()));
				sudokuPanel.add(cells[i][j]);
				cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				if (j == 3 || j == 6) {
					cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.black));
					if (i == 2 || i == 5) {
						cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 5, 5, 1, Color.black));
					}
				} else {
					cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
					if (i == 2 || i == 5) {
						cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black));
					}
				}
			}
		}
	}

	/**
	 * Gets the grid from the actual sudoku class and assigns it to the grid for the GUI class.
	 */
	public void setGrid() {
		grid = sudoku.getGrid();
	}

	/**
	 * Updates labels while solving the puzzle after each algorithm tick.
	 */
	public void updateLabels() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].setText(String.valueOf(grid[i][j].getValue()));
			}
		}
	}

	/**
	 * Calls the load method from the sudoku class and gives it the file path to load.
	 * Also sets the labels for in the GUI.
	 *
	 * @param file A string with the file's path taken from the file chooser.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadFile(String file) throws IOException {
		sudoku.loadGrid(file);
		grid = sudoku.getGrid();
		setLabels();
	}

	/**
	 * Solve method called when the solve button on the GUI is pressed.
	 * Starts the solver in a new thread.
	 */
	public void solve() {
		new Thread(this).start();
	}

	/**
	 * Calls the stop method in the sudoku class to change the boolean variable in order to pause.
	 */
	public void stop() {
		sudoku.stop();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("Load Sudoku")) {
			JFileChooser c = new JFileChooser();
			FileNameExtensionFilter filter =new FileNameExtensionFilter(".sud Files","sud");
			c.setFileFilter(filter);
			int rVal = c.showOpenDialog(SudokuFrame.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					String file = c.getSelectedFile().toString();
					loadFile(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			if (rVal == JFileChooser.CANCEL_OPTION) {

			}

		} else if (actionCommand.equals("Start Solving")) {
			this.solve();

		} else if (actionCommand.equals("Stop solving")) {

			this.stop();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (sudoku.isLoaded()) {
			while (!sudoku.solved) {
				sudoku.solve();
				updateLabels();
				if (sudoku.isSolved() == 81) {
					sudoku.solved = true;
				}
			}
		}
	}
}
