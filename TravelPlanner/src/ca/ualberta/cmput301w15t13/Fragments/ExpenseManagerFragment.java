package ca.ualberta.cmput301w15t13.Fragments;

import java.util.Date;

import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

public class ExpenseManagerFragment extends Fragment {
	private EditText descriptionView;
	private TextView dateView;
	private String description; 
	private String dateText;
	private TravelItineraryList itineraryList;
	private Date Date;
	private boolean areFieldsComplete, isEditing;
	private int claimIndex;
	private int expenseIndex;

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
