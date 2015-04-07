package dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.GoogleMapViewerActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;
import ca.ualberta.cmput301w15t13.Controllers.User;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;

public class ClaimantGetDestinationLocationDialog extends DialogFragment{
	ClaimActivity activity;
	int travelItemIndex, claimIndex;
	User user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = (ClaimActivity) getActivity();
		user = activity.getUser();
		travelItemIndex = getArguments().getInt("travelIndex");
		claimIndex = getArguments().getInt("claimIndex");
	}
	
    final OnClickListener homeListener = new OnClickListener() {
    	
        @Override
		public void onClick(final View v) {
        	 TravelItinerary item = ClaimFragmentNavigator.getFragmentManagerTravelItinenary(travelItemIndex);
        	 Location location = UserLocationManager.getHomeLocation();
        	 if(location != null){
        		 item.setLocation(location);
        	 }else {
        		 Toast.makeText(activity, "Set Home Location first.", Toast.LENGTH_SHORT).show();
        	 }
        	 Dialog d = getDialog();
        	 d.dismiss();
        	 ClaimFragmentNavigator.updateDestinations();
        }
    };
   
    
    final OnClickListener mapListener = new OnClickListener() {
	        @Override
			public void onClick(final View v) {
	        	Intent intent = new Intent(getActivity(), GoogleMapViewerActivity.class);
	        	startActivity(intent);
	      	    Dialog d = getDialog();
	      	    d.dismiss();
	      	    ClaimFragmentNavigator.updateDestinations();
	        }
    };
	
    final OnClickListener viewMapListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	Intent intent = new Intent(getActivity(), GoogleMapViewerActivity.class);
        	startActivity(intent);
      	    Dialog d = getDialog();
      	    d.dismiss();
        }
    };
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.claimant_location_picker_dialog, null);
	    builder.setView(view);
	    
	    Button homeLocation = (Button) view.findViewById(R.id.buttonHomeLocation);
	    Button mapLocation = (Button) view.findViewById(R.id.buttonNewMapLocation);
	    Button viewLocation = (Button) view.findViewById(R.id.buttonViewMapLocation);

	    homeLocation.setOnClickListener(homeListener);
	    mapLocation.setOnClickListener(mapListener);
	    viewLocation.setOnClickListener(viewMapListener);
	    
	    return builder.create();
	}
}
