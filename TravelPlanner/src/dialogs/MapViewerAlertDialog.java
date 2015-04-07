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
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewerAlertDialog extends DialogFragment{
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
		
	    googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMap();
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
				Toast.makeText(getActivity(), "Location: " + location.getLatitude(), Toast.LENGTH_SHORT).show();
			}
			Dialog d = getDialog();
			d.dismiss();
		}
	};
	
	
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
	
	
}
