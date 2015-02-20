package ca.ualberta.cmput301w15t13;

public class TravelItinerary {

	protected String name = null;
	protected String description = null;
	
	
	public TravelItinerary(String destination, String description){
		
		this.name = destination;
		this.description = description;
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
