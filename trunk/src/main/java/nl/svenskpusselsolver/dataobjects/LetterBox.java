package nl.svenskpusselsolver.dataobjects;

/**
 * The letterbox contains a single letter.
 */
public class LetterBox extends Box {
	private char letter;
	
	public LetterBox(int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
	}

	public LetterBox(int xCoordinate, int yCoordinate, char letter) {
		this(xCoordinate, yCoordinate);
		
		this.letter = letter;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}

	public char getLetter() {
		return letter;
	}	
}
