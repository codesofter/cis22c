import java.io.PrintWriter;
import java.util.Comparator;
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
	
	LinkedStack undoStack = new LinkedStack<>();
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	boolean addStreet (boolean twoWay, E source, E destination)
	{
		// Call addEdge from Graph class (includes addToVertexSet and addToAdjList)
		addEdge(source, dest, COST);
		if (twoWay) {
			addEdge(dest, source, COST);
		}
		return false;
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: Bruce Decker, Vignesh Senthil
	 */
	boolean removeStreet (boolean twoWay, E source, E destination)
	{
		remove(source, destination);
		/*
			NOTE: remove function in Graph.java has a snippet of code for undirected graph. We can just uncomment that section too.
		*/
		if (twoWay) {
			remove(destination, source);
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
