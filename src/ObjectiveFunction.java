/**
 * Algorithm to see if current state is a winner
 * @author kimkong
 *
 */
public interface ObjectiveFunction 
{
	/**
	 * Strategy pattern is used to separate the checkIfWinning algorithm,
	 * this is because this algorithm is potentially complex, and should be
	 * easy to replace
	 */
	
	/**
	 * Evaluates the whole state
	 * @param state
	 * @return
	 */
	public int evaluate(State state);
	
	/**
	 * evaluates a single domain
	 * @param state
	 * @param x
	 * @param y
	 * @return
	 */
	public int evaluate(State state, int x, int y);
}
