import java.util.Random;


public class EggCartonEggRandomizer_v2 implements EggRandomizer
{
	private int n;
	private int k;
	private Random rand;
	
	/**
	 * Improved egg randomizing algorithm, this one allows a maximum of k queens on each column
	 * @param nk
	 */
	public EggCartonEggRandomizer_v2(int n, int k)
	{
		this.n = n;
		this.k = k;
		rand = new Random();
	}
	
	@Override
	public int[][] randomizeEggs() 
	{
		//inserts eggs
		int[][] eggs = new int[n*k][2];
		
		//for each row in a carton
		int eggIndex = 0;
		for(int i = 0; i < n; i++)
		{
			//need k different values between 0 and n
			int[] mountedQueens = recursiveQueenIndexer();
			for(int j = 0; j < mountedQueens.length; j++)
			{
				eggs[eggIndex] = new int[]{ i, mountedQueens[j] };
				eggIndex++;
			}
		}
		return eggs;
	}
	
	private int[] recursiveQueenIndexer()
	{
		int[] l = new int[k];
		for(int i = 0; i < l.length; i++)
			l[i] = -1;
		return recursiveQueenIndexer(l);
	}
	
	private int[] recursiveQueenIndexer(int[] precomputedIndexes)
	{
		int availableIndex = -1;
		for(int i = 0; i < precomputedIndexes.length; i++)
			if(precomputedIndexes[i] == -1)
			{
				availableIndex = i;
				break;
			}
		if(availableIndex == -1)
			return precomputedIndexes;
		else
		{
			int potentialQueenIndex = rand.nextInt(n);
			boolean exists = false;
			for(int i : precomputedIndexes)
			{
				if(potentialQueenIndex == i)
				{
					exists = true;
					break;
				}
			}
			if(!exists)
				precomputedIndexes[availableIndex] = potentialQueenIndex;
			return recursiveQueenIndexer(precomputedIndexes);
		}
				
	}
}
