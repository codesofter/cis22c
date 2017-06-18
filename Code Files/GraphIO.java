import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GraphIO
{
	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile(String filename)
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
				description = fileScanner.nextLine().trim();

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
			
			graph.clearUndoStack();
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
	public static String[][] getNeighborhoodList(String filename)
	{
		int numberOfRecords;
		String name, mapFileName;
		String[][] mapList;
		
		// Open file. If unsuccessful, display error message and return null.
		Scanner fileScanner = openInputFile(filename);
		if (fileScanner == null)
		{
			System.out.println(String.format("%sUnable to open \"%s\" file.", GroupProject.tab, filename));
			return null;
		}

		try
		{
			// Read numberOfRecords, make sure it is valid.
			numberOfRecords = fileScanner.nextInt();
			fileScanner.nextLine();			
			
			//Make sure numberOfRecords is valid.
			if (numberOfRecords < 1) throw new IOException();
			
			//Create new array with array row size based on numberOfRecords.
			mapList = new String[numberOfRecords][2];		
			
			// Read the vertices.
			for (int i = 0; i < numberOfRecords; i++)
			{
				name = fileScanner.nextLine().trim();
				mapFileName = fileScanner.nextLine();
				mapList[i][0] = name;
				mapList[i][1] = mapFileName;
			}

		} catch (Exception ex)
		{
			System.out.println(String.format("%sUnable to read data from \"%s\" file.", GroupProject.tab, filename));
			mapList = null;
		}

		return mapList;
	}
	
	public static boolean saveGraphToFile(NeighborhoodGraph<LocationPoint> targetGraph, String filename)
	{
		FileOutputStream outputFile;
		PrintWriter outputWriter = null;
		SavingVisitor savingVisitor;
		boolean result;
		if (targetGraph == null) 
			throw new NullPointerException("SaveGraphToFile - Input parameter can not be null.");
		
		try 
		{
			outputFile = new FileOutputStream(filename);
			outputWriter = new PrintWriter(outputFile, true);

			savingVisitor = new SavingVisitor();
			savingVisitor.setPrintWriter(outputWriter);
			
			result = targetGraph.saveToFile(outputWriter, savingVisitor);
			if (result == true) 
			{
				targetGraph.clearUndoStack();
				System.out.println(String.format("%sData has been saved to \"%s\" file.", GroupProject.tab, filename));				
			}
		}
		catch (Exception e) {
			System.out.println(String.format("%sUnable to write data to \"%s\" file.", GroupProject.tab, filename));
			result = false;
		}		
		
		if (outputWriter != null) outputWriter.close();
		return result;
	}
}
