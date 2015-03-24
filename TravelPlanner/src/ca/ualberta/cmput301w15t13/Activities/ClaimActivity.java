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

package ca.ualberta.cmput301w15t13.Activities;

import persistanceController.DataManager;
import persistanceModel.LoadASyncTask;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.User;
import ca.ualberta.cmput301w15t13.Fragments.ClaimDetailViewerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/**
 * This activity is used to manage claims for all users.
 * You can view all claims you have permission to see,
 * whether thats your own claims or submitted claims.
 * From this activity you can also create, edit, and 
 * approve claims which are supported by corresponding 
 * fragments.
 * 
 * Outstanding Issues: Searching of claims by tag is unimplemented
 */

public class ClaimActivity extends Activity {
	
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ClaimViewerFragment claimViewerFragment;
	private ClaimManagerFragment claimManagerFragment;
	private ClaimDetailViewerFragment claimDetailViewerFragment;
	private ActionBar actionBar; //Based on http://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities March 06 2015
	private boolean isClaimant;
	private User user;

	/**
	 * Switches the fragment/layout
	 * to the claim viewer.
	 */
	public void setFragmentToClaimViewer(){
		actionBar.show();
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimViewerFragment, "ClaimViewer");
		ft.commit();
	}
	
	/**
	 * Switch to the claim manager
	 * fragment/layout
	 */
	public void setFragementToClaimManager(){
		actionBar.hide();
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimManagerFragment, "ClaimManager");
		ft.commit();
	}
	
	/**
	 * Switch to the fragment
	 * that views claim details
	 */
	public void setFragmentToDetailViewer(int index){
		actionBar.hide();
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimDetailViewerFragment, "ClaimDetailsViewer");
		ft.commit();
		
		claimDetailViewerFragment.setClaim(index);
	}
	
	/**
	 * Starts a new claim and switches
	 * the fragment layout to the 
	 * Claim Manager Layout
	 */
	public void newClaim(View v){
		setFragementToClaimManager();
		claimManagerFragment.setStateAsEditing(false);
	}
	
	/**
	 * Opens the claim manager fragment
	 * sets the claim index to the corresponding
	 * position in the array adapter and
	 * tells the claim manager that it should edit,
	 * not create.
	 * @param index
	 */
	public void editClaim(int index) {
		setFragementToClaimManager();
		claimManagerFragment.setStateAsEditing(true);
		claimManagerFragment.setClaimIndex(index);
	}
	
	/**
	 * Create a new claim object,
	 * then return to the viewing fragment. 
	 * Whenever claim details are being set, if it's a new claim,
	 * you cannot finish until everything is filled, so it will only
	 * allow you to change fragments when createClaim() returns true.
	 * @throws InvalidUserPermissionException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 */
	public void finishClaim(View v) throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException, InvalidNameException{
		claimManagerFragment.updateReferences();
		boolean newData = true;
		if(claimManagerFragment.isEditing()){ //check if we're updating a claim or creating a claim
			claimManagerFragment.updateClaim();
		}
		else{
			newData = claimManagerFragment.createClaim();
		} 
		if(newData){
			ClaimListSingleton.getClaimList().notifyListeners();
			setFragmentToClaimViewer();
		}
	}
	
	/**
	 * Cancels the claim creation/editing
	 * and returns back to the viewing screen.
	 */
	public void cancelClaim(View v){
		setFragmentToClaimViewer();
	}
	
	/**
	 * Will open a date picker dialog
	 * but passes the proper start date text view id
	 * such that it can be updated.
	 */
	public void openStartDateDialog(View v){
		TextView textId = (TextView) findViewById(R.id.textViewStartDate);
		claimManagerFragment.openDateDialog(textId);
	}
	
	/**
	 * Will open a date picker dialog
	 * but passes the proper end date text view id
	 * such that it can be updated.
	 */
	public void openEndDateDialog(View v){
		TextView textId = (TextView) findViewById(R.id.textViewEndDate);
		claimManagerFragment.openDateDialog(textId);
	}
	
	/**
	 * Opens a destination dialog
	 * for adding the location and reason
	 * for travel.
	 */
	public void addDestination(View v) {
		claimManagerFragment.openDestinationDialog();
	}
	
	public void addTag(View v) {
		claimManagerFragment.openTagDialog();
	}

	public String getUsername() {
		return this.user.getName();
	}

	public boolean isClaimant() {
		return isClaimant;
	}
	
	public User getUser() {
		return this.user;
	}

	/* Below this is android stuff */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_activity_layout);
		// TODO make a better method for removing listeners
		//ClaimListSingleton.getClaimList().clearListeners();
		
		setActionBar();
		this.actionBar = getActionBar();
		
		this.fm = getFragmentManager();
		claimViewerFragment = new ClaimViewerFragment();
		claimManagerFragment = new ClaimManagerFragment();
		claimDetailViewerFragment = new ClaimDetailViewerFragment();
		
		Intent intent = getIntent();
		this.isClaimant = intent.getExtras().getBoolean(LoginActivity.ISCLAIMANT);
		String username = intent.getStringExtra(LoginActivity.USERID);
		this.user = User.getUserByUsername(username);
		
		//load data
		DataManager.setOnlineMode();
		DataManager.loadClaimsByUserName(username);
		//new LoadASyncTask().execute(username);
		
		//this.notifyAll();
		// TODO add a save file listener
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setFragmentToClaimViewer();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ClaimListSingleton.getClaimList().notifyListeners();
	}

	/**
	 * Set the action bar
	 * to the corresponding search and
	 * sort settings for claim viewing.
	 */
	@SuppressLint("InflateParams")
	private void setActionBar(){
		//Based on http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android Jan 25 2015
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.claim_actionbar_layout, null);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		
		ImageButton searchButton = (ImageButton) findViewById(R.id.buttonSearchClaim);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText searchBar = (EditText) findViewById(R.id.editTextSearchClaims);
				String searchMessage = searchBar.getText().toString();
				// TODO test for not null and not empty throw exception thing
				//TODO actually search
				Toast.makeText(getBaseContext(), searchMessage, Toast.LENGTH_SHORT).show();
			}
		});
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
	}
}
