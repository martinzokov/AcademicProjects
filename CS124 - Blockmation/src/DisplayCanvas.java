import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * DisplayCanvas class builds display panel for the display frame
 * 
 * @author Mariya Blagoeva
 */
public class DisplayCanvas extends JPanel implements Runnable {

	private Board board;
	private int size, frames;
	private KeptFrame keptFrames;
	private String file;
	private boolean run;

	/**
	 * Constructor for objects of class DisplayCanvas
	 */
	public DisplayCanvas() {
		setBorder(BorderFactory.createLineBorder(Color.black, 6));
		setVisible(true);
	}

	/**
	 * This method paints the canvas
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		repaint();
	}

	/**
	 * This method draws the grid
	 * 
	 * @param g
	 *            requires a graphics object
	 */
	public void draw(Graphics g) {
		int x, y, startx = 40, starty = 40;
		for (x = 0; x < size; x++) {
			for (y = 0; y < size; y++) {
				if (board.getChar(x, y) == ('b')) {
					g.setColor(Color.blue);
					g.fillRect(startx + x * 15, starty + y * 15, 13, 13);
				} else if (board.getChar(x, y) == ('l')) {
					g.setColor(Color.gray);
					g.fillRect(startx + x * 15, starty + y * 15, 13, 13);
				} else if (board.getChar(x, y) == ('d')) {
					g.setColor(Color.black);
					g.fillRect(startx + x * 15, starty + y * 15, 13, 13);
				}
			}
		}
	}

	/**
	 * This method stops the animation
	 */
	public void stopRun() {
		run = false;
	}

	/**
	 * This method runs the animation
	 */
	public void run() {
		run = true;
		while (run == true) {
			for (int i = 0; i < frames; i++) {
				board.setBoard(keptFrames.getGrid(i));
				try {
					Thread.sleep(175);
				} catch (InterruptedException ie) {

				}
				this.repaint();
			}
		}
	}

	/**
	 * This method loads the animation
	 * 
	 * @param fileName
	 *            requires a String
	 */
	public void loadFile(String fileName) {
		try {
			keptFrames = new KeptFrame();
			keptFrames.load(fileName);
			frames = keptFrames.getNumberOfFrames();
			size = keptFrames.getLength();
			board = new Board(keptFrames.getLength());
			board.setBoard(keptFrames.getGrid(0));
			this.repaint();
		} catch (IndexOutOfBoundsException o) {

		}
	}
}
