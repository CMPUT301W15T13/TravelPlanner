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

package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;
import java.util.Collections;

import ca.ualberta.cmput301w15t13.Controllers.ClaimDateSorter;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import exceptions.EmptyFieldException;
import exceptions.InvalidUserPermissionException;


public class ClaimList {
	/* A wrapper class for ArrayList<Claim>
	 * with standard ArrayList methods.
	 * Also contains a list of listeners such that 
	 * on updates, the appropriate lists are
	 * updated all at once.
	 * 
	 */
	ArrayList<Claim> claimList = null;
	ArrayList<Listener> listenerList = null;
	public TagManager tagManager = null;

	public ClaimList(ArrayList<Claim> oldClaims) {
		if(oldClaims != null){
			claimList = oldClaims;
		}else{
			claimList = new ArrayList<Claim>();
		}
		listenerList = new ArrayList<Listener>();
		this.tagManager = new TagManager();
	}

	public ClaimList() {
		claimList = new ArrayList<Claim>();
		listenerList = new ArrayList<Listener>();
	}

	public int size() {
		return claimList.size();
	}

	public boolean contains(Claim claim) {
		return claimList.contains(claim);
	}

	public void remove(Claim claim) {

			if(claimList.contains(claim)){
				claimList.remove(claim);
			}

	}

	public void add(Claim claim) {
		//Check for duplicates
		if(!claimList.contains(claim)){
			claimList.add(claim);
		}
	}

	public void removeClaimAtIndex(int i) { 
		//make sure the index is valid
		if(!this.claimList.isEmpty() || (i < this.claimList.size()) || (i >= 0)){
			claimList.remove(i);
		}
	}
	
	public void claimRemove(Claim c) throws InvalidUserPermissionException {
		
		if( c.status.getStatus() == ClaimStatus.SUBMITTED || c.status.getStatus() == ClaimStatus.APPROVED){
			throw new InvalidUserPermissionException("Non-Editable");
		} else {
			
			this.claimList.remove(c);
		}
	}

	public ArrayList<Claim> getClaimArrayList() {
		if(claimList == null){
			this.claimList = new ArrayList<Claim>();
		}
		return claimList;
	}

	public Claim getClaimAtIndex(int i) {
		//make sure the index is valid
		if(this.claimList.isEmpty() || (this.claimList.size() <= i) || (i < 0)){
			return null;
		}
		return this.claimList.get(i);
	}

	public void addListener(Listener listener) {
		if(listener != null){
			listenerList.add(listener);
		}
	}

	public void notifyListeners() {
		for(Listener l : listenerList){
			l.update();
		}
	}

	public void sortClaimListByDate(){
		Collections.sort(claimList, new ClaimDateSorter());
	}

	public boolean isClaimEditable(String claimID) {
	
		for (Claim claim: claimList)
		{
			if (claim.getclaimID().equals(claimID))
				return claim.isEditable();
		}
		
		return false;

	}
	// Filter works with use of tagManagers getAssociatedClaims method
	// which returns an ArrayList of claimIds (strings) associated with a given tag.
	// Input an arrayList of tags and it creates a new arrayList of tags, adding only
	// the common claimIds
	public ArrayList<String> filter(ArrayList<Tag> tags) {
		ArrayList<String> claimIds = new ArrayList<String>();
		
		claimIds.addAll(tagManager.getAssociatedClaims(tags.get(0)));
		
		if (tags.size() > 1) {
			
			for (int i = 1; i < tags.size(); i++) {
				
				ArrayList<String> tmp = tagManager.getAssociatedClaims(tags.get(i));
				
				for (String claimId : tmp) {
					if (claimIds.contains(claimId)) { // this means the claimId is associated with all tags
						continue;
					} else {						  
						claimIds.remove(claimId);
					}
					
				}
				
			}
		}
		
		return claimIds;
	}
	
	public void setTagMan(TagManager tm) {
		this.tagManager = tm;
	}
	
}
