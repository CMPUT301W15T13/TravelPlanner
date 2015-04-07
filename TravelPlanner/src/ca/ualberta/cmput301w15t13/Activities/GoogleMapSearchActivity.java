package ca.ualberta.cmput301w15t13.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

public class GoogleMapSearchActivity extends FragmentActivity {
	// Based on https://developers.google.com/maps/documentation/android/ April 6th 2015
	private GoogleMap googleMap;
	private MarkerOptions marker;
	private GeoPoint point;

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.google_maps_search_layout);
		//map =  findViewById(R.id.google_map);
		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_search)).getMap();
		getActionBar().hide();
		
		Button closeView = (Button) findViewById(R.id.buttonCloseMaps);
		Button addLocation = (Button) findViewById(R.id.buttonAddLocation);
		closeView.setOnClickListener(close);
		addLocation.setOnClickListener(addLocationListener);
		setLocation();
	}

	private void setLocation(){
		Location myLocation = UserLocationManager.getViewLocation();
		if(myLocation != null){
			LatLng ll = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 5));
		}
	}
	private final OnClickListener close = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UserLocationManager.setViewLocation(null); // clear the set location
			finish();
		}
	};
	
	private final OnClickListener addLocationListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UserLocationManager.setViewLocation(null); // clear the set location
			finish();
		}
	};
	
	
	class MapOverlay extends com.google.android.maps.Overlay
	{ 
		// Based on http://stackoverflow.com/questions/5375654/how-to-implement-google-maps-search-by-address-in-android April 6th 2015
	    public boolean draw(Canvas canvas, com.google.android.maps.MapView mapView, 
	    boolean shadow, long when) 
	    {
	        super.draw(canvas, mapView, shadow);                   

	        //translate the GeoPoint to screen pixels
	        Point screenPts = new Point();
	        mapView.getProjection().toPixels(point, screenPts);

	        Bitmap bmp = BitmapFactory.decodeResource(
	            getResources(), android.R.drawable.ic_menu_mylocation);            
	        canvas.drawBitmap(bmp, screenPts.x, screenPts.y-32, null);         
	        return true;
	    }
	} 
}