import java.util.ArrayList;
import java.util.Scanner;

public class GroupProject
{
	public static Scanner scanner = new Scanner(System.in);

	private enum MenuCode
	{
		MAIN_MENU, SELECT_NEIGHBORHOOD, UPDATE_NEIGHBORHOOD, NEIGHBORHOOD_FUNCTIONS, QUIT
	}

	public static final String tab = "     ";
	private static NeighborhoodGraph<LocationPoint> graph = null;
	private static String mapFileName = "";


	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	public static void main(String[] args)
	{	
		boolean testing = false;		
		if (testing)
		{
			System.out.println("Testing............");
			//Call test function.
			tester.testHasEulerCircuit();
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

	/*
	 * Coder: Bao Chau
	 */
	private static MenuCode MainMenu()
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

	/*
	 * Coder: Bao Chau
	 */
	private static MenuCode selectNeighborhood()
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

	/*
	 * Coder: Bao Chau
	 */
	private static MenuCode neighborhoodFunctions(NeighborhoodGraph<LocationPoint> inputGraph)
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
					showEulerCircuit(inputGraph);
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

	/*
	 * Coder: Bao Chau
	 */
	private static MenuCode updateNeighborhoodMap(NeighborhoodGraph<LocationPoint> inputGraph)
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
	/*
	 * Coder: Bao Chau
	 */
	private static int getIntegerInputFromUser()
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
	
	/*
	 * Coder: Bao Chau
	 */
	private static void showAdjacencyTable(NeighborhoodGraph<LocationPoint> inputGraph)
	{		
		System.out.println("\n" + tab + "Adjacency List Output:");
		graph.showAdjTable();		
	}
	
	/*
	 * Coder: Bao Chau
	 */
	private static void showEulerCircuit(NeighborhoodGraph<LocationPoint> inputGraph) {
		LocationPoint location;
		boolean hasResult = false;
		String response = "", fileName = "";
		LocationPoint startLocation = AskUserForLocation("Please enter the start location: ");
		ArrayList<String> eulerDisplayList;
		
		if (startLocation != null) {
			LinkedStack<LocationPoint> eulerCiruitStack = inputGraph.getEulerCircuit(startLocation);
			hasResult = !eulerCiruitStack.isEmpty();
			
			eulerDisplayList = convertEulerOutput(inputGraph.getNeighborhoodName(), eulerCiruitStack);
			
			for (String line : eulerDisplayList)
				System.out.println(tab + line);

			if (hasResult)
			{
				System.out.print("\nWould you like to save the output?\n(Enter Y for yes, other for no): ");

				while (response.equals(""))
					response = scanner.nextLine().trim().toUpperCase();

				if (response.equals("Y") || (response.equals("YES"))) {
					System.out.print("Please enter to output file name: ");
					
					while (fileName.equals("")) {
						fileName = scanner.nextLine().trim();
					}

					GraphIO.saveTextToFile(fileName, eulerDisplayList);
				}
			}
		}
	}
	
	/*
	 * Coder: Bao Chau
	 */
	private static ArrayList<String> convertEulerOutput(String neighborhoodName, LinkedStack<LocationPoint> eulerCiruitStack)
	{
		LocationPoint location;
		ArrayList<String> eulerList = new ArrayList<>();
		
		//Add title row
		eulerList.add(String.format("Euler Circuit output for \"%s\" neighborhood: ", neighborhoodName));

		if (!eulerCiruitStack.isEmpty()) {			
			location = eulerCiruitStack.pop();			
			eulerList.add(String.format("Starting at %s (%s)", location.getName(), location.getDescription()));		

			while (!eulerCiruitStack.isEmpty()) 
			{
				location = eulerCiruitStack.pop();
				if (!eulerCiruitStack.isEmpty())
					eulerList.add(String.format("%sGo to %s (%s)", tab, location.getName(), location.getDescription()));
				else
					eulerList.add(String.format("Go back to starting location at %s (%s)", location.getName(), location.getDescription()));
			}
		} else 
		{
			eulerList.add("Your graph configuration does not have an Euler Circuit.");
		}
		return eulerList;
	}
	
	/*
	 * Coder: Bao Chau
	 */
	private static void showBreadthFirstTraversal(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		LocationPoint startPoint = AskUserForLocation("Please enter the start location: ");
		if (startPoint != null)
		{
			System.out.print("\n" + tab + "Breadth First Traversal Output:\n" + tab);
			graph.breadthFirstTraversal(startPoint, new LocationPointVisitor());
			System.out.println();	
		}
	}	

	/*
	 * Coder: Bao Chau
	 */
	private static void showDepthFirstTraversal(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		LocationPoint startPoint = AskUserForLocation("Please enter the start location: ");
		if (startPoint != null)
		{
			System.out.print("\n" + tab + "Depth First Traversal Output:\n" + tab);
			graph.depthFirstTraversal(startPoint, new LocationPointVisitor());
			System.out.println();	
		}
	}	
	
	/*
	 * Coder: Bao Chau
	 */
	private static void addStreet(NeighborhoodGraph<LocationPoint> inputGraph)
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
	
	/*
	 * Coder: Bao Chau
	 */
	private static void removeStreet(NeighborhoodGraph<LocationPoint> inputGraph)
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
	
	/*
	 * Coder: Bao Chau
	 */
	private static LocationPoint AskUserForLocation(String prompt)
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
	
	/*
	 * Coder: Bao Chau
	 */
	private static void changeNeighborhoodName(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		String newName = "";
		System.out.print("Please enter the new name: ");
		while (newName.equals(""))
			newName = scanner.nextLine().trim();
		
		inputGraph.setNeighborhoodName(newName);									
		System.out.println(tab + "New neighborhood name is: " + inputGraph.getNeighborhoodName());
	}
	
	/*
	 * Coder: Bao Chau
	 */
	private static void undoLastUpdate(NeighborhoodGraph<LocationPoint> inputGraph)
	{
		if (inputGraph.undoLastUpdate())
			System.out.println(tab + "Undo completed.");
		else
			System.out.println(tab + "Undo failed. Undo stack is empty.");
	}
}
