package nl.svenskpusselsolver.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import nl.svenskpusselsolver.dataobjects.LetterBox;

import org.apache.log4j.Logger;

public class LetterBoxPanel extends BoxPanel {
	private static final long serialVersionUID = 6676587437754602079L;
	private final static Logger logger = Logger.getLogger(LetterBoxPanel.class);
	
	private JTextPane textPane; // Text container
	private LetterBox letterBox;
	
	public LetterBoxPanel(LetterBox letterBox) {
		super(letterBox);
				
		this.letterBox = letterBox;
		
		// Create textpane
		this.textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(70, 50));
		textPane.addMouseListener(bpml);
		
		// Center text in textpane
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
        // Change appearance
		this.setBackground(Color.white);
		this.textPane.setBackground(Color.white);
		this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		this.textPane.setText(Character.toString(letterBox.getLetter()));
		
		this.add(textPane);	
	}

	@Override
	public void handleRightClick() {
		// Determine message
		String message = "Enter a letter ";
		
		logger.trace("Showing dialog (" + letterBox.getXCoordinate() + "," + letterBox.getYCoordinate() + ").");
		
		// Get word or letter
		String input = (String) JOptionPane.showInputDialog(this,
				message, message, JOptionPane.PLAIN_MESSAGE, null, null,
				letterBox.getLetter());
		
		logger.debug("Result from dialog: " + input + " (" + letterBox.getXCoordinate() + "," + letterBox.getYCoordinate() + ").");
		
		// Cancel button
		if(input == null)
			return;
		
		// Check for empty string
		if(input.length() <= 0)
			input = " ";
				
		updateLetter(input.toUpperCase().charAt(0));
	}
	
	public void updateLetter(char letter) {
		letterBox.setLetter(letter);
		this.textPane.setText(Character.toString(letter));
	}
}
