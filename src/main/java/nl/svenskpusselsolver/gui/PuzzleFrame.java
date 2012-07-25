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
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.gui.panel.BoxPanel;
import nl.svenskpusselsolver.gui.panel.LetterBoxPanel;
import nl.svenskpusselsolver.solver.BruteForceSolver;

import org.apache.log4j.Logger;

/**
 * The main frame of the puzzle GUI.
 */
public class PuzzleFrame extends JFrame {
	private static final long serialVersionUID = 3103179150718872483L;
	private final static Logger logger = Logger.getLogger(PuzzleFrame.class);
	
	private static BoxPanelManager boxPanelManager;
	
	private Container contentPane;	
	
	private PuzzleFrame() {
		logger.trace("Building PuzzleFrame.");
		
		contentPane = this.getContentPane();		
				
		logger.trace("Building menu.");
		
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
				BoxPanel[][] currentBoxPanelGrid = boxPanelManager.getBoxPanelGrid();
				Box[][] currentBoxGrid = new Box[currentBoxPanelGrid.length][currentBoxPanelGrid[0].length];
				
				for (int x = 0; x < currentBoxPanelGrid.length; x++) {
					for (int y = 0; y < currentBoxPanelGrid[0].length; y++) {			
						currentBoxGrid[x][y] = currentBoxPanelGrid[x][y].getBox();
					}
				}
				
				// Get solved grid
				Box[][] newGrid = new BruteForceSolver().solvePuzzle(currentBoxGrid);
				
				for (int y = 0; y < newGrid[0].length; y++) {
					for (int x = 0; x < newGrid.length; x++) {
						BoxPanel panel = currentBoxPanelGrid[x][y];
						if(! (panel instanceof LetterBoxPanel))
							continue;
						
						LetterBoxPanel letterBoxPanel = (LetterBoxPanel) panel;
						LetterBox letterBox = (LetterBox) newGrid[x][y];
						letterBoxPanel.updateLetter(letterBox.getLetter());
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
		this.setVisible(true);
		
		logger.info("PuzzleFrame built.");
	}

	public PuzzleFrame(int xLength, int yLength) {
		this();
		this.initializePuzzle(xLength, yLength);
		
		// Create grid with static boxes only
		Box[][] grid = new Box[xLength][yLength];
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {			
				grid[x][y] = new StaticBox(x, y);
			}
		}
		
		boxPanelManager.setBoxPanelGrid(grid);
		
		this.pack();
	}
	
	public PuzzleFrame(Box[][] grid) {	
		this();		
		this.initializePuzzle(grid.length, grid[0].length);
		
		logger.trace("Initializing grid with content.");
		
		boxPanelManager.setBoxPanelGrid(grid);
		
		this.pack();
	}
	
	public static BoxPanelManager getBoxPanelManager() {
		return boxPanelManager;
	}
	
	/**
	 * Initialize puzzle grid
	 * 
	 * @param xLength Boxes on x-axis
	 * @param yLength Boxes on y-axis
	 */
	private void initializePuzzle(int xLength, int yLength) {
		boxPanelManager = new BoxPanelManager(contentPane, xLength, yLength);
		contentPane.setBackground(Color.black);
		contentPane.setLayout(new GridLayout(xLength, yLength, 3, 3));
	}
}
