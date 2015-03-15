package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;

import exceptions.DuplicateException;
import exceptions.InvalidFieldEntryException;


public class TravelItineraryList {

	protected ArrayList<TravelItinerary> travelList = null;
	
	public TravelItineraryList(){
		this.travelList = new ArrayList<TravelItinerary>();
	}
	
	
	/**
	 * This will add a Travel Destination
	 * @param travelDestination

	 */
	public void addTravelDestination(TravelItinerary travelDestination){
			this.travelList.add(travelDestination);
	}

	/**
	 * This will edit the travel Destination if index is within bounds
	 * @param index
	 * @param travelDestination
	 */
	public void editTravelDestination(int index, TravelItinerary travelDestination){
		
		if ((index < 0) || (index >= this.travelList.size())) {
			throw new IndexOutOfBoundsException("index is out of range");
		}
			this.travelList.set(index, travelDestination);
	}

	/**
	 * This will return the number of destinations
	 * @return
	 */
	public int numberofDestinations() {
		
		return this.travelList.size();
	}


	/**
	 * This will return a travel itinerary based on a selected index
	 * @param index
	 * @return
	 * @throws InvalidFieldEntryException
	 */
	public TravelItinerary getTravelDestinationAtIndex(int index) throws InvalidFieldEntryException {
	
		if ((index < 0) && (index >= this.travelList.size() )){
			throw new InvalidFieldEntryException("Index of Travel Destination is invalid.");
		}
		return this.travelList.get(index);
	}
	
	/**
	 * This will delete a travel destination at the specifed index
	 * @param index
	 */
	public void deleteTravelDestination(int index){
		
		if (this.travelList.size() >= index){
			this.travelList.remove(index);
		}
	}


	/**
	 * This will check to see if the travel Itinerary exists
	 * @param travelItinerary
	 * @return
	 */
	public boolean contains(TravelItinerary travelItinerary) {

		for (TravelItinerary destination : this.travelList) {
			if (destination.getDestinationName().equalsIgnoreCase(travelItinerary.getDestinationName())){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * this will return the size of the list
	 * @return
	 */
	public int size() {
		return travelList.size(); 
	}
	
	/**
	 * This will turn the list into a string
	 */
	@Override
	public String toString(){
		
		String travelListString = "";
		
		for (TravelItinerary ti: travelList){
			travelListString = ti.getDestinationName() + " : " + ti.getDestinationDescription() + "/n";
		}
		return travelListString;
		
	
		
	}
}
