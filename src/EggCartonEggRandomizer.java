import java.util.Random;


public class EggCartonEggRandomizer implements EggRandomizer
{
	private int n;
	private int k;
	private Random rand;
	
	/**
	 * Egg randomizing algorithm used for assignment 4
	 * @param nk
	 */
	public EggCartonEggRandomizer(int n, int k)
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
		
		int eggsCount = 0;
		while(eggsCount != n*k)
		{
			int x = rand.nextInt(n);
			int y = rand.nextInt(n);
			
			if(HelpfullMethods.eggExists(x, y, eggs) == -1)
			{
				eggs[eggsCount][0] = x;
				eggs[eggsCount][1] = y;
				eggsCount++;
			}
		}
		return eggs;
	}
}
