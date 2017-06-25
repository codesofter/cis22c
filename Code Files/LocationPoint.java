/*
* Coder: Bao Chau
*/
public class LocationPoint
{
	private String name = "";
	private String description = "";
	private String hashKey = "";
	
	public LocationPoint() 
	{
	}	
	
	public LocationPoint(String name, String description)
	{
		this.name = name.trim();
		this.description = description.trim();
		hashKey = this.name + this.description;
	}

	public String getName() { return name; }
	public String getDescription() { return description; }
	
	public void setName(String name) 
	{ 
		this.name = name.trim();
		hashKey = this.name + this.description;
	}
	
	public void setDescription(String description) 
	{ 
		this.description = description.trim();
		hashKey = this.name + this.description;
	}
	
	public String toString() { return description; }
	
	/*
	* Coder: Bao Chau
	*/
	public boolean equals(LocationPoint obj)
	{
		if (obj == null) return false;
		if (!name.equalsIgnoreCase(obj.name))
			return false;
		else
			return (description.equalsIgnoreCase(obj.description));
	}
	
	
	/*
	* Coder: Bao Chau
	*/
	public int hashCode()
	{
		int hashCode = 0;
		for(int k = 0; k < hashKey.length(); k++ )
			hashCode = 37 * hashCode + hashKey.charAt(k);
		
		return hashCode;
	}
}

