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
import java.util.Collections;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import dialogs.ApproverChoiceDialogFragment;

/**
 * Manages Approver specific data.
 * Provides the ability to approve and return claims,
 * with comments, get the approver-viewable claims,
 * and on click listeners. It should be used as
 * a way to control the interface, re: both display
 * and functionality.
 * 
 * Classes it works with:
 * User, Claim, ClaimStatus,ApproverChoiceDialogFragment
 * 
 * Sample use:
 * Approver a = new Approver("approver name");
 * a.approveClaim(claim)
 */

public class Approver extends User {

	public Approver(String name) {
		super(name);
	}
	
	/**
	 * Sets a submitted claim's status to approved,
	 * and marks the approver's name on the claim.
	 * @param claim
	 */
	public void approveClaim(Claim claim) {
		if (claim != null && claim.getStatus() == statusEnum.SUBMITTED) {
			claim.setLastApproverName(this.name);
			claim.giveStatus(statusEnum.APPROVED);
		}
	}
	
	/**
	 * Sets a submitted claim's status to returned,
	 * and marks the approver's name on the claim.
	 * @param claim
	 */
	public void returnClaim(Claim claim) {
		if (claim != null && claim.getStatus() == statusEnum.SUBMITTED) {
			claim.setLastApproverName(this.name);
			claim.giveStatus(statusEnum.RETURNED);
		}
	}

	/**
	 * Adds an approver's comment to a submitted claim.
	 * @param claim
	 * @param comment
	 */
	public void addComment(Claim claim, String comment) {
		if (claim.getStatus() == statusEnum.SUBMITTED && comment != null) {
			claim.addComment(comment, this.name);
		}
	}
	
	/**
	 * Gets an ArrayList of claims that the given
	 * user is allowed to see. Approvers 
	 * will see submitted claims.
	 */
	@Override
	public ArrayList<Claim> getPermittableClaims(ArrayList<Claim> claims) {
		ArrayList<Claim> newClaims = new ArrayList<Claim>();
		for(Claim c: claims){
			if(c.getStatus() == ClaimStatus.statusEnum.SUBMITTED){
				newClaims.add(c);
			}
		}
		Collections.sort(newClaims);
		return newClaims;
	}

	/**
	 * Returns the OnItemClickListener for the approver,
	 * such that the Approver can only view the details of
	 * the expense items, not edit them.
	 */
	@Override
	public OnItemClickListener getClaimAdapterShortClickListener(final Activity claimActivity) {
		final OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Toast.makeText(claimActivity, "Cannot edit this claim.", Toast.LENGTH_SHORT).show();
			}
		};
		
		return listener;
	}

	/**
	 * Returns the OnItemLongClickListener that the approver will
	 * see when the long-click a submitted claim. Spcefically
	 * it creates a new dialog that displays the options to 
	 * Approve, return and view.
	 */
	@Override
	public OnItemLongClickListener getClaimAdapterLongClickListener(final FragmentManager fm) {
		final OnItemLongClickListener listener = new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ApproverChoiceDialogFragment dialog = new ApproverChoiceDialogFragment();
			    Bundle args = new Bundle();
			    args.putInt("index", position);
			    dialog.setArguments(args);
			    dialog.show(fm, "Long Click Pop-Up");
				return true;
			}
		};
		return listener;
	}

	/**
	 * Approvers should not see the Create Claim button,
	 * and as such it's visibility should be GONE.
	 */
	@Override
	public int getButtonVisibility() {
		return View.GONE;
	}
	
}
