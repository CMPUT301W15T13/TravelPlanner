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
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/**
 * This fragment is used for creating and editing claims.
 * Both functions use the same layout, so it's more familiar
 * to the user. When creating, all fields are empty, and 
 * when editting, they're populated by existing data.
 * @author mfritze
 *
 */

public class ClaimManagerFragment extends Fragment{
	EditText claimName, description;
	TextView startDateView, endDateView;
	String nameText, descriptionText;
	TravelItineraryList itineraryList;
	Date startDate, endDate;
	boolean completeFields;
	
	//final Calendar startDateCal = Calendar.getInstance(), endDateCal = Calendar.getInstance();
	
	
	//TODO add a back button, in the case they don't want to submit a claim? And maybe
	//force change what the back button does from this screen, in that it moves to the old fragment
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itineraryList = new TravelItineraryList();
		completeFields = false;
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
		claimName = (EditText) getView().findViewById(R.id.editTextClaimName);
		description = (EditText) getView().findViewById(R.id.editTextClaimDescription);
		startDateView = (TextView) getView().findViewById(R.id.textViewStartDate);
		endDateView = (TextView) getView().findViewById(R.id.textViewEndDate);
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
		Toast.makeText(getActivity(), Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year), Toast.LENGTH_SHORT).show();
	}

	public void openDestinationDialog() {
		DestinationDialogFragment dialog = new DestinationDialogFragment();
		dialog.show(getFragmentManager(), "TEST TAG");
	}
	

	public void createClaim() throws InvalidDateException, InvalidNameException, InvalidUserPermissionException{
		if(true){ // TODO change to check that all fields are filled, maybe in the update Ref function
			Claim newClaim = new Claim(this.nameText, startDate, endDate, 
				this.descriptionText, itineraryList);
			ClaimListSingleton.getClaimList().add(newClaim);
			((ClaimActivity) getActivity()).setFragmentToClaimViewer();
		}else{
			Toast.makeText(getActivity(), "Fill in all fields before submitting", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Updates the class level variables to reflect what's in the
	 * fields in the layout.
	 */
	public void updateReferences(){
		// TODO check fields are not empty
		nameText = claimName.getText().toString().trim() + ""; //add the "" to check for an empty field
		descriptionText = description.getText().toString().trim() + ""; 
		
		
		// for loop to parse the destinations and their reasons
		
		//TODO should we assert they fill in all fields?
		if(!nameText.equals("") && descriptionText.equals("") &&
				startDate != null && endDate != null && itineraryList.size() == 0){
			this.completeFields = true;
		}
	}
}
