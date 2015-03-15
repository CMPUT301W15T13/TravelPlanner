package ca.ualberta.cmput301w15t13.Activities;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.R.layout;
import ca.ualberta.cmput301w15t13.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ExpenseViewerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_item_viewer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_viewer, menu);
		return true;
	}

}
