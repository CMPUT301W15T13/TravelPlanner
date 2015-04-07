package ca.ualberta.cmput301w15t13.Controllers;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * A Global access point to locations, to allow
 * the users to access home locations, and set 
 * the search/view locations from different activities
 * and dialog fragments.  
 *
 */
public class UserLocationManager {
	static Location homeLocation = null;
	static Location expenseLocation = null;
	static Location viewLocation = null;
	static Location searchLocation = null;
	
	static Listener locationListener = null;	

	public static void setLocationListener(Listener l){
		UserLocationManager.locationListener = l;
	}
	
	/**
	 * A listener set by the user to update a location
	 * to that of the search location. It was make like this
	 * because there are multiple segments of code that need
	 * to get a search location, but the variables they update
	 * are different.
	 */
	public static void listenerUpdate(){
		if(UserLocationManager.locationListener != null){
			UserLocationManager.locationListener.update();
		}
	}
	
	public static Location getHomeLocation() {
		return homeLocation;
	}


	public static void setHomeLocation(Location homeLocation) {
		UserLocationManager.homeLocation = homeLocation;
	}

	public static Location getExpenseLocation() {
		return expenseLocation;
	}

	public static void setExpenseLocation(Location mapLocation) {
		UserLocationManager.expenseLocation = mapLocation;
	}

	public static Location getViewLocation() {
		return viewLocation;
	}

	public static void setViewLocation(Location viewLocation) {
		UserLocationManager.viewLocation = viewLocation;
	}

	public static Location getSearchLocation() {
		return UserLocationManager.searchLocation;
	}

	/**
	 * Called to update the search location.
	 * Make sure that a custom listener has been set before hand,
	 * so that the required location can be updated.
	 * @param homeLocation
	 */
	public static void setSearchLocation(Location searchLocation) {
		UserLocationManager.searchLocation = searchLocation;
	}
	
	public static Location getGPSLocation(Activity activity){
    	LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);
    	Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	return location;
	}
	
	/** 
	 * Called to update the home location, when the gps is accessed.
	 */
    private final static LocationListener listener = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			if(location != null){
				UserLocationManager.setHomeLocation(location);
			}
		}
		/* These methods won't be over-written */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}

		@Override
		public void onProviderEnabled(String provider) {}

		@Override
		public void onProviderDisabled(String provider) {}
    
    };
	

}
