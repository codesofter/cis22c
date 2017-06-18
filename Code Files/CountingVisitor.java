public class CountingVisitor implements Visitor<LocationPoint>
{
	int count = 0;
	public int getCount() { return count; }
	
	@Override
	public void visit(LocationPoint obj)
	{
		count++;	
		//System.out.println(obj + " ");
	}
	public void resetCount() {
		count = 0;
	}

}
