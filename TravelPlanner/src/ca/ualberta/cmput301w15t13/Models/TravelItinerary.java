package ca.ualberta.cmput301w15t13.Models;

import android.annotation.SuppressLint;
import android.location.Location;
import exceptions.EmptyFieldException;
import exceptions.ExceptionHandler;
import exceptions.ExceptionHandler.FIELD;

/**
 * This class represents a place the user wants to
 * visit. It contains a destination, an
 * associated reason for travel, and operations
 * on it's data.
 * It should be used for just that; holding
 * a destination and reason. This is preferable to
 * using strings as we can have a single List
 * of Itinerary objects, each with both fields.
 * 
 * Classes it works with:
 * TravelItineraryList, Claim
 */
public class TravelItinerary {

	protected String destinationName = null;
	protected String description = null;
	protected Location location = null;
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public TravelItinerary(String destination, String description) throws EmptyFieldException{
		this.setDestinationName(destination);
		this.setDestinationDescription(description);
	}
	
	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String name) throws EmptyFieldException {
		new ExceptionHandler().throwExeptionIfEmpty(name,FIELD.TRAVEL_DESTINATION);
		this.destinationName = name;
	}
	

	public String getDestinationDescription() {
		return description;
	}
	
	public void setDestinationDescription(String description) throws EmptyFieldException {
		new ExceptionHandler().throwExeptionIfEmpty(description,FIELD.TRAVEL_DESCRIPTION);
		this.description = description;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public boolean equals(Object rhs) {
		String rhsDestination = ((TravelItinerary) rhs).getDestinationName().trim().toLowerCase();
		if (this.destinationName.trim().toLowerCase().equals(rhsDestination)) {
			return true;
		}
		return false;
	}
}
