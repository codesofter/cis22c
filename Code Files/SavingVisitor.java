import java.io.PrintWriter;

/*
		Helper class for saving current progress to file.

		Coder: Bruce Decker, Bao Chau
 */
public class SavingVisitor implements Visitor<LocationPoint>
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
			printWriter.println(obj.getName() + " " + obj.getLatitude() + " " + 
						obj.getLongitude() + " " + obj.getDescription());
		}
	}

}
