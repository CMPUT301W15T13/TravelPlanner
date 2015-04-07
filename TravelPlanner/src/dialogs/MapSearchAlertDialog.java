package dialogs;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An Alert Dialog that allows the user to search a location
 * and add it to either an expense item, TraverItinerary item,
 * or home location.
 * Before starting the search dialog, it needs to know what to do with 
 * the location once it has been selected. To do so, create a custom listener, for example:
 * 
 * final Listener locationUpdater = new Listener() {
		
		@Override
		public void update() {
			TravelItinerary item = ClaimFragmentNavigator.getFragmentManagerTravelItinenary(travelItemIndex);
			item.setLocation(UserLocationManager.getSearchLocation());
       	 	ClaimFragmentNavigator.updateDestinations();
		}
	};
 *
 * Then you can start the dialog.
 * 
 * 	  MapSearchAlertDialog dialog = new MapSearchAlertDialog();
      dialog.show(getFragmentManager(), "Search Location");
 * 
 */
public class MapSearchAlertDialog extends DialogFragment{
	private Geocoder geoCoder;
	private GoogleMap googleMap;
	private Location location;
	private MarkerOptions marker;
	private View view;
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.map_search_alert_dialog, null);
	    builder.setView(view);
		
	    /* Set the map fragment */
	    googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map_search)).getMap();
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		geoCoder = new Geocoder(getActivity());
	    
	    Button cancel = (Button) view.findViewById(R.id.buttonCloseMaps);
	    Button addLocation = (Button) view.findViewById(R.id.buttonAddLocation);
	    ImageButton search = (ImageButton) view.findViewById(R.id.imageButtonSearchMap);
	    
	    cancel.setOnClickListener(closeListener);
	    addLocation.setOnClickListener(addLocationListener);
	    search.setOnClickListener(searchListener);
	    
	    return builder.create();
	}
	private final OnClickListener closeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Dialog d = getDialog();
			d.dismiss();
		}
	};
	
	private final OnClickListener addLocationListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(location != null){
				UserLocationManager.setSearchLocation(location);
				UserLocationManager.listenerUpdate();
			}
			Dialog d = getDialog();
			d.dismiss();
		}
	};
	
	/**
	 * Take the text in the search box, and get a list of at most 1
	 * address that could match the search text. Then turn that into a location,
	 * move to it, and add a marker on it.
	 */
	private final OnClickListener searchListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText searchBar = (EditText) view.findViewById(R.id.editTextMapSearch);
			String search = searchBar.getText().toString();
			List<android.location.Address> addresses = null;
			try {
				// tries to match the search text to at most 1 location.
				addresses = geoCoder.getFromLocationName(search,1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(addresses != null && addresses.size() > 0){
				double lat = addresses.get(0).getLatitude(), lon = addresses.get(0).getLongitude();
				location = new Location("New");
				location.setLongitude(lon);
				location.setLatitude(lat);
				
				LatLng ll = new LatLng(lat, lon);
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 8));
				marker = new MarkerOptions().position(ll);
				googleMap.addMarker(marker);
			}
		}
	};
	
	/**
	 * The map must be destroyed, else you can't start
	 * another one.
	 */
	@Override
	public void onDestroyView() {
		// Based on http://stackoverflow.com/questions/17533619/null-pointer-on-inflated-view-when-loading-for-the-second-time-a-google-map-frag April 6th
	    super.onDestroyView();
	    MapFragment f = (MapFragment) getFragmentManager().findFragmentById(R.id.google_map_search);
	    if (f != null){ 
	        getFragmentManager().beginTransaction().remove(f).commit();
	    }
	}
	
}
