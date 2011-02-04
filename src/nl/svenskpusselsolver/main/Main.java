package nl.svenskpusselsolver.main;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.gui.PuzzleFrame;

public class Main {
	public static void main(String[] args) throws Exception {
		Box[][] grid = new Box[10][10];
		
		grid[0][0] = new StaticBox(0, 0);
		grid[0][1] = new WordBox(0, 1, "Zuivelproduct", WordBox.DIRECTION_DOWN);
		grid[0][2] = new StaticBox(0, 2);
		grid[0][3] = new WordBox(0, 3, "Sportiefkledingmerk", WordBox.DIRECTION_DOWN);
		grid[0][4] = new StaticBox(0, 4);
		grid[0][5] = new WordBox(0, 5, "Eens", WordBox.DIRECTION_DOWN);
		grid[0][6] = new StaticBox(0, 6);
		grid[0][7] = new WordBox(0, 7, "Wedstrijd", WordBox.DIRECTION_DOWN);
		grid[0][8] = new WordBox(0, 8, "Onder andere", WordBox.DIRECTION_DOWN);
		grid[0][9] = new WordBox(0, 9, "Vuur", WordBox.DIRECTION_DOWN);

		grid[1][0] = new WordBox(1, 1, "Buideldier", WordBox.DIRECTION_RIGHT);		
		grid[1][1] = new LetterBox(1, 2);
		grid[1][2] = new LetterBox(1, 3);
		grid[1][3] = new LetterBox(1, 4);
		grid[1][4] = new LetterBox(1, 5);
		grid[1][5] = new LetterBox(1, 6);
		grid[1][6] = new LetterBox(1, 7);
		grid[1][7] = new LetterBox(1, 8);
		grid[1][8] = new LetterBox(1, 9);
		grid[1][9] = new LetterBox(1, 0);

		grid[2][0] = new StaticBox(2, 1);		
		grid[2][1] = new LetterBox(2, 2);
		grid[2][2] = new WordBox(2, 3, "Wapen van een insect", WordBox.DIRECTION_DOWN);
		grid[2][3] = new LetterBox(2, 4);
		grid[2][4] = new StaticBox(2, 5);
		grid[2][5] = new LetterBox(2, 6);
		grid[2][6] = new WordBox(2, 7, "Paling", WordBox.DIRECTION_RIGHT);
		grid[2][7] = new LetterBox(2, 8);
		grid[2][8] = new LetterBox(2, 9);
		grid[2][9] = new LetterBox(2, 0);
		
		grid[3][0] = new WordBox(3, 1, "Vaartuig", WordBox.DIRECTION_RIGHT);		
		grid[3][1] = new LetterBox(3, 2);
		grid[3][2] = new LetterBox(3, 3);
		grid[3][3] = new LetterBox(3, 4);
		grid[3][4] = new WordBox(3, 5, "Vernis", WordBox.DIRECTION_DOWN);
		grid[3][5] = new LetterBox(3, 6);
		grid[3][6] = new WordBox(3, 7, "Elegant dier", WordBox.DIRECTION_DOWN);
		grid[3][7] = new LetterBox(3, 8);
		grid[3][8] = new WordBox(3, 9,  "Sneeuwhut", WordBox.DIRECTION_DOWN);
		grid[3][9] = new LetterBox(3, 0);
		
		grid[4][0] = new WordBox(4, 1, "Vervoermiddel", WordBox.DIRECTION_RIGHT);		
		grid[4][1] = new LetterBox(4, 2);
		grid[4][2] = new LetterBox(4, 3);
		grid[4][3] = new LetterBox(4, 4);
		grid[4][4] = new LetterBox(4, 5);
		grid[4][5] = new LetterBox(4, 6);
		grid[4][6] = new LetterBox(4, 7);
		grid[4][7] = new LetterBox(4, 8);
		grid[4][8] = new LetterBox(4, 9);
		grid[4][9] = new LetterBox(4, 0);

		grid[5][0] = new StaticBox(5, 1);		
		grid[5][1] = new WordBox(5, 2, "Nevel", WordBox.DIRECTION_DOWN);
		grid[5][2] = new LetterBox(5, 3);
		grid[5][3] = new WordBox(5, 4, "Ontkenning", WordBox.DIRECTION_DOWN);
		grid[5][4] = new LetterBox(5, 5);
		grid[5][5] = new WordBox(5, 6, "Bolster", WordBox.DIRECTION_DOWN);
		grid[5][6] = new LetterBox(5, 7);
		grid[5][7] = new WordBox(5, 8, "Rivier in Duitsland", WordBox.DIRECTION_DOWN);
		grid[5][8] = new LetterBox(5, 9);
		grid[5][9] = new WordBox(5, 0, "Bestemming", WordBox.DIRECTION_DOWN);
		
		grid[6][0] = new WordBox(6, 1, "Gedachte", WordBox.DIRECTION_RIGHT);		
		grid[6][1] = new LetterBox(6, 2);
		grid[6][2] = new LetterBox(6, 3);
		grid[6][3] = new LetterBox(6, 4);
		grid[6][4] = new LetterBox(6, 5);
		grid[6][5] = new LetterBox(6, 6);
		grid[6][6] = new LetterBox(6, 7);
		grid[6][7] = new LetterBox(6, 8);
		grid[6][8] = new LetterBox(6, 9);
		grid[6][9] = new LetterBox(6, 0);
		
		grid[7][0] = new WordBox(7, 1, "Bier", WordBox.DIRECTION_RIGHT);		
		grid[7][1] = new LetterBox(7, 2);
		grid[7][2] = new LetterBox(7, 3);
		grid[7][3] = new LetterBox(7, 4);
		grid[7][4] = new StaticBox(7, 5);
		grid[7][5] = new LetterBox(7, 6);
		grid[7][6] = new WordBox(7, 7, "Bos", WordBox.DIRECTION_RIGHT);
		grid[7][7] = new LetterBox(7, 8);
		grid[7][8] = new LetterBox(7, 9);
		grid[7][9] = new LetterBox(7, 0);
		
		grid[8][0] = new StaticBox(8, 1);		
		grid[8][1] = new LetterBox(8, 2);
		grid[8][2] = new StaticBox(8, 3);
		grid[8][3] = new LetterBox(8, 4);
		grid[8][4] = new StaticBox(8, 5);
		grid[8][5] = new LetterBox(8, 6);
		grid[8][6] = new StaticBox(8, 7);
		grid[8][7] = new LetterBox(8, 8);
		grid[8][8] = new StaticBox(8, 9);
		grid[8][9] = new LetterBox(8, 0);
		
		grid[9][0] = new WordBox(9, 1, "Stipt", WordBox.DIRECTION_RIGHT);		
		grid[9][1] = new LetterBox(9, 2);
		grid[9][2] = new LetterBox(9, 3);
		grid[9][3] = new LetterBox(9, 4);
		grid[9][4] = new LetterBox(9, 5);
		grid[9][5] = new LetterBox(9, 6);
		grid[9][6] = new LetterBox(9, 7);
		grid[9][7] = new LetterBox(9, 8);
		grid[9][8] = new LetterBox(9, 9);
		grid[9][9] = new LetterBox(9, 0);
		
		new PuzzleFrame(grid);
	}
}
