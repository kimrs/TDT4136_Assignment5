
public class EggCartonObjectiveFunction implements ObjectiveFunction
{
	private static enum Direction { UL_LR, UR_LL, LR_UL, LL_UR };
	
	@Override
	public int evaluate(State state, int x, int y) 
	{
		boolean[][] occupiedSpaces = HelpfullMethods.convertStateToBooleanArray(state);
		int horizontalEggsCounter = 0;
		int diagonalEggsCounter = 0;
		int iDiagonalEggsCounter = 0;
		int violations = 0;
		int edgeX = x - y;
		int edgeY = x + y;
		
		diagonalEggsCounter += evaluateDiagonals(edgeX, 0, -1, state.n, Direction.LL_UR, occupiedSpaces);
		iDiagonalEggsCounter += evaluateDiagonals(0, edgeY, -1, state.n, Direction.UL_LR, occupiedSpaces);
		
		//Horizontal spaces
		for(int i = 0; i < state.n; i++)
			if(occupiedSpaces[i][y])
				horizontalEggsCounter++;
		//Not counting the one at pos (x,y)
		if(occupiedSpaces[x][y])
		{
			diagonalEggsCounter--; //It will have been counted three times by now
			iDiagonalEggsCounter--;
			horizontalEggsCounter--;
		}
		
			violations += diagonalEggsCounter;// 	- (state.k - 1);
			violations += iDiagonalEggsCounter;// 	- (state.k - 1);
			violations += horizontalEggsCounter;//  - (state.k - 1);
	
		return violations;
	}
	
	@Override
	public int evaluate(State state) 
	{	
		boolean[][] occupiedSpaces = HelpfullMethods.convertStateToBooleanArray(state);
		int violations = 0;

		for(int i = 0; i < state.eggs.length; i ++)
		{
			violations += evaluate(state, state.eggs[i][0], state.eggs[i][1]);
		}
				
		return violations / 2; //violations will be counted twice. Once for each egg involving the violation.
	}
	
	/**
	 * returns the amount of illegal eggs in the diagonals 
	 * @param x
	 * @param y
	 * @param k
	 * @param n if -n, then inverse
	 * @param occupiedSpaces
	 * @return
	 */
	private int evaluateDiagonals(int x, int y, int k, int n, Direction d,
			boolean[][] occupiedSpaces)
	{
		return evaluateDiagonals(x, y, k, n, d, occupiedSpaces, 0);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param k
	 * @param n
	 * @param d
	 * @param occupiedSpaces
	 * @param eggCounter eggs counted so far
	 * @return
	 */
	private int evaluateDiagonals(int x, int y, int k, int n, Direction d,
			boolean[][] occupiedSpaces, int eggCounter)
	{
		if(k != -1 && eggCounter > k)
			return eggCounter - k;
		
		else if(x >= -n && x < n*2 && y >= -n && y < n*2)
		{
			try
			{
				if(occupiedSpaces[x][y])
					eggCounter++;
			} catch (IndexOutOfBoundsException e) //This exception should only be raised when k = -1
			{
				//Nothing needs to happen. If it's out of bounds, it will not increase eggCounter.
			}
			if(d == Direction.UL_LR) //upper left to lower right
				return(evaluateDiagonals(x + 1, y - 1, k, n, d, occupiedSpaces, eggCounter));
			else if(d == Direction.LL_UR) //lower left to upper right
				return(evaluateDiagonals(x + 1, y + 1, k, n, d, occupiedSpaces, eggCounter));
			else if(d == Direction.UR_LL) //upper_right to lower left
				return(evaluateDiagonals(x - 1, y + 1, k, n, d, occupiedSpaces, eggCounter));
			else //lower right to upper left
				return(evaluateDiagonals(x - 1, y - 1, k, n, d, occupiedSpaces, eggCounter));
		}
		return eggCounter;
	}
}
