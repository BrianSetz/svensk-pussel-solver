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
import nl.svenskpusselsolver.logging.Logger;

/**
 * Represents a box within the puzzle.
 */
public class BoxPanel extends JPanel {	
	private JTextPane textPane; // Text container
	private JLabel directionLabel; // Direction of wordbox
	private Box box;
	
	/**
	 * Create a new Box panel.
	 * @param xCoordinate X coordinate of this panel on the grid.
	 * @param yCoordinate Y coordinate of this panel on the grid.
	 */
	public BoxPanel(Box box) {
		super();
		
		Logger.log(Logger.LogLevel.TRACE, "Building BoxPanel (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");

		// Initialize box
		BoxPanelMouseListener bpml = new BoxPanelMouseListener(this);

		// Create textpane
		this.textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(70, 50));
		textPane.addMouseListener(bpml);
		
		// Create directionlabel
		this.directionLabel = new JLabel("oi");
		directionLabel.addMouseListener(bpml);		
		
		// Center text in textpane
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		// Init panel
		this.add(textPane);
		this.add(directionLabel);		
		this.addMouseListener(bpml);
		this.setPreferredSize(new Dimension(75, 75)); 	// Makes the layout
														// manager happy
		this.updateType(box); // Update
		
		Logger.log(Logger.LogLevel.INFO, "BoxPanel built (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
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
		if (box instanceof WordBox) {
			WordBox wordBox = (WordBox) box;
			textPane.setText(wordBox.getWord());
		} else if (box instanceof LetterBox) {
			textPane.setText(String.valueOf(((LetterBox)box).getLetter()).toUpperCase());
		}
		
		updateType(box);
	}
	/**
	 * Update the type of the box.
	 * 
	 * @param box
	 *            New box.
	 */
	public void updateType(Box box) {
		Logger.log(Logger.LogLevel.TRACE, "Updating box type (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		//type = newType;

		// Update box to new type
		if(box instanceof StaticBox) {
			Logger.log(Logger.LogLevel.DEBUG, "Updating box type to STATICBOX (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
			this.setBackground(Color.gray);
			this.textPane.setVisible(false);
			this.directionLabel.setVisible(false);
		} else if(box instanceof WordBox) {
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));			
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(true);
			
			WordBox wordBox = (WordBox) box;
			
			if(wordBox.getDirection() == WordBox.Direction.UP) {
				Logger.log(Logger.LogLevel.DEBUG, "Updating box type to WORDBOX, UP (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
				this.directionLabel.setText("^");
			} else if(wordBox.getDirection() == WordBox.Direction.RIGHT) {
				Logger.log(Logger.LogLevel.DEBUG, "Updating box type to WORDBOX, RIGHT (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
				this.directionLabel.setText(">");
			} else if(wordBox.getDirection() == WordBox.Direction.DOWN) {
				Logger.log(Logger.LogLevel.DEBUG, "Updating box type to WORDBOX, DOWN (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
				this.directionLabel.setText("v");
			} else if(wordBox.getDirection() == WordBox.Direction.LEFT) {
				Logger.log(Logger.LogLevel.DEBUG, "Updating box type to WORDBOX, LEFT (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
				this.directionLabel.setText("<");
			}
		} else if(box instanceof LetterBox) {
			Logger.log(Logger.LogLevel.DEBUG, "Updating box type to LETTERBOX (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
			this.setBackground(Color.white);
			this.textPane.setBackground(Color.white);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(false);
		}

		this.box = box;
		
		this.updateUI();
	}
}
