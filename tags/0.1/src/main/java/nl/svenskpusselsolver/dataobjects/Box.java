package nl.svenskpusselsolver.dataobjects;

/**
 * Basis box in a Zweedse Puzzel. Every box has 
 * a x,y coordinate.
 */
public abstract class Box {
	private int xCoordinate;
	private int yCoordinate;
	
	public Box(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public void setXCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	public int getXCoordinate() {
		return xCoordinate;
	}
	
	public void setYCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public int getYCoordinate() {
		return yCoordinate;
	}
}
