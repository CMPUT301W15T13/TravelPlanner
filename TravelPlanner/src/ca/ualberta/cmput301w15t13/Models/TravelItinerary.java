package ca.ualberta.cmput301w15t13.Models;

public class TravelItinerary {

	protected String destinationName = null;
	protected String description = null;
	
	
	public TravelItinerary(String destination, String description){
		
		this.destinationName = destination;
		this.description = description;
		
	}
	
	
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String name) {
		this.destinationName = name;
	}
	public String getDestinationDescription() {
		return description;
	}
	public void setDestinationDescription(String description) {
		this.description = description;
	}


	@Override
	public boolean equals(Object rhs){
		
		String rhsDestination = ((TravelItinerary) rhs).getDestinationName().trim().toLowerCase();
		
		if (this.destinationName.trim().toLowerCase().equals(rhsDestination))
		{
			return true;
		}
		return false;
		
	}
	
	
}
