package nl.svenskpusselsolver.solver;

import nl.svenskpusselsolver.dataobjects.Box;

/**
 * The interface for all solvers for Zweedse Puzzels.
 */
public interface Solver {
	/**
	 * Will try to solve the puzzle specified in the grid and return the solved grid.
	 * @param grid Grid to solve.
	 * @return Solved grid.
	 */
	public Box[][] solvePuzzle(Box[][] grid);
}