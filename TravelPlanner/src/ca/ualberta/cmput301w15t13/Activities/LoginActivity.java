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

import java.util.concurrent.ExecutionException;

import persistanceController.DataManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;

/**
 * This activity is used for getting login information.
 * It will identify what kind of user is attempting to log in
 * and direct them to a page to view claims specific to 
 * their access rights. The server set up happens before
 * the users login, so that the claims are ready
 * before the next activity starts.
 * 
 * Classes it works with:
 * DataManager
 * 
 * Sample use:
 * Intent intent = new Intent(this, LoginActivity.class);
 * startActivity(intent);
 */

public class LoginActivity extends Activity {
	
	public static final String USERID = "ca.ualberta.cmput301w15t13.username";
	public static final String ISCLAIMANT = "ca.ualberta.cmput301w15t13.isclaimant";

	/**
	 * Take the username and password inputted by the
	 * user and attempt to login. For now, this only
	 * logs in as a Claimant, but will be extended
	 * to login as either. 
	 * @param view
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public String getLoginCredentials() throws  ExecutionException, InterruptedException {
		EditText usernameEditText;
		String username;

		usernameEditText = (EditText) findViewById(R.id.editTextUsername);
		username = usernameEditText.getText().toString();
		
		if (username.equals("") || username == null) {
			Toast.makeText(this, "Add username before logging in", Toast.LENGTH_SHORT).show();
			return null;
		}
		return username;
	}
	
	/**
	 * Gets the user's username and if
	 * valid, login as an approver.
	 * @param v
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void approverLogin(View v) throws ExecutionException, InterruptedException{
		String username = getLoginCredentials();
		if(username != null){
			startClaimActivity(username, false);
		}
	}
	
	/**
	 * Gets the user's name and if valid,
	 * login as an approver.
	 * @param v
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void claimantLogin(View v) throws ExecutionException, InterruptedException{
		String username = getLoginCredentials();
		if(username != null){
			startClaimActivity(username, true);
		}
	}
	/**
	 * Takes the user's name and the type of user.
	 * Starts the communication with the server.
	 * Passes the username and the type of user to the 
	 * next activity, ClaimActivity, then starts it.
	 * @param username
	 * @param isClaimant
	 */
	public void startClaimActivity(String username, boolean isClaimant){	
		ClaimListSingleton.getClaimList().clearListeners();
		//load data
		DataManager.setOfflineMode();
		Toast.makeText(getApplicationContext(), "synchronizing with server", Toast.LENGTH_SHORT).show();
		DataManager.loadClaimsByUserName(username);
		
		Intent intent = new Intent(this, ClaimActivity.class);
		intent.putExtra(USERID, username);
		intent.putExtra(ISCLAIMANT, isClaimant);
		startActivity(intent);
	}
	

	/* Below this is android stuff */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		DataManager.setCurrentContext(getApplicationContext());
	}
}
