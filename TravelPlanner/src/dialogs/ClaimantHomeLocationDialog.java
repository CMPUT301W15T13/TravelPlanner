package dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.User;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;

/**
 * Dialog pop-up to allow the user to set their home location.
 * Selecting the GPS location will use their last known location
 * if it's available, and selecting the map will allow the user
 * to pick a location from a map.
 *
 */
public class ClaimantHomeLocationDialog extends DialogFragment{
	User user;
	ClaimActivity activity;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = (ClaimActivity) getActivity();
		user = activity.getUser();
	}
	
    final OnClickListener gpsListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);
        	Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        	if(location != null){
        		Toast.makeText(activity,"Added Home Location" ,Toast.LENGTH_SHORT).show();
        		UserLocationManager.setHomeLocation(location);
        	}else{
        		Toast.makeText(activity, "Could not get location", Toast.LENGTH_SHORT).show();
        	}
    		
        	Dialog d = getDialog();
        	d.dismiss();
        }
    };
    
    private final LocationListener listener = new LocationListener(){

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
    
    final Listener locationUpdater = new Listener() {
		
		@Override
		public void update() {
			UserLocationManager.setHomeLocation(UserLocationManager.getSearchLocation());
		}
	};
    
    final OnClickListener mapListener = new OnClickListener() {
	        @Override
			public void onClick(final View v) {
	        	UserLocationManager.setLocationListener(locationUpdater);
	        	MapSearchAlertDialog dialog = new MapSearchAlertDialog();
	        	dialog.show(getFragmentManager(), "Map Searcher");
	      	    Dialog d = getDialog();
	      	    d.dismiss();
	        }
    };
    
    final OnClickListener viewHomeListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	Location homeLocation = UserLocationManager.getHomeLocation();
        	UserLocationManager.setViewLocation(homeLocation);
        	MapViewAlertDialog dialog = new MapViewAlertDialog();
        	dialog.show(getFragmentManager(), "View Location");
      	    Dialog d = getDialog();
      	    d.dismiss();
        }
    };
	
    
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.claimant_home_location_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    Button gpsLocation = (Button) view.findViewById(R.id.buttonGPSLocation);
	    Button mapLocation = (Button) view.findViewById(R.id.buttonMapLocation);
	    Button viewLocation = (Button) view.findViewById(R.id.buttonViewHomeLocation);

	    gpsLocation.setOnClickListener(gpsListener);
	    mapLocation.setOnClickListener(mapListener);
	    viewLocation.setOnClickListener(viewHomeListener);
	    
	    return builder.create();
	}
}
