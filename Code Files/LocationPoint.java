public class LocationPoint
{
	private String name = "";
	private double latitude = 0.0;
	private double longitude = 0.0;	
	private String description = "";
	
	public LocationPoint(String name, double latitude, 
					double longitude, String description)
	{
		this.name = name.trim();
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description.trim();
	}

	public String getName() { return name; }
	public double getLatitude() { return latitude; }
	public double getLongitude() { return longitude; }
	public String getDescription() { return description; }
	
	public String toString() { return name; }
	
	/*
			Define equals method to check the LocationPoint object's name/latitude/longitude.

			Coder: So Choi, Bao Chau
	 */
	public boolean equals(LocationPoint obj)
	{
		if (obj == null) return false;
		if (!name.equalsIgnoreCase(obj.name))
			return false;
		else if (latitude != obj.latitude)
			return false;
		else
			return (longitude == obj.longitude);
	}
	
	public int hashCode()
	{
		int hashCode = 0;
		
		String key = name + latitude + longitude;
		
		for(int k = 0; k < key.length(); k++ )
			hashCode = 37 * hashCode + key.charAt(k);
		
		return hashCode;
	}
}

