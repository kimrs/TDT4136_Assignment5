import java.util.ArrayList;


public class SimulatedAnnealingPuzzleSolver implements PuzzleSolver
{
	private final int MAX_ATTEMPTS = 20;
	
	private ObjectiveFunction of;
	
	public SimulatedAnnealingPuzzleSolver(ObjectiveFunction of)
	{
		this.of = of;
	}
	
	@Override
	public State solve(State state) 
	{
		int attempts = 0;
		boolean solved = false;
		while(!solved)
		{
			int heuristic = of.evaluate(state);
			if(heuristic == 0)
				solved = true;
			else
			{
				ArrayList<State> neighbors = generateNeighbors(state);
				boolean stateReplaced = false;
				for(State neighbor : neighbors)
					if(of.evaluate(neighbor) < heuristic)
					{
						state = neighbor;
						stateReplaced = true;
						break;
					}
				if(!stateReplaced && attempts < MAX_ATTEMPTS)
				{
					/*
					 * Before we generate a new map. We try out one neighbor with the same
					 * heuristic value as this
					 */
					for(State neighbor : neighbors)
						if(of.evaluate(neighbor) == heuristic)
						{
							state = neighbor;
							stateReplaced = true;
							attempts++;
							break;
						}	
				}
				
				/*
				 * if we still have not yet found a valid move, it is time to randomize
				 * and start over. 
				 */
				if(!stateReplaced)
				{
					state.randomizeEggs();
					heuristic = of.evaluate(state);
					attempts = 0;
				}	
			}
		}
		return state;
	}
	
	public ArrayList<State> generateNeighbors(State state)
	{
		ArrayList<State> neighbors = new ArrayList<>();
		for(int[] egg : state.eggs)
		{
			State[] immediateNeighbors = new State[]{ 
					state.move(egg[0], egg[1], 'n'),
					state.move(egg[0], egg[1], 's'),
					state.move(egg[0], egg[1], 'e'),
					state.move(egg[0], egg[1], 'w'),
			};
			for(State s : immediateNeighbors)
				if(s != null)
					neighbors.add(s);
		}
		return neighbors;
	}

	@Override
	public void setVerbose(boolean v) {
		// TODO Auto-generated method stub
		
	}

}
