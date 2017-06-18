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
	private static String mapFileName = "";
	
	public static void main(String[] args)
	{	
		boolean testing = true;		
		if (testing)
		{
			System.out.println("Testing............");
			tester.testGetEulerCircuit();
			//System.out.println(found);
			//tester.testIsValidEdge();
			//if (found == true)
			
			return;
		}
		
		MenuCode mainSelection = MenuCode.MAIN_MENU;
		do
		{			
			try
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
			}
			catch (Exception e)
			{
				System.out.println(tab + "Runtime error encoutered.");
				System.out.println(tab + "Return to main menu.");
				mainSelection = MenuCode.MAIN_MENU;
			}
			
		} while (mainSelection != MenuCode.QUIT);
		
		return;
	}

	public static MenuCode MainMenu()
	{
		StringBuilder menu = new StringBuilder(70);
		
		//Build the menu
		menu.append("\n\nMAIN MENU\n");
		menu.append("---------\n");		
		menu.append(tab + "1.  Select a Neighborhood\n");
		menu.append(tab + "2.  Quit\n");
		menu.trimToSize();
		
		while (true)
		{
			System.out.println(menu); //Show menu
				
			switch (getIntegerInputFromUser())
			{
				case 1:			
					return MenuCode.SELECT_NEIGHBORHOOD;

				case 2:
					return MenuCode.QUIT;	
					
				default:
					System.out.println(tab + "Your selection is invalid.");
					break;
			}
		}
	}

	public static MenuCode selectNeighborhood()
	{
		String[][] mapList = GraphIO.getNeighborhoodList("MapList.txt");		
		if (mapList == null) return MenuCode.MAIN_MENU;
		
		int userInput, numberOfRecords;
		StringBuilder menu = new StringBuilder(1000);
		numberOfRecords = mapList.length;
		
		//Build the menu
		menu.append("\n\nSELECTING A NEIGHBORHOOD\n");
		menu.append("------------------------\n");		
		for (int i = 0; i < numberOfRecords; i++)
			menu.append(String.format("%s%d. %s (%s)\n", tab, i + 1, mapList[i][0], mapList[i][1]));
		
		menu.append(String.format("%s%d. Main Menu\n", tab, numberOfRecords + 1));
		menu.trimToSize();
		
		while (true)
		{						
			System.out.println(menu); //Show menu
			userInput = getIntegerInputFromUser();
			
			if ((userInput >= 1) && (userInput <= numberOfRecords))
			{
				mapFileName = mapList[userInput - 1][1];
				graph = GraphIO.getNeighborhoodMap(mapFileName);
				
				if (graph == null)
					return MenuCode.SELECT_NEIGHBORHOOD;
				else
				{
					graph.clearUndoStack();
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
				}
			} 			
			else if (userInput ==  numberOfRecords + 1)
				return MenuCode.MAIN_MENU;
			else
				System.out.println(tab + "Your selection is invalid.\n");
		}
	}

	public static MenuCode neighborhoodFunctions(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		if (inputGraph == null) 
			throw new NullPointerException("NeighborhoodFunctions - Input parameter can not be null.");		
		
		StringBuilder menu = new StringBuilder(400);
		
		//Build the menu
		menu.append(String.format("\n\nNEIGHBORHOOD MENU FOR %s\n", inputGraph.getNeighborhoodName().toUpperCase()));
		menu.append("-------------------------------------\n");
		menu.append(tab + "1.  Update Neighborhood Map\n");
		menu.append(tab + "2.  Show Euler Circuit\n");
		menu.append(tab + "3.  Show Breadth First Traversal\n");
		menu.append(tab + "4.  Show Depth First Traversal\n");
		menu.append(tab + "5.  Show Adjacency Table\n");
		menu.append(tab + "6.  Return to previous Menu\n");
		menu.append(tab + "7.  Main Menu\n");		
		menu.trimToSize();
		
		while (true)
		{					
			System.out.println(menu); //Show menu
			
			switch (getIntegerInputFromUser())
			{
				case 1:			
					return MenuCode.UPDATE_NEIGHBORHOOD;
					
				case 2:
					tester.testGetEulerCircuit();
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
					
				case 3:
					showBreadthFirstTraversal(inputGraph);
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
					
				case 4:
					showDepthFirstTraversal(inputGraph);
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
					
				case 5:
					showAdjacencyTable(inputGraph);
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
					
				case 6:
					return MenuCode.SELECT_NEIGHBORHOOD;
					
				case 7:
					return MenuCode.MAIN_MENU;
					
				default:
					System.out.println(tab + "Your selection is invalid.");
					break;
			}
		}
	}	

	public static MenuCode updateNeighborhoodMap(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		if (inputGraph == null) 
			throw new NullPointerException("UpdateNeighborhoodMap - Input parameter can not be null.");		
		
		StringBuilder menu = new StringBuilder(350);		
		
		//Build the menu
		menu.append(String.format("\n\nUPDATE NEIGHHOOD MAP FOR %s\n", inputGraph.getNeighborhoodName().toUpperCase()));
		menu.append("----------------------------------\n");
		menu.append(tab + "1.  Change Neighborhood Name\n");
		menu.append(tab + "2.  Report/Add New Street\n");
		menu.append(tab + "3.  Report/Remove Closed Street\n");
		menu.append(tab + "4.  Undo Last Update\n");
		menu.append(tab + "5.  Save Neighborhood Map\n");
		menu.append(tab + "6.  Show Adjacency Table\n");
		menu.append(tab + "7.  Return to previous Menu\n");
		menu.append(tab + "8.  Main Menu\n");
		menu.trimToSize();
		
		while (true)
		{		
			System.out.println(menu); //Show menu
			
			switch (getIntegerInputFromUser())
			{
				case 1:			
					changeNeighborhoodName(inputGraph);
					return MenuCode.UPDATE_NEIGHBORHOOD;
					
				case 2:
					addStreet(inputGraph);
					break;
					
				case 3:
					removeStreet(inputGraph);
					break;
					
				case 4:
					undoLastUpdate(inputGraph);
					return MenuCode.UPDATE_NEIGHBORHOOD;
					
				case 5:
					GraphIO.saveGraphToFile(inputGraph, mapFileName);
					break;
					
				case 6:
					showAdjacencyTable(inputGraph);
					return MenuCode.UPDATE_NEIGHBORHOOD;
					
				case 7:
					return MenuCode.NEIGHBORHOOD_FUNCTIONS;
					
				case 8:
					return MenuCode.MAIN_MENU;
					
				default:
					System.out.println(tab + "Your selection is invalid.");
					break;
			}
		}
	}

	//Get an input from user. If it is a valid integer, return the integer value.
	//If user did not enter a valid integer, return -1.
	public static int getIntegerInputFromUser()
	{
		int userInput = -1;
		try
		{
			System.out.print("Please enter your selection: ");
			userInput = scanner.nextInt();
			scanner.nextLine();
		}
		catch (Exception e)
		{
			if (scanner.hasNext()) scanner.nextLine();
			userInput = -1;
		}
		return userInput;
	}
	
	public static void showAdjacencyTable(NeighborhoodGraph<LocationPoint> inputGraph)
	{		
		System.out.println("\n" + tab + "Adjacency List Output:");
		graph.showAdjTable();		
	}
	
	public static void showBreadthFirstTraversal(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		LocationPoint startPoint = AskUserForLocation("Please enter the start location: ");
		if (startPoint != null)
		{
			System.out.print("\n" + tab + "Depth First Traversal Output:\n" + tab);
			graph.breadthFirstTraversal(startPoint, new LocationPointVisitor());
			System.out.println();	
		}
	}	

	public static void showDepthFirstTraversal(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		LocationPoint startPoint = AskUserForLocation("Please enter the start location: ");
		if (startPoint != null)
		{
			System.out.print("\n" + tab + "Depth First Traversal Output:\n" + tab);
			graph.depthFirstTraversal(startPoint, new LocationPointVisitor());
			System.out.println();	
		}
	}	
	
	public static void addStreet(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		LocationPoint startPoint, endLocation;
		
		startPoint = AskUserForLocation("Please enter the start location: ");
		if (startPoint == null) return;
		
		endLocation = AskUserForLocation("Please enter the end location: ");
		if (endLocation == null) return;
		
		inputGraph.addStreet(startPoint, endLocation);
		
		System.out.print(tab + "Report/Add New Street is completed.");	
		return;
	}
	
	public static void removeStreet(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		LocationPoint startPoint, endLocation;
		
		startPoint = AskUserForLocation("Please enter the start location: ");
		if (startPoint == null) return;
		
		endLocation = AskUserForLocation("Please enter the end location: ");
		if (endLocation == null) return;
		
		inputGraph.removeStreet(startPoint, endLocation);
		
		System.out.print(tab + "Report/Remove Closed Street is completed.");		
		return;
	}
	
	public static LocationPoint AskUserForLocation(String prompt)
	{
		String input = "";
		LocationPoint location;
		
		System.out.print(prompt);
		while (input.equals(""))
			input = scanner.nextLine().trim();
		
		location = graph.findLocationByName(input);
		
		if (location == null)
			System.out.println(tab + "The location entry is invalid.");
		
		return location;
	}	
	
	public static void changeNeighborhoodName(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		String newName = "";
		System.out.print("Please enter the new name: ");
		while (newName.equals(""))
			newName = scanner.nextLine().trim();
		
		inputGraph.setNeighborhoodName(newName);									
		System.out.println(tab + "New neighborhood name is: " + inputGraph.getNeighborhoodName());
	}
	
	public static void undoLastUpdate(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		if (inputGraph.undoLastUpdate())
			System.out.println(tab + "Undo completed.");
		else
			System.out.println(tab + "Undo failed. Undo stack is empty.");
	}
}
