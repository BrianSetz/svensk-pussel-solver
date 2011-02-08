package nl.svenskpusselsolver.solver;

import java.util.Iterator;
import java.util.List;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.dictionary.MijnWoordenBoekDotNL;
import nl.svenskpusselsolver.dictionary.PuzzleDictionary;
import nl.svenskpusselsolver.logging.Logger;
import nl.svenskpusselsolver.logging.Logger.LogLevel;

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
				if (grid[x][y] instanceof WordBox) {
					WordBox wordBox = (WordBox) grid[x][y];
					int length = getWordLength(wordBox);
					Logger.log(Logger.LogLevel.TRACE, "Downloading answers for " + wordBox.getWord() + ".");
					List<String> possibleAnswers = puzzleDictionary.getAnswers(wordBox.getWord(), length);
					possibleAnswersGrid[x][y] = possibleAnswers;
				}
			}
		}

		boolean changesInGrid = true;

		// Find words that fit in the current situation, those are
		// words with one fitting answer. As long as answers are being
		// found, continue.
		while (changesInGrid) {
			changesInGrid = false;
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {
					if (grid[x][y] instanceof WordBox) {
						WordBox wordBox = (WordBox) grid[x][y];
						List<String> possibleAnswers = possibleAnswersGrid[x][y];
						Logger.log(Logger.LogLevel.TRACE,
								"Checking answers for " + wordBox.getWord()
										+ ".");

						// No answers in list
						if (possibleAnswers.size() <= 0) {
							Logger.log( Logger.LogLevel.TRACE, "Skipping " + wordBox.getWord() + ", already answered or no answers available.");
							continue;
						}

						// Count possible answers
						int answerCount = 0;
						for (Iterator<String> iter = possibleAnswers.iterator(); iter
								.hasNext();) {
							String answer = iter.next();
							if (isPossibleAnswer(wordBox, answer)) {
								Logger.log(Logger.LogLevel.DEBUG, "Found a word with a possible answer, for " + wordBox.getWord() + ": " + answer + ".");
								answerCount++;
							} else {
								Logger.log(Logger.LogLevel.TRACE, "Answer will not fit for" + wordBox.getWord() + ". removing: " + answer + ".");
								iter.remove();
							}
						}

						// If there is one possible answer, use it
						if (answerCount == 1) {
							Logger.log(Logger.LogLevel.DEBUG, "Found a word with a single answer, for " + wordBox.getWord() + ": " + possibleAnswers.get(0) + ".");
							changesInGrid = true;
							setWord(wordBox, possibleAnswers.get(0));
							possibleAnswers.remove(0);
							// If not, check if there are any letters that can
							// be filled in
						} else if (answerCount > 1) {
							Iterator<String> iter = possibleAnswers.iterator();
							char[] possibleLetters = iter.next().toCharArray();

							while (iter.hasNext()) {
								char[] answer = iter.next().toCharArray();

								for (int i = 0; i < possibleLetters.length; i++) {
									if (possibleLetters[i] != answer[i])
										possibleLetters[i] = ' ';
								}
							}

							// If it contains letters
							if (!new String(possibleLetters).matches(" *")) {
								if (setWord(wordBox, possibleLetters)) {
									changesInGrid = true;
								}
							}
						}
					}
				}
			}
		}

		Box[][] tmpGrid = grid.clone();
		List<String>[][] tmpPossibleAnswersGrid = possibleAnswersGrid.clone();

		return grid;
	}

	private boolean isPossibleAnswer(WordBox wordBox, String word) {
		Logger.log(Logger.LogLevel.DEBUG, "Checking if " + word
				+ " is a possible answer for: " + wordBox.getWord() + ".");

		int position = 0;
		WordBox.Direction direction = wordBox.getDirection();
		LetterBox letterBox = getNextLetterBox(wordBox, direction);

		while (letterBox instanceof LetterBox) {
			if (letterBox.getLetter() != '\u0000' && letterBox.getLetter() != ' ' && letterBox.getLetter() != word.charAt(position))
				return false;
			position++;

			letterBox = getNextLetterBox(letterBox, direction);
		}
		
		return true;
	}

	private int getWordLength(WordBox wordBox) {
		Logger.log(Logger.LogLevel.DEBUG, "Calculating length for " + wordBox.getWord() + ".");

		int length = 0;

		WordBox.Direction direction = wordBox.getDirection();
		LetterBox letterBox = getNextLetterBox(wordBox, direction);

		while (letterBox != null) {
			length++;
			letterBox = getNextLetterBox(letterBox, direction);
		}

		Logger.log(Logger.LogLevel.TRACE, "Found length " + length + " for " + wordBox.getWord() + ".");
		return length;
	}

	private boolean setWord(WordBox wordBox, String word) {
		return setWord(wordBox, word.toCharArray());
	}

	/**
	 * @return true if any letters where filled in
	 */
	private boolean setWord(WordBox wordBox, char[] word) {
		Logger.log(Logger.LogLevel.DEBUG, "Setting word \"" + new String(word) + "\" for: " + wordBox.getWord() + ".");

		boolean lettersChanged = false;
		int position = 0;
		WordBox.Direction direction = wordBox.getDirection();
		LetterBox letterBox = getNextLetterBox(wordBox, direction);

		while (letterBox instanceof LetterBox) {
			if (setLetter(letterBox, word[position])) {
				lettersChanged = true;
			}
			position++;

			letterBox = getNextLetterBox(letterBox, direction);
		}

		Logger.log(Logger.LogLevel.TRACE, "Set word \"" + new String(word) + "\" for: " + wordBox.getWord() + ".");
		return lettersChanged;
	}

	/**
	 * @return true if the letter was filled in
	 */
	private boolean setLetter(LetterBox letterBox, char letter) {
		if (letter != ' ' && letterBox.getLetter() != letter) {
			letterBox.setLetter(letter);
			return true;
		}
		return false;
	}

	private LetterBox getNextLetterBox(Box letterBox,
			WordBox.Direction direction) {
		int x = letterBox.getXCoordinate();
		int y = letterBox.getYCoordinate();
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
		if (!isOutOfBounds(x, y)) {
			Box nextBox = grid[x][y];
			if (nextBox instanceof LetterBox)
				return (LetterBox) nextBox;
		}

		return null;
	}

	private boolean isOutOfBounds(int x, int y) {
		return y < 0 || x < 0 || x > grid.length - 1 || y > grid.length - 1;
	}
}
