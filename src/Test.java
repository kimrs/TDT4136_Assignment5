import java.util.Scanner;


public class Test 
{
	private final static char TAG_VERBOSE = 'v';
	private final static char TAG_PRINT 	= 'p';
	private final static char TAG_PRINT_OBJECTIVE = 'o';
	private final static char TAG_HELP 	= 'h';
	private final static char TAG_QUIT = 'q';
	private final static char TAG_NORTH 	= 'n';
	private final static char TAG_SOUTH 	= 's';
	private final static char TAG_EAST 	= 'e';
	private final static char TAG_WEST 	= 'w';
	
	private static Scanner scan;
	
	public static void main(String[] args)
	{
		int k = 1, n = 5;
		boolean testAlgorithm = false;
		boolean useOldSolution = false;
		boolean verbose = false;
		ObjectiveFunction of = new EggCartonObjectiveFunction();
		
		if(args.length == 0)
			printInfo();
		for(String arg : args)
		{
			if(arg.charAt(0) == 'k')
				k = Integer.parseInt(arg.substring(1));
			else if(arg.charAt(0) == 'n')
				n = Integer.parseInt(arg.substring(1));
			else if(arg.charAt(0) == 'g')
				testAlgorithm = false;
			else if(arg.charAt(0) == 'a')
				testAlgorithm = true;
			else if(arg.charAt(0) == 'o')
				useOldSolution = true;
			else if(arg.charAt(0) == 'v')
				verbose = true;
			else
				printInfo();			
		}
		
		State start = new State(n, k);
		if(testAlgorithm)
		{
			PuzzleSolver ps;
			System.out.println("Started with state: ");
			start.printGrid();
			System.out.println("-----------------\nsolving: ");
	 		if(useOldSolution)
				ps = new SimulatedAnnealingPuzzleSolver(of);
			else
				ps = new MinConflictLocalSearchPuzzleSolver(of);
	 		ps.setVerbose(verbose);
			State solved = ps.solve(start);
			solved.printGrid();
		} else
		{
			printHelp();
			playGame(start);
		}
	}
	
	private static void printInfo()
	{
		System.out.println("There exists four different arguments: k, n, g and a" +
				". to play the game, use the argument g, to test the algoithm use the argument" +
				" a. To set the gridsize, use the argument n. To set the maximum allowed number of " +
				"eggs in a line use the argument k. The following sets maximum eggs in a line to 2, " +
				"the grid size is set to 5 and the program provides the solution: \nTest a k2 n5");
	}
	
	private static void playGame(State grid)
	{
		playGame(grid, new EggCartonObjectiveFunction());
	}
	
	private static void playGame(State grid, ObjectiveFunction of)
	{
		if(scan == null)
			scan = new Scanner(System.in);
		//System.out.println("--- game started ---");
		if(of.evaluate(grid) != 0)
		{
			String[] option = scan.nextLine().toLowerCase().split(" ");

			char[] optionLetter = new char[option.length];
			for(int i = 0; i < option.length; i++)
				optionLetter[i] = option[i].charAt(0);
			
			if(optionLetter[0] == TAG_NORTH || optionLetter[0] == TAG_SOUTH ||
					optionLetter[0] == TAG_EAST || optionLetter[0] == TAG_WEST)	
			{
				try
				{
				playGame(
						grid.move(optionLetter[1], optionLetter[2], optionLetter[0])	
						);
				} catch (IndexOutOfBoundsException e)
				{
					System.out.println("Out of bounds. Queen not moved");
					playGame(grid);
				} catch (NullPointerException npe)
				{
					System.out.println("Out of bounds. Queen not moved");
					playGame(grid);
				}
			}
			
			switch(optionLetter[0])
			{
			case TAG_HELP:
				printHelp();
				playGame(grid);
				break;
			case TAG_PRINT:
				grid.printGrid();
				playGame(grid);
				break;
			case TAG_PRINT_OBJECTIVE:
				System.out.println( "OF: " + 
				of.evaluate(grid, Character.getNumericValue(optionLetter[1]), 
						Character.getNumericValue(optionLetter[2])));
				playGame(grid);
				break;
				
			case TAG_QUIT:
				scan.close();
				break;
			}		
		} else
		{
			System.out.println("--- WINNER! ---");
			scan.close();
		}
	}
	
	private static void printHelp()
	{
		System.out.println("--- helptext ---");
		System.out.println("For helptext type 'h'");
		System.out.println("To print carton, type 'p'");
		System.out.println("To quit, type 'q'");
		System.out.println("To move egg, type [direction] [posX] [posY]");
		System.out.println("To move egg at position 3,4 one step north type");
		System.out.println("n 3 4");
	}
}
