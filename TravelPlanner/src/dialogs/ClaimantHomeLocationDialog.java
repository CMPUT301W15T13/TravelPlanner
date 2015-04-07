package dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.location.Location;
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
    		Location homeLocation = UserLocationManager.getGPSLocation(activity);
    		if(homeLocation != null){
        		UserLocationManager.setHomeLocation(homeLocation);
        		Toast.makeText(activity, "Home Location Set", Toast.LENGTH_SHORT).show();
    		}else{
        		Toast.makeText(activity, "Couldn't set Home Location", Toast.LENGTH_SHORT).show();
    		}
        	Dialog d = getDialog();
        	d.dismiss();
        }
    };
    

    
    final Listener locationUpdater = new Listener() {
		
		@Override
		public void update() {
			Location homeLocation = UserLocationManager.getSearchLocation();
    		if(homeLocation != null){
        		UserLocationManager.setHomeLocation(homeLocation);
        		Toast.makeText(activity, "Home Location Set", Toast.LENGTH_SHORT).show();
    		}else{
        		Toast.makeText(activity, "Couldn't set Home Location", Toast.LENGTH_SHORT).show();
    		}
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
