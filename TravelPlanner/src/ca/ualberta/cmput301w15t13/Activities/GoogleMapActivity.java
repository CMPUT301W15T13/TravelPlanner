package ca.ualberta.cmput301w15t13.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import ca.ualberta.cmput301w15t13.R;

import com.google.android.gms.maps.GoogleMap;

public class GoogleMapActivity extends FragmentActivity {
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.google_maps_layout);
	}


}
