package nl.svenskpusselsolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Represents a box within the puzzle.
 */
public class BoxPanel extends JPanel {
	public static final int TYPE_BOX = 0; // Normal box, contains nothing
	public static final int TYPE_WORDBOX = 1; 	// Word box, contains the word to
												// find a synonym for
	public static final int TYPE_LETTERBOX = 2; // Letter box, contains the
												// letter of a word

	private int type = TYPE_LETTERBOX; // Initial box is letter box
	private JTextPane textPane;

	public BoxPanel() {
		super();

		// Initialize box
		BoxPanelMouseListener bpml = new BoxPanelMouseListener(this);

		// Create textpane
		this.textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(70, 70));
		textPane.addMouseListener(bpml);

		// Center text in textpane
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		this.add(textPane);
		this.addMouseListener(bpml);
		this.setPreferredSize(new Dimension(75, 75)); 	// Makes the layout
														// manager happy
		this.updateType(type); // Update
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
			break;

		case TYPE_WORDBOX:
			this.setBackground(Color.lightGray);
			this.textPane.setBackground(Color.lightGray);
			textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
			this.textPane.setVisible(true);
			break;

		case TYPE_LETTERBOX:
			this.setBackground(Color.white);
			this.textPane.setBackground(Color.white);
			textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
			this.textPane.setVisible(true);
			break;
		}

		this.updateUI();
	}

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
				boxPanel.updateType(TYPE_WORDBOX);
				break;

			case TYPE_WORDBOX:
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
