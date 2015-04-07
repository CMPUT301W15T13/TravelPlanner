package ca.ualberta.cmput301w15t13.test;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import exceptions.EmptyFieldException;


/**
 * Due to Problems with the Google
 * Maps API and Lab resources implementing
 * ...
 *
 */
public class GeoLocationTests extends ActivityInstrumentationTestCase2<LoginActivity>{
	
	public GeoLocationTests(Class<LoginActivity> activityClass) {
		super(activityClass);
	}

	/**
	 * Tests US01.07.01
	 * @throws EmptyFieldException 
	 */
	public void testDestinationClaimAttach() throws EmptyFieldException {
		//This should test this user story:
		//As a claimant, I want to attach a geolocation to a destination.
		TravelItinerary item = new TravelItinerary("Destination", "Reason");
		Location location = new Location("");
		location.setLatitude(0.0);
		location.setLongitude(0.0);
		UserLocationManager.setHomeLocation(location);
		item.setLocation(UserLocationManager.getHomeLocation());
		assertTrue("Location has been added to Travel Destination", item.getLocation() == location);
	}

	/**
	 * Tests US02.03.01
	 * Will not do, as there is no color attribute for a claim,
	 * but just an attribute on a TextView.
	 */
	public void testColour() {
		//
		
		//This should test this user story:
		//As a claimant, I want the list of expense claims to have each 
		//claim color coded by the distance of its first destination geolocation 
		//to my home geolocation, so that claims for distant travel can be 
		//distinguished from claims for nearby travel.
	}
	
	/**
	 * Tests US04.09.01
	 */
	public void testDestinationExpenseAttach() {
		
		//This should test this user story:
		//As a claimant, I want to optionally attach a geolocation to an editable expense 
		//item, so I can record where an expense was incurred.
		ExpenseItem item = new ExpenseItem(null, null, null, 0, null, null);
		Location location = new Location("");
		location.setLatitude(0.0);
		location.setLongitude(0.0);
		UserLocationManager.setHomeLocation(location);
		item.setLocation(UserLocationManager.getHomeLocation());
		assertTrue("Expense item location is not set", 
				item.getLocation() == UserLocationManager.getHomeLocation());
		
	}
	
	/**
	 * Tests US10.01.01
	 */
	
	public void testSetHomeGeo() {
		
		//This should test this user story:
		//As a claimant, I want to set my home geolocation.
		Location location = new Location("");
		location.setLatitude(0.0);
		location.setLongitude(0.0);
		UserLocationManager.setHomeLocation(location);
		assertTrue("Home location not set", location == UserLocationManager.getHomeLocation());
		assertTrue("Distance betweeen home and original is not 0", 
				location.distanceTo(UserLocationManager.getHomeLocation()) == 0);
	}
	
	/**
	 * Tests US10.02.01
	 */
	public void testSpecify() {
		//TODO
		
		//This should test this user story:
		//As a claimant, I want to specify a geolocation assisted 
		//by my mobile device (e.g., GPS) or manually using a map.
	}

	/**
	 * Tests US10.03.01
	 */
	public void testViewGeo() {
		//TODO
		
		//This should test this user story:
		//As a claimant, I want to view any set or attached 
		//geolocation using a map.
	}
}
