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

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.logging.Logger;
import nl.svenskpusselsolver.solver.BruteForceSolver;

/**
 * This is the main frame of the puzzle GUI.
 */
public class PuzzleFrame extends JFrame {
	private Container contentPane;
	private BoxPanel[][] boxPanelGrid;
	private Box[][] grid;
	
	/**
	 * Puzzle frame contains all the boxes.
	 */
	public PuzzleFrame() {
		Logger.log(Logger.TRACE, "Building PuzzleFrame.");
		
		contentPane = this.getContentPane();

		
		Logger.log(Logger.TRACE, "Building menu.");
		this.initializePuzzle(10, 10);

		// Build MenuBar
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		// Add File menu
		JMenu file = new JMenu("File");
		file.setMnemonic('F');		
		bar.add(file);			

		// Add Solve item
		JMenuItem solveItem = new JMenuItem("Solve");
		solveItem.setMnemonic('s');
		solveItem.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
			Box[][] newGrid = new BruteForceSolver().solvePuzzle(grid);
			for (int y = 0; y < newGrid[0].length; y++) {
				for (int x = 0; x < newGrid.length; x++) {
					boxPanelGrid[x][y].setBox(newGrid[x][y]);
				}
			}	
		}	
		});
		file.add(solveItem);

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
		
		Logger.log(Logger.TRACE, "Initializing frame.");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Svensk Pussel Solver");
		this.pack();
		this.setVisible(true);
		
		Logger.log(Logger.INFO, "PuzzleFrame built.");
	}

	public PuzzleFrame(Box[][] grid) {	
		this();
		
		this.grid = grid;
		
		Logger.log(Logger.TRACE, "Initializing grid with content.");
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid.length; x++) {
				boxPanelGrid[x][y].setBox(grid[x][y]);
			}
		}		
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
		boxPanelGrid = new BoxPanel[x][y];		
		contentPane.setBackground(Color.black);
		contentPane.setLayout(new GridLayout(y, x, 1, 1));

		Logger.log(Logger.TRACE, "Creating boxes for grid.");
		// Create all boxes
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				BoxPanel bp = new BoxPanel(j, i);
				contentPane.add(bp); // New box
				boxPanelGrid[j][i] = bp; // Store reference in grid
			}
		}
	}
}
