import java.util.Comparator;

public class NameComparator implements Comparator<LocationPoint>
{
	@Override
	public int compare(LocationPoint firstLocation, LocationPoint secondLocation)
	{
		return firstLocation.getName().compareToIgnoreCase(secondLocation.getName());
	}

}