import java.util.Scanner;

public class GroupProject
{
	public static Scanner scanner = new Scanner(System.in);

	public enum MenuCode
	{
		MAIN_MENU, SELECT_NEIGHBORHOOD, UPDATE_NEIGHBORHOOD, NEIGHBORHOOD_FUNCTIONS, QUIT
	}

	public static final String tab = "     ";
	private static NeighborhoodGraph<LocationPoint> graph = null;
	
	public static void main(String[] args)
	{	
		boolean testing = false;		
		if (testing)
		{
			System.out.println("Testing............");
			tester.testDepthFirstTraversal();
			//tester.testBreadthFirstTraversal();			
			return;
		}
		
		MenuCode mainSelection = MenuCode.MAIN_MENU;
		do
		{
			switch (mainSelection)
			{
				case MAIN_MENU:
					mainSelection = MainMenu();
					break;
				case SELECT_NEIGHBORHOOD:  
					mainSelection = selectNeighborhood();
					break;
				case NEIGHBORHOOD_FUNCTIONS:
					mainSelection = neighborhoodFunctions(graph);
					break;
				case UPDATE_NEIGHBORHOOD:
					mainSelection = updateNeighborhoodMap(graph);
					break;
				default:
					break;
			}
		} while (mainSelection != MenuCode.QUIT);

		return;
	}

	public static MenuCode MainMenu()
	{
		int userInput;
		while (true)
		{
			System.out.println("\nMAIN MENU");
			System.out.println("---------");
			System.out.println(tab + "1.  Select a Neighborhood");
			System.out.println(tab + "2.  Quit");

			System.out.print("\nPlease enter your selection: ");
			userInput = scanner.nextInt();

			if (userInput == 1)
				return MenuCode.SELECT_NEIGHBORHOOD;
			else if (userInput == 2)
				return MenuCode.QUIT;
			else
				System.out.println(tab + "Your selection is invalid.\n");
		}
	}

	public static MenuCode selectNeighborhood()
	{
		int userInput;
		while (true)
		{
			System.out.println("\nSELECTING A NEIGHBORHOOD");
			System.out.println("------------------------");
			System.out.println(tab + "1.  Name of Neighborhood 1");
			System.out.println(tab + "2.  Name of Neighborhood 2");
			System.out.println(tab + "3.  Name of Neighborhood 3 (Bad Data)");
			System.out.println(tab + "4.  Name of Neighborhood 4 (File not existed)");
			System.out.println(tab + "5.  Main Menu");

			System.out.print("\nPlease enter your selection: ");
			userInput = scanner.nextInt();

			if ((userInput >= 1) && (userInput <= 2))
			{
				graph = GraphIO.getNeighborhoodMap("NeighborhoodMap1.txt");
				if (graph == null)
					return MenuCode.SELECT_NEIGHBORHOOD;
				else
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			} 
			else if (userInput == 3)
			{
				graph = GraphIO.getNeighborhoodMap("BadMapFile.txt");
				if (graph == null)
					return MenuCode.SELECT_NEIGHBORHOOD;
				else
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			} 
			else if (userInput == 4)
			{
				graph = GraphIO.getNeighborhoodMap("InvalidFileName.txt");
				if (graph == null)
					return MenuCode.SELECT_NEIGHBORHOOD;
				else
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			} 
			else if (userInput == 5)
				return MenuCode.MAIN_MENU;
			else
				System.out.println(tab + "Your selection is invalid.\n");
		}
	}

	public static MenuCode neighborhoodFunctions(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		if (graph == null) 
			throw new NullPointerException("NeighborhoodFunctions - Input parameter can not be null.");
		
		int userInput;
		while (true)
		{
			System.out.println("\nNEIGHBORHOOD MENU");
			System.out.println("-----------------");
			System.out.println(tab + "1.  Update Neighborhood Map");
			System.out.println(tab + "2.  Show Euler Circuit");
			System.out.println(tab + "3.  Show Breadth First Traversal");
			System.out.println(tab + "4.  Show Depth First Traversal");
			System.out.println(tab + "5.  Show Adjacent List Table");
			System.out.println(tab + "6.  Return to previous Menu");
			System.out.println(tab + "7.  Main Menu");

			System.out.print("\nPlease enter your selection: ");
			userInput = scanner.nextInt();

			if (userInput == 1)
				return MenuCode.UPDATE_NEIGHBORHOOD;
			else if (userInput == 2)
			{
				System.out.println(tab + "Processing your selection....\n");
				return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			}	
			else if (userInput == 3)
			{
				showBreadthFirstTraversal(inputGraph);
				return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			}	
			else if (userInput == 4)
			{
				showDepthFirstTraversal(inputGraph);
				return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			}	
			else if (userInput == 5)
			{
				showAdjacencyList(inputGraph);
				return MenuCode.NEIGHBORHOOD_FUNCTIONS;
			}				
			else if (userInput == 6)
				return MenuCode.SELECT_NEIGHBORHOOD;
			else if (userInput == 7)
				return MenuCode.MAIN_MENU;
			else
				System.out.println(tab + "Your selection is invalid.\n");
		}
	}	

	/*	 
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bao Chau
	 * 
	 */	 
	public static MenuCode updateNeighborhoodMap(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		int userInput;
		while (true)
		{
			System.out.println("\nUPDATE NEIGHHOOD MAP");
			System.out.println("--------------------");
			System.out.println(tab + "1.  Change Neighborhood Name");
			System.out.println(tab + "2.  Report New Street");
			System.out.println(tab + "3.  Report Closed Street");
			System.out.println(tab + "4.  Undo Last Update");
			System.out.println(tab + "5.  Save Neighborhood Map");
			System.out.println(tab + "6.  Return to previous Menu");
			System.out.println(tab + "7.  Main Menu");

			System.out.print("\nPlease enter your selection: ");
			userInput = scanner.nextInt();

			if (userInput == 1)
			{
				changeNeighborhoodName(inputGraph);
				return MenuCode.UPDATE_NEIGHBORHOOD;
			}
			else if ((userInput >= 2) && (userInput <= 5))
				System.out.println(tab + "Processing your selection....\n");
			else if (userInput == 6)
				return MenuCode.SELECT_NEIGHBORHOOD;
			else if (userInput == 7)
				return MenuCode.MAIN_MENU;
			else
				System.out.println(tab + "Your selection is invalid.\n");
		}
	}

	public static void showAdjacencyList(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		System.out.println();	
		System.out.println(tab + "Adjacency List Output:");
		graph.showAdjTable();
		System.out.println();			
	}
	
	public static void showBreadthFirstTraversal(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		String input;
		LocationPoint startPoint;
		
		System.out.print("Please enter the start location: ");
		if (scanner.hasNext()) scanner.nextLine();
		input = scanner.nextLine().trim();
		
		startPoint = graph.findLocationByName(input);
		if (startPoint != null)
		{
			System.out.println();	
			System.out.println(tab + "Breadth First Traversal Output:");
			System.out.print(tab);
			LocationPoint startElement = graph.findLocationByName("A");
			graph.breadthFirstTraversal(startElement, new LocationPointVisitor());
			System.out.println();				
		}
		else
			System.out.println(tab + "The location entry is invalid.");
	}	

	public static void showDepthFirstTraversal(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		String input;
		LocationPoint startPoint;
		
		System.out.print("Please enter the start location: ");
		if (scanner.hasNext()) scanner.nextLine();
		input = scanner.nextLine().trim();
		
		startPoint = graph.findLocationByName(input);
		if (startPoint != null)
		{
			System.out.println();	
			System.out.println(tab + "Depth First Traversal Output:");
			System.out.print(tab);
			graph.depthFirstTraversal(startPoint, new LocationPointVisitor());
			System.out.println();	
		}
		else
			System.out.println(tab + "The location entry is invalid.");
	}	
	
	public static void changeNeighborhoodName(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		String newName;
		System.out.println("Please enter the new name: ");
		
		if (scanner.hasNext()) scanner.nextLine();
		newName = scanner.nextLine().trim();
		
		if (newName.length() == 0)
			System.out.println("The input name is blank.");
		else
		{
			inputGraph.setNeighborhoodName(newName);
			System.out.println("New neighborhood name is: " + inputGraph.getNeighborhoodName());
		}
	}
}
