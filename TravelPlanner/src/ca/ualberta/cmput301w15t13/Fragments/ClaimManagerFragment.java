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

import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar.LayoutParams;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/**
 * This fragment is used for creating and editing claims.
 * Both functions use the same layout, so it's more familiar
 * to the user. When creating, all fields are empty, and 
 * when editing, they're populated by existing data.
 *
 */

public class ClaimManagerFragment extends Fragment{
	private EditText nameView, descriptionView;
	private TextView startDateView, endDateView, destinationView;
	private String name, description, startDateText, endDateText;
	private TravelItineraryList itineraryList;
	private Date startDate, endDate;
	private boolean areFieldsComplete, isEditing;
	private int claimIndex;	
	
	//TODO add a back button, in the case they don't want to submit a claim? And maybe
	//force change what the back button does from this screen, in that it moves to the old fragment
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itineraryList = new TravelItineraryList();
		areFieldsComplete = false;
		startDate = new Date();
		endDate = new Date();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claim_manager_layout, container, false);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		nameView = (EditText) getView().findViewById(R.id.editTextClaimName);
		descriptionView = (EditText) getView().findViewById(R.id.editTextClaimDescription);
		destinationView = (TextView) getView().findViewById(R.id.textViewDestinationsList);
		startDateView = (TextView) getView().findViewById(R.id.textViewStartDate);
		endDateView = (TextView) getView().findViewById(R.id.textViewEndDate);
		
		setFields();
	}
	
	/**
	 * Sets the "Mode" of the fragment to edit or create, 
	 * depending where it's called from
	 */
	public void setStateAsEditing(boolean isEditing){
		this.isEditing = isEditing;
	}

	/**
	 * If the claim is going to be edited,
	 * set the fields to the values to match the claim
	 * else clear the fields of old values. 
	 */
	private void setFields(){
		if(isEditing){
			Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
			this.name = editClaim.getUserName();
			this.description = editClaim.getDescription();
			this.startDate = editClaim.getStartDate();
			this.endDate = editClaim.getEndDate();
			this.itineraryList = editClaim.getTravelList();
			
			this.nameView.setText(this.name);
			this.descriptionView.setText(this.description);
			
			// TODO add the text views for dates and itenerary
			
		}else{
			this.nameView.setText("");
			this.descriptionView.setText("");
		}
	}
	
	/**
	 * Opens a new date picker dialog to set the 
	 * start and end dates of a claim. It will parse
	 * the date selected and update the corresponding
	 * text view, passed as textId
	 * @param textId
	 */
	public void openDateDialog(TextView textId) {
		// Based on http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext March 06 2015
		final TextView dateTextView = textId;
		final Calendar myCalendar = Calendar.getInstance();
		
		DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		    	// When the positive button is clicked collect the date data
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        String dateText = Integer.toString(dayOfMonth) + "/" + Integer.toString(monthOfYear + 1)
		        		+ "/" + Integer.toString(year);
		        dateTextView.setText(dateText);
		        
		        addDateData(year, monthOfYear, dayOfMonth, dateTextView);
		    }

		};
		
		new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		        
	}
	
	/**
	 * Method to escape the scope issue inside the 
	 * onDateSet method. Simply sets the local value of 
	 * the start or end date to the local date var.
	 * @param year
	 * @param month
	 * @param day
	 * @param text
	 */
	@SuppressWarnings("deprecation")
	public void addDateData(int year, int month, int day, TextView text){
		Date date;
		if(text == startDateView){
			date = startDate;
		}else{
			date = endDate;
		}
		date.setDate(day);
		date.setMonth(month);
		date.setYear(year);
	}

	/**
	 * Opens the custom dialog to get a new
	 * destination ond it's purpose from the user
	 */
	public void openDestinationDialog() {
		DestinationDialogFragment dialog = new DestinationDialogFragment();
		dialog.show(getFragmentManager(), "TEST TAG");
	}
	
	/**
	 * Creates a new claim based on the fields
	 * that have been updated by updateReferences.
	 * Then adds it to the CLaimlist and updates the
	 * ArrayAdapter
	 * @throws InvalidDateException
	 * @throws InvalidNameException
	 * @throws InvalidUserPermissionException
	 * @throws EmptyFieldException 
	 */
	public void createClaim() throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException{
		if(this.areFieldsComplete){
			Claim newClaim = new Claim(this.name, startDate, endDate, 
				this.description, itineraryList);
			ClaimListSingleton.getClaimList().add(newClaim);
			
		}else{
			Toast.makeText(getActivity(), "Fill in all fields before submitting", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Create a new claim and replace the old one 
	 * with it.
	 * @throws InvalidUserPermissionException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 */
	public void updateClaim() throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException {
		updateReferences();
		Claim newClaim = new Claim(this.name, startDate, endDate, 
				this.description, itineraryList);
		ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
		ClaimListSingleton.getClaimList().add(newClaim);
		//TODO needs a sort method
	}
	
	/**
	 * Updates the class level variables to reflect what's in the
	 * fields in the layout.
	 */
	public void updateReferences(){
		name = nameView.getText().toString().trim() + ""; //add the "" to check for an empty field
		description = descriptionView.getText().toString().trim() + "";
		startDateText = startDateView.getText().toString().trim() + "";
		endDateText = endDateView.getText().toString().trim() + "";
				
		//TODO should we assert they fill in all fields?
		if(!name.equals("") && !description.equals("") &&
				!startDateText.equals("") && !endDateText.equals("") && itineraryList.size() != 0){
			this.areFieldsComplete = true;
		}
	}

	/**
	 * Method of subverting the locality issues within
	 * a custom AlertDialog. It gets creates a new TravelItenerary item
	 * and adds it to the local list. 
	 * @param item
	 * @throws DuplicateException
	 */
	public void addTravelItenerarItem(TravelItinerary item) {
		this.itineraryList.addTravelDestination(item);
		String dest_list = destinationView.getText().toString();
		
		if(!dest_list.equals("")) dest_list += "\n";
		dest_list += "  " + item.getDestinationName() + " : " + item.getDestinationDescription();
		destinationView.setText(dest_list);

		// TODO this needs to change the layout size
		if(itineraryList.size() > 2){
			//If the text view is set to wrap content too early,
			//it looks like the field is too small
			destinationView.setHeight(LayoutParams.WRAP_CONTENT); 
		}
	}

	public void setClaimIndex(int index){
		this.claimIndex = index;
	}

	/**
	 * Returns if the claim is new or 
	 * a modification.
	 * @return
	 */
	public boolean isEditing(){
		return this.isEditing;
	}


}
