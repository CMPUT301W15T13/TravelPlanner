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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.User;

/**
 * This activity is used for getting login information.
 * It will identify what kind of user is attempting to log in
 * and direct them to a page to view claims specific to 
 * their access rights.
 *
 * Outstanding Issues: Communicate with the server
 * and actually validate the logins.
 */

public class LoginActivity extends Activity {
	
	public static final String USERID = "ca.ualberta.cmput301w15t13.username";
	public static final String ISCLAIMANT = "ca.ualberta.cmput301w15t13.isclaimant";
	
	/**
	 * Take the username and password inputted by the
	 * user and attempt to login. For now, this only
	 * logs in as a Claimant, but will be extended
	 * to login as either. TODO
	 * @param view
	 */
	public void login(View view) {
		EditText usernameEditText;
		EditText passwordEditText;
		String username, password;

		usernameEditText = (EditText) findViewById(R.id.editTextUsername);
		passwordEditText = (EditText) findViewById(R.id.editTextPassword);
		username = usernameEditText.getText().toString();
		password = passwordEditText.getText().toString();
		
		if (username.equals("") || username == null) {
			Toast.makeText(this, "Add username before logging in", Toast.LENGTH_SHORT).show();
		} else if (password.equals("") || password == null) {
			Toast.makeText(this, "Add password before logging in", Toast.LENGTH_SHORT).show();
		} else {
			//password and username are filled in
			User user= User.login(username, password);
			if (user != null) {
				startClaimActivity(username, true);
			} else {
				Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * 
	 * @param username
	 */
	public void startClaimActivity(String username, boolean isClaimant){		
		Intent intent = new Intent(this, ClaimActivity.class);
		intent.putExtra(USERID, username);
		intent.putExtra(ISCLAIMANT, isClaimant);
		startActivity(intent);
	}
	
	/**
	 * A temporary method that will allow us to set
	 * the functionality of the approvers, but without
	 * dealing with login authentication
	 */
	public void loginAsApprover(View v){
		startClaimActivity("TESTUSER", false);
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
}
