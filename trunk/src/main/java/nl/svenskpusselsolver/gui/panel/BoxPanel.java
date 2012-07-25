package nl.svenskpusselsolver.gui.panel;

import java.awt.Dimension;

import javax.swing.JPanel;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.gui.BoxPanelMouseListener;

import org.apache.log4j.Logger;

/**
 * Represents a GUI box within the puzzle.
 */
public abstract class BoxPanel extends JPanel {
	private static final long serialVersionUID = 3851925912509349366L;
	private final static Logger logger = Logger.getLogger(BoxPanel.class);
	
	protected BoxPanelMouseListener bpml = new BoxPanelMouseListener(this);
	
	private Box box;
	
	/**
	 * Create a new Box panel.
	 * @param xCoordinate X coordinate of this panel on the grid.
	 * @param yCoordinate Y coordinate of this panel on the grid.
	 */
	public BoxPanel(Box box) {
		super();
		
		logger.trace("Building BoxPanel (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		this.box = box;
		this.addMouseListener(bpml);
		this.setPreferredSize(new Dimension(75, 75)); 	// Makes the layout manager happy
		
		logger.info("BoxPanel built (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
	}

	/**
	 * Cycle the type of the box
	 */

	/**
	 * Get the box object associated with this panel.
	 * @return Box object.
	 */
	public Box getBox() {
		return box;
	}
	
	public void handleLeftClick() { }
	public void handleMiddleClick() { }
	public void handleRightClick() { }	
}
