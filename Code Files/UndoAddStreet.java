
/*
 * Coder: Bao Chau
 */
public class UndoAddStreet<E> implements Undoable
{
	E startLocation, endLocation;
	NeighborhoodGraph<E> targetGraph;

	UndoAddStreet(NeighborhoodGraph<E> targetGraph, E startLocation, E endLocation)
	{
		this.targetGraph = targetGraph;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	@Override
	public void undo()
	{
		this.targetGraph.remove(startLocation, endLocation);
	}
	
	@Override
	public String toString()
	{
		return "Undo of add street (add edge) is completed.";
	}
}
