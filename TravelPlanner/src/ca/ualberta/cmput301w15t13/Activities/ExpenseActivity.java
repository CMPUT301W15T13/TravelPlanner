package ca.ualberta.cmput301w15t13.Activities;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ExpenseManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ExpenseViewerFragment;
import ca.ualberta.cmput301w15t13.R.layout;
import ca.ualberta.cmput301w15t13.R.menu;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ExpenseActivity extends Activity {

	private FragmentManager fm;
	private FragmentTransaction ft;
	private ExpenseViewerFragment claimViewerFragment;
	private ExpenseManagerFragment claimManagerFragment;
	// Don't know if I want an action bar yet
	// TODO remove this and actionbar calls
	//private ActionBar actionBar; //Based on http://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities March 06 2015
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_item_layout);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_activity_layout);
		
		//setActionBar();
		//this.actionBar = getActionBar();
		
		this.fm = getFragmentManager();
		claimViewerFragment = new ExpenseViewerFragment();
		claimManagerFragment = new ExpenseManagerFragment();
		
		Intent intent = getIntent();
		// TODO get passed claim
		// TODO load data
		// TODO add a save file listener
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_viewer, menu);
		return true;
	}

}


