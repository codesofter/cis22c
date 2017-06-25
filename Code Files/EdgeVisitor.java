import java.io.PrintWriter;

/*
* Coder: Bao Chau
*/
public class EdgeVisitor implements Visitor<LocationPoint>
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
			printWriter.print(obj.getName());
		else
			System.out.print(obj.getName());
	}
}
