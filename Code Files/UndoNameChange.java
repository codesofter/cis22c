
/*
 * Coder: Bao Chau
 */
public class UndoNameChange<E> implements Undoable
{
	String oldName;
	NeighborhoodGraph<E> targetGraph;
	
	UndoNameChange(NeighborhoodGraph<E> targetGraph, String oldName)
	{
		this.targetGraph = targetGraph;
		this.oldName = oldName;
	}
	
	@Override
	public void undo()
	{		
		this.targetGraph.undoSetName(this.oldName);
	}
}
