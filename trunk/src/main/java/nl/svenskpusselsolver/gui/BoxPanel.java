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
 * Represents a box within the puzzle.
 */
public class BoxPanel extends JPanel {	
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(BoxPanel.class);
	
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
		
		logger.trace("Building BoxPanel (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");

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
		
		logger.info("BoxPanel built (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
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
	 * @param box New box.
	 */
	public void updateType(Box box) {
		logger.trace("Updating box type (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
		//type = newType;

		// Update box to new type
		if(box instanceof StaticBox) {
			logger.debug("Updating box type to STATICBOX (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
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
			WordBox.Direction direction = wordBox.getDirection();
			
			switch (direction) {
				case UP:
					logger.debug("Updating box type to WORDBOX, UP (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
					this.directionLabel.setText("^");
					break;
				case RIGHT:
					logger.debug("Updating box type to WORDBOX, RIGHT (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
					this.directionLabel.setText(">");
					break;
				case DOWN:
					logger.debug("Updating box type to WORDBOX, DOWN (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
					this.directionLabel.setText("v");
					break;
				case LEFT:
					logger.debug("Updating box type to WORDBOX, LEFT (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
					this.directionLabel.setText("<");
					break;
			}
			
		} else if(box instanceof LetterBox) {
			logger.debug("Updating box type to LETTERBOX (" + box.getXCoordinate() + "," + box.getYCoordinate() + ").");
			
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
