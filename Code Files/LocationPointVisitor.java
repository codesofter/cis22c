/*
* Coder: Bao Chau
*/
public class LocationPointVisitor implements Visitor<LocationPoint>
{
	@Override
	public void visit(LocationPoint obj)
	{
		System.out.print(obj + " ");		
	}

}
