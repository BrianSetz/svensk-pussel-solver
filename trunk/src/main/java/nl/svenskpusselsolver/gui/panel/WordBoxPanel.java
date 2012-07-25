package nl.svenskpusselsolver.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

import nl.svenskpusselsolver.dataobjects.WordBox;

public class WordBoxPanel extends BoxPanel {
	private static final long serialVersionUID = -922204686841810560L;
	private final static Logger logger = Logger.getLogger(WordBoxPanel.class);
	
	private WordBox wordBox;
	private JTextPane textPane; // Text container	
	private JLabel directionLabel; // Direction of wordbox
	
	public WordBoxPanel(WordBox wordBox) {
		super(wordBox);
				
		this.wordBox = wordBox;
		
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
        
		// Create directionlabel
		this.directionLabel = new JLabel("");
		directionLabel.addMouseListener(bpml);
				
		// Change appearance
		this.setBackground(Color.lightGray);
		this.textPane.setBackground(Color.lightGray);
		this.textPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
		
		WordBox.Direction direction = wordBox.getDirection();
		this.textPane.setText(wordBox.getWord());
		
		switch (direction) {
		case UP:
			this.directionLabel.setText("^");
			break;
		case RIGHT:
			this.directionLabel.setText(">");
			break;
		case DOWN:
			this.directionLabel.setText("v");
			break;
		case LEFT:
			this.directionLabel.setText("<");
			break;			
		}
		
		this.add(textPane);
		this.add(directionLabel);	
	}
	
	@Override
	public void handleRightClick() {
		// Determine message
		String message = "Enter a word ";
		
		logger.trace("Showing dialog (" + wordBox.getXCoordinate() + "," + wordBox.getYCoordinate() + ").");
		
		// Get word or letter
		String input = (String) JOptionPane.showInputDialog(this,
				message, message, JOptionPane.PLAIN_MESSAGE, null, null,
				wordBox.getWord());
		
		logger.debug("Result from dialog: " + input + " (" + wordBox.getXCoordinate() + "," + wordBox.getYCoordinate() + ").");
		
		// Cancel button
		if(input == null)
			return;
		
		// Check for empty string
		if(input.length() <= 0)
			input = " ";
				
		updateWord(input);
	}
	
	public void updateWord(String word) {
		wordBox.setWord(word);
		this.textPane.setText(word);
	}
}
