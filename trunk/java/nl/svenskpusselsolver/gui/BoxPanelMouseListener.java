package nl.svenskpusselsolver.gui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
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
		
		// If other button than right button is pressed or if we're clicking
		// on a non-editable field, do nothing.
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != InputEvent.BUTTON3_MASK
				|| box instanceof StaticBox)
			return;

		logger.trace("Right button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");			
		logger.trace("Showing dialog (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
					
		// Determine message
		String message = "";
		String currentValue = "";
		if (box instanceof LetterBox) {
			currentValue = String.valueOf(((LetterBox)box).getLetter());
			message = "Enter a letter ";
		} else {
			currentValue = (((WordBox)box).getWord());
			message = "Enter a word ";
		}

		// Get word or letter
		String result = (String) JOptionPane.showInputDialog(boxPanel,
				message, message, JOptionPane.PLAIN_MESSAGE, null, null,
				currentValue);

		logger.debug("Result from dialog: " + result + " (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");

		// Format letter
		if(result == "" || result == null) {
			return;
		} else
		if (box instanceof LetterBox && result.length() > 0) {
			result = String.valueOf(result.charAt(0)).toUpperCase();
			boxPanel.setBox(new LetterBox(box.getXCoordinate(), box.getYCoordinate(), result.charAt(0)));
		} else {
			WordBox wordBox = (WordBox) box;
			boxPanel.setBox(new WordBox(box.getXCoordinate(), box.getYCoordinate(), result, wordBox.getDirection()));
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
		Box box = boxPanel.getBox();
		
		logger.trace("Button pressed (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		// If other button than left button is pressed, do nothing.
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK)
			return;
		
		logger.trace("Left button pressed (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");			
		logger.trace("Cycling box type (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		// Cycle box type and update box to new type
		if(box instanceof StaticBox) {
			boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.Direction.UP));
		} else if(box instanceof WordBox) {
			WordBox wordBox = (WordBox) box;
			WordBox.Direction direction = wordBox.getDirection();
			
			switch (direction) {
				case UP:
					boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.Direction.RIGHT));
					break;
				case RIGHT:
					boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.Direction.DOWN));
					break;
				case DOWN:
					boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.Direction.LEFT));
					break;
				case LEFT:
					boxPanel.updateType(new LetterBox(box.getXCoordinate(), box.getYCoordinate()));
					break;
			}
		} else if(box instanceof LetterBox) {
			boxPanel.updateType(new StaticBox(box.getXCoordinate(), box.getYCoordinate()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}