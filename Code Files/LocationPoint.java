public class LocationPoint
{
	private String name = "";
	private double latitude = 0.0;
	private double longitude = 0.0;	
	private String description = "";
	
	public LocationPoint(String name, double latitude, 
					double longitude, String description)
	{
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}

	public String getName() { return name; }
	public double getLatitude() { return latitude; }
	public double getLongitude() { return longitude; }
	public String getDescription() { return description; }
	
	public String toString() { return name; }
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	public boolean equals(LocationPoint obj)
	{
		
	}
	
	
	/*
	 * Description (or) documentation of methods.
	 * 
	 * Coder: So Choi, Bao Chau
	 */
	public int hashCode()
	{
		
	}
}

