import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GraphIO
{
	// opens a text file for input, returns a Scanner:
	private static Scanner openInputFile(String filename)
	{
		File file = new File(filename);
		try
		{
			return new Scanner(file);
		} catch (FileNotFoundException fe)
		{
			return null;
		}
	}

	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	public static NeighborhoodGraph<LocationPoint> getNeighborhoodMap(String filename)
	{
		String neighborhoodName, name, description, source, destination;
		int numberOfLocation;
		double latitude, longitude;
		LocationPoint location, srcLocation, destLocation;
		NeighborhoodGraph<LocationPoint> graph = null;
		
		//Open file. If unsuccessful, display error message and return null. 
		Scanner fileScanner = openInputFile(filename);;
		if (fileScanner == null) 
		{
			System.out.println(String.format("%sUnable to open \"%s\" file.", 
					GroupProject.tab, filename));
			return null;
		}
		
		try
		{
			//Read neighborhoodName and numberOfLocation, make sure they are valid.
			neighborhoodName = fileScanner.nextLine().trim();
			numberOfLocation = fileScanner.nextInt();
			if ((numberOfLocation < 2) || (neighborhoodName.length() == 0)) 
				throw new IOException();
			
			graph = new NeighborhoodGraph<LocationPoint>();
			graph.setNeighborhoodName(neighborhoodName);

			// Read the vertices.
			for (int i = 0; i < numberOfLocation; i++)
			{
				name = fileScanner.next().trim();
				latitude = fileScanner.nextDouble();
				longitude = fileScanner.nextDouble();
				description = fileScanner.nextLine();

				location = new LocationPoint(name, latitude, longitude, description);				
				graph.addToVertexSet(location);
			}

			// Read the edges.
			while (fileScanner.hasNext())
			{
				source = fileScanner.next().trim();
				destination = fileScanner.nextLine().trim();

				srcLocation = graph.findLocationByName(source);
				destLocation = graph.findLocationByName(destination);

				if ((srcLocation == null) || (destLocation == null)) 
					throw new IOException();

				graph.addEdge(srcLocation, destLocation, 0.0);
			}
		} 
		catch (Exception ex)
		{
			System.out.println(String.format("%sUnable to read data from \"%s\" file.", 
					GroupProject.tab, filename));
			graph = null;
		}

		if (fileScanner != null) fileScanner.close();
		return graph;
	}
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	public static boolean getNeighborhoodList(String filename)
	{
		return false;
	}

}
