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
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * An alert dialog that displays a set
 * location, from UserLocationManager, on a map.
 * If there is no location set, it will show an
 * unmarked map.
 * Before starting the dialog, you should set the 
 * desired location, as follows: 
 * 
 * Location viewLocation = someLocation();
 * UserLocationManager.setViewLocation(viewLocation);
 * MapViewAlertDialog dialog = new MapViewAlertDialog();
 * dialog.show(getFragmentManager(), "View Map");
 */
public class MapViewAlertDialog extends DialogFragment{
	private GoogleMap googleMap;
	private Location location;
	private MarkerOptions marker;
	private View view;
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.map_view_alert_dialog, null);
	    builder.setView(view);
		
	    /* Get the map fragment and set viewing type to hybrid.*/
	    googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map_view)).getMap();
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
	    Button cancel = (Button) view.findViewById(R.id.buttonCloseMaps);
	    
	    cancel.setOnClickListener(closeListener);

	    moveToLocation();
	    return builder.create();
	}
	
	/**
	 * Moves the map the set location,
	 * and adds a marker to it. 
	 * 
	 * Outstanding issues: Consider allowing the 
	 * user to set the zoom depth, currently set to 8.
	 */
	private void moveToLocation(){
		location = UserLocationManager.getViewLocation();
		if(location != null){
			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 8));
			marker = new MarkerOptions().position(ll);
			googleMap.addMarker(marker);
		}
	}
	
	private final OnClickListener closeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Dialog d = getDialog();
			d.dismiss();
		}
	};
	
	/**
	 * You must destroy the map after use,
	 * else you can't open another one.
	 */
	@Override
	public void onDestroyView() {
		// Based on http://stackoverflow.com/questions/17533619/null-pointer-on-inflated-view-when-loading-for-the-second-time-a-google-map-frag April 6th
	    super.onDestroyView();
	    MapFragment f = (MapFragment) getFragmentManager().findFragmentById(R.id.google_map_view);
	    if (f != null){ 
	        getFragmentManager().beginTransaction().remove(f).commit();
	    }
	}

}
