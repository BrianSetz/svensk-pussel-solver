package nl.svenskpusselsolver.solver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.dictionary.MijnWoordenBoekDotNL;
import nl.svenskpusselsolver.dictionary.PuzzleDictionary;

import org.apache.log4j.Logger;

/**
 * A semi-bruteforce solver that has a little intelligence. 
 */
public class BruteForceSolver implements Solver {
	private final static Logger logger = Logger.getLogger(BruteForceSolver.class);
	
	private static Map<String, List<String>> cachedPossibleAnswers = new HashMap<String, List<String>>();
	
	private Box[][] grid;
	private List<String>[][] possibleAnswersGrid;
	private PuzzleDictionary puzzleDictionary;

	public BruteForceSolver() {
		puzzleDictionary = new MijnWoordenBoekDotNL();
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Will try to solve the puzzle specified in the grid and return the solved grid.
	 * @param grid Grid to solve.
	 * @return Solved grid.
	 */
	public Box[][] solvePuzzle(Box[][] grid) {
		this.grid = grid;
		this.possibleAnswersGrid = new List[grid.length][grid[0].length];

		// Download all answers available for words.
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (grid[x][y] instanceof WordBox) {
					WordBox wordBox = (WordBox) grid[x][y];
					int length = getWordLength(wordBox);
					
					// Check if word is cached
					List<String> possibleAnswers = null;
					if(cachedPossibleAnswers.containsKey(wordBox.getWord())) {
						logger.trace("Cached answers for " + wordBox.getWord() + ".");
						possibleAnswers = cachedPossibleAnswers.get(wordBox.getWord());
					} else {					
						logger.trace("Downloading answers for " + wordBox.getWord() + ".");
						possibleAnswers = puzzleDictionary.getAnswers(wordBox.getWord(), length);
						cachedPossibleAnswers.put(wordBox.getWord(), possibleAnswers);
					}
					
					possibleAnswersGrid[x][y] = possibleAnswers;
				}
			}
		}

		// Defines whether to continue looping or whether to stop.
		boolean changesInGrid = true;

		// Find words that fit in the current situation, those are
		// words with one fitting answer. As long as answers are being
		// found, continue.
		while (changesInGrid) {
			changesInGrid = false;
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {
					// If the current item in the grid is not a WordBox
					if (!(grid[x][y] instanceof WordBox)) {
						continue;
					}
					
					// Get answers found for the WordBox
					WordBox wordBox = (WordBox) grid[x][y];
					List<String> possibleAnswers = possibleAnswersGrid[x][y];
					logger.trace("Checking answers for " + wordBox.getWord() + ".");

					// No answers in list
					if (possibleAnswers.size() <= 0) {
						logger.trace("Skipping " + wordBox.getWord() + ", already answered or no answers available.");
						continue;
					}

					// Count possible answers
					int answerCount = 0;
					for (Iterator<String> iter = possibleAnswers.iterator(); iter.hasNext();) {
						String answer = iter.next();
						// If word is a possible answer, add it to the count
						if (isPossibleAnswer(wordBox, answer)) {
							logger.debug("Found a word with a possible answer, for " + wordBox.getWord() + ": " + answer + ".");
							answerCount++;
						// If word is not a possible answer, remove it
						} else {
							logger.trace("Answer will not fit for" + wordBox.getWord() + ". removing: " + answer + ".");
							iter.remove();
						}
					}

					// If there is one possible answer and the answer has new letters,
					// use it and remove it from the list
					if (answerCount == 1 && setAnswer(wordBox, possibleAnswers.get(0))) {
						logger.debug("Found a word with a single answer, for " + wordBox.getWord() + ": " + possibleAnswers.get(0) + ".");
						changesInGrid = true;
						possibleAnswers.remove(0);
					// If not, check if there are any letters that can be filled in
					} else if (answerCount > 1) {
						Iterator<String> iter = possibleAnswers.iterator();
						char[] possibleLetters = iter.next().toCharArray();

						// Loop all possible answers
						while (iter.hasNext()) {
							char[] answer = iter.next().toCharArray();

							// Loop all letters in the answers, replace a letter with a space if it doesn't fit
							for (int i = 0; i < possibleLetters.length; i++) {
								if (possibleLetters[i] != answer[i])
									possibleLetters[i] = ' ';
							}
						}

						// Check if the possible answer does not contain spaces
						if (!new String(possibleLetters).matches(" *")) {
							if (setAnswer(wordBox, possibleLetters)) {
								changesInGrid = true;
							}
						}
					}
				} // End Y loop
			} // End X loop
		} // End while loop

		return grid;
	}

	/**
	 * Check if the word is a possible answer for the given word box.
	 * @param wordBox Word box to find a possible answer for.
	 * @param possibleAnswer Answer to check.
	 * @return True if the word is a possible answer.
	 */
	private boolean isPossibleAnswer(WordBox wordBox, String possibleAnswer) {
		logger.debug("Checking if " + possibleAnswer
				+ " is a possible answer for: " + wordBox.getWord() + ".");

		// Look for the next letter box
		WordBox.Direction direction = wordBox.getDirection();
		LetterBox letterBox = getNextLetterBox(wordBox, direction);

		// Loop all letter boxes and all characters in the possible answer
		int position = 0;
		while (letterBox != null) {
			// If there already is something in the box that is not 'null', a space, or the same letter: return false
			if (letterBox.getLetter() != '\u0000' && letterBox.getLetter() != ' ' && letterBox.getLetter() != possibleAnswer.charAt(position))
				return false;
			
			// Move to next character
			position++;

			// Look for the next letter box
			letterBox = getNextLetterBox(letterBox, direction);
		}
		
		// Word is a possible answer
		return true;
	}

	/**
	 * Get the length of the answer we are looking for.
	 * @param wordBox The word box to find the answer for.
	 * @return The length of the answer.
	 */
	private int getWordLength(WordBox wordBox) {
		logger.debug("Calculating length for " + wordBox.getWord() + ".");

		// Look for the next letter box
		WordBox.Direction direction = wordBox.getDirection();
		LetterBox letterBox = getNextLetterBox(wordBox, direction);

		// Continue looping until the last letterbox is found and count the total letterboxes
		int length = 0;
		while (letterBox != null) {
			length++;
			
			// Look for the next letter box
			letterBox = getNextLetterBox(letterBox, direction);
		}

		logger.trace("Found length " + length + " for " + wordBox.getWord() + ".");
		return length;
	}

	/**
	 * Set the answer to a wordbox.
	 * @param wordBox Wordbox to set the answer for.
	 * @param answer The answer to set.
	 * @return True if there the answer changed the old answer
	 */
	private boolean setAnswer(WordBox wordBox, String answer) {
		return setAnswer(wordBox, answer.toCharArray());
	}

	/**
	 * Set the answer to a wordbox.
	 * @param wordBox Wordbox to set the answer for.
	 * @param answer The answer to set.
	 * @return True if there the answer changed the old answer
	 */
	private boolean setAnswer(WordBox wordBox, char[] answer) {
		logger.debug("Setting word \"" + new String(answer) + "\" for: " + wordBox.getWord() + ".");

		// Look for the next letter box
		WordBox.Direction direction = wordBox.getDirection();
		LetterBox letterBox = getNextLetterBox(wordBox, direction);

		// Loop all letterboxes and set each letter of the answer
		boolean lettersChanged = false;
		int position = 0;
		while (letterBox != null) {
			// If the letter has been set, changes = true
			if (setLetter(letterBox, answer[position])) {
				lettersChanged = true;
			}
			
			// Move letter
			position++;
			
			// Look for the next letter box
			letterBox = getNextLetterBox(letterBox, direction);
		}

		logger.trace("Set word \"" + new String(answer) + "\" for: " + wordBox.getWord() + ".");
		return lettersChanged;
	}

	/**
	 * Set the letter in the wordbox specified.
	 * @param letterBox The letter box to set.
	 * @param letter The letter to set.
	 * @return True if the letter has been set, false if the letter has not been set.
	 */
	private boolean setLetter(LetterBox letterBox, char letter) {
		// If the letter is not a space and the letter is different from the current letter:
		if (letter != ' ' && letterBox.getLetter() != letter) {
			// Set the new letter
			letterBox.setLetter(letter);
			return true;
		}
		
		// No changes
		return false;
	}

	/**
	 * Get the next letterbox in the specified direction.
	 * @param letterBox Current letterbox.
	 * @param direction Direction.
	 * @return The next letterbox or null if there is none.
	 */
	private LetterBox getNextLetterBox(Box letterBox, WordBox.Direction direction) {
		// Get current coordinates.
		int x = letterBox.getXCoordinate();
		int y = letterBox.getYCoordinate();
		
		// Move current coordinates.
		switch (direction) {
			case UP:
				y--;
				break;
			case RIGHT:
				x++;
				break;
			case DOWN:
				y++;
				break;
			case LEFT:
				y--;
				break;
		}
		
		// Check if coordinates are not out of bounds.
		if (!isOutOfBounds(x, y)) {
			
			// Check if next box is a letterbox
			Box nextBox = grid[x][y];
			if (nextBox instanceof LetterBox)
				return (LetterBox) nextBox;
		}

		// No next letterbox
		return null;
	}

	/**
	 * Check if index of x and y are out of bounds.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return True if out of bounds, false if not out of bounds.
	 */
	private boolean isOutOfBounds(int x, int y) {
		return y < 0 || x < 0 || x > grid.length - 1 || y > grid.length - 1;
	}
}
