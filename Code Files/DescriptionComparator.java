import java.util.Comparator;

public class DescriptionComparator implements Comparator<LocationPoint>
{
	@Override
	public int compare(LocationPoint firstLocation, LocationPoint secondLocation)
	{
		return firstLocation.getDescription().compareToIgnoreCase(secondLocation.getDescription());
	}

}