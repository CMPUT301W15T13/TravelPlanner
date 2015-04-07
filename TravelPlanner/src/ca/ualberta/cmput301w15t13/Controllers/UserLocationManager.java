package ca.ualberta.cmput301w15t13.Controllers;

import android.location.Location;

public class UserLocationManager {
	static Location homeLocation = null;
	static Location expenseLocation = null;
	static Location viewLocation = null;
	static Location searchLocation = null;
	
	static Listener locationListener = null;	

	public static void setLocationListener(Listener l){
		UserLocationManager.locationListener = l;
	}
	
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

	public static void setSearchLocation(Location searchLocation) {
		UserLocationManager.searchLocation = searchLocation;
	}
	

}
