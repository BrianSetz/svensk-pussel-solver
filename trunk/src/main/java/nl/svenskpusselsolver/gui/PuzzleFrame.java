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
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.solver.BruteForceSolver;

import org.apache.log4j.Logger;

/**
 * This is the main frame of the puzzle GUI.
 */
public class PuzzleFrame extends JFrame {
	private final static Logger logger = Logger.getLogger(PuzzleFrame.class);
	
	private Container contentPane;
	private BoxPanel[][] boxPanelGrid;
	//private Box[][] grid;
	
	/**
	 * Puzzle frame contains all the boxes.
	 */
	public PuzzleFrame() {
		logger.trace("Building PuzzleFrame.");
		
		contentPane = this.getContentPane();
		
		logger.trace("Building menu.");
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
				Box[][] currentGrid = new Box[boxPanelGrid.length][boxPanelGrid[0].length];
				
				for (int x = 0; x < boxPanelGrid.length; x++) {
					for (int y = 0; y < boxPanelGrid[0].length; y++) {			
						currentGrid[x][y] = boxPanelGrid[x][y].getBox();
					}
				}
				
				Box[][] newGrid = new BruteForceSolver().solvePuzzle(currentGrid);
				for (int y = 0; y < newGrid[0].length; y++) {
					for (int x = 0; x < newGrid.length; x++) {
						boxPanelGrid[x][y].setBox(newGrid[x][y]);
					}
				}	
			}	
		});
		file.add(solveItem);

		// Add separator
		file.addSeparator();
		
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
		
		logger.trace("Initializing frame.");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Svensk Pussel Solver");
		this.pack();
		this.setVisible(true);
		
		logger.info("PuzzleFrame built.");
	}

	public PuzzleFrame(Box[][] grid) {	
		this();
		
		//this.grid = grid;
		
		logger.trace("Initializing grid with content.");
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
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
		contentPane.setLayout(new GridLayout(x, y, 1, 1));

		logger.trace("Creating boxes for grid.");
		// Create all boxes
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				BoxPanel bp = new BoxPanel(new LetterBox(i, j));
				contentPane.add(bp); // New box
				boxPanelGrid[j][i] = bp; // Store reference in grid, flip x and y for Gridbag layout 
			}
		}
	}
}
