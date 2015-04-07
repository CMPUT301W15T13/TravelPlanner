package ca.ualberta.cmput301w15t13.Fragments;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.UserLocationManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import dialogs.ClaimantGetExpenseLocationDialog;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidUserPermissionException;

public class ExpenseManagerFragment extends Fragment {
	private EditText expenseNameView;
	private String expenseName;
	private Spinner categorySpinner;
	private ImageView ib;
	private TextView dateView, locationTextView;
	private Date Date;
	private String dateText;
	private double amount;
	private TextView amountView;
	private Spinner currencySpinner; 
	private String description; 
	private EditText descriptionView;
	private boolean areFieldsComplete;
	private boolean isEditing;
	private int claimIndex;
	private String claimID;
	private int expenseIndex;
	private ImageButton addLocationButton;
	private Location location;
	
	
	//TODO force change what the back button does from this screen, in that it moves to the old fragment
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		areFieldsComplete = false;
		Date = new Date();
		
		claimIndex = getArguments().getInt("claimIndex");
		claimID = getArguments().getString("claimID");
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
		expenseNameView = (EditText) getView().findViewById(R.id.editTextExpenseName);
		descriptionView = (EditText) getView().findViewById(R.id.editTextExpenseDescription);
		dateView = (TextView) getView().findViewById(R.id.textViewDateExpense);
		amountView = (TextView) getView().findViewById(R.id.editTextAmount);
		categorySpinner = (Spinner) getView().findViewById(R.id.categorySpinner);
		currencySpinner = (Spinner) getView().findViewById(R.id.currencySpinner);
		ib = (ImageView) getView().findViewById(R.id.receiptImageHolder);

		locationTextView = (TextView) getView().findViewById(R.id.textViewExpenseLocation);
		addLocationButton = (ImageButton) getView().findViewById(R.id.imageButtonLocationPicker);
		addLocationButton.setOnClickListener(locationListener);

		setFields();
	}
	
	/** 
	 * Handle action bar item clicks here. The action bar will
	 *  automatically handle clicks on the Home/Up button, so long
	 * as you specify a parent activity in AndroidManifest.xml.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//Will set the fields that we need 
	private void setFields() {
		//if we are editing set all field to their proper values
		if(isEditing){
			Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
			ExpenseItem editExpense = editClaim.getExpenseItems().get(expenseIndex);
			this.expenseNameView.setText(editExpense.getExpenseName());
			this.description = editExpense.getExpenseDescription();
			this.location = editExpense.getLocation();
			dateView.setText(editExpense.getPurchseDateAsString());
			this.descriptionView.setText(editExpense.getExpenseDescription());
			this.amountView.setText(String.valueOf(editExpense.getAmount()));
			this.categorySpinner.setSelection(getIndex(categorySpinner,editExpense.getExpenseCategory()));
			this.currencySpinner.setSelection(getIndex(currencySpinner,editExpense.getExpenseCategory()));
			if(this.location != null){
				String tmp = "Lat: " + location.getLatitude() + 
							 " Long: " + location.getLongitude();
				this.locationTextView.setText(tmp);
			}
			
			if ((editExpense.getReceipt() != null)) {
				Drawable pic = editExpense.getReceipt().getDrawable();
				ib.setImageDrawable(pic);
			}
			
		} else {
			this.descriptionView.setText("");
			this.expenseNameView.setText("");
			this.amountView.setText("");
			this.dateView.setText("");
			this.categorySpinner.setSelection(0);
			this.currencySpinner.setSelection(0);
			
		}
		
	}

	/**
	 * Sets the "Mode" of the fragment to edit or create, 
	 * depending where it's called from
	 */
	public void setStateAsEditing(boolean isEditing){
		this.isEditing = isEditing;
	}
	
	public void setExpenseIndex(int index) {
		expenseIndex = index;
	}
	
	/**
	 * Updates the class level variables to reflect what's in the
	 * fields in the layout.
	 */
	public void updateReferences(){
		expenseName = expenseNameView.getText().toString().trim() + "";
		description = descriptionView.getText().toString().trim() + "";
		dateText = dateView.getText().toString().trim() + "";
		
		if (amountView.getText().toString().equals("")) {
			amount = 0;
		} else { amount = Double.parseDouble(amountView.getText().toString()); }
		
		//TODO should we assert they fill in all fields?
		if(	!dateText.equals("") && !expenseName.equals("")){
			this.areFieldsComplete = true;
		}
	}
	
	/**
	 * Returns false if the expense is new or 
	 * true if a modification.
	 * @return
	 */
	public boolean isEditing(){
		return this.isEditing;
	}
	
	/**
	 * Method used to update our expense
	 * TODO THIS IS CALLED WHEN WE ARE EDITTING
	 * @throws InvalidFieldEntryException 
	 */
	public void updateExpense() throws InvalidFieldEntryException {
		
		updateReferences();
		String categorySet = categorySpinner.getSelectedItem().toString();
		String currencySet = currencySpinner.getSelectedItem().toString();
		
		
		//ExpenseItem editExpense = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).getExpenseItems().get(expenseIndex);
		ExpenseItem editExpense = ClaimListSingleton.getClaimList().getClaimByID(claimID).getExpenseItems().get(expenseIndex);
		
		editExpense.setExpenseCategory(categorySet);
		editExpense.setCurrency(currencySet);
		editExpense.setExpenseDescription(description);
		editExpense.setAmount(amount);
		editExpense.setLinkedToclaimID(claimID);
		editExpense.setExpenseName(expenseName);
		editExpense.setLocation(location);
		
		Claim newClaim = ClaimListSingleton.getClaimList().getClaimByID(claimID);
		ClaimListSingleton.getClaimList().replaceClaim(claimID, newClaim);
		
		
		//ClaimListSingleton.getClaimList().replaceClaimAtIndex(claimIndex, newClaim);
		
	}
	
	/**
	 * Create the expense from our input values
	 * TODO THE EXPENSE IS CREATED HERE IF WE ARE NOT EDITTING 
	 * @throws InvalidDateException
	 * @throws InvalidUserPermissionException
	 * @throws EmptyFieldException
	 */
	public void createExpenseItem() throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException{
		if(this.areFieldsComplete){
			String categorySet = categorySpinner.getSelectedItem().toString();
			String currencySet = currencySpinner.getSelectedItem().toString();
			ExpenseItem newExpense = new ExpenseItem(categorySet, Date, description, amount, currencySet, claimID);
			newExpense.setExpenseName(expenseName);
			newExpense.setLocation(location);
			ClaimListSingleton.getClaimByID(claimID).addExpenseItem(newExpense);	
			
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
	/**
	 * Get the date picker
	 */
	new DatePickerDialog(getActivity(), date, myCalendar
            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	        
}
	/**
	 * A simple medthod to add date data 
	 * @param year
	 * @param month
	 * @param day
	 * @param text
	 */
	@SuppressWarnings("deprecation")
	public void addDateData(int year, int month, int day, TextView text){
		Date date;
		date = Date;
		
		date.setDate(day);
		date.setMonth(month);
		date.setYear(year);
	}
	
	/**
	 * This method will search a spinner for the index of a value
	 * this is usefull for when we want to set the spinner when loading
	 * an expense to edit
	 * Code reused from James Devito's Assignment 1
	 * @param spinner
	 * @param myString
	 * @return
	 */
	private int getIndex(Spinner spinner, String myString) {
		  int index = 0;
		  for (int i=0;i<spinner.getCount();i++){
		   if (spinner.getItemAtPosition(i).equals(myString)){
		    index = i;
		   }
		  }
		  return index;
		 }

	public void removeReceipt() {
		ExpenseItem editExpense = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).getExpenseItems().get(expenseIndex);
		editExpense.removeReceipt();
		int id = getResources().getIdentifier("TravelPlanner:drawable/ic_ayy.png" , null, null);
		ib.setImageResource(id);
	}
	
	private final OnClickListener locationListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UserLocationManager.setViewLocation(location);
			ClaimantGetExpenseLocationDialog dialog = new ClaimantGetExpenseLocationDialog();
			dialog.show(getFragmentManager(), "Add a location");
		}
	};

	public void setExpenseLocation(Location location) {
		this.location = location;
		String tmp = "Lat: " + location.getLatitude() + 
				 " Long: " + location.getLongitude();
		this.locationTextView.setText(tmp);
		UserLocationManager.setExpenseLocation(location);
	}
} 
