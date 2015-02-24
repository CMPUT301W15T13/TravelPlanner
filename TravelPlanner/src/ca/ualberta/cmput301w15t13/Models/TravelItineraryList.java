package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;

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


	public TravelItinerary getTravelDestinationAtIndex(int index) {
	
		return this.travelList.get(index);
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
