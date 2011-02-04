package nl.svenskpusselsolver.solver;

import java.util.List;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.dictionary.MijnWoordenBoekDotNL;
import nl.svenskpusselsolver.dictionary.PuzzleDictionary;

public class BruteForceSolver implements Solver {
	private Box[][] grid;
	private PuzzleDictionary puzzleDictionary;
	
	public BruteForceSolver() {
		puzzleDictionary = new MijnWoordenBoekDotNL();
	}
	
	@Override
	public Box[][] solvePuzzle(Box[][] grid) {		
		this.grid = grid;			
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {			
				if(grid[x][y] instanceof WordBox) {
					WordBox wordBox = (WordBox) grid[x][y];
					int length = getWordLength(wordBox);
					List<String> answers = puzzleDictionary.getAnswers(wordBox.getWord(), length);
					if(answers.size() == 1) {
						setWord(wordBox, answers.get(0));
					}						
				}
			}
		}	
		
		return grid;
	}

	private int getWordLength(WordBox wordBox) {
		int x = wordBox.getXCoordinate();
		int y = wordBox.getYCoordinate();
		
		int length = 0;
		
		if(wordBox.getDirection() == WordBox.DIRECTION_UP) {
			y--;
			while(grid[x][y] instanceof LetterBox) {
				length++;
				y--;
				
				if(y < 0)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_RIGHT) {
			x++;
			while(grid[x][y] instanceof LetterBox) {
				length++;
				x++;
				
				if(x > grid.length - 1)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_DOWN) {
			y++;
			while(grid[x][y] instanceof LetterBox) {
				length++;
				y++;
				
				if(y > grid.length - 1)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_LEFT) {
			x--;
			while(grid[x][y] instanceof LetterBox) {
				length++;
				x--;
				
				if(x < 0)
					break;
			}
		}
		
		return length;		
	}
	
	private void setWord(WordBox wordBox, String word) {
		int x = wordBox.getXCoordinate();
		int y = wordBox.getYCoordinate();
		
		int length = 0;
		
		if(wordBox.getDirection() == WordBox.DIRECTION_UP) {
			y--;
			while(grid[x][y] instanceof LetterBox) {
				((LetterBox)grid[x][y]).setLetter(word.charAt(length));
				length++;
				y--;							
				if(y < 0)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_RIGHT) {
			x++;
			while(grid[x][y] instanceof LetterBox) {
				((LetterBox)grid[x][y]).setLetter(word.charAt(length));
				length++;
				x++;				
				if(x > grid.length - 1)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_DOWN) {
			y++;
			while(grid[x][y] instanceof LetterBox) {
				((LetterBox)grid[x][y]).setLetter(word.charAt(length));
				length++;
				y++;
				if(y > grid.length - 1)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_LEFT) {
			x--;
			while(grid[x][y] instanceof LetterBox) {
				((LetterBox)grid[x][y]).setLetter(word.charAt(length));
				length++;
				x--;								
				if(x < 0)
					break;
			}
		}
	}
}
