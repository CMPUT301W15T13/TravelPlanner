package dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ca.ualberta.cmput301w15t13.R;

public class ClaimantLocationDialog extends DialogFragment{
	
    final OnClickListener gpsListener = new OnClickListener() {
        @Override
		public void onClick(final View v) {

      	   Dialog d = getDialog();
      	   d.dismiss();
        }
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
