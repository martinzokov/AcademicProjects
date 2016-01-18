import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * KeptFrame class creates an Array List with arrays to keep the frames from the
 * animation
 * 
 * @author Mariya Blagoeva
 */
public class KeptFrame {

	private ArrayList<char[][]> keptFrames;

	/**
	 * Constructor for objects of class KeptFrame
	 */
	public KeptFrame() {
		keptFrames = new ArrayList<char[][]>();
	}

	/**
	 * Adds a new array to the ArrayList
	 * 
	 * @param frame
	 *            requires char[][]
	 */
	public void addFrame(char[][] frame) {
		this.keptFrames.add(frame);
	}

	/**
	 * Returns the length of the first element of the ArrayList
	 * 
	 * @return int value
	 */
	public int getLength() {
			return keptFrames.get(0).length;
		
	}

	/**
	 * Saves the animation
	 * 
	 * @param fileName
	 *            requires a String
	 */
	public void save(String fileName) {
		try {
			PrintWriter outfile = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(fileName)));
			outfile.println(keptFrames.size());
			outfile.println(getLength());
			for (char[][] f : keptFrames) {
				for (int i = 0; i < f.length; i++) {
					for (int j = 0; j < f.length; j++) {
						outfile.print(f[j][i]);
					}
					outfile.println();
				}
			}
			outfile.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(), "No File Name.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Loads the animation
	 * 
	 * @param fileName
	 *            requires a String
	 */
	public void load(String fileName) {
		try {
			Scanner infile = new Scanner(new InputStreamReader(
					new FileInputStream(fileName)));
			keptFrames = new ArrayList<char[][]>();
			int numOfFrames = infile.nextInt();
			infile.nextLine();
			int size = infile.nextInt();
			infile.nextLine();
			for (int i = 0; i < numOfFrames; i++) {
				char[][] loadArray = new char[size][size];
				for (int r = 0; r < size; r++) {
					String read = infile.nextLine();
					char[] tempArray = read.toCharArray();
					for (int c = 0; c < tempArray.length; c++) {
						loadArray[c][r] = tempArray[c];
					}
				}
				keptFrames.add(loadArray);
			}
			infile.close();
		} catch (FileNotFoundException i) {
			JOptionPane.showMessageDialog(new JFrame(), "No such file found.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Returns the size of the ArrayList
	 * 
	 * @return an int value
	 */
	public int getNumberOfFrames() {
		return keptFrames.size();
	}

	/**
	 * @param position
	 *            requires an int
	 * @return the position of the array in the ArrayList
	 */
	public char[][] getGrid(int position) {
		return keptFrames.get(position);
	}
}
