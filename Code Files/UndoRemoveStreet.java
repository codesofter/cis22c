/*
 * Coder: Bao Chau
 */
public class UndoRemoveStreet<E> implements Undoable
{
	E startLocation, endLocation;
	NeighborhoodGraph<E> targetGraph;
	
	UndoRemoveStreet(NeighborhoodGraph<E> targetGraph, E startLocation, E endLocation)
	{
		this.targetGraph = targetGraph;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	
	@Override
	public void undo()
	{		
		this.targetGraph.addEdge(startLocation, endLocation, 0.0);
	}
}
