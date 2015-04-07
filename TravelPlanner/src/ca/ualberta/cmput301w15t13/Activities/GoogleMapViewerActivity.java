package ca.ualberta.cmput301w15t13.Activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapViewerActivity extends FragmentActivity{
	// Based on https://developers.google.com/maps/documentation/android/ April 6th 2015
	private GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.google_maps_viewer_layout);
		//map =  findViewById(R.id.google_map);
		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map)).getMap();
		getActionBar().hide();
		
		Button closeView = (Button) findViewById(R.id.buttonCloseMaps);
		closeView.setOnClickListener(close);
		
		setLocation();
	}

	private void setLocation(){
		Location myLocation = UserLocationManager.getViewLocation();
		if(myLocation != null){
			LatLng ll = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 5));
	
			googleMap.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_mylocation))
					.position(ll));
		}
	}
	
	private final OnClickListener close = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UserLocationManager.setViewLocation(null); // clear the set location
			finish();
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UserLocationManager.setViewLocation(null); // clear the set location
	}
	
	


}
