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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;

/**
 * This activity is used only for login in and authentication.
 * It will identify what kind of user is attempting to log in
 * and direct them to a page to view claims specific to 
 * their access rights.
 * @author mfritze
 *
 */

public class LoginActivity extends Activity {
	
	public static final String USERID = "ca.ualberta.cmput301w15t13.username";
	public static final String ISCLAIMANT = "ca.ualberta.cmput301w15t13.isclaimant";
	
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

	/**
	 * 
	 * @param view
	 */
	public void login(View view){
		/*
		 * Needs to:
		 * validate the user credentials
		 * send the user identification to the
		 * next activity, and store it there
		 */
		EditText usernameEditText;
		EditText passwordEditText;
		String username, password;

		usernameEditText = (EditText) findViewById(R.id.editTextUsername);
		passwordEditText = (EditText) findViewById(R.id.editTextPassword);
		username = usernameEditText.getText().toString();
		password = passwordEditText.getText().toString();
		
		
		if(username.equals("") || username == null){
			Toast.makeText(this, "Add username before logging in", Toast.LENGTH_SHORT).show();
		}else if(password.equals("") || password == null){
			Toast.makeText(this, "Add password before logging in", Toast.LENGTH_SHORT).show();
		}else{
			//password and username are filled in
			
			//TODO maybe should be User.checkValidLogin(username, password);
			if(checkValidLogin(username, password)){
				startClaimActivity(username);
			}else{
				Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * 
	 * @param username
	 */
	public void startClaimActivity(String username){
		/*
		 * Get the user from the server now, since 
		 * it's less frustrating to wait for the server
		 * when you hit login (before you go to the 
		 * next screen) rather than in a transition phase
		 * between layouts.
		 * But you can't pass non-primitive objects. hmm.
		 */
		//User user;
		//TODO package user in intent
		//user = getUserByUsername(username);
		
		Intent intent = new Intent(this, ClaimActivity.class);
		intent.putExtra(USERID, username);
		intent.putExtra(ISCLAIMANT, true);
		//TODO start new activity with the user
		startActivity(intent);
		
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkValidLogin(String username, String password){
		//Should this be a static method in the user class?
		//TODO check the proper username and password
		return true;
	}
	
	/**
	 * A temporary method that will allow us to set
	 * the functionality of the approvers, but without
	 * dealing with login authentication
	 */
	public void loginAsApprover(View v){
		//TODO remove this for project part 5
		Intent intent = new Intent(this, ClaimActivity.class);
		intent.putExtra(USERID, "TESTUSERNAME");
		intent.putExtra(ISCLAIMANT, false);
		startActivity(intent);
		
	}
}
