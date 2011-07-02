package nl.svenskpusselsolver.gui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.WordBox;

import org.apache.log4j.Logger;

/**
 * Mouse listener for the box panel.
 */
public class BoxPanelMouseListener implements MouseListener {
	private final static Logger logger = Logger.getLogger(BoxPanelMouseListener.class);
	
	private BoxPanel boxPanel;
	
	public BoxPanelMouseListener(BoxPanel boxPanel) {
		this.boxPanel = boxPanel;		
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		Box box = boxPanel.getBox();
		
		logger.trace("Button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");

		// Determine which mouse button is pressed
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) { // Left click
			logger.trace("Left button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
			
			// Cycle box type
			boxPanel.cycleType();
		} else if ((e.getModifiers() & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) { // Middle click
			logger.trace("Middle button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		} else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) { // Right click
			logger.trace("Right button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ")."); 
						
			// Determine message
			String message = "";
			String currentValue = boxPanel.getBoxValue();
			if (box instanceof LetterBox) {
				message = "Enter a letter ";
			} else if(box instanceof WordBox) {
				message = "Enter a word ";
			} else {
				return;
			}
			
			logger.trace("Showing dialog (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
			
			// Get word or letter
			String result = (String) JOptionPane.showInputDialog(boxPanel,
					message, message, JOptionPane.PLAIN_MESSAGE, null, null,
					currentValue);
			
			logger.debug("Result from dialog: " + result + " (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
			
			// Cancel button
			if(result == null)
				return;
			
			// Set result
			boxPanel.setBoxValue(result);
		} 
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}