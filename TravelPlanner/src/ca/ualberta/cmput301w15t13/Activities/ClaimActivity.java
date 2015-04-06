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
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Controllers.User;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/**
 * This activity is used to manage claims for all users.
 * You can view all claims you have permission to see,
 * whether your own claims or submitted claims.
 * From this activity you can also create, edit, and 
 * approve claims which are supported by corresponding 
 * fragments.
 * Classes it works with: ClaimFagmentNavigator, User
 * 
 * Overall It acts as the base activity of the App and should
 * be used this way.
 * 
 * Sample use:
 * Intent intent = new Intent(this, ClaimActivity.class);
 * startActivity(intent);
 */

public class ClaimActivity extends Activity  {
	private User user;
	private Location currentLocation;
	
	public Location getCurrentLocation() {
		return currentLocation;
	}


	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}


	public User getUser() {
		return this.user;
	}
	

	/* OnClick button methods */ 
	
	/**
	 * Will open a date picker dialog
	 * but passes the proper start date text view id
	 * such that it can be updated.
	 */
	public void openStartDateDialog(View v){
		TextView textId = (TextView) findViewById(R.id.textViewStartDate);
		ClaimFragmentNavigator.openStartDateDialog(textId);
	}
	
	/**
	 * Will open a date picker dialog
	 * but passes the proper end date text view id
	 * such that it can be updated.
	 */
	public void openEndDateDialog(View v){
		TextView textId = (TextView) findViewById(R.id.textViewEndDate);
		ClaimFragmentNavigator.openEndDateDialog(textId);
	}
	
	/**
	 * Opens a destination dialog
	 * for adding the location and reason
	 * for travel.
	 */
	public void addDestination(View v) {
		ClaimFragmentNavigator.addDestination();
	}
	
	/**
	 * Starts a new claim and switches
	 * the fragment layout to the 
	 * Claim Manager Layout
	 */
	public void newClaim(View v){
		ClaimFragmentNavigator.newClaim();
	}
	
	/**
	 * Opens a dialog for the user to
	 * select Tags to filter the claimList
	 * by.
	 */
	public void filterClaim() {
		ClaimFragmentNavigator.filterClaim();
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
		ClaimFragmentNavigator.finishClaim();
	}
	
	/**
	 * Cancels the claim creation/editing
	 * and returns back to the viewing screen.
	 */
	public void cancelClaim(View v){
		ClaimFragmentNavigator.showClaimViewer();
	}
	
	/**
	 * Called when the user wants to add
	 * a tag, opens a dialog which also has
	 * options to edit current tags
	 * @param v
	 */
	public void addTag(View v) {
		ClaimFragmentNavigator.openTag();
	}
	
	/* On Click Listeners */
	
	private OnClickListener filterListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			filterClaim();
		}
	};
	
	private OnClickListener locationListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ClaimFragmentNavigator.openLocationDialog();
		}
	};
	
	/* Below this is android stuff */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_activity_layout);
		
		ClaimFragmentNavigator.createInstance(getFragmentManager());
		
		DataManager.setCurrentContext(this.getApplicationContext());
		
		Intent intent = getIntent();
		String username = intent.getStringExtra(LoginActivity.USERID);
		boolean isClaimant = intent.getBooleanExtra(LoginActivity.ISCLAIMANT,true);
		if(isClaimant){
			this.user = new Claimant(username);
		}else{
			this.user = new Approver(username);
		}
		setActionBar();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ClaimFragmentNavigator.showClaimViewer();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		DataManager.setCurrentContext(getApplicationContext());
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
			
		Button filterButton = (Button) findViewById(R.id.buttonClaimFilter);		
		filterButton.setOnClickListener(filterListener);
		
		Button locationButton = (Button) findViewById(R.id.buttonHomeLocation);
		locationButton.setOnClickListener(locationListener);
		locationButton.setVisibility(user.getButtonVisibility());
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
	}
}
