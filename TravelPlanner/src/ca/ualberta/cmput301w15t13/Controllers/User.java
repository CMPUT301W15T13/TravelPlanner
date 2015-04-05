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
package ca.ualberta.cmput301w15t13.Controllers;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.location.Location;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * Parent class to Approver and Claimant.
 * Most of the methods are abstract, but
 * it provides the get and set methods for
 * a user's name and location.
 */

public abstract class User {
	
	protected String name;
	protected Location location;
	
	public User(String name){
		this.name = name;
		location = null;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Returns the claims that are viewable by either an approver or a 
	 * claimant. TODO This will be moved to a controller.
	 */
	public abstract ArrayList<Claim> getPermittableClaims(ArrayList<Claim> claims);
	
	/**
	 * Allows for abstraction in defining the array adapters between different users.
	 * @param activity
	 * @return The OnItemClickListener defined for the specific user.
	 */
	public abstract OnItemClickListener getClaimAdapterShortClickListener(Activity activity);
	
	public abstract OnItemLongClickListener getClaimAdapterLongClickListener(FragmentManager fm);
	
	public abstract int getButtonVisibility();
}
