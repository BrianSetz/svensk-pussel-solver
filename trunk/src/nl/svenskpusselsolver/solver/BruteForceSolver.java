package nl.svenskpusselsolver.solver;

import java.util.Iterator;
import java.util.List;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.dictionary.MijnWoordenBoekDotNL;
import nl.svenskpusselsolver.dictionary.PuzzleDictionary;
import nl.svenskpusselsolver.logging.Logger;

public class BruteForceSolver implements Solver {
	private Box[][] grid;
	private List<String>[][] possibleAnswersGrid;
	
	private PuzzleDictionary puzzleDictionary;
	
	public BruteForceSolver() {
		puzzleDictionary = new MijnWoordenBoekDotNL();
	}
	
	@Override
	public Box[][] solvePuzzle(Box[][] grid) {		
		this.grid = grid;		
		this.possibleAnswersGrid = new List[grid.length][grid[0].length];
		
		
		// Download all answers available for words.
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {			
				if(grid[x][y] instanceof WordBox) {
					WordBox wordBox = (WordBox) grid[x][y];
					int length = getWordLength(wordBox);
					Logger.log(Logger.TRACE, "Downloading answers for " + wordBox.getWord() + ".");
					List<String> possibleAnswers = puzzleDictionary.getAnswers(wordBox.getWord(), length);
					possibleAnswersGrid[x][y] = possibleAnswers;				
				}
			}
		}	
		
		boolean changesInGrid = true;

		// Find words that fit in the current situation, those are 
		// words with one fitting answer. As long as answers are being 
		// found, continue.
		while(changesInGrid) {
			changesInGrid = false;
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {			
					if(grid[x][y] instanceof WordBox) {
						WordBox wordBox = (WordBox) grid[x][y];
						List<String> possibleAnswers = possibleAnswersGrid[x][y];
						Logger.log(Logger.TRACE, "Checking answers for " + wordBox.getWord() + ".");
						
						if(possibleAnswers.size() <= 0) {
							Logger.log(Logger.TRACE, "Skipping " + wordBox.getWord() + ", already answered or no answers available.");
							continue;
						}
						
						int answerCount = 0;
						for (Iterator<String> iter = possibleAnswers.iterator(); iter.hasNext();) {
							String answer = iter.next();
							if(isPossibleAnswer(wordBox, answer)) {
								Logger.log(Logger.DEBUG, "Found a word with a possible answer, for " + wordBox.getWord() + ": " + answer +  ".");
								answerCount++;								
							} else {
								Logger.log(Logger.TRACE, "Answer will not fit for" + wordBox.getWord() + ". removing: " + answer +  ".");
								iter.remove();
							}
						}
						
						if(answerCount == 1) {
							Logger.log(Logger.DEBUG, "Found a word with a single answer, for " + wordBox.getWord() + ": " + possibleAnswers.get(0) +  ".");
							changesInGrid = true;
							setWord(wordBox, possibleAnswers.get(0));
							possibleAnswers.remove(0);
						}
					}
				}
			}
		}
		
		//TODO: Brute force remaining answers.
		
		
		return grid;		
	}

	private boolean isPossibleAnswer(WordBox wordBox, String word) {
		Logger.log(Logger.DEBUG, "Checking if " + word + " is a possible answer for: " + wordBox.getWord() + ".");
		
		int x = wordBox.getXCoordinate();
		int y = wordBox.getYCoordinate();
		
		int length = 0;		
		boolean couldFit = true;
		if(wordBox.getDirection() == WordBox.DIRECTION_UP) {
			y--;
			while(grid[x][y] instanceof LetterBox) {
				if(((LetterBox)grid[x][y]).getLetter() != '\u0000' && ((LetterBox)grid[x][y]).getLetter() != ' ')
					if(((LetterBox)grid[x][y]).getLetter() != word.charAt(length))
						return false;

				length++;
				y--;							
				if(y < 0)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_RIGHT) {
			x++;
			while(grid[x][y] instanceof LetterBox) {
				if(((LetterBox)grid[x][y]).getLetter() != '\u0000' && ((LetterBox)grid[x][y]).getLetter() != ' ')
					if(((LetterBox)grid[x][y]).getLetter() != word.charAt(length))
						return false;
				
				length++;
				x++;				
				if(x > grid.length - 1)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_DOWN) {
			y++;
			while(grid[x][y] instanceof LetterBox) {
				if(((LetterBox)grid[x][y]).getLetter() != '\u0000' && ((LetterBox)grid[x][y]).getLetter() != ' ')
					if(((LetterBox)grid[x][y]).getLetter() != word.charAt(length))
						return false;
				
				length++;
				y++;
				if(y > grid.length - 1)
					break;
			}
		} else if(wordBox.getDirection() == WordBox.DIRECTION_LEFT) {
			x--;
			while(grid[x][y] instanceof LetterBox) {
				if(((LetterBox)grid[x][y]).getLetter() != '\u0000' && ((LetterBox)grid[x][y]).getLetter() != ' ')
					if(((LetterBox)grid[x][y]).getLetter() != word.charAt(length))
						return false;
				
				length++;
				x--;								
				if(x < 0)
					break;
			}
		}
		
		return couldFit;
	}
	
	private int getWordLength(WordBox wordBox) {
		Logger.log(Logger.DEBUG, "Calculating length for " + wordBox.getWord() + ".");
		
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
		
		Logger.log(Logger.TRACE, "Found length " + length + " for " + wordBox.getWord() + ".");
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