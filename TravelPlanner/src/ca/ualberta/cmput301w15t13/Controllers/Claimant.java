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
 * Manages Claimant specific data.
 * Provides the ability to create and submit claims including
 * constituent expense Items, and channel a claimant 
 * specific interface. It should be used as
 * a way to control the interface, re: both display
 * and functionality.
 * 
 * Classes it works with:
 * User, Claim, ClaimStatus,ApproverChoiceDialogFragment
 * 
 * Sample use:
 * Claimant c = new Claimant("name");
 * c.submitClaim(claim)
 */
public class Claimant extends User {
	
	public Claimant(String name) {
		super(name);
	}
	
	/**
	 * A claim can be submitted when it has been newly created
	 * A claim can also be re-submitted when it has been returned
	 * Therefore, the claim should be able to submit when the statusEnum == RETURNED 
	 * 
	 * Claim can now be re-submitted after it has been returned
	 * @param claim
	 * @throws InvalidUserPermissionException
	 */
	public void submitClaim(Claim claim) throws InvalidUserPermissionException{
		if (claim != null && (claim.getStatus() == statusEnum.INPROGRESS) || claim.getStatus() == statusEnum.RETURNED) {
			claim.giveStatus(statusEnum.SUBMITTED);
		}
	}
	
	/**
	 * Gets an ArrayList of claims that the given
	 * user is allowed to see. Claimants can see
	 * any claim made in their own name.
	 */
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
				ArrayList<Integer> indexList = ClaimListSingleton.getClaimList().getIndexList();	//must be set beforehand
				int Index = position;
				int claimIndex = indexList.get(Index);
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
	
	/**
	 * Returns the OnItemLongClickListener that the approver will
	 * see when the long-click a submitted claim. Specifically
	 * it creates a new dialog that displays the options to 
	 * View, edit, submit, delete.
	 */
	@Override
	public OnItemLongClickListener getClaimAdapterLongClickListener(final FragmentManager fm) {
		final OnItemLongClickListener listener = new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// ArrayList<Integer> indexList = ClaimListSingleton.getClaimList().getIndexList();	//must be set beforehand
				// int claimIndex = indexList.get(position);
				ClaimantChoiceDialogFragment dialog = new ClaimantChoiceDialogFragment();
			    Bundle args = new Bundle();
			    args.putInt("index", ClaimListSingleton.getClaimList().getIndexList().get(position));
			    dialog.setArguments(args);
			    dialog.show(fm, "Long Click Pop-Up");
				return true;
			}
		};
		return listener;
	}

	/**
	 * Claimants should be able to see and use the
	 * the Create Claim button, as such it's visibility 
	 * is set to VISIBLE.
	 */
	@Override
	public int getButtonVisibility() {
		return View.VISIBLE;
	}
	
	/**
	 * Checks each of a claim's expense items to check
	 * for incompleteness indicators. 
	 * @param claim
	 * @return true when all claims are complete.
	 */
	public static boolean isComplete(Claim claim){
		for(ExpenseItem item: claim.getExpenseItems()){
			if(!item.isComplete()){
				return false;
			}
		}
		return true;
	}
}
