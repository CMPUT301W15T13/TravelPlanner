package adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;

/**
 * Custom array adapter to display the destination information
 * for the claim manager. ALso sets the on click listener for 
 * each element to allow the attachment of locations.
 *
 */
public class DestinationAdapter extends ArrayAdapter<TravelItinerary>{
	public ArrayList<TravelItinerary> travelList;
	
	public DestinationAdapter(Context context, int textViewResourceId, List<TravelItinerary> travelList) {
		super(context, textViewResourceId, travelList);
		this.travelList = (ArrayList<TravelItinerary>) travelList;
	}
	
	/**
	 * Sets up the details in the custom array
	 * elements, including the claim details
	 * and an image to describe it's status.
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.destination_adapter_layout, null);	
		}
		TravelItinerary item = travelList.get(position);
		if (item != null) {
			TextView destinationView = (TextView) view.findViewById(R.id.textViewDestination);
			TextView reasonView = (TextView) view.findViewById(R.id.textViewReason);
			ImageView locationView = (ImageView) view.findViewById(R.id.imageButtonAddedLocation);
			
			destinationView.setText(item.getDestinationName());
			reasonView.setText(item.getDestinationDescription());
			
			
			// Shows the image if the location is set
			Location location = item.getLocation();
			if (location != null) {
				locationView.setVisibility(View.VISIBLE);
			} 
		}
		return view;
	}

}
