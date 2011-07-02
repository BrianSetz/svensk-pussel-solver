package nl.svenskpusselsolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;

import org.apache.log4j.Logger;

/**
 * Represents a GUI box within the puzzle.
 */
public class BoxPanel extends JPanel {	
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(BoxPanel.class);
	
	private JTextPane textPane; // Text container
	private JLabel directionLabel; // Direction of wordbox
	private String boxValue = " ";
	private Box box;
	
	/**
	 * Create a new Box panel.
	 * @param xCoordinate X coordinate of this panel on the grid.
	 * @param yCoordinate Y coordinate of this panel on the grid.
	 */
	public BoxPanel(Box box) {
		super();
		
		logger.trace("Building BoxPanel (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		
		// Initialize box
		BoxPanelMouseListener bpml = new BoxPanelMouseListener(this);

		// Create textpane
		this.textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(70, 50));
		textPane.addMouseListener(bpml);
		
		// Create directionlabel
		this.directionLabel = new JLabel("");
		directionLabel.addMouseListener(bpml);		
		
		// Center text in textpane
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		// Set box
		this.box = box;
		
		// Init panel
		this.add(textPane);
		this.add(directionLabel);		
		this.addMouseListener(bpml);
		this.setPreferredSize(new Dimension(75, 75)); 	// Makes the layout manager happy
		this.refreshUI(); // Update
		
		logger.info("BoxPanel built (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
	}
	
	/**
	 * Update the type of the box.
	 * @param box New box.
	 */
	public void refreshUI() {
		logger.trace("Refreshing box at (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
		
		// Refresh box
		if(this.box instanceof StaticBox) {
			logger.debug("Refreshing box of type STATICBOX (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
			
			this.setBackground(Color.gray);
			this.textPane.setVisible(false);
			this.textPane.setText("");
			this.directionLabel.setVisible(false);
		} else if(this.box instanceof WordBox) {
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));			
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(true);
			
			WordBox wordBox = (WordBox) this.box;
			WordBox.Direction direction = wordBox.getDirection();
			this.textPane.setText(wordBox.getWord());
			
			switch (direction) {
				case UP:
					logger.debug("Refreshing box of type WORDBOX, UP (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
					this.directionLabel.setText("^");
					break;
				case RIGHT:
					logger.debug("Refreshing box of type WORDBOX, RIGHT (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
					this.directionLabel.setText(">");
					break;
				case DOWN:
					logger.debug("Refreshing box of type WORDBOX, DOWN (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
					this.directionLabel.setText("v");
					break;
				case LEFT:
					logger.debug("Refreshing box of type to WORDBOX, LEFT (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
					this.directionLabel.setText("<");
					break;
			}
			
		} else if(this.box instanceof LetterBox) {
			logger.debug("Refreshing box of type LETTERBOX (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
			
			this.setBackground(Color.white);
			this.textPane.setBackground(Color.white);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(false);

			LetterBox letterBox = (LetterBox) this.box;
			this.textPane.setText(Character.toString(letterBox.getLetter()));
		}
		
		this.updateUI();
	}

	/**
	 * Cycle the type of the box
	 */
	public void cycleType() {
		// Cycle box type and update box to new type
		if(box instanceof StaticBox) {
			this.box = new WordBox(box.getXCoordinate(), box.getYCoordinate(), boxValue, WordBox.Direction.UP);
		} else if(box instanceof WordBox) {
			WordBox wordBox = (WordBox) box;
			WordBox.Direction direction = wordBox.getDirection();
			
			switch (direction) {
				case UP:
					this.box = new WordBox(box.getXCoordinate(), box.getYCoordinate(), boxValue, WordBox.Direction.RIGHT);
					break;
				case RIGHT:
					this.box = new WordBox(box.getXCoordinate(), box.getYCoordinate(), boxValue, WordBox.Direction.DOWN);
					break;
				case DOWN:
					this.box = new WordBox(box.getXCoordinate(), box.getYCoordinate(), boxValue, WordBox.Direction.LEFT);
					break;
				case LEFT:
					this.box = new LetterBox(box.getXCoordinate(), box.getYCoordinate(), boxValue.toUpperCase().charAt(0));
					break;
			}
		} else if(box instanceof LetterBox) {
			this.box = new StaticBox(box.getXCoordinate(), box.getYCoordinate());
		}
		
		refreshUI();
	}
	
	/**
	 * Set the new box value of the boxpanel
	 * @param boxValue New box value
	 */
	public void setBoxValue(String boxValue) {
		logger.trace("Setting box value of box at (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");		
		
		// Check for empty string
		if(boxValue.length() <= 0)
			boxValue = " ";
		
		this.boxValue = boxValue;
		
		// Set value in box
		if(this.box instanceof WordBox) {			
			logger.debug("Setting box value of type WORDBOX (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");
			
			WordBox wordBox = (WordBox) this.box;
			wordBox.setWord(boxValue);
		} else if(this.box instanceof LetterBox) {
			logger.debug("Setting box value of type LETTERBOX (" + this.box.getXCoordinate() + "," + this.box.getYCoordinate() + ").");

			LetterBox letterBox = (LetterBox) this.box;
			letterBox.setLetter(boxValue.toUpperCase().charAt(0));
		} else {
			return;
		}
		
		refreshUI();
	}

	/**
	 * Get the current value of the boxpanel
	 * @return Box value
	 */
	public String getBoxValue() {
		if(boxValue == null || boxValue.length() <= 0)
			return " ";
		
		return boxValue;
	}

	/**
	 * Get the box object associated with this panel.
	 * @return Box object.
	 */
	public Box getBox() {
		return box;
	}

	/**
	 * Sets the box object associated with this panel, and
	 * updates the panel accordingly.
	 * @param box Box object to set.
	 */
	public void setBox(Box box) {
		logger.trace("Setting box type (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
	
		this.box = box;
		
		// Update boxValue
		if(this.box instanceof WordBox) {			
			WordBox wordBox = (WordBox) this.box;
			setBoxValue(wordBox.getWord());
		} else if(this.box instanceof LetterBox) {
			LetterBox letterBox = (LetterBox) this.box;
			setBoxValue(Character.toString(letterBox.getLetter()));
		}
		
		refreshUI();
	}
}
