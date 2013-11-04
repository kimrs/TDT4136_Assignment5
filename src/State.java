import java.util.ArrayList;

public class State 
{
	private final char TAG_OCCUPIED = '*';
	private final char TAG_EMPTY = '.';
	
	private final int M = 5;
	private final int N = 5;
	private final int K = 2;
	
	private EggRandomizer er;
	
	public int[][] eggs;
	public int heuristic = -1;
	
	public int m;
	public int n;
	public int k;
	
	/**
	 * Empty constructor, sets m = 5, n = 5, k = 2
	 */
	public State()
	{
		m = M;
		n = N;
		k = K;
		
		init();
	}
	
	/**
	 * This constructor has not been fully testet yet
	 * @param m
	 * @param n
	 * @param k
	 */
	public State(int m, int n, int k)
	{
		this.m = m;
		this.n = n;
		this.k = k;
		
		init();
	}
	
	/**
	 * Sets m = n = mn, k = k
	 * @param nm
	 * @param k
	 */
	public State(int mn, int k)
	{
		this.m = mn;
		this.n = mn;
		this.k = k;
		
		init();
	}
	
	public State move(char x, char y, char direction)
	{
		int posX = Character.getNumericValue(x);
		int posY = Character.getNumericValue(y);
		return move(posX, posY, direction);
	}
	
	/**
	 * moves the egg at position x,y one space in either one of the directions
	 * n, s, w, e.
	 * If the egg does not exist, nothing happens
	 * @param x
	 * @param y
	 * @param direction
	 */
	public State move(int x, int y, char direction)
	{
		return move(x, y, direction, 1);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @param distance
	 * @return
	 */
	public State move(int x, int y, char direction, int distance)
	{	
		int indexOfEgg = HelpfullMethods.eggExists(x, y, eggs);
		if(indexOfEgg != -1)
		{
			switch(direction)
			{
			case 'n':
				y -= distance;
				break;
			case 's':
				y += distance;
				break;
			case 'e':
				x += distance;
				break;
			case 'w':
				x -= distance;
				break;
			}
		}
		
		if(x >= 0 && x < n && y >= 0 && y < m && HelpfullMethods.eggExists(x, y, eggs) == -1)
		{
			State newState = new State(n, m, k);
			newState.eggs = eggs.clone();
			newState.eggs[indexOfEgg] = new int[] { x, y };
			return newState;
		} else
			return null;
	}
	
	/**
	 * Prints a map of the state
	 */
	public void printGrid()
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
				if(HelpfullMethods.eggExists(j,i, eggs) != -1)
					System.out.print(TAG_OCCUPIED);
				else
					System.out.print(TAG_EMPTY);
			System.out.println();
		}
	}
	
	/**
	 * Clears the current grid and replaces it with input
	 * Only used for testing
	 * @param newEggs
	 */
	public void setGrid(int[][] newEggs)
	{
		eggs = newEggs;
	}
	
	/**
	 * called in every constructor
	 */
	private void init()
	{
		er = new EggCartonEggRandomizer_v2(n, k);
		eggs = er.randomizeEggs();
	}
	
	public void randomizeEggs()
	{
		eggs = er.randomizeEggs();
	}
}
