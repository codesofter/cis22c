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

	static LinkedQueue<String> pathStack = new LinkedQueue<String>();
	boolean recursion_break = false;
	boolean found = false;

	public void clearUndoStack()
	{
		while (!undoStack.isEmpty())
			undoStack.pop();
	}

	public E findLocationByName(String name)
	{
		E data;
		Vertex<E> tempVertex;
		Iterator<Entry<E, Vertex<E>>> iterator;

		if ((name != null) && (name.length() > 0))
		{
			iterator = vertexSet.entrySet().iterator();
			while (iterator.hasNext())
			{
				tempVertex = iterator.next().getValue();
				data = tempVertex.getData();
				if (name.compareToIgnoreCase(data.toString()) == 0) return data;
			}
		}
		return null;
	}

	public String getNeighborhoodName()
	{
		return neighborhoodName;
	}

	void setNeighborhoodName(String name)
	{
		if (!neighborhoodName.equalsIgnoreCase(name))
		{
			undoStack.push(new UndoNameChange<E>(this, neighborhoodName));
			neighborhoodName = name;
		}
	}

	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker
	 */
	boolean hasEulerCircuit()
	{
		return false;
	}

	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	public void getEulerCircuit(E startLocation, Visitor<E> visitor)
	{
		LinkedStack<E> pathStack = new LinkedStack<>();
		LinkedStack<E> circuitStack = new LinkedStack<>();
		pathStack.push(startLocation);
		showEulerCircuit(startLocation, pathStack, circuitStack, visitor);
	}

	public boolean showEulerCircuit(E startLocation, LinkedStack<E> pathStack, LinkedStack<E> circuitStack, Visitor<E> visitor)
	{
/*		Vertex<E> startVertex, endVertex;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> vertexIterator;

		startVertex = vertexSet.get(startLocation);
		vertexIterator = startVertex.iterator();
		
		if (vertexIterator.hasNext())
		{
			endVertex = vertexIterator.next().getValue().first;
			removeStreet(startLocation, endVertex.getData());
		}*/
		
		
		/* currentLocation = peek of pathStack.
		 * if currentLocation = startLocation
		 * {	
		 * 		if (completed)
		 * 			return;
		 * 		else
		 * 		{
		 * 			NextEdge = NULL
		 * 			while (nextEdge == null)
		 * 			{
		 * 				stackLocation = pop from pathStack
		 *	 			push stackLocation onto circuitStack
		 * 				if stackLocation has edges
		 * 				{
		 * 					NextEdge = stackLocation's next edge
		 * 					nextLocation = destination of nextEdge
		 *	 				push nextLocation onto pathStack
		 *					return recursive call.
		 * 				}
		 * 			}
		 * 		}
		 * }
		 * else
		 * {
		 * 		NextEdge = currentLocation next edge
		 * 		nextLocation = destination of nextEdge
		 *	 	push nextLocation onto pathStack
		 *		return recursive call.
		 * }
		 * 
		 */
		return true;
		
		/*Vertex<E> startVertex, endVertex;
		LinkedQueue<Entry<E, Pair<Vertex<E>, Double>>> listQueue1 = new LinkedQueue<Entry<E, Pair<Vertex<E>, Double>>>();
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter_1;

		visitor.visit(startLocation);

		startVertex = vertexSet.get(startLocation);
		iter_1 = startVertex.iterator();

		while (iter_1.hasNext())
		{
			listQueue1.enqueue(iter_1.next());
		}
		boolean result;
		while (!listQueue1.isEmpty())
		{

			endVertex = listQueue1.dequeue().getValue().first;

			// listQueue1.enqueue(endVertex);

			System.out.println("***From " + startLocation + " to " + endVertex.getData());
			this.showAdjTable();
			
			result = isNextEdgeValid(startVertex.getData(), endVertex.getData());			
			
			if (result)
			{

				removeStreet(startVertex.getData(), endVertex.getData());
				System.out.println(GroupProject.tab + "Remove " + startVertex.getData() + " to " + endVertex.getData());
				showEulerCircuit(endVertex.getData(), fixLocation, visitor);
			}
			
			this.showAdjTable();
			System.out.println(GroupProject.tab + "From " + startLocation + " to " + endVertex.getData() + " : " + result);
			System.out.println("--------------------------------------------------------");
		}

		return true;

		
		 * while (iter_1.hasNext()) { listQueue1.enqueue(iter_1.next()); }
		 * 
		 * while (!listQueue1.isEmpty() && !recursion_break) {
		 * 
		 * endVertex = listQueue1.dequeue().getValue().first; info =
		 * "From point " + startVertex.getData().toString() + "->" +
		 * endVertex.getData().toString();
		 * 
		 * // isTwoWay = isTwoWayStreet(startVertex, endVertex); if
		 * (isNextEdgeValid(startVertex.getData(), endVertex.getData())) {
		 * 
		 * System.out.println(info); remove(startVertex.getData(),
		 * endVertex.getData()); showEulerCircuit(endVertex.getData(),
		 * fixLocation, visitor); } } iter_1 = startVertex.iterator(); while
		 * (iter_1.hasNext()) { listQueue1.enqueue(iter_1.next()); }
		 * 
		 * while (!listQueue1.isEmpty() && !recursion_break) { endVertex =
		 * listQueue1.dequeue().getValue().first; info = "From point " +
		 * startVertex.getData().toString() + "->" +
		 * endVertex.getData().toString(); // isTwoWay =
		 * isTwoWayStreet(startVertex, endVertex); if
		 * (!isNextEdgeValid(startVertex.getData(), endVertex.getData())) {
		 * pathStack.enqueue(info); System.out.println(info);
		 * remove(startVertex.getData(), endVertex.getData());
		 * showEulerCircuit(endVertex.getData(), fixLocation, visitor); } } if
		 * (!recursion_break) { if (startVertex.getData() == fixLocation) {
		 * found = true; } } recursion_break = true; return found;
		 * 
		  */}

	public void display_path()
	{
		while (!pathStack.isEmpty())
		{
			System.out.println(pathStack.dequeue());
		}
	}

	public boolean isNextEdgeValid(E startLocation, E endLocation)
	{
		// System.out.println("Called isNextEdgeValid");
		//if (startLocation.toString().equalsIgnoreCase("N"))
		//	System.out.println("startLocation = N");
		int count1, count2, count = 0;
		boolean found;

		Vertex<E> startVertex = vertexSet.get(startLocation);
		Vertex<E> endVertex = vertexSet.get(endLocation);
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter = startVertex.iterator();
		
		//if (startLocation.toString().equalsIgnoreCase("N"))
		//	System.out.println("startLocation = N --- 1");
		
		while (iter.hasNext())
		{
			iter.next();
			count++;
		}
		
		if (count == 1)	
		{ 
			System.out.println(GroupProject.tab + GroupProject.tab + "It is the only edge.");
			return true; 
			}
		else
			System.out.println(GroupProject.tab + GroupProject.tab + "It is not the only edge.");

	
		CountingVisitor countingVisitor = new CountingVisitor();
		depthFirstTraversal(endLocation, (Visitor<E>) countingVisitor);
		count1 = countingVisitor.getCount();
			
		System.out.println(GroupProject.tab + GroupProject.tab + "count1 = " + count1);
		removeStreet(startLocation, endLocation);

		countingVisitor.resetCount();
		depthFirstTraversal(endLocation, (Visitor<E>) countingVisitor);
		count2 = countingVisitor.getCount();
		addStreet(startLocation, endLocation);
		
		System.out.println(GroupProject.tab + GroupProject.tab + "count2 = " + count2);
		return (count1 > count2) ? false : true;
	}

	public boolean isTwoWayStreet(Vertex<E> u, Vertex<E> v)
	{
		boolean forward = false;
		boolean backward = false;

		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter_1;
		iter_1 = u.iterator();

		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter_2;
		iter_2 = v.iterator();

		while (!forward && iter_1.hasNext())
		{
			if (iter_1.next().getValue().first.data.equals(v.getData()))
			{
				forward = true;
			}
		}
		while (!backward && iter_2.hasNext())
		{
			if (iter_2.next().getValue().first.data.equals(u.getData()))
			{
				backward = true;
			}
		}
		return (forward && backward);
	}


	void addStreet(E startLocation, E endLocation)
	{
		if (!isEdgeExisted(startLocation, endLocation))
		{
			// Call addEdge from Graph class (includes addToVertexSet and addToAdjList)
			addEdge(startLocation, endLocation, COST);					
			undoStack.push(new UndoAddStreet<E>(this, startLocation, endLocation));	
		}
	}

	void removeStreet(E startLocation, E endLocation)
	{
		if (isEdgeExisted(startLocation, endLocation))
		{
			remove(startLocation, endLocation);
			undoStack.push(new UndoRemoveStreet<E>(this, startLocation, endLocation));	
		}
	}

	public boolean undoLastUpdate()
	{
		if (undoStack.isEmpty()) return false;
		
		undoStack.pop().undo();
		return true;
	}

	void undoSetName(String name)
	{
		neighborhoodName = name;
	}

	public boolean isEdgeExisted(E startLocation, E endLocation)
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
				if (endLocation.equals(tempLocation))
					return true;
			}								
		}				
		return false;
	}
	
	public boolean saveToFile(PrintWriter printWriter, Visitor<E> savingVisitor)
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
			savingVisitor.visit(vertexIterator.next().getValue().getData());
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
				printWriter.println(String.format("%s %s", startVertex.getData(), pair.first.getData()));
			}
		}
		return true;
	}
}
