package ca.ualberta.cmput301w15t13.Fragments;

import java.util.Date;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
		descriptionView = (EditText) getView().findViewById(R.id.editTextClaimDescription);
		dateView = (TextView) getView().findViewById(R.id.textViewStartDate);
		
		setFields();
	}

	private void setFields() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	public void createExpenseItem() {
		// TODO Auto-generated method stub
		
	}
	public void openDateDialog(TextView textId) {
		// TODO Auto-generated method stub
		
	}
	public void openDestinationDialog() {
		// TODO Auto-generated method stub
		
	}
}
