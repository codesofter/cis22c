import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class GraphIO
{
	/*
	 * Coder: Bao Chau
	 * opens a text file for input, returns a Scanner:
	 */
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
	 * Coder: Bao Chau
	 */
	public static NeighborhoodGraph<LocationPoint> getNeighborhoodMap(String fileName)
	{
		String neighborhoodName, name, description, source, destination;
		int numberOfLocation;
		LocationPoint location, srcLocation, destLocation;
		NeighborhoodGraph<LocationPoint> graph = null;
		Comparator<LocationPoint> comparator = new NameComparator();
		
		//Open file. If unsuccessful, display error message and return null. 
		Scanner fileScanner = openInputFile(fileName);;
		if (fileScanner == null) 
		{
			System.out.println(String.format("%sUnable to open \"%s\" file.", 
					GroupProject.tab, fileName));
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
				description = fileScanner.nextLine().trim();

				location = new LocationPoint(name, description);				
				graph.addToVertexSet(location);
			}

			
			location = new LocationPoint();
			
			// Read the edges.
			while (fileScanner.hasNext())
			{
				source = fileScanner.next().trim();
				destination = fileScanner.nextLine().trim();
				
				location.setName(source);
				srcLocation = graph.findLocationByNameOrDescription(location, comparator);
				
				location.setName(destination);
				destLocation = graph.findLocationByNameOrDescription(location, comparator);

				if ((srcLocation == null) || (destLocation == null)) 
					throw new IOException();

				graph.addEdge(srcLocation, destLocation, 0.0);
			}
			
			graph.clearUndoStack();
		} 
		catch (Exception ex)
		{
			System.out.println("     Unable to read data from \"" + fileName + "\" file.");
			System.out.println("     It is not a valid graph file.");
			graph = null;
		}

		if (fileScanner != null) fileScanner.close();
		return graph;
	}
	
	/*
	 * Coder: Bao Chau
	 */
	public static String[][] getNeighborhoodList()
	{
		String[][] mapList;
		int numberOfRecords = 3;		

		//Create new array with array row size based on numberOfRecords.
		mapList = new String[numberOfRecords][2];		
		mapList[0][0] = "AbornNieman Map.txt";
		mapList[1][0] = "Cannongate Map.txt";
		mapList[2][0] = "Sterling Map.txt";
		
		for (int i = 0; i < numberOfRecords; i++)
			mapList[i][1] = getNeighborhoodNameFromFile(mapList[i][0]);

		return mapList;
	}
	
	/*
	 * Coder: Bao Chau
	 */
	private static String getNeighborhoodNameFromFile(String fileName)
	{
		String neighborhoodName;

		// Open file. If unsuccessful, display error message and return default string.
		Scanner fileScanner = openInputFile(fileName);
		if (fileScanner == null) return "Unknown";

		try
		{
			neighborhoodName = fileScanner.nextLine();
		} catch (Exception ex)
		{
			neighborhoodName = "Unknown";
		}
		fileScanner.close();
		return neighborhoodName;
	}
	
	/*
	 * Coder: Bao Chau
	 */
	public static boolean saveGraphToFile(NeighborhoodGraph<LocationPoint> targetGraph, String fileName)
	{
		FileOutputStream outputFile;
		PrintWriter outputWriter = null;
		boolean result;
		LocationPointVisitor savingVisitor;
		EdgeVisitor edgeVisitor;
		
		
		if (targetGraph == null) 
			throw new NullPointerException("SaveGraphToFile - Input parameter can not be null.");

		savingVisitor = new LocationPointVisitor();
		edgeVisitor = new EdgeVisitor();
		
		try 
		{
			outputFile = new FileOutputStream(fileName);
			outputWriter = new PrintWriter(outputFile, true);

			savingVisitor.setPrintWriter(outputWriter);
			edgeVisitor.setPrintWriter(outputWriter);

			result = targetGraph.saveToFile(outputWriter, savingVisitor, edgeVisitor);
			if (result == true) 
			{
				targetGraph.clearUndoStack();
				System.out.println(String.format("%sData has been saved to \"%s\" file.", GroupProject.tab, fileName));				
			}
		}
		catch (Exception e) {
			System.out.println(String.format("%sUnable to write data to \"%s\" file.", GroupProject.tab, fileName));
			result = false;
		}		
		
		savingVisitor.clearPrintWriter();
		edgeVisitor.clearPrintWriter();
		if (outputWriter != null) outputWriter.close();
		return result;
	}
	
	/*
	 * Coder: Bao Chau
	 */
	public static void saveTextToFile(String fileName, ArrayList<String> inputText)
	{
		FileOutputStream outputFile;
		PrintWriter outputWriter = null;
		
		try 
		{
			outputFile = new FileOutputStream(fileName);
			outputWriter = new PrintWriter(outputFile, true);

			for (String line : inputText)
				outputWriter.println(line);
			
			System.out.println(String.format("%sData has been saved to \"%s\" file.", GroupProject.tab, fileName));	
		}
		catch (Exception e) {
			System.out.println(String.format("%sUnable to write data to \"%s\" file.", GroupProject.tab, fileName));
		}		
		
		if (outputWriter != null) outputWriter.close();		
	}
}
