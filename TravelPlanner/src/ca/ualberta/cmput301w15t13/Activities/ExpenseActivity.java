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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ExpenseListSingleton;
import ca.ualberta.cmput301w15t13.Fragments.ExpenseManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ExpenseListViewerFragment;
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
 */

public class ExpenseActivity extends Activity {
	
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ExpenseListViewerFragment ExpenseViewerFragment;
	private ExpenseManagerFragment ExpenseManagerFragment;
	private ActionBar actionBar; //Based on http://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities March 06 2015
	//Temporary force claimant true
	//Will most likely need to handle this elegantly in part 5
	//TODO handle isClaimant properly
	private boolean isClaimant = true;
	private String username;
	private int claimIndex;
	private String claimID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_activity_layout);
		
		setActionBar();
		this.actionBar = getActionBar();
		
		this.fm = getFragmentManager();
		ExpenseViewerFragment = new ExpenseListViewerFragment();
		ExpenseManagerFragment = new ExpenseManagerFragment();
		
		//Need to extract passed claim info
		Bundle bundle = getIntent().getExtras();
		claimIndex = bundle.getInt("claimIndex");
		claimID = bundle.getString("claimID");
		//TODO handle isClaimant properly
		//this.isClaimant = intent.getExtras().getBoolean(LoginActivity.ISCLAIMANT);
		//this.username = intent.getStringExtra(LoginActivity.USERID);
		
		// TODO load data
		// TODO add a save file listener
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setFragmentToExpenseViewer();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ExpenseListSingleton.getExpenseItemList().notifyListeners();
	}

	/**
	 * Set the action bar
	 * Very simple and planeactionbar
	 * Useful for screen space economy on expense items
	 */
	private void setActionBar(){
		//Based on http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android Jan 25 2015
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.expense_actionbar_layout, null);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
	}

	/**
	 * Switches the fragment/layout
	 * to the claim viewer.
	 */
	public void setFragmentToExpenseViewer(){
		actionBar.hide();
		//Inspired by
		//http://stackoverflow.com/a/16036693
		//3/15/2015
		Bundle bundle=new Bundle();
		bundle.putInt("claimIndex", claimIndex);
		bundle.putString("claimID", claimID);
		//set Fragmentclass Arguments
		ExpenseViewerFragment.setArguments(bundle);
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.ExpenseViewerFragment, "ExpenseViewer");
		ft.commit();
	}
	
	/**
	 * Switch to the claim manager
	 * fragment/layout
	 */
	public void setFragementToExpenseManager(){
		actionBar.hide();
		//Inspired by
		//http://stackoverflow.com/a/16036693
		//3/15/2015
		Bundle bundle= new Bundle();
		bundle.putInt("claimIndex", claimIndex);
		bundle.putString("claimID", claimID);
		//set Fragmentclass Arguments
		ExpenseManagerFragment.setArguments(bundle);
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.ExpenseManagerFragment, "ExpenseManager");
		ft.commit();
	}
	
	/**
	 * Starts a new claim and switches
	 * the fragment layout to the 
	 * Claim Manager Layout
	 */
	public void newExpenseItem(View v){
		setFragementToExpenseManager();
		ExpenseManagerFragment.setStateAsEditing(false);
	}
	
	/**
	 * Opens the claim manager fragment
	 * sets the claim index to the corresponding
	 * position in the array adapter and
	 * tells the claim manager that it should edit,
	 * not create.
	 * @param index
	 */
	
	// may not be necessary
	public void editClaim(int index) {
		setFragementToExpenseManager();
		ExpenseManagerFragment.setStateAsEditing(true);
		ExpenseManagerFragment.setExpenseIndex(index);
	}
	
	/**
	 * Create a new claim object,
	 * then return to the viewing fragment. 
	 * @throws InvalidUserPermissionException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 */
	public void finishClaim(View v) throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException, InvalidNameException{
		ExpenseManagerFragment.updateReferences();
		if(ExpenseManagerFragment.isEditing()){ //check if we're updating a claim or creating a claim
			ExpenseManagerFragment.updateExpense();
		}
		else{
			ExpenseManagerFragment.createExpenseItem();

		} 
		
		ExpenseListSingleton.getExpenseItemList().notifyListeners();
		setFragmentToExpenseViewer();
	}
	
	/**
	 * Cancels the claim creation/editing
	 * and returns back to the viewing screen.
	 */
	public void cancelClaim(View v){
		setFragmentToExpenseViewer();
	}
	
	/**
	 * Will open a datepicker dialog
	 * but passes the proper startdate textview id
	 * such that it can be updated.
	 */
	public void openDateDialog(View v){
		TextView textId = (TextView) findViewById(R.id.textViewDateExpense);
		ExpenseManagerFragment.openDateDialog(textId);
	}
	
	public String getUsername(){
		return this.username;
	}

	public boolean isClaimant(){
		return isClaimant;
	}
	public void takePicture(View v) {
		Intent intent = new Intent(this, PrimitivePhotoActivity.class);
		startActivity(intent);
	}
	public void editExpense(int index) {
		setFragementToExpenseManager();
		ExpenseManagerFragment.setStateAsEditing(true);
		ExpenseManagerFragment.setExpenseIndex(index);
	}
}