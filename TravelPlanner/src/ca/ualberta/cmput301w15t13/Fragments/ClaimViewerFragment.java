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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import adapters.ClaimAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.Tag;
import dialogs.ApproverCommentDialogFragment;
import dialogs.FilterFragmentDialog;
import exceptions.InvalidUserPermissionException;

/**
 * This fragment is used to view claims and 
 * surface level details, including name, cost, 
 * status and dates. These fields are prone to change
 * or grow. 
 *
 * Outstanding Issues: Getting claimant and approver specific
 * claims should be moved to a controller
 */

public class ClaimViewerFragment extends Fragment {
	public static ClaimAdapter claimAdapter;
	private ArrayList<Claim> claims;
	private ClaimActivity activity;
	private ArrayList<String> filterTags = new ArrayList<String>();
	
	private Listener updateClaimList = new Listener(){

		@Override
		public void update() {
			claims = ClaimListSingleton.getClaimList().getClaimArrayList();
			claims = activity.getUser().getPermittableClaims(claims);
			
			// claims arraylist is a new instance, the adapter is using some other arraylist
			claimAdapter.clear();
			claimAdapter.addAll(claims);
			claimAdapter.notifyDataSetChanged();
		}
		
	};

	/**
	 * Adds the Listeners for the ArrayAdapter that 
	 * holds all of the claims. Other standard listeners
	 * can be added here.
	 */
	private void addListeners(){
		ClaimListSingleton.getClaimList().addListener(updateClaimList);
	}
	
	/**
	 * Binds the array list of claims to the array adapter
	 * and sets the appropriate onClick and onLongClick for 
	 * approvers and claimants.
	 */
	private void initializeAdapter(){
		final ListView claimListView = (ListView) getView().findViewById(R.id.listViewClaim);
		claimListView.setAdapter(claimAdapter);
		
		claimListView.setOnItemClickListener(activity.getUser().getClaimAdapterShortClickListener(getActivity()));
		
		claimListView.setOnItemLongClickListener(activity.getUser().getClaimAdapterLongClickListener(getFragmentManager()));
	}
	
	/**
	 * When permissible for the user and the clicked claim,
	 * change the fragment to the ClaimManager and fill the
	 * fields with the existing data.
	 */
	public void editClaim(int claimIndex){
		Claim submitClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		if(submitClaim.isEditable()){
			ClaimFragmentNavigator.editClaim(claimIndex);
		} else{
			Toast.makeText(getActivity(), "Cannot edit this claim.", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Delete the selected claim and 
	 * update the view.
	 */
	public void deleteClaim(int claimIndex){
		Claim claim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		if(claim.getStatus() != ClaimStatus.statusEnum.SUBMITTED){
			ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
			ClaimListSingleton.getClaimList().notifyListeners();
		}
	}
	
	/**
	 * View the selected claim's claim-level
	 * details.
	 */
	public void viewClaim(int claimIndex){
		ClaimFragmentNavigator.showClaimDetails(claimIndex);
	}
	
	/**
	 * Changes the selected claim's status
	 * to submitted, which allows it to be
	 * viewed by approvers.
	 */
	public void submitClaim(int claimIndex){
		Claim submitClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		try {
			((Claimant) activity.getUser()).submitClaim(submitClaim);
			ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
			ClaimListSingleton.getClaimList().add(submitClaim);
			ClaimListSingleton.getClaimList().notifyListeners();
		} catch (InvalidUserPermissionException e) {
			Toast.makeText(activity, "Cannot submit this claim.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	/**
	 * An approver will return the selected
	 * claim back to the user, with the updated
	 * information (comments). 
	 * 
	 * TODO approver viewer shouldn't be able to see
	 * the returned and approver claims.
	 */
	public void returnClaim(int claimIndex){
		Claim submitClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		((Approver) activity.getUser()).returnClaim(submitClaim);
		ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
		ClaimListSingleton.getClaimList().add(submitClaim);
		ClaimListSingleton.getClaimList().notifyListeners();
	}
	
	/**
	 * Opens a dialog letting the user select which
	 * Tags they want to filter by
	 */
	
	public void openFilterDialog() {
		FilterFragmentDialog dialog = new FilterFragmentDialog();
		int i = 0;
		int size = 0;
		String[] tags = {""};
		boolean[] isSelected = {false};
		try {
	    	ClaimList cl = ClaimListSingleton.getClaimList();
		    TagManager tm = cl.getTagMan();
		    size = tm.size();
		    tags = new String[size];
		    isSelected = new boolean[size];
	    	HashMap<Tag, ArrayList<String>> map = tm.getManager();
	    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
	    		tags[i] = entry.getKey().getTagName();
	    		isSelected[i] = false;
	    		i += 1;
	    	}
		} catch (NullPointerException e) {}
		dialog.setTags(tags);
		dialog.setIsSelected(isSelected);
		dialog.show(getFragmentManager(), "FILTER TAG");
	}
	
	/**
	 * TODO given the tag indices, make an array of
	 * tag names and pass it to the filter function,
	 * and finally filte
	 * @param tagIndexes
	 */
	
	public void filterByTag(ArrayList<Integer> tagIndexes) {
		return;
	}
	
	/**
	 * Upon returning a claim, an approver must write comments for reason why
	 * This method will call comment fragment for approver to comment
	 * returnClaim method will be called once an approver has finished commenting
	 * @param index
	 */
	public void approverComment(int claimIndex) {
		// shows the approver comment dialog fragment
		ApproverCommentDialogFragment dialog = new ApproverCommentDialogFragment();
	    Bundle args = new Bundle();
	    args.putInt("index", claimIndex);
	    dialog.setArguments(args);
		dialog.show(getFragmentManager(), "Approver Comment");
	}
	
	/** 
	 * An approver will approve the selected
	 * claim back to the user, with the updated
	 * information (comments). 
	 */
	public void approveClaim(int claimIndex){
		Claim submitClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		((Approver) activity.getUser()).approveClaim(submitClaim);
		ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
		ClaimListSingleton.getClaimList().add(submitClaim);
		ClaimListSingleton.getClaimList().notifyListeners();
	}
	
	/* Below this is android stuff */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 *  DO NOT do any layout work in here. The layout
		 *  is only initialized by onStart()
		 */
		super.onCreate(savedInstanceState);
		activity = (ClaimActivity) getActivity();
		claims = ClaimListSingleton.getClaimList().getClaimArrayList();
		claims = activity.getUser().getPermittableClaims(claims);

		ClaimViewerFragment.claimAdapter = new ClaimAdapter(activity, R.layout.claim_adapter_layout, this.claims);

	}
	
	@Override
	public void onStart() {
		super.onStart();
		addListeners();
		initializeAdapter();
		
		int visibility = activity.getUser().getButtonVisibility();
		getView().findViewById(R.id.buttonNewClaim).setVisibility(visibility);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claimlist_viewer_layout, container, false);
		return v;
	}
}
