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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import dialogs.ClaimantChoiceDialogFragment;
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

	/**
	 * Returns the OnItemClickListener for the Claimant such that it will
	 * open the ExpenseItem creator activity.
	*/
	@Override
	public OnItemClickListener getClaimAdapterShortClickListener(final Activity claimActivity) {
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
					Intent intent = new Intent(claimActivity, ExpenseActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("claimIndex", position);
					bundle.putString("claimID", claimID);
					intent.putExtras(bundle);
					// allow a new activity to be started outside an activity
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					claimActivity.getApplication().startActivity(intent);
				}else{
					Toast.makeText(claimActivity, "Cannot edit this claim.", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		return listener;
	}
	
	@Override
	public OnItemLongClickListener getClaimAdapterLongClickListener(final FragmentManager fm) {
		final OnItemLongClickListener listener = new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ClaimantChoiceDialogFragment dialog = new ClaimantChoiceDialogFragment();
			    Bundle args = new Bundle();
			    args.putInt("index", position);
			    dialog.setArguments(args);
			    dialog.show(fm, "Long Click Pop-Up");
				return true;
			}
		};
		return listener;
	}


	@Override
	public int getButtonVisibility() {
		return View.VISIBLE;
	}
	
	
	public static boolean isComplete(Claim claim){
		for(ExpenseItem item: claim.getExpenseItems()){
			if(!item.isComplete()){
				return false;
			}
		}
		return true;
	}
}
