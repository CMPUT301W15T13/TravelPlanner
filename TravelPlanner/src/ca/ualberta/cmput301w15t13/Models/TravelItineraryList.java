package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;

import exceptions.DuplicateException;
import exceptions.InvalidFieldEntryException;


public class TravelItineraryList {

	protected ArrayList<TravelItinerary> travelList = null;
	
	
	
	public TravelItineraryList(){
		
		this.travelList = new ArrayList<TravelItinerary>();
		
	}
	
	
	public void addTravelDestination(TravelItinerary travelDestination) throws DuplicateException{
		
		if (!travelList.contains(travelDestination))
			this.travelList.add(travelDestination);
		else
			throw new DuplicateException("Travel Destination already Exists!");
		
	}


	public int numberofDestinations() {
		
		return this.travelList.size();
	}


	public TravelItinerary getTravelDestinationAtIndex(int index) throws InvalidFieldEntryException {
	
		if (this.travelList.isEmpty() && (this.travelList.size() > index))
			throw new InvalidFieldEntryException("Index of Travel Destination is invalid.");
		else
			return this.travelList.get(index);
	}
	
	
	
	public void deleteTravelDestination(int index){
		
		if (this.travelList.size() >= index)
			this.travelList.remove(index);
	}


	public boolean contains(TravelItinerary travelItinerary) {

		for (TravelItinerary destination : this.travelList) {
			if (destination.getDestinationName().equalsIgnoreCase(travelItinerary.getDestinationName()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	

	
}
