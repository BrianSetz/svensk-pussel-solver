package nl.svenskpusselsolver.gui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.logging.Logger;

/**
 * Mouse listener for the box panel.
 */
public class BoxPanelMouseListener implements MouseListener {
	private BoxPanel boxPanel;
	
	public BoxPanelMouseListener(BoxPanel boxPanel) {
		this.boxPanel = boxPanel;		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Box box = boxPanel.getBox();
		
		Logger.log(Logger.LogLevel.TRACE, "Button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		// If other button than right button is pressed or if we're clicking
		// on a non-editable field, do nothing.
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != InputEvent.BUTTON3_MASK
				|| box instanceof StaticBox)
			return;

		Logger.log(Logger.LogLevel.TRACE, "Right button clicked (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");			
		Logger.log(Logger.LogLevel.TRACE, "Showing dialog (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
					
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

		Logger.log(Logger.LogLevel.DEBUG, "Result from dialog: " + result + " (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");

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
		
		Logger.log(Logger.LogLevel.TRACE, "Button pressed (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		// If other button than left button is pressed, do nothing.
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK)
			return;
		
		Logger.log(Logger.LogLevel.TRACE, "Left button pressed (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");			
		Logger.log(Logger.LogLevel.TRACE, "Cycling box type (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		// Cycle box type and update box to new type
		if(box instanceof StaticBox) {
			boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.DIRECTION_UP));
		} else if(box instanceof WordBox) {
			WordBox wordBox = (WordBox) box;
			
			// Determine direction
			if(wordBox.getDirection() == WordBox.DIRECTION_UP) {
				boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.DIRECTION_RIGHT));
			} else if(wordBox.getDirection() == WordBox.DIRECTION_RIGHT) {
				boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.DIRECTION_DOWN));
			} else if(wordBox.getDirection() == WordBox.DIRECTION_DOWN) {
				boxPanel.updateType(new WordBox(box.getXCoordinate(), box.getYCoordinate(), "", WordBox.DIRECTION_LEFT));
			} else if(wordBox.getDirection() == WordBox.DIRECTION_LEFT) {
				boxPanel.updateType(new LetterBox(box.getXCoordinate(), box.getYCoordinate()));
			}
		} else if(box instanceof LetterBox) {
			boxPanel.updateType(new StaticBox(box.getXCoordinate(), box.getYCoordinate()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}