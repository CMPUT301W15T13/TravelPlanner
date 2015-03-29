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

import adapters.ClaimAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import dialogs.ApproverChoiceDialogFragment;
import dialogs.ClaimantChoiceDialogFragment;
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
	private ClaimAdapter claimAdapter;
	private ArrayList<Claim> claims;
	private int claimIndex;
	private String claimID;
	private ClaimActivity activity;
	
	private Listener updateClaimList = new Listener(){

		@Override
		public void update() {
			claims = ClaimListSingleton.getClaimList().getClaimArrayList();
			claims = activity.getUser().getPermittableClaims(claims);
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
		
		claimListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				claimIndex = position;
				claimID = claims.get(claimIndex).getclaimID();
				if(activity.isClaimant()){
					//Toast.makeText(getActivity(), "Open expense edit", Toast.LENGTH_SHORT).show();
					/*
					 * Pass required claim information to the expense portion of the code
					 * IE: The claim holds our expense list so we need to know which
					 * claim to get expense items from
					*/
					if(ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).isEditable()){
						Intent intent = new Intent(activity, ExpenseActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("claimIndex", position);
						bundle.putString("claimID", claimID);
						intent.putExtras(bundle);
						startActivity(intent);
					}else{
						Toast.makeText(getActivity(), "Cannot edit this claim.", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getActivity(), "Open Expense Item View", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		claimListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				claimIndex = position;

				if(activity.isClaimant()){
					new ClaimantChoiceDialogFragment().show(getFragmentManager(), "Long Click Pop-Up");
				}else{
					new ApproverChoiceDialogFragment().show(getFragmentManager(), "Long Click Pop-Up");
				}
				return true;
			}
		});
	}
	
	/**
	 * When permissible for the user and the clicked claim,
	 * change the fragment to the ClaimManager and fill the
	 * fields with the existing data.
	 */
	public void editClaim(){
		Claim submitClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		if(submitClaim.isEditable()){
			activity.editClaim(claimIndex);
		} else{
			Toast.makeText(getActivity(), "Cannot edit this claim.", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Delete the selected claim and 
	 * update the view.
	 */
	public void deleteClaim(){
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
	public void viewClaim(){
			activity.setFragmentToDetailViewer(claimIndex);
	}
	
	/**
	 * Changes the selected claim's status
	 * to submitted, which allows it to be
	 * viewed by approvers.
	 */
	public void submitClaim(){
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
	public void returnClaim(){
		Claim submitClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		((Approver) activity.getUser()).returnClaim(submitClaim);
		ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
		ClaimListSingleton.getClaimList().add(submitClaim);
		ClaimListSingleton.getClaimList().notifyListeners();
	}
	
	/** 
	 * An approver will approve the selected
	 * claim back to the user, with the updated
	 * information (comments). 
	 * 
	 * TODO approver viewer shouldn't be able to see
	 * the returned and approver claims.
	 */
	public void approveClaim(){
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
		this.claimAdapter = new ClaimAdapter(activity, R.layout.claim_adapter_layout, this.claims);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		addListeners();
		initializeAdapter();
		
		if(!activity.isClaimant()){
			getView().findViewById(R.id.buttonNewClaim).setVisibility(View.GONE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claimlist_viewer_layout, container, false);
		return v;
	}
}
