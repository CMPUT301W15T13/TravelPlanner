package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;

import exceptions.InvalidFieldEntryException;

/**
 * This class is a model class for TravelItineraries.
 * Essentially, as a claim can have many travel
 * destinations with descriptions, each of those
 * are made into a class instance (a travelItinerary)
 * and managed here.
 * This should be used to collect and contain
 * all travelItinerary objects for a particular
 * claim.
 * 
 * Classes it works with:
 * Claim,TravelItinerary
 */

public class TravelItineraryList {

	protected ArrayList<TravelItinerary> travelList = null;
	
	public TravelItineraryList(){
		this.travelList = new ArrayList<TravelItinerary>();
	}

	public void addTravelDestination(TravelItinerary travelDestination) {
			this.travelList.add(travelDestination);
	}

	/**
	 * Edit the travel Destination if the
	 * index is appropriate
	 * @param index
	 * @param travelDestination
	 */
	public void editTravelDestination(int index, TravelItinerary travelDestination) {
		
		if ((index < 0) || (index >= this.travelList.size())) {
			throw new IndexOutOfBoundsException("index is out of range");
		}
		this.travelList.set(index, travelDestination);
	}

	public int numberofDestinations() {
		return this.travelList.size();
	}

	/**
	 * This will return a travel itinerary 
	 * based on a given index
	 * @param index
	 * @return
	 * @throws InvalidFieldEntryException
	 */
	public TravelItinerary getTravelDestinationAtIndex(int index) throws InvalidFieldEntryException {
		if ((index < 0) && (index >= this.travelList.size() )) {
			throw new InvalidFieldEntryException("Index of Travel Destination is invalid.");
		}
		return this.travelList.get(index);
	}
	
	public void deleteTravelDestination(int index) {
		if (this.travelList.size() >= index) {
			this.travelList.remove(index);
		}
	}

	/**
	 * Can be used check to see 
	 * if the travel Itinerary exists
	 * or is related to the claim
	 * in question
	 * @param travelItinerary
	 * @return
	 */
	public boolean contains(TravelItinerary travelItinerary) {
		for (TravelItinerary destination : this.travelList) {
			if (destination.getDestinationName().equalsIgnoreCase(travelItinerary.getDestinationName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all travelItinerary objects
	 * in the list as a ready-to-display
	 * String
	 */
	@Override
	public String toString() {
		String travelListString = "";
		if (this.travelList.size() > 0)
		{
			travelListString = this.travelList.get(0).getDestinationName()  + " : " + this.travelList.get(0).getDestinationDescription();
		}
		for (int index = 1; index < this.travelList.size(); index ++) {
			travelListString = travelListString +  "\n" + this.travelList.get(index).getDestinationName()  + " : " + this.travelList.get(index).getDestinationDescription();
		}
		return travelListString;
	}
}
