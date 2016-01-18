import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * TheDirector builds a frame for editing
 * 
 * @author Mariya Blagoeva
 * 
 */
public class TheDirector extends JFrame implements ActionListener {

	private JPanel buttonPanel;
	private JButton size, keep, save;
	private JTextField inputField, fileName;
	private DrawCanvas canvas;

	/**
	 * Constructor for objects of class TheDirector
	 */
	public TheDirector() {

		setTitle("Blockmation - Director");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(170, 200);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		add(buttonPanel, BorderLayout.NORTH);

		inputField = new JTextField(6);
		buttonPanel.add(inputField);

		size = new JButton("Size");
		buttonPanel.add(size);
		size.addActionListener(this);

		keep = new JButton("Keep");
		buttonPanel.add(keep);
		keep.addActionListener(this);

		fileName = new JTextField(10);
		buttonPanel.add(fileName);

		save = new JButton("Save");
		buttonPanel.add(save);
		save.addActionListener(this);

		setSize(450, 450);
		setVisible(true);
	}

	/**
	 * Takes information from the inputField and builds a canvas
	 */
	public void setNumber() {
		try {
			int squareNumber = Integer.parseInt(inputField.getText());
			if ( canvas != null ) {
				this.remove(canvas);
			}
			canvas = new DrawCanvas(squareNumber);
			add(canvas, BorderLayout.CENTER);
			setVisible(true);
			this.repaint();
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Enter a number.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Performs operations when the buttons are clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == size) {
			  
			this.setNumber();
		} else if (e.getSource() == keep) {
			try {
				canvas.keepFrame();
			} catch (NullPointerException n) {
				JOptionPane.showMessageDialog(new JFrame(),
						"There is nothing drawn yet.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == save) {
			try {
			canvas.keepFrame();
			String name = fileName.getText();
			canvas.saveCanvas(name);
			}
			catch (NullPointerException n) {
				JOptionPane.showMessageDialog(new JFrame(),
						"No File To Be Saved.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}

	}
}
