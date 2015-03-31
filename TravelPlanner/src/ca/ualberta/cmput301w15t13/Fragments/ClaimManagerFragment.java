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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.EditTag;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.Tag;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import dialogs.ApproverCommentDialogFragment;
import dialogs.DestinationDialogFragment;
import dialogs.TagDialogFragment;
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
 * Outstanding Issues: Change the back button to change fragments, 
 * not activities.
 */

public class ClaimManagerFragment extends Fragment{
	private EditText descriptionView;
	private ArrayList<Tag> tagList;
	/**
	 * commentView is used to show the comments written by approver
	 * commentView will be showed in pair
	 * ex)
	 * John, "This claim is not completed"
	 * Doe, "This claim lacks information"
	 * Susan, "This claim needs more description"
	 * and will be showed in order of entry
	 */
	private TextView startDateView, endDateView, destinationView, tagView, commentView;
	private String description, startDateText, endDateText;
	private TravelItineraryList itineraryList;
	private Date startDate, endDate;
	private boolean incompleteFields, invalidDates, isEditing;
	private int claimIndex;	
	private ClaimActivity activity;
	/**
	 *  approverComments will have type ArrayList<String> after the getComments() method
	 */
	private ArrayList<String> approverComments;
	
	//TODO force change what the back button does from this screen, in that it moves to the old fragment
	

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
			this.description = editClaim.getDescription();
			this.startDate = editClaim.getStartDate();
			this.endDate = editClaim.getEndDate();
			this.itineraryList = editClaim.getTravelList();
			this.descriptionView.setText(this.description);
			this.approverComments = editClaim.getComments();
			
			this.startDateView.setText(editClaim.getStartDateAsString());
			this.endDateView.setText(editClaim.getEndDateAsString());
			this.destinationView.setText(editClaim.getTravelItineraryAsString());
			this.tagView.setText(editClaim.getTagsAsString());
			
			//changes the tag button text
			Button tagButton = (Button) getView().findViewById(R.id.addTags);
			tagButton.setText("Edit");
		}else{
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
		Date date = new Date();
		date.setDate(day);
		date.setMonth(month);
		date.setYear(year);
		
		if(text == startDateView){
			startDate = date;
		}else{
			endDate = date;
		}
		if(startDate != null && endDate != null
			&& endDate.before(startDate)){
			invalidDates = true;
		}else{
			invalidDates = false;
		}
			
		// TODO make sure that this checks the start date is before the end date
	}

	/**
	 * Opens the custom dialog to get a new
	 * destination and it's purpose from the user
	 */
	public void openDestinationDialog() {
		DestinationDialogFragment dialog = new DestinationDialogFragment();
		dialog.show(getFragmentManager(), "TEST TAG");
	}
	
	/**
	 * Creates a new claim based on the fields
	 * that have been updated by updateReferences.
	 * Then adds it to the CLaimlist and updates the
	 * ArrayAdapter.
	 * Returns true only when a claim has been created
	 * @throws InvalidDateException
	 * @throws InvalidNameException
	 * @throws InvalidUserPermissionException
	 * @throws EmptyFieldException
	 * @return valid claim created 
	 */
	
	public boolean createClaim() throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException{
		if(invalidDates){
			Toast.makeText(getActivity(), "Start Date must be before End Date", Toast.LENGTH_SHORT).show();
			return false;
		}else if(incompleteFields){
			Toast.makeText(getActivity(), "Fill in all fields before submitting", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			Claim newClaim = new Claim(activity.getUsername(), startDate, endDate, 
					this.description, itineraryList);
			newClaim.tags = tagList;
			ClaimList claimlist = ClaimListSingleton.getClaimList();
			claimlist.add(newClaim);
			
			// Get the tagManager for the claimList or make a new one
			// update it with all the added tags and the related claimId
			if (claimlist.getTagMan() == null) {
				TagManager tm = new TagManager();
				String claimId = newClaim.getclaimID();
				
				for (int i = 0; i < tagList.size(); i++) {
					tm.add(tagList.get(i), claimId);
				}
				claimlist.setTagMan(tm);
			} else { 
				TagManager tm = claimlist.getTagMan();
				String claimId = newClaim.getclaimID();
				
				for (int i = 0; i < tagList.size(); i++) {
					tm.add(tagList.get(i), claimId);
				}
				claimlist.setTagMan(tm);	
			}
			return true;
		}
	}
	
	/**
	 * Opens the custom dialog to get a new
	 * tag from the user
	 */
	public void openTagDialog() {
		if (isEditing) {
			Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
			Intent intent = new Intent(activity, EditTag.class);
			Bundle bundle = new Bundle();
			bundle.putString("ID", editClaim.getclaimID());
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			TagDialogFragment dialog = new TagDialogFragment();
			dialog.show(getFragmentManager(), "TEST TAG");
		}
	}
	
	/**
	 * This method is called for approver to return a claim with comments
	 * I'm not sure where the transaction of the approver's comment going to be
	 * Whether in ClaimManagerFragment or at ClaimViewerFragment
	 */
	public void openApproverCommentDialog() {
		// check if the user is an approver
		// check the status of claim as non-editable, submitted
		Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		ApproverCommentDialogFragment dialog = new ApproverCommentDialogFragment();
		//ApproverCommentDialogFragment dialog = new ApproverCommentDialogFragment(editClaim);
		dialog.show(getFragmentManager(), "Approver Comment Dialog");

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
		Claim newClaim = new Claim(activity.getUsername(), startDate, endDate, 
				this.description, itineraryList);
		newClaim.tags = this.tagList;
		ClaimListSingleton.getClaimList().removeClaimAtIndex(claimIndex);
		ClaimListSingleton.getClaimList().add(newClaim);
		//TODO needs a sort method
	}
	
	/**
	 * Updates the class level variables to reflect what's in the
	 * fields in the layout.
	 */
	public void updateReferences(){
		description = descriptionView.getText().toString().trim() + "";
		startDateText = startDateView.getText().toString().trim() + "";
		endDateText = endDateView.getText().toString().trim() + "";
				
		//TODO should we assert they fill in all fields?
		if(	!startDateText.equals("") && !endDateText.equals("") && itineraryList.size() != 0){
			this.incompleteFields = false;
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
		
		if(!dest_list.equals("")){
			dest_list += "\n";
		}
		dest_list += "  " + item.getDestinationName() + " : " + item.getDestinationDescription();
		destinationView.setText(dest_list);

		// TODO this needs to change the layout size
		//if(itineraryList.size() > 2){
			//If the text view is set to wrap content too early,
			//it looks like the field is too small
			//Toast.makeText(activity, "GROW ME", Toast.LENGTH_SHORT).show();
			//destinationView.setHeight(LayoutParams.WRAP_CONTENT); 
		//}
	}

	/** uses the addTravelItenerarItem function as a template,
	* adds a tag to the tagList, and sets the text so the 
	* user can see it. If the claim is being edited, starts
	* a new edit tag activity
	*/
	public void addTagItem(String tag) {
		Tag tmp = new Tag(tag);
		
		if (tagList.contains(tmp)) {
			Toast.makeText(activity, "Tag has already been added", Toast.LENGTH_SHORT).show();
		} else {
			this.tagList.add(tmp);
			String tag_list = tagView.getText().toString();
			
			if(!tag_list.equals("")){
				tag_list += "\n";
			}
			tag_list += "  " + tmp.getTagName();
			tagView.setText(tag_list);
	
			 //TODO this needs to change the layout size
			if(tagList.size() > 2){
				//If the text view is set to wrap content too early,
				//it looks like the field is too small
				
				tagView.setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			}
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

	/* Below this is android stuff */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itineraryList = new TravelItineraryList();
		incompleteFields = true;
		startDate = new Date();
		endDate = new Date();
		activity = (ClaimActivity) getActivity();
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
		descriptionView = (EditText) getView().findViewById(R.id.editTextClaimDescription);
		destinationView = (TextView) getView().findViewById(R.id.textViewDestinationsList);
		startDateView = (TextView) getView().findViewById(R.id.textViewStartDate);
		endDateView = (TextView) getView().findViewById(R.id.textViewEndDate);
		tagView = (TextView) getView().findViewById(R.id.textViewTags);
		// approver comment will be shown in this textView
		commentView = (TextView) getView().findViewById(R.id.ApproverCommentView);
		Button tagButton = (Button) getView().findViewById(R.id.addTags);
		tagButton.setText("Add");
				
		this.tagList = new ArrayList<Tag>();
		
		setFields();
	}
	

}
