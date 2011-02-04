package nl.svenskpusselsolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;

/**
 * Represents a box within the puzzle.
 */
public class BoxPanel extends JPanel {
	public static final int TYPE_BOX = 0; // Normal box, contains nothing
	public static final int TYPE_WORDBOX_UP = 1;	// Word box, contains the word to
													// find a synonym for
	public static final int TYPE_WORDBOX_RIGHT = 2; 	// Word box, contains the word to
													// find a synonym for
	public static final int TYPE_WORDBOX_DOWN = 3; 	// Word box, contains the word to
													// find a synonym for
	public static final int TYPE_WORDBOX_LEFT = 4; // Word box, contains the word to
													// find a synonym for
	public static final int TYPE_LETTERBOX = 5; 	// Letter box, contains the
													// letter of a word

	private int xCoordinate, yCoordinate; // Coordinates	
	private int type = TYPE_LETTERBOX; // Initial box is letter box
	private JTextPane textPane; // Text container
	private JLabel directionLabel; // Direction of wordbox

	/**
	 * Create a new Box panel.
	 * @param xCoordinate X coordinate of this panel on the grid.
	 * @param yCoordinate Y coordinate of this panel on the grid.
	 */
	public BoxPanel(int xCoordinate, int yCoordinate) {
		super();
		
		// Init coordinates
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		
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
		this.updateType(type); // Update
	}

	public Box getBox() {
		switch (type) {
			case TYPE_BOX:
				return new StaticBox(xCoordinate, yCoordinate);
	
			case TYPE_WORDBOX_UP:
			case TYPE_WORDBOX_LEFT:
			case TYPE_WORDBOX_DOWN:
			case TYPE_WORDBOX_RIGHT:
				WordBox wb = new WordBox(xCoordinate, yCoordinate);
				wb.setDirection(type - 1);
				wb.setWord(textPane.getText());
				return wb; 
				
			case TYPE_LETTERBOX:
				LetterBox lb = new LetterBox(xCoordinate, yCoordinate);
				lb.setLetter(textPane.getText().charAt(0));
				return lb; 		
		}
		
		return null;
	}

	public void setBox(Box box) {
		if(box instanceof StaticBox) {
			updateType(TYPE_BOX);
		} else if (box instanceof WordBox) {
			WordBox wordBox = (WordBox) box;
			textPane.setText(wordBox.getWord());
			updateType(wordBox.getDirection() + 1);
		} else if (box instanceof LetterBox) {
			textPane.setText(String.valueOf(((LetterBox)box).getLetter()));
			updateType(TYPE_LETTERBOX);
		}
	}
	/**
	 * Update the type of the box.
	 * 
	 * @param newType
	 *            New type of the box.
	 */
	private void updateType(int newType) {
		type = newType;

		// Update box to new type
		switch (newType) {
		case TYPE_BOX:
			this.setBackground(Color.gray);
			this.textPane.setVisible(false);
			this.directionLabel.setVisible(false);
			break;

		case TYPE_WORDBOX_UP:
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));			
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(true);
			this.directionLabel.setText("^");
			break;
			
		case TYPE_WORDBOX_LEFT:
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));			
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(true);
			this.directionLabel.setText("<");
			break;
			
		case TYPE_WORDBOX_DOWN:			
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));			
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(true);
			this.directionLabel.setText("v");
			break;

		case TYPE_WORDBOX_RIGHT:
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));			
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(true);
			this.directionLabel.setText(">");
			break;
			
		case TYPE_LETTERBOX:
			this.setBackground(Color.white);
			this.textPane.setBackground(Color.white);
			this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
			this.textPane.setVisible(true);
			this.directionLabel.setVisible(false);
			break;
		}

		this.updateUI();
	}

	/**
	 * Mouse listener for the box panel.
	 */
	private class BoxPanelMouseListener implements MouseListener {
		private BoxPanel boxPanel;

		public BoxPanelMouseListener(BoxPanel boxPanel) {
			this.boxPanel = boxPanel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// If other button than right button is pressed or if we're clicking
			// on a non-editable field, do nothing.
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != InputEvent.BUTTON3_MASK
					|| type == TYPE_BOX)
				return;

			String message = "";

			// Determine message
			if (type == TYPE_LETTERBOX) {
				message = "Enter a letter ";
			} else {
				message = "Enter a word ";
			}

			// Get word or letter
			String result = (String) JOptionPane.showInputDialog(boxPanel,
					message, message, JOptionPane.PLAIN_MESSAGE, null, null,
					boxPanel.textPane.getText());

			// Format letter
			if (type == TYPE_LETTERBOX && result.length() > 0)
				result = String.valueOf(result.charAt(0)).toUpperCase();

			// Update textpane
			boxPanel.textPane.setText(result);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// If other button than left button is pressed, do nothing.
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK)
				return;

			// Cycle types
			switch (boxPanel.type) {
			case TYPE_BOX:
				boxPanel.updateType(TYPE_WORDBOX_UP);
				break;

			case TYPE_WORDBOX_UP:
				boxPanel.updateType(TYPE_WORDBOX_LEFT);
				break;

			case TYPE_WORDBOX_LEFT:
				boxPanel.updateType(TYPE_WORDBOX_DOWN);
				break;
				
			case TYPE_WORDBOX_DOWN:
				boxPanel.updateType(TYPE_WORDBOX_RIGHT);
				break;
				
			case TYPE_WORDBOX_RIGHT:
				boxPanel.updateType(TYPE_LETTERBOX);
				break;
				
			case TYPE_LETTERBOX:
				boxPanel.updateType(TYPE_BOX);
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
