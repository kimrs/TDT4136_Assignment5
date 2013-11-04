
public class HelpfullMethods 
{
	/**
	 * 
	 * @param x
	 * @param y
	 * @return index of the egg, -1 if not
	 */
	public static int eggExists(int x, int y, int[][] eggs)
	{
		for(int i = 0; i < eggs.length; i++)
			if(eggs[i][0] == x && eggs[i][1] == y)
				return i;
		//if we have come this far, the egg does not exists.
		return -1;
	}
	
	public static boolean[][] convertStateToBooleanArray(State state)
	{
		boolean[][] spacesOccupied = new boolean[state.n][state.m];
		for(int i = 0; i < state.n; i++)
			for(int j = 0; j < state.m; j++)
			{
				spacesOccupied[i][j] = false;
				for(int[] egg : state.eggs)
					if(egg[0] == i && egg[1] == j)
						spacesOccupied[i][j] = true;
			}
		return spacesOccupied;
	}

}
