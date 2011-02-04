package nl.svenskpusselsolver.dataobjects;

public class WordBox extends Box {
	public static final int DIRECTION_UP = 0;	
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	
	private String word;
	private int direction;
	
	public WordBox(int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
	}
	
	public WordBox(int xCoordinate, int yCoordinate, String word, int direction) {		
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

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}	
}
