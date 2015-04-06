package dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.GeolocationMapActivity;
import ca.ualberta.cmput301w15t13.Controllers.User;

public class ClaimantGetLocationDialog extends DialogFragment{
	User user;
	ClaimActivity activity;
	public static final String MOCK_PROVIDER = "mockLocationProvider";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = (ClaimActivity) getActivity();
		user = activity.getUser();
	}
	
    final OnClickListener homeListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	 Dialog d = getDialog();
        	 d.dismiss();
        }
    };
    

    
    final OnClickListener mapListener = new OnClickListener() {
	        @Override
			public void onClick(final View v) {
	        	Intent intent = new Intent(getActivity(), GeolocationMapActivity.class);
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
	    View view = inflater.inflate(R.layout.claimant_location_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    Button homeLocation = (Button) view.findViewById(R.id.buttonHomeLocation);
	    Button mapLocation = (Button) view.findViewById(R.id.buttonNewMapLocation);

	    homeLocation.setOnClickListener(homeListener);
	    mapLocation.setOnClickListener(mapListener);
	    
	    return builder.create();
	}
}
