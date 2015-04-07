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
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.User;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;

public class ClaimantGetExpenseLocationDialog extends DialogFragment{
	ExpenseActivity activity;
	int claimIndex;
	User user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = (ExpenseActivity) getActivity();
		user = ClaimFragmentNavigator.getUser();
		
	}
	
    final OnClickListener homeListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	 activity.setExpenseLocation(UserLocationManager.getHomeLocation());
        	 Dialog d = getDialog();
        	 d.dismiss();
        }
    };
    
    final Listener locationUpdater = new Listener() {
		
		@Override
		public void update() {
			activity.setExpenseLocation(UserLocationManager.getSearchLocation());
		}
	};
    
    final OnClickListener mapListener = new OnClickListener() {
	        @Override
			public void onClick(final View v) {
	        	UserLocationManager.setLocationListener(locationUpdater);
	        	MapSearchAlertDialog dialog = new MapSearchAlertDialog();
	        	dialog.show(getFragmentManager(), "Search Map");
	      	    Dialog d = getDialog();
	      	    d.dismiss();
	        }
    };
    
    final OnClickListener viewExpenseLocationListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	Location expenseLocation = UserLocationManager.getExpenseLocation();
        	UserLocationManager.setViewLocation(expenseLocation);
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
	    View view = inflater.inflate(R.layout.claimant_location_picker_dialog, null);
	    builder.setView(view);
	    
	    Button homeLocation = (Button) view.findViewById(R.id.buttonHomeLocation);
	    Button mapLocation = (Button) view.findViewById(R.id.buttonNewMapLocation);
	    Button expenseLocation = (Button) view.findViewById(R.id.buttonViewMapLocation);

	    homeLocation.setOnClickListener(homeListener);
	    mapLocation.setOnClickListener(mapListener);
	    expenseLocation.setOnClickListener(viewExpenseLocationListener);
	    
	    return builder.create();
	}
}