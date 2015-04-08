/*
 * Copyright 2015 James Devito
 * Copyright 2015 Matthew Fritze
 * Copyright 2015 Ben Hunter
 * Copyright 2015 Ji Hwan Kim
 * Copyright 2015 Edwin Rodriguez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;

/** 
 * This is a Custom array adapter used to 
 * display the necessary info for a claim.
 * It should be used when you have to display
 * a list of claim objects, as you would use
 * an ordinary arrayAdapter.
 * 
 * Classes it works with: Claim, ClaimStatus
 * 
 * Sample use
 * ClaimViewerFragment.claimAdapter = new ClaimAdapter(activity, R.layout.claim_adapter_layout, this.claims);
 *
 */
public class ClaimAdapter extends ArrayAdapter<Claim>{
	//based on http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view Jan 24th 2015
	// and http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter Jan 25 2015
	public ArrayList<Claim> claims;
	/* These are distances, in meters to measure distance between location
	 * points. Short is with 10KM and medium is within 1000km.
	 */
	private final int SHORT = 10000, MEDIUM = 1000000;  
	private Context activityContext;

	
	public ClaimAdapter(Context context, int textViewResourceId, List<Claim> claims) {
		super(context, textViewResourceId,claims);
		this.claims = (ArrayList<Claim>) claims;
		activityContext = context;
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
			view = inflater.inflate(R.layout.claim_adapter_layout, null);	
		}
		Claim claim = claims.get(position);
		if (claim != null) {
			TextView titleView = (TextView) view.findViewById(R.id.textViewAdapterClaimTitle);
			TextView dateView = (TextView) view.findViewById(R.id.textViewAdapterClaimDate);
			TextView costView = (TextView) view.findViewById(R.id.textViewAdapterClaimCost);
			ImageView statusView = (ImageView) view.findViewById(R.id.imageViewAdapterStatus);
			
			titleView.setText(claim.getUserName());
			dateView.setText("From: " + claim.getStartDateAsString() + ",  To: " + claim.getEndDateAsString());
			costView.setText(claim.getCost());
			
			// Sets an icon to display the status
			ClaimStatus.statusEnum status = claim.getStatus();
			if (status == ClaimStatus.statusEnum.INPROGRESS) {
				statusView.setImageResource(android.R.drawable.ic_menu_edit);
			} else if (status == ClaimStatus.statusEnum.SUBMITTED) {
				statusView.setImageResource(android.R.drawable.ic_dialog_email);
			} else if (status == ClaimStatus.statusEnum.RETURNED) {
				statusView.setImageResource(android.R.drawable.ic_menu_revert);
			} else { // CLOSED
				statusView.setImageResource(android.R.drawable.ic_menu_myplaces);
			}
			
			/* Sets the color of the title of the expense to red, yellow or green
			 * depending on how far away the expense is from the home location
			 */
			Location homeLocation, claimLocation;
			homeLocation = UserLocationManager.getHomeLocation();
			//jeeeezus better make sure this NEVER NullPointers
			claimLocation = claim.getTravelList().getTravelArrayList().get(0).getLocation();
			Resources r = ClaimFragmentNavigator.getResources();
			if(claimLocation != null && homeLocation != null){
				double distance = homeLocation.distanceTo(claimLocation);
				if(distance < SHORT){
					titleView.setTextColor(r.getColor(R.color.location_close));
				}else if (distance < MEDIUM){
					titleView.setTextColor(r.getColor(R.color.location_medium));
				}else{
					titleView.setTextColor(r.getColor(R.color.location_far));
				}
			}else{ /* No location, set text to white */
				titleView.setTextColor(r.getColor(R.color.text));
			}
			
			/* Adds destinations to the claim adapter */
			LinearLayout destListView = (LinearLayout) view.findViewById(R.id.destinationClaimList);
			ArrayList<TravelItinerary> destList = claim.getTravelList().getTravelArrayList();
			
			destListView.removeAllViews();
			
			for(TravelItinerary item : destList){
				TextView destItem = new TextView(activityContext);
				destItem.setText(item.getDestinationName() + ": " + item.getDestinationDescription());
				destListView.addView(destItem);
			}
			
			
			LinearLayout tagListView = (LinearLayout) view.findViewById(R.id.tagClaimList);
			TextView tagItem = new TextView(activityContext);
			tagItem.setText(claim.getTagsAsString());
			tagListView.removeAllViews();
			tagListView.addView(tagItem);
		}
		return view;
	}
}