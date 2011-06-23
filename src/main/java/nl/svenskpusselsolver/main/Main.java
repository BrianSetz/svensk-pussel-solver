package nl.svenskpusselsolver.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.gui.PuzzleFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
	private final static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {		
		initializeLogging();
		
		
		logger.trace("Initializing demo puzzle.");		
		Box[][] grid = new Box[10][10];

		grid[0][0] = new StaticBox(0, 0);
        grid[1][0] = new WordBox(1, 0, "Zuivelproduct", WordBox.Direction.DOWN);
        grid[2][0] = new StaticBox(2, 0);
        grid[3][0] = new WordBox(3, 0, "Sportief kledingsmerk", WordBox.Direction.DOWN);
        grid[4][0] = new StaticBox(4, 0);
        grid[5][0] = new WordBox(5, 0, "Eens", WordBox.Direction.DOWN);
        grid[6][0] = new StaticBox(6, 0);
        grid[7][0] = new WordBox(7, 0, "Wedstrijd", WordBox.Direction.DOWN);
        grid[8][0] = new WordBox(8, 0, "Onder andere", WordBox.Direction.DOWN);
        grid[9][0] = new WordBox(9, 0, "Vuur", WordBox.Direction.DOWN);

        grid[0][1] = new WordBox(0, 1, "Buideldier", WordBox.Direction.RIGHT);          
        grid[1][1] = new LetterBox(1, 1);
        grid[2][1] = new LetterBox(2, 1);
        grid[3][1] = new LetterBox(3, 1);
        grid[4][1] = new LetterBox(4, 1);
        grid[5][1] = new LetterBox(5, 1);
        grid[6][1] = new LetterBox(6, 1);
        grid[7][1] = new LetterBox(7, 1);
        grid[8][1] = new LetterBox(8, 1);
        grid[9][1] = new LetterBox(9, 1);

        grid[0][2] = new StaticBox(0, 2);               
        grid[1][2] = new LetterBox(1, 2);
        grid[2][2] = new WordBox(2, 2, "Wapen van een insect", WordBox.Direction.DOWN);
        grid[3][2] = new LetterBox(3, 2);
        grid[4][2] = new StaticBox(4, 2);
        grid[5][2] = new LetterBox(5, 2);
        grid[6][2] = new WordBox(6, 2, "Paling", WordBox.Direction.RIGHT);
        grid[7][2] = new LetterBox(7, 2);
        grid[8][2] = new LetterBox(8, 2);
        grid[9][2] = new LetterBox(9, 2);
        
        grid[0][3] = new WordBox(0, 3, "Vaartuig", WordBox.Direction.RIGHT);            
        grid[1][3] = new LetterBox(1, 3);
        grid[2][3] = new LetterBox(2, 3);
        grid[3][3] = new LetterBox(3, 3);
        grid[4][3] = new WordBox(4, 3, "Vernis", WordBox.Direction.DOWN);
        grid[5][3] = new LetterBox(5, 3);
        grid[6][3] = new WordBox(6, 3, "Elegant dier", WordBox.Direction.DOWN);
        grid[7][3] = new LetterBox(7, 3);
        grid[8][3] = new WordBox(8, 3,  "Sneeuwhut", WordBox.Direction.DOWN);
        grid[9][3] = new LetterBox(9, 3);
        
        grid[0][4] = new WordBox(0, 4, "Vervoermiddel", WordBox.Direction.RIGHT);               
        grid[1][4] = new LetterBox(1, 4);
        grid[2][4] = new LetterBox(2, 4);
        grid[3][4] = new LetterBox(3, 4);
        grid[4][4] = new LetterBox(4, 4);
        grid[5][4] = new LetterBox(5, 4);
        grid[6][4] = new LetterBox(6, 4);
        grid[7][4] = new LetterBox(7, 4);
        grid[8][4] = new LetterBox(8, 4);
        grid[9][4] = new LetterBox(9, 4);

        grid[0][5] = new StaticBox(0, 5);               
        grid[1][5] = new WordBox(1, 5, "Nevel", WordBox.Direction.DOWN);
        grid[2][5] = new LetterBox(2, 5);
        grid[3][5] = new WordBox(3, 5, "Ontkenning", WordBox.Direction.DOWN);
        grid[4][5] = new LetterBox(4, 5);
        grid[5][5] = new WordBox(5, 5, "Bolster", WordBox.Direction.DOWN);
        grid[6][5] = new LetterBox(6, 5);
        grid[7][5] = new WordBox(7, 5, "Rivier in Duitsland", WordBox.Direction.DOWN);
        grid[8][5] = new LetterBox(8, 5);
        grid[9][5] = new WordBox(9, 5, "Bestemming", WordBox.Direction.DOWN);
        
        grid[0][6] = new WordBox(0, 6, "Gedachte", WordBox.Direction.RIGHT);            
        grid[1][6] = new LetterBox(1, 6);
        grid[2][6] = new LetterBox(2, 6);
        grid[3][6] = new LetterBox(3, 6);
        grid[4][6] = new LetterBox(4, 6);
        grid[5][6] = new LetterBox(5, 6);
        grid[6][6] = new LetterBox(6, 6);
        grid[7][6] = new LetterBox(7, 6);
        grid[8][6] = new LetterBox(8, 6);
        grid[9][6] = new LetterBox(9, 6);
        
        grid[0][7] = new WordBox(0, 7, "Bier", WordBox.Direction.RIGHT);                
        grid[1][7] = new LetterBox(1, 7);
        grid[2][7] = new LetterBox(2, 7);
        grid[3][7] = new LetterBox(3, 7);
        grid[4][7] = new StaticBox(4, 7);
        grid[5][7] = new LetterBox(5, 7);
        grid[6][7] = new WordBox(6,7, "Bos", WordBox.Direction.RIGHT);
        grid[7][7] = new LetterBox(7, 7);
        grid[8][7] = new LetterBox(8, 7);
        grid[9][7] = new LetterBox(9, 7);
        
        grid[0][8] = new StaticBox(0, 8);               
        grid[1][8] = new LetterBox(1, 8);
        grid[2][8] = new StaticBox(2, 8);
        grid[3][8] = new LetterBox(3, 8);
        grid[4][8] = new StaticBox(4, 8);
        grid[5][8] = new LetterBox(5, 8);
        grid[6][8] = new StaticBox(6, 8);
        grid[7][8] = new LetterBox(7, 8);
        grid[8][8] = new StaticBox(8, 8);
        grid[9][8] = new LetterBox(9, 8);
        
        grid[0][9] = new WordBox(0, 9, "Stipt", WordBox.Direction.RIGHT);               
        grid[1][9] = new LetterBox(1, 9);
        grid[2][9] = new LetterBox(2, 9);
        grid[3][9] = new LetterBox(3, 9);
        grid[4][9] = new LetterBox(4, 9);
        grid[5][9] = new LetterBox(5, 9);
        grid[6][9] = new LetterBox(6, 9);
        grid[7][9] = new LetterBox(7, 9);
        grid[8][9] = new LetterBox(8, 9);
        grid[9][9] = new LetterBox(9, 9);

		
		new PuzzleFrame(grid);
	}

	private static void initializeLogging() {
		Properties logProperties = new Properties();

		try {
			logProperties.load(new FileInputStream("src/main/resources/log4j.properties"));
		} catch (IOException e) {
			logger.error("Unable to load logging properties", e);
		}

		PropertyConfigurator.configure(logProperties);
		logger.info("Logging initialized.");
	}
}
