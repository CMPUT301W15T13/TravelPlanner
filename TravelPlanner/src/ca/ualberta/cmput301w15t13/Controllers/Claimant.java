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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.InvalidUserPermissionException;

/**
 * Claimant child class.
 * Currently only submits but 
 * as more details
 * are released, as will more
 * functionality
 */

public class Claimant extends User {
	
	public Claimant(String name) {
		super(name);
	}
	
	public void submitClaim(Claim claim) throws InvalidUserPermissionException{
		if (claim != null && claim.getStatus() == statusEnum.INPROGRESS) {
			claim.giveStatus(statusEnum.SUBMITTED);
		}
	}

	@Override
	public ArrayList<Claim> getPermittableClaims(ArrayList<Claim> claims) {
		ArrayList<Claim> newClaims = new ArrayList<Claim>();
		for(Claim c: claims){
			if(c.getUserName().equals(this.name)){
				newClaims.add(c);
			}
		}
		return newClaims;
	}

	//Toast.makeText(getActivity(), "Open expense edit", Toast.LENGTH_SHORT).show();
	/**
	 * Returns the OnItemClickListener for the Claimant such that it will
	 * open the ExpenseItem creator activity.
	*/
	@Override
	public OnItemClickListener getClaimAdapterShortClickListener(Activity claimActivity) {
		final ClaimActivity context = (ClaimActivity) claimActivity;
		final OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				/*
				 * Pass required claim information to the expense portion of the code
				 * IE: The claim holds our expense list so we need to know which
				 * claim to get expense items from
				 */
				int claimIndex = position;
				ArrayList<Claim> claims = ClaimListSingleton.getClaimList().getClaimArrayList();
				String claimID = claims.get(claimIndex).getclaimID();
				if(ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).isEditable()){
					Intent intent = new Intent(context, ExpenseActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("claimIndex", position);
					bundle.putString("claimID", claimID);
					intent.putExtras(bundle);
					// allow a new activity to be started outside an activity
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					context.getApplication().startActivity(intent);
				}else{
					Toast.makeText(context, "Cannot edit this claim.", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		return listener;
	}
	
}
