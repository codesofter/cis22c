import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

interface Undoable {
	public void undo();
}

public class NeighborhoodGraph<E> extends Graph<E> {
	private String neighborhoodName = "";
	private final double COST = 0.0;
	LinkedStack<Undoable> undoStack = new LinkedStack<>();	

	/*
	 * Bao Chau
	 */
	public void clearUndoStack() {
		while (!undoStack.isEmpty())
			undoStack.pop();
	}

	/*
	 * Bao Chau
	 */
	public E findLocationByName(String name) {
		E data;
		Vertex<E> tempVertex;
		Iterator<Entry<E, Vertex<E>>> iterator;

		if ((name != null) && (name.length() > 0)) {
			iterator = vertexSet.entrySet().iterator();
			while (iterator.hasNext()) {
				tempVertex = iterator.next().getValue();
				data = tempVertex.getData();
				if (name.compareToIgnoreCase(data.toString()) == 0)
					return data;
			}
		}
		return null;
	}

	/*
	 * Coder: Bao Chau
	 */
	public String getNeighborhoodName() {
		return neighborhoodName;
	}

	/*
	 * Bao Chau
	 */
	void setNeighborhoodName(String name) {
		if (!neighborhoodName.equalsIgnoreCase(name)) {
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
	public LinkedStack<E> getEulerCircuit(E startLocation) {
		LinkedStack<E> pathStack = new LinkedStack<>();
		LinkedStack<E> circuitStack = new LinkedStack<>();
		
		if (!hasEulerCircuit(startLocation))
			return circuitStack;
		
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
	private boolean isCompleted(E currentLocation, E fixLocation) {
		if (!currentLocation.equals(fixLocation))
			return false;

		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIterator;

		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext()) {
			edgeIterator = vertexIterator.next().getValue().iterator();
			if (edgeIterator.hasNext())
				return false;
		}

		return true;
	}

	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	private void moveToNextLocation(E currentLocation, LinkedStack<E> pathStack) {
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
	private void moveToPreviousLocation(LinkedStack<E> pathStack, LinkedStack<E> circuitStack) {
		circuitStack.push(pathStack.pop());
	}
	
	/*
	 * Coder: Bruce Decker, So Choi, Bao Chau
	 */
	public void findEulerCircuit(E fixLocation, LinkedStack<E> pathStack, LinkedStack<E> circuitStack) {
		E currLocation = pathStack.peek();
		Vertex<E> startVertex;
		startVertex = vertexSet.get(currLocation);
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter_1;
		iter_1 = startVertex.iterator();

		if (currLocation.equals(fixLocation)) {
			if (isCompleted(currLocation, fixLocation)) {
				return;
			} 
		}

		if (iter_1.hasNext()) {
			moveToNextLocation(currLocation, pathStack);
			findEulerCircuit(fixLocation, pathStack, circuitStack);
		} else {
			moveToPreviousLocation(pathStack, circuitStack);
			findEulerCircuit(fixLocation, pathStack, circuitStack);
		}
	}
	
	/*
	 * Coder: So Choi, Bao Chau
	 */
	public void addStreet(E startLocation, E endLocation) {
		if (!isEdgeExisted(startLocation, endLocation)) {
			// Call addEdge from Graph class (includes addToVertexSet and addToAdjList)
			addEdge(startLocation, endLocation, COST);
			undoStack.push(new UndoAddStreet<E>(this, startLocation, endLocation));
		}
	}

	/*
	 * Coder: So Choi, Bao Chau
	 */
	public void removeStreet(E startLocation, E endLocation) {
		if (isEdgeExisted(startLocation, endLocation)) {
			remove(startLocation, endLocation);
			undoStack.push(new UndoRemoveStreet<E>(this, startLocation, endLocation));
		}
	}

	/*
	 * Coder: Bao Chau
	 */
	public boolean undoLastUpdate() {
		if (undoStack.isEmpty()) return false;

		undoStack.pop().undo();
		return true;
	}

	/*
	 * Coder: Bao Chau
	 */
	void undoSetName(String name) {
		neighborhoodName = name;
	}

	/*
	 * Coder: Bao Chau
	 */
	private boolean isEdgeExisted(E startLocation, E endLocation) {
		E tempLocation;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> vertextIterator;

		Vertex<E> startVertex = vertexSet.get(startLocation);
		if ((startVertex != null) && (endLocation != null)) {
			vertextIterator = startVertex.iterator();
			while (vertextIterator.hasNext()) {
				tempLocation = vertextIterator.next().getValue().first.getData();
				if (endLocation.equals(tempLocation))
					return true;
			}
		}
		return false;
	}

	/*
	 * Coder: Bao Chau
	 */
	public boolean saveToFile(PrintWriter printWriter, Visitor<E> savingVisitor) {
		Iterator<Entry<E, Vertex<E>>> vertexIterator;
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> edgeIterator;
		Pair<Vertex<E>, Double> pair;
		Vertex<E> startVertex;

		// Write neighborhoodName and number of vertices to file.
		printWriter.println(neighborhoodName);
		printWriter.println(this.vertexSet.size());

		// Use savingVisitor to write vertex's data to file.
		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext()) {
			savingVisitor.visit(vertexIterator.next().getValue().getData());
		}

		// Write edges to file.
		vertexIterator = vertexSet.entrySet().iterator();
		while (vertexIterator.hasNext()) {
			startVertex = vertexIterator.next().getValue();
			edgeIterator = startVertex.iterator();

			while (edgeIterator.hasNext()) {
				pair = edgeIterator.next().getValue();
				printWriter.println(String.format("%s %s", startVertex.getData(), pair.first.getData()));
			}
		}
		return true;
	}
}
