package nl.svenskpusselsolver.solver;

import nl.svenskpusselsolver.dataobjects.Box;

public interface Solver {
	public Box[][] solvePuzzle(Box[][] grid);
}