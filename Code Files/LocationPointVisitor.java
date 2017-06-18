
public class LocationPointVisitor implements Visitor<LocationPoint>
{
	/*
			Define visit method to print the LocationPoint object.

			Coder: So Choi, Bao Chau
	 */
	@Override
	public void visit(LocationPoint obj)
	{
		System.out.print(obj + " ");		
	}

}
