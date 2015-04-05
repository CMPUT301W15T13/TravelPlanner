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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;

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
	
	public ClaimAdapter(Context context, int textViewResourceId, List<Claim> claims) {
		super(context, textViewResourceId,claims);
		this.claims = (ArrayList<Claim>) claims;
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
		}
		return view;
	}
}