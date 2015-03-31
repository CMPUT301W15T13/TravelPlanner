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

package ca.ualberta.cmput301w15t13.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * Gives a detailed, non-editable view 
 * of a claim. 
 * 
 * Outstanding Issues: Approvers should be able
 * to add comments to a claim, as well as view their 
 * comments
 */
public class ClaimDetailViewerFragment extends Fragment {
	private Claim claim;

	final OnClickListener closeFragment = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			((ClaimActivity) getActivity()).setFragmentToClaimViewer();
		}
	}; 
	
	/**
	 * Sets all of the fields in the form to the values set
	 * by the editing claim claim.
	 */
	private void initializeFields(){
		TextView name = (TextView) getView().findViewById(R.id.textViewClaimName);
		TextView startDate = (TextView) getView().findViewById(R.id.textViewStartDate);
		TextView endDate = (TextView) getView().findViewById(R.id.textViewEndDate);
		TextView destination = (TextView) getView().findViewById(R.id.textViewDestinationList);
		TextView descrption = (TextView) getView().findViewById(R.id.textViewDescription);
		TextView tags = (TextView) getView().findViewById(R.id.textViewTagsList);
		TextView comments = (TextView) getView().findViewById(R.id.textViewComments);
		
		name.setText(claim.getUserName());
		startDate.setText(claim.getStartDateAsString());
		endDate.setText(claim.getEndDateAsString());
		destination.setText(claim.getTravelItineraryAsString());
		descrption.setText(claim.getDescription());
		tags.setText(claim.getTagsAsString());
		
		/**
		 * Currently our claim class has addcomment method that takes multiple approver's comment
		 * this seems to be conflicting from what I know, which is that only one approver can
		 * comment upon the returning
		 * It's not possible that multiple approver comments and return at the same time
		 * 
		 * I need to come up with the way to setText to the comment TextView
		 * Perhaps I need to change the claim class addcomment & getcomment methods
		 */
	}
	
	public void setClaim(int i){
		this.claim = ClaimListSingleton.getClaimList().getClaimAtIndex(i);
	}
	
	/* Below this is android stuff */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 *  DO NOT do any layout work in here. The layout
		 *  is only initialized by onStart()
		 */
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Button done = (Button) getView().findViewById(R.id.buttonDone);
		done.setOnClickListener(closeFragment);
		initializeFields();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claim_view_layout, container, false);
		return v;
	}
}