
public class MinConflictLocalSearchPuzzleSolver implements PuzzleSolver 
{
	private static final int MAX_ATTEMPTS = 6;
	private int previousEvaluation = -1;
	private int stagnationCounter = 0;

	private ObjectiveFunction of;
	
	private boolean verbose = false;
	
	public MinConflictLocalSearchPuzzleSolver(ObjectiveFunction of)
	{
		this.of = of;
	}
	
	@Override
	public State solve(State state) 
	{
		boolean done = false;
		int[] column = new int[state.n];
		int[] indexes = new int[state.k];
		
		while(!done)
		{
			for(int i = 0; i < state.n; i++)
			{
				for(int j = 0; j < state.n; j++)
					column[j] = of.evaluate(state, i, j);
				
				//variable to advance the min index if the first one actually was 0
				boolean wasZero = true;
				
				for(int j = 0; j < state.k; j++)
				{
					int indexOfMin = 0;
					int min = column[0];
					//This code is to avoid any error because the min value existed in 0
					if(wasZero && j >= 1)
					{
						indexOfMin = 1;
						min = column[1];
						//we do not want this to happen again
						wasZero = false;
					}

					for(int k = 1; k < state.n; k++)
						if(min >= column[k])
						{
							if(j == 0 || k != indexes[j - 1])
							{
								min = column[k];
								indexOfMin = k;
								wasZero = false;
							}
						}
					
					indexes[j] = indexOfMin;
				}
				/*
				 * k preferred locations in the i'th row are now found.  
				 */
				int nextIndex = 0;
				for(int[] egg : state.eggs)
				{
					if(egg[0] == i)
					{
						egg[1] = indexes[nextIndex];
						nextIndex++;
						if(nextIndex == state.k)
							break;
					}
				}
				System.out.print("");
				if(verbose)
				{
					System.out.print("number of conflicts for each domain in the first column: \n|");
					for(int conflict : column)
						System.out.print(conflict + " |");
					System.out.println();
					System.out.println("state after movement: ");
					state.printGrid();
					System.out.println("-----------------");
					System.out.println("solution: ");
					verbose = false;
				}
			}
//			state.printGrid();
//			System.out.println("---------------------");
			int evaluation = of.evaluate(state);
			
			if(evaluation == 0)
			{
				done = true;
				break;
			}
			
			if(evaluation == previousEvaluation)
			{
				stagnationCounter++;
				if(stagnationCounter == MAX_ATTEMPTS)
				{
					state.randomizeEggs();
					stagnationCounter = 0;
				}
			}
			else
			{
				previousEvaluation = evaluation;
				stagnationCounter = 0;
			}
		}
		return state;
	}

	@Override
	public void setVerbose(boolean v) {
		verbose = v;
	}
}
