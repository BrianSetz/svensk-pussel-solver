package nl.svenskpusselsolver.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This is the main frame of the puzzle GUI.
 */
public class PuzzleFrame extends JFrame {
	private Container contentPane;

	/**
	 * Puzzle frame contains all the boxes.
	 */
	public PuzzleFrame() {
		contentPane = this.getContentPane();

		this.initializePuzzle(10, 10);

		// Build MenuBar
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		// Add File menu
		JMenu file = new JMenu("File");
		file.setMnemonic('F');		
		bar.add(file);
		
		// Add Exit item
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		exitItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		file.add(exitItem);				

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Svensk Pussel Solver");
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Initialize puzzle grid
	 * 
	 * @param x
	 *            Boxes on x-axis
	 * @param y
	 *            Boxes on y-axis
	 */
	private void initializePuzzle(int x, int y) {
		contentPane.setBackground(Color.black);
		contentPane.setLayout(new GridLayout(y, x, 1, 1));

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				contentPane.add(new BoxPanel()); // New box
			}
		}
	}
}
