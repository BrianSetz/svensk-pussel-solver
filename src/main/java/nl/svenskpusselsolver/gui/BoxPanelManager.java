package nl.svenskpusselsolver.gui;

import java.awt.Container;

import org.apache.log4j.Logger;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.dataobjects.WordBox.Direction;
import nl.svenskpusselsolver.gui.panel.BoxPanel;
import nl.svenskpusselsolver.gui.panel.LetterBoxPanel;
import nl.svenskpusselsolver.gui.panel.StaticBoxPanel;
import nl.svenskpusselsolver.gui.panel.WordBoxPanel;
import nl.svenskpusselsolver.gui.panel.exception.UnknownBoxException;

public class BoxPanelManager {
	private final static Logger logger = Logger.getLogger(BoxPanelManager.class);
	
	private Container contentPane;
	private int xLength;
	private int yLength;
	
	private BoxPanel[][] boxPanelGrid;

	public BoxPanelManager(Container contentPane, int xLength, int yLength) {
		this.contentPane = contentPane;
		this.xLength = xLength;
		this.yLength = yLength;
		
		this.boxPanelGrid = new BoxPanel[xLength][yLength];
	}

	public void cycle(BoxPanel boxPanel) {
		logger.trace("Cycling box (" + boxPanel.getBox().getXCoordinate() + "," + boxPanel.getBox().getYCoordinate() + ").");
		
		int x = boxPanel.getBox().getXCoordinate();
		int y = boxPanel.getBox().getYCoordinate();		
		int index = y * xLength + x;
		
		// Cycle to next panel
		BoxPanel newBoxPanel = null; 
		if(boxPanel instanceof LetterBoxPanel) {
			newBoxPanel = new WordBoxPanel(new WordBox(x, y, "", Direction.UP));
		} else if(boxPanel instanceof WordBoxPanel) {
			WordBox wordBox = (WordBox) boxPanel.getBox();
			
			switch(wordBox.getDirection()) {
			case UP:
				newBoxPanel = new WordBoxPanel(new WordBox(x, y, "", Direction.RIGHT));
				break;
			case RIGHT:
				newBoxPanel = new WordBoxPanel(new WordBox(x, y, "", Direction.DOWN));
				break;
			case DOWN:
				newBoxPanel = new WordBoxPanel(new WordBox(x, y, "", Direction.LEFT));
				break;
			case LEFT:
				newBoxPanel = new StaticBoxPanel(new StaticBox(x, y));
				break;
			}						
		} else {
			newBoxPanel = new LetterBoxPanel(new LetterBox(x, y, ' '));
		}
		
		// Remove current panel
		contentPane.remove(index);
		
		// Add new panel
		contentPane.add(newBoxPanel, index);
		boxPanelGrid[x][y] = newBoxPanel;
		
		// Redraw
		contentPane.validate();
	}
	
	/**
	 * @return the boxPanelGrid
	 */
	public BoxPanel[][] getBoxPanelGrid() {
		return boxPanelGrid;
	}
	
	public void setBoxPanelGrid(Box[][] grid) {
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid.length; x++) {
				try {
					boxPanelGrid[x][y] = BoxPanelFactory.createBoxPanel(grid[x][y]);
					contentPane.add(boxPanelGrid[x][y], y * grid.length + x);
				} catch (UnknownBoxException e) {
					e.printStackTrace();
				}
			}
		}	
		
		contentPane.validate();
	}
}
