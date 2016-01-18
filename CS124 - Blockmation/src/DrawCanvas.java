import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * DrawCanvas class builds draw panel for the director frame
 * @author Mariya Blagoeva
 */
public class DrawCanvas extends JPanel implements MouseListener {

	private Board board;
	private int size;
	private KeptFrame keptFrames;
	private String info;

	/**
	 * Constructor for objects of class DrawCanvas
	 * @param squareSize requires an int
	 */
	public DrawCanvas(int squareSize) {

		keptFrames = new KeptFrame();
		size = squareSize;
		board = new Board(size);
		this.addMouseListener(this);
		setBorder(BorderFactory.createLineBorder(Color.black, 6));
		setVisible(true);
		info = "left button - light / right button - dark";
	}

	/**
	 * This method paints the canvas 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		g.drawString(info, 70, 20);
		repaint();
		
	}
	
	/**
	 * This method draws the grid
	 * @param g requires a graphics object
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
	 * Saves a frame from the animation
	 */
	public void keepFrame() {

		char[][] temp = new char[board.getSize()][board.getSize()];
		for (int x = 0; x < board.getSize(); x++) {
			for (int y = 0; y < board.getSize(); y++) {
				temp[x][y] = board.getBlock(x, y);
			}
		}

		keptFrames.addFrame(temp); 
	}

	/**
	 * Tells the program what to do when the mouse is clicked
	 */
	public void mouseClicked(MouseEvent e) {
		int x, y, startx = 40, starty = 40;
		int p = e.getX();
		int o = e.getY();
		for (x = 0; x < size; x++)
			for (y = 0; y < size; y++)
				if (p > startx + x * 15 && p < startx + x * 15 + 13
						&& o > starty + y * 15 && o < starty + y * 15 + 13) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						board.setChar(x, y, 'l');
						this.repaint();
					}

					else if (SwingUtilities.isRightMouseButton(e)) {
						board.setChar(x, y, 'd');
						this.repaint();
					}

					else if (SwingUtilities.isMiddleMouseButton(e)) {
						board.setChar(x, y, 'b');
						this.repaint();
					}
				}
	}

	/**
	 * Saves the canvas
	 * @param fileName requires a String
	 */
	public void saveCanvas(String fileName) {
		keptFrames.save(fileName);
	}
	
	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
