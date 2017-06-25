import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

interface Undoable
{
	public void undo();
}

public class NeighborhoodGraph<E> extends Graph<E>
{
	private String neighborhoodName = "";
	private final double COST = 0.0;
	LinkedStack<Undoable> undoStack = new LinkedStack<>();

	/*
	 * Bao Chau
	 */
	public E findLocationByNameOrDescription(E LocationForCompare, Comparator<E> comparator)
	{
		E data;
		Vertex<E> tempVertex;
		Iterator<Entry<E, Vertex<E>>> iterator;
	
		iterator = vertexSet.entrySet().iterator();
		while (iterator.hasNext())
		{
			tempVertex = iterator.next().getValue();
			data = tempVertex.getData();
			if (comparator.compare(data, LocationForCompare) == 0) return data;
		}
		return null;
	}

	
	/*
	 * Coder: Bao Chau
	 */
	public String getNeighborhoodName()
	{
		return neighborhoodName;
	}

	/*
	 * Bao Chau
	 */
	void setNeighborhoodName(String name)
	{
		if (!neighborhoodName.equalsIgnoreCase(name))
		{
			undoStack.push(new UndoNameChange<E>(this, neighborhoodName));
			neighborhoodName = name;
		}
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	private boolean hasEulerCircuit(E startLocation)
	{
		int count, currIndex, edgeIndex;
		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIterator;
		HashMap<E, Integer> indexMap = new HashMap<>();
		E edgeLocation;
		Vertex<E> currVertex;

		int size = this.vertexSet.size();
		int countArray[] = new int[size];

		for (int i = 0; i < size; i++)
			countArray[i] = 0;

		count = 0;
		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext())
		{
			indexMap.put(vertexIterator.next().getValue().getData(), count);
			count++;
		}

		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext())
		{
			currVertex = vertexIterator.next().getValue();
			currIndex = indexMap.get(currVertex.getData());

			edgeIterator = currVertex.iterator();
			while (edgeIterator.hasNext())
			{
				edgeLocation = edgeIterator.next().getValue().first.getData();
				edgeIndex = indexMap.get(edgeLocation);

				countArray[edgeIndex]++;
				countArray[currIndex]--;
			}
		}

		for (int i = 0; i < size; i++)
			if (countArray[i] != 0) return false;

		return true;
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	public LinkedStack<E> getEulerCircuit(E startLocation)
	{
		LinkedStack<E> pathStack = new LinkedStack<>();
		LinkedStack<E> circuitStack = new LinkedStack<>();

		if (!hasEulerCircuit(startLocation)) return circuitStack;

		int originalSizeOfUndoStack = undoStack.size();

		pathStack.push(startLocation);
		findEulerCircuit(startLocation, pathStack, circuitStack);
		circuitStack.push(startLocation);

		while (undoStack.size() > originalSizeOfUndoStack)
			undoStack.pop().undo();

		return circuitStack;
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	private boolean isCompleted(E currentLocation, E fixLocation)
	{
		if (!currentLocation.equals(fixLocation)) return false;

		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIterator;

		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext())
		{
			edgeIterator = vertexIterator.next().getValue().iterator();
			if (edgeIterator.hasNext()) return false;
		}

		return true;
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	private void moveToNextLocation(E currentLocation, LinkedStack<E> pathStack)
	{
		Vertex<E> startVertex = vertexSet.get(currentLocation);
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter_1;
		iter_1 = startVertex.iterator();
		E nextLocation = iter_1.next().getValue().first.getData();
		pathStack.push(nextLocation);
		removeStreet(currentLocation, nextLocation);
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	private void moveToPreviousLocation(LinkedStack<E> pathStack, LinkedStack<E> circuitStack)
	{
		circuitStack.push(pathStack.pop());
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	public void findEulerCircuit(E fixLocation, LinkedStack<E> pathStack, LinkedStack<E> circuitStack)
	{
		E currLocation = pathStack.peek();
		Vertex<E> startVertex;
		startVertex = vertexSet.get(currLocation);
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter_1;
		iter_1 = startVertex.iterator();

		if (currLocation.equals(fixLocation))
		{
			if (isCompleted(currLocation, fixLocation))
			{
				return;
			}
		}

		if (iter_1.hasNext())
		{
			moveToNextLocation(currLocation, pathStack);
			findEulerCircuit(fixLocation, pathStack, circuitStack);
		} else
		{
			moveToPreviousLocation(pathStack, circuitStack);
			findEulerCircuit(fixLocation, pathStack, circuitStack);
		}
	}

	/*
	 * Coder: So Choi, Bao Chau
	 */
	public boolean addStreet(E startLocation, E endLocation)
	{
		if (!isEdgeExisted(startLocation, endLocation))
		{
			addEdge(startLocation, endLocation, COST);
			undoStack.push(new UndoAddStreet<E>(this, startLocation, endLocation));
			return true;
		}
		return false;
	}

	/*
	 * Coder: So Choi, Bao Chau
	 */
	public boolean removeStreet(E startLocation, E endLocation)
	{
		if (isEdgeExisted(startLocation, endLocation))
		{
			remove(startLocation, endLocation);
			undoStack.push(new UndoRemoveStreet<E>(this, startLocation, endLocation));
			return true;
		}
		return false;
	}

	/*
	 * Coder: Bao Chau
	 */
	public void undoLastUpdate()
	{
		if (undoStack.isEmpty()) 
			System.out.println("     Undo failed. Undo stack is empty.");
		else
		{
			Undoable undoObject = undoStack.pop();
			undoObject.undo();

			//Display status of undo on screen.
			System.out.println("     " + undoObject);	
		}		
	}

	/*
	 * Bao Chau
	 */
	public void clearUndoStack()
	{
		while (!undoStack.isEmpty())
			undoStack.pop();
	}
	
	/*
	 * Coder: Bao Chau
	 */
	void undoSetName(String name)
	{
		neighborhoodName = name;
	}

	/*
	 * Coder: Bao Chau
	 */
	private boolean isEdgeExisted(E startLocation, E endLocation)
	{
		E tempLocation;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> vertextIterator;

		Vertex<E> startVertex = vertexSet.get(startLocation);
		if ((startVertex != null) && (endLocation != null))
		{
			vertextIterator = startVertex.iterator();
			while (vertextIterator.hasNext())
			{
				tempLocation = vertextIterator.next().getValue().first.getData();
				if (endLocation.equals(tempLocation)) return true;
			}
		}
		return false;
	}

	/*
	 * Coder: Bao Chau
	 */
	public E getFirstLocation()
	{
		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		vertexIterator = vertexSet.entrySet().iterator();
			
		if (vertexIterator.hasNext())
			return vertexIterator.next().getValue().getData();
		else
			return null;
		
	}
	
	/*
	 * Coder: Bao Chau
	 */
	public void showNeighbohoodData(Visitor<E> visitor, Visitor<E> edgeVisitor)
	{
		Vertex<E> locationVertex;
		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIterator;
		Pair<Vertex<E>, Double> pair;
		
		vertexIterator = vertexSet.entrySet().iterator();
			
		if (!vertexIterator.hasNext())
		{
			System.out.print("\n     Neighborhood map has no data.\n");
			return;
		}
					
		System.out.println("\nNEIGHBORHOOD DATA");
		System.out.println("--------------------------------------------------");		
		System.out.println("     Neighborhood name: " + neighborhoodName);
		System.out.println("     Number of locations (Vertices): " + this.vertexSet.size());
		System.out.println("\n     List of locations (Vertices):");
		System.out.println("        Name -  Location Descrition");
		System.out.println("        ----    -------------------");

		while (vertexIterator.hasNext())
			visitor.visit(vertexIterator.next().getValue().getData());

		System.out.println("\n     List of adjacent edges (Show location name only, see above for location description):");
				
		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext())
		{
			locationVertex = vertexIterator.next().getValue();
			
			System.out.print("          ");
			edgeVisitor.visit(locationVertex.getData());
			System.out.print(":  ");
			
			edgeIterator = locationVertex.iterator();			
			while (edgeIterator.hasNext())
			{
				pair = edgeIterator.next().getValue();
				edgeVisitor.visit(pair.first.getData());
				if (edgeIterator.hasNext()) 
					System.out.print(",  ");
			}
			System.out.println();
		}		
	}
	
	/*
	 * Coder: Bao Chau
	 */
	public boolean saveToFile(PrintWriter printWriter, Visitor<E> savingLocationVisitor, Visitor<E> savingEdgeVisitor)
	{
		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIterator;
		Pair<Vertex<E>, Double> pair;
		Vertex<E> startVertex;

		// Write neighborhoodName and number of vertices to file.
		printWriter.println(neighborhoodName);
		printWriter.println(this.vertexSet.size());
		
		// Use savingVisitor to write vertex's data to file.
		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext())
		{
			savingLocationVisitor.visit(vertexIterator.next().getValue().getData());
		}

		// Write edges to file.
		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext())
		{
			startVertex = vertexIterator.next().getValue();
			edgeIterator = startVertex.iterator();

			while (edgeIterator.hasNext())
			{
				pair = edgeIterator.next().getValue();
				savingEdgeVisitor.visit(startVertex.getData());
				printWriter.print(" ");
				savingEdgeVisitor.visit(pair.first.getData());
				printWriter.println("");
			}
		}
		return true;
	}
}
