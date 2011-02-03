package nl.svenskpusselsolver.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import nl.svenskpusselsolver.woordenboek.MijnWoordenBoekDotNL;
import nl.svenskpusselsolver.woordenboek.PuzzelDictionary;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	private JTextField wordField;
	private JTextField countField;
	private JButton searchButton;
	private JComboBox resultComboBox;
	
	public MainGUI() {		
		Container content = this.getContentPane();
		content.setLayout(new GridLayout(0,2,20,0));
		
		content.add(new JLabel("Woord:"));
		wordField = new JTextField();
		content.add(wordField);
		
		content.add(new JLabel("Letters:"));
		countField = new JTextField();		
		content.add(countField);
		
		searchButton = new JButton("Zoek");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PuzzelDictionary pwb = new MijnWoordenBoekDotNL();
				
				int count = -1;
				try {
					count = Integer.parseInt(countField.getText());
				}catch(NumberFormatException e) {					
				}
				
				List<String> words = pwb.getAnswers(wordField.getText(), count);
								
				resultComboBox.removeAllItems();
				for(String word : words) {		
					resultComboBox.addItem(word);
				}
				
				pack();
			}
		});
		
		content.add(searchButton);
		resultComboBox = new JComboBox();
		content.add(resultComboBox);
		
		this.setTitle("Svensk Pussel Solver");
		this.pack();
		this.setVisible(true);
	}
}
