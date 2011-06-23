package nl.svenskpusselsolver.dataobjects;

public class WordBox extends Box {
	
	public enum Direction { UP, RIGHT, DOWN, LEFT }
	
	private String word;
	private Direction direction;
	
	public WordBox(int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
	}
	
	public WordBox(int xCoordinate, int yCoordinate, String word, Direction direction) {		
		this(xCoordinate, yCoordinate);
		
		this.word = word;
		this.direction = direction;		
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}	
}
