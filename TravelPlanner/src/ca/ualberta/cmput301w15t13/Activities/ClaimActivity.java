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

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;

/**
 * This activity is used to manage claims for all users.
 * You can view all claims you have permission to see,
 * whether thats your own claims or submitted claims.
 * From this activity you can also create, edit, and 
 * approve claims which are supported by corresponding 
 * fragments.
 * @author mfritze
 *
 */

public class ClaimActivity extends Activity {
	
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ClaimViewerFragment claimViewerFragment;
	private ClaimManagerFragment claimManagerFragment;
	private ActionBar actionBar; //Based on http://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities March 06 2015

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_activity_layout);
		setActionBar();
		this.actionBar = getActionBar();
		
		this.fm = getFragmentManager();
		claimViewerFragment = new ClaimViewerFragment();
		claimManagerFragment = new ClaimManagerFragment();
		
		// TODO load data
	}
	
	/**
	 * Set the action bar
	 * to the corresponding search and
	 * sort settings for claim viewing.
	 */
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

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	
	@Override
	protected void onStart() {
		super.onStart();
		setFragmentToClaimViewer();
	}

	/**
	 * Switches the fragment/layout
	 * to the claim viewer.
	 */
	public void setFragmentToClaimViewer(){
		actionBar.show();
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimViewerFragment);
		ft.commit();
	}
	
	/**
	 * Switch to the claim manager
	 * fragment/layout
	 */
	public void setFragementToClaimManager(){
		actionBar.hide();
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimManagerFragment);
		ft.commit();
	}
	
	/**
	 * Starts a new claim and switches
	 * the fragment layout to the 
	 * Claim Manager Layout
	 */
	public void newClaim(View v){
		setFragementToClaimManager();
	}
	
	/**
	 * Create a new claim object,
	 * then return to the viewing fragment. 
	 */
	public void finishClaim(View v){
		//TODO
		//ClaimManagerFragment.manageClaim();
		setFragmentToClaimViewer();
	}
	
	/**
	 * Will open a datepicker dialog
	 * but passes the proper startdate textview id
	 * such that it can be updtated.
	 */
	public void openStartDateDialog(View v){
		TextView textId = (TextView) findViewById(R.id.textViewStartDate);
		claimManagerFragment.openDateDialog(textId);
	}
	
	/**
	 * Will open a datepicker dialog
	 * but passes the proper end date textview id
	 * such that it can be updtated.
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
	public void addDestination(View v){
		claimManagerFragment.openDestinationDialog();
	}
	
	
}
