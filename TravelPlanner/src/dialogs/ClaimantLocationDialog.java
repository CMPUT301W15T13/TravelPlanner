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
import ca.ualberta.cmput301w15t13.Controllers.User;

/**
 * Dialog pop-up to allow the user to set their home location.
 * Selecting the GPS location will use their last known location
 * if it's available, and selecting the map will allow the user
 * to pick a location from a map.
 *
 */
public class ClaimantLocationDialog extends DialogFragment{
	User user;
	ClaimActivity activity;
	public static final String MOCK_PROVIDER = "mockLocationProvider";
	
	
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
        		Toast.makeText(activity,"Lat: " + location.getLatitude() +
        								"Long: " + location.getLongitude()
        								,Toast.LENGTH_SHORT).show();
        		user.setLocation(location);
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
				user.setLocation(location);
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
    
    final OnClickListener mapListener = new OnClickListener() {
	        @Override
			public void onClick(final View v) {
	      	   Dialog d = getDialog();
	      	   d.dismiss();
	        }
    };
	
    
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.claim_location_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    Button gpsLocation = (Button) view.findViewById(R.id.buttonGPSLocation);
	    Button mapLocation = (Button) view.findViewById(R.id.buttonMapLocation);

	    gpsLocation.setOnClickListener(gpsListener);
	    mapLocation.setOnClickListener(mapListener);
	    
	    return builder.create();
	}
}
