import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * TheDisplay builds a frame for display
 * @author Mariya Blagoeva
 *
 */
public class TheDisplay extends JFrame implements ActionListener{

	private JPanel buttonPanel;
	private JButton load, run, stop;
	private JTextField inputField;
	private DisplayCanvas canvas;
	
	/**
	 * Constructor for objects of class TheDisplay
	 */
	public TheDisplay() {
		
		setTitle("Blockmation - Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(625,200);
		setSize(450, 450);
		
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		add(buttonPanel, BorderLayout.NORTH);
		
		inputField=new JTextField(10);
		buttonPanel.add(inputField);
		
		load=new JButton("Load");
		buttonPanel.add(load);
		load.addActionListener(this);
		
		run=new JButton("Run");
		buttonPanel.add(run);
		run.addActionListener(this);
		
		stop=new JButton("Stop");
		buttonPanel.add(stop);
		stop.addActionListener(this);
		
		
		setVisible(true);
	}
	
	/**
	 * Performs operations when the buttons are clicked
	 */
	public void actionPerformed(ActionEvent i) {
		if (i.getSource() == load) {
				this.load();
		}
		
		else if (i.getSource() == run) {
			this.runFrames();
		}
		
		else if (i.getSource() == stop) {
			this.stopFrames();
		}
	}
	
	/**
	 * Runs the animation
	 */
	public void runFrames() {
		Thread frames = new Thread(canvas);
		frames.start();
	}
	
	/**
	 * Stops the animation from running
	 */
	public void stopFrames() {
		try {
			canvas.stopRun();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Load a file first.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Loads the animation
	 */
	public void load() {
		if(canvas !=null){
			this.remove(canvas);
		}
		canvas = new DisplayCanvas();
		canvas.loadFile(inputField.getText());
		add(canvas, BorderLayout.CENTER);
		setVisible(true);
	}
	
}
