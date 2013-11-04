
public interface PuzzleSolver {
	
	/**
	 * Returns a solved puzzle
	 * @param state
	 * @return
	 */
	public State solve(State state);
	public void setVerbose(boolean v);
}
