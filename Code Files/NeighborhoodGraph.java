import java.io.PrintWriter;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NeighborhoodGraph<E> extends Graph<E>
{
	private String neighborhoodName = "";
	private final double COST = 0.0;

	
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
				if (name.compareToIgnoreCase(data.toString()) == 0)
					return data;
			}	
		}

		return null;
	}
	
	public String getNeighborhoodName()
	{
		return neighborhoodName;
	}
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */	
	void setNeighborhoodName (String name)
	{
		neighborhoodName = name;
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	boolean hasEulerCircuit ()
	{
		return false;
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	LinkedQueue<E> getEulerCircuit ()
	{
		return null;
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	void showEulerCircuit (LinkedQueue<E> list)
	{
		
	}
	
	LinkedStack<ArrayList> undoStack = new LinkedStack<ArrayList>();
	/*
		Helper class to add each instruction to an undo stack
	*/
	private void addInstructionToUndoStack(boolean isAdd, boolean isUndirected, E vertA, E vertB) {
		ArrayList<Object> instruction = new ArrayList<Object>();
		instruction.add(isAdd);
		instruction.add(isUndirected);
		instruction.add(vertA);
		instruction.add(vertB);
		System.out.println("instruction: " + instruction);
		System.out.println(instruction.get(0) + ", " + instruction.get(1) + ", " + instruction.get(2) + ", " + instruction.get(3));
		undoStack.push(instruction);
	}

	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	boolean addStreet (boolean isUndirected, E source, E dest)
	{
		// Call addEdge from Graph class (includes addToVertexSet and addToAdjList)
		addEdge(source, dest, COST);
		addInstructionToUndoStack(true, isUndirected, source, dest);
		if (isUndirected) {
			addEdge(dest, source, COST);
			addInstructionToUndoStack(true, isUndirected, dest, source);
		}
		return false;
	}
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	boolean removeStreet (boolean isUndirected, E source, E dest)
	{
		remove(source, dest);
		addInstructionToUndoStack(false, isUndirected, source, dest);
		/*
			NOTE: remove function in Graph.java has a snippet of code for undirected graph. We can just uncomment that section too.
		*/
		if (isUndirected) {
			remove(dest, source);
			addInstructionToUndoStack(false, isUndirected, dest, source);
		}
		return false;
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	void undoLastUpdate()
	{
		if (!undoStack.isEmpty()) {
			ArrayList<Object> instruction = new ArrayList<Object>();
			instruction = undoStack.pop();

			// If instruction was true, it was an add instruction. must remove again.
			// if (instruction.get(0)) {
				// removeStreet(instruction.get(1), instruction.get(2), instruction.get(3));
			// } else {
				// addStreet(instruction.get(1), instruction.get(2), instruction.get(3));
			// }
		}
	}
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	public void saveToFile (PrintWriter out)
	{
		
	}
}
