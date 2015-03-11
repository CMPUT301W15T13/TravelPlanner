package ca.ualberta.cmput301w15t13.Models;

import exceptions.EmptyFieldException;
import exceptions.ExceptionHandler;
import exceptions.ExceptionHandler.FIELD;

public class TravelItinerary {

	protected String destinationName = null;
	protected String description = null;
	
	
	/**
	 * This will make a new Itinerary based on the info passed in.
	 * @param destination
	 * @param description
	 * @throws EmptyFieldException 
	 */
	public TravelItinerary(String destination, String description) throws EmptyFieldException{
		
		this.setDestinationName(destination);
		this.setDestinationDescription(description);
		
	}
	
	
	//////////Travel Destionation getter/setter
	/**
	 * This will get the destination Name
	 * @return
	 */
	public String getDestinationName() {
		return destinationName;
	}
	/**
	 * This will set the destination name
	 * @param name
	 * @throws EmptyFieldException
	 */
	public void setDestinationName(String name) throws EmptyFieldException {
		new ExceptionHandler().throwExeptionIfEmpty(name,FIELD.TRAVEL_DESTINATION);
		this.destinationName = name;
	}
	
	///////////Travel Description getter/setter
	/**
	 * This will get the destination description
	 * @return
	 */
	public String getDestinationDescription() {
		return description;
	}
	/**
	 * This will set the destination description
	 * @param description
	 * @throws EmptyFieldException
	 */
	public void setDestinationDescription(String description) throws EmptyFieldException {
		new ExceptionHandler().throwExeptionIfEmpty(description,FIELD.TRAVEL_DESCRIPTION);
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
