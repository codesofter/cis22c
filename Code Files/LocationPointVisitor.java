import java.io.PrintWriter;

/*
* Coder: Bao Chau
*/
public class LocationPointVisitor implements Visitor<LocationPoint>
{
	PrintWriter printWriter;

	public void setPrintWriter(PrintWriter printWriter)
	{
		this.printWriter = printWriter;
	}
	
	public void clearPrintWriter()
	{
		this.printWriter = null;
	}
	
	@Override
	public void visit(LocationPoint obj)
	{
		if (obj == null) 
			throw new NullPointerException("Input paramter must not be null");  
	
		if (printWriter != null)
			printWriter.println(obj.getName() + " " + obj.getDescription());
		else
			System.out.println("          " + obj.getName() + "  -  " + obj.getDescription());
	}
}


/*
 * import java.io.PrintWriter;

public class SavingLocationVisitor implements Visitor<LocationPoint>
{
	PrintWriter printWriter;

	public void setPrintWriter(PrintWriter printWriter)
	{
		this.printWriter = printWriter;
	}
	
	public void clearPrintWriter()
	{
		this.printWriter = null;
	}
	
	@Override
	public void visit(LocationPoint obj)
	{				
		if (obj != null)
		{
			printWriter.println(obj.getName() + " " + obj.getDescription());
		}
	}

}


*/