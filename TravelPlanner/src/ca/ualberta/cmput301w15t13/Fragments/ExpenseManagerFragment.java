package ca.ualberta.cmput301w15t13.Fragments;

import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.SpinnerSelectedListener;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidUserPermissionException;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseManagerFragment extends Fragment {
	private EditText expenseName;
	private Spinner categorySpinner;
	private TextView dateView;
	private Date Date;
	private String dateText;
	private float amount;
	private Spinner currencySpinner; 
	private String description; 
	private EditText descriptionView;
	
	private boolean areFieldsComplete, isEditing;
	private int claimIndex;
	private int expenseIndex;
	
	//TODO force change what the back button does from this screen, in that it moves to the old fragment
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		areFieldsComplete = false;
		Date = new Date();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.expense_item_manager, container, false);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		descriptionView = (EditText) getView().findViewById(R.id.editTextExpenseDescription);
		dateView = (TextView) getView().findViewById(R.id.textViewDateExpense);
		categorySpinner = (Spinner) getView().findViewById(R.id.categorySpinner);
		currencySpinner = (Spinner) getView().findViewById(R.id.currencySpinner);
		
		//personal reference on get item TODO delete this line 
		String categorySet = categorySpinner.getSelectedItem().toString();
		setFields();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void setFields() {
		//TODO change this for expenses 
		if(isEditing){
			Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
			this.description = editClaim.getDescription();
			this.Date = editClaim.getStartDate();
			this.descriptionView.setText(this.description);
			
			this.dateView.setText(editClaim.getStartDateAsString());
			
		}else{
			this.descriptionView.setText("");
		}
		
	}

	public void setStateAsEditing(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public void setExpenseIndex(int index) {
		// TODO Auto-generated method stub
		
	}
	public void updateReferences() {
		// TODO Auto-generated method stub
		
	}
	public boolean isEditing() {
		// TODO Auto-generated method stub
		return false;
	}
	public void updateClaim() {
		// TODO May still need this (update the claim for the new info in expense)
	}
	public void createExpenseItem() throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException{
		if(this.areFieldsComplete){
			//TODO add to our list of expenses for the claim
			//ExpenseItem newExpense = new ExpenseItem(((ClaimActivity) getActivity()).getUsername(), startDate, endDate, 
			//this.description, itineraryList);
			//ClaimListSingleton.getClaimList().add(newExpense);
		}else {
			Toast.makeText(getActivity(), "Fill in all fields before submitting", Toast.LENGTH_SHORT).show();
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
	public void addDateData(int year, int month, int day, TextView text){
		Date date;
		date = Date;
		
		date.setDate(day);
		date.setMonth(month);
		date.setYear(year);
	}
}
