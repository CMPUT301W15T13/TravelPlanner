package ca.ualberta.cmput301w15t13.Activities;

import android.os.Bundle;
import ca.ualberta.cmput301w15t13.R;

import com.google.android.maps.MapActivity;


public class GeolocationMapActivity extends MapActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.google_maps_layout);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
