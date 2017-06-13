import java.util.Scanner;
public class GroupProject
{
	public static Scanner scanner = new Scanner( System.in );
	public enum SelectionCode { MAIN_MENU, SELECT_NEIGHBORHOOD, 
		UPDATE_NEIGHBORHOOD, NEIGHBORHOOD_FUNCTIONS, QUIT }
	public static final String tab = "     ";
	
	// ------- main --------------
	public static void main(String[] args)
	{
		SelectionCode mainSelection = SelectionCode.MAIN_MENU;
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
					mainSelection = neighborhoodFunctions();
					break;
				case UPDATE_NEIGHBORHOOD:
					mainSelection = updateNeighborhoodMap();
					break;
				default:
					break;
			}
		} while (mainSelection != SelectionCode.QUIT);
		
		return;
	}
	
	public static SelectionCode MainMenu()
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
				return SelectionCode.SELECT_NEIGHBORHOOD;
			else if (userInput == 2)
				return SelectionCode.QUIT;			
			else
				System.out.println(tab + "Your selection is invalid.\n");		
		}
	}
	
	public static SelectionCode selectNeighborhood()
	{	
		int userInput;
		while (true)
		{
			System.out.println("\nSELECTING A NEIGHBORHOOD");
			System.out.println("------------------------");
			System.out.println(tab + "1.  Name of Neighborhood 1");
			System.out.println(tab + "2.  Name of Neighborhood 2");
			System.out.println(tab + "3.  Name of Neighborhood 3");
			System.out.println(tab + "4.  Name of Neighborhood 4");
			System.out.println(tab + "5.  Main Menu");			
			
			System.out.print("\nPlease enter your selection: ");			
			userInput = scanner.nextInt();			
			
			if ((userInput >= 1) && (userInput <= 4)) 
				return SelectionCode.NEIGHBORHOOD_FUNCTIONS;
			else if (userInput == 5)
				return SelectionCode.MAIN_MENU;			
			else
				System.out.println(tab + "Your selection is invalid.\n");		
		}
	}
	
	public static SelectionCode neighborhoodFunctions()
	{	
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
				return SelectionCode.UPDATE_NEIGHBORHOOD;
			else if ((userInput >= 2) && (userInput <= 5)) 
				System.out.println(tab + "Processing your selection....\n");
			else if (userInput == 6)
				return SelectionCode.SELECT_NEIGHBORHOOD;
			else if (userInput == 7)
				return SelectionCode.MAIN_MENU;		
			else
				System.out.println(tab + "Your selection is invalid.\n");		
		}
	}
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bao Chau
	 */
	public static SelectionCode updateNeighborhoodMap()
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

			if ((userInput >= 1) && (userInput <= 5)) 
				System.out.println(tab + "Processing your selection....\n");
			else if (userInput == 6)
				return SelectionCode.SELECT_NEIGHBORHOOD;
			else if (userInput == 7)
				return SelectionCode.MAIN_MENU;		
			else
				System.out.println(tab + "Your selection is invalid.\n");		
		}
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	boolean getNeighborhoodList(String filename)
	{
	}

	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	boolean getNeighborhoodMap(String filename)
	{
	}

}

