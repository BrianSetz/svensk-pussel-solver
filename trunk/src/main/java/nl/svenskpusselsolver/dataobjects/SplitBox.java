package nl.svenskpusselsolver.dataobjects;

import nl.svenskpusselsolver.dataobjects.WordBox.Direction;

public class SplitBox extends Box {
	private Box upperBox;
	private Box lowerBox;

	public SplitBox(int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
		
		upperBox = new WordBox(xCoordinate, yCoordinate, "", Direction.UP);
		lowerBox = new WordBox(xCoordinate, yCoordinate, "", Direction.UP);
	}
	
	public Box getUpperBox() {
		return upperBox;
	}

	public void setUpperBox(Box upperBox) {
		this.upperBox = upperBox;
	}

	public Box getLowerBox() {
		return lowerBox;
	}
	
	public void setLowerBox(Box lowerBox) {
		this.lowerBox = lowerBox;
	}
}