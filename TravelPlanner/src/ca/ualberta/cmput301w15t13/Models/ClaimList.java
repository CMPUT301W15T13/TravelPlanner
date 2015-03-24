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

import persistanceController.DataManager;
import persistanceModel.LoadASyncTask;
import persistanceModel.NetworkPersistance;
import persistanceModel.SaveASyncTask;
import ca.ualberta.cmput301w15t13.Controllers.ClaimDateSorter;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.EmptyFieldException;
import exceptions.InvalidUserPermissionException;

/**
 * A wrapper class for ArrayList<Claim>
 * with standard ArrayList methods.
 * Also contains a list of listeners such that 
 * on updates, the appropriate lists are
 * updated all at once.
 */

public class ClaimList {
	
	private ArrayList<Claim> claimList = null;
	private ArrayList<Listener> listenerList = null;
	public TagManager tagManager = null;

	public ClaimList(ArrayList<Claim> oldClaims) {
		if (oldClaims != null) {
			claimList = oldClaims;
		} else {
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
			if (claimList.contains(claim)) {
				claimList.remove(claim);
			}
	}

	public void add(Claim claim) {
		//Check for duplicates
		if (!claimList.contains(claim)) {
			claimList.add(claim);
			
			//DataManager.setOfflineMode();
			DataManager.saveClaim(claim);
			//new SaveASyncTask().execute(claim.getclaimID());
			
		}
	}

	public void removeClaimAtIndex(int i) { 
		//make sure the index is valid
		if (!this.claimList.isEmpty() || (i < this.claimList.size()) || (i >= 0)) {
			claimList.remove(i);
		}
	}
	
	public ArrayList<Claim> getClaimArrayList() {
		if (claimList == null) {
			this.claimList = new ArrayList<Claim>();
		}
		
		Collections.sort(claimList);
		
		return claimList;
	}

	public Claim getClaimAtIndex(int i) {
		//make sure the index is valid
		if (this.claimList.isEmpty() || (this.claimList.size() <= i) || (i < 0)) {
			return null;
		}
		return this.claimList.get(i);
	}

	public void addListener(Listener listener) {
		if (listener != null && !listenerList.contains(listener)) {
			listenerList.add(listener);
		}
	}

	public void notifyListeners() {
		for (Listener l : listenerList) {
			l.update();
		}
	}

	public void sortClaimListByDate(){
		Collections.sort(claimList, new ClaimDateSorter());
	}

	public boolean isClaimEditable(String claimID) {
		for (Claim claim: claimList) {
			if (claim.getclaimID().equals(claimID)) {
				return claim.isEditable();
			}
		}
		return false;
	}
	
	public ArrayList<String> filter(ArrayList<Tag> tags) {
		ClaimListSingleton control = new ClaimListSingleton();
		ArrayList<String> result = control.filterClaimList(tags, this.tagManager);
		return result;
	}

	public void setTagMan(TagManager tm) {
		this.tagManager = tm;
	}

	public ArrayList<ExpenseItem> getExpenseList(int ClaimIndex) {
		return claimList.get(ClaimIndex).getExpenseItems();
	}
	
	
	/** 
	 * TEMPORARY METHOD
	 * TODO REMOVED ME
	 */
	public void clearListeners(){
		this.listenerList = new ArrayList<Listener>();
	}

	public void clearList() {
		this.claimList = new ArrayList<Claim>();
		
	}

	public Claim getClaimByID(String iD) {
		for (Claim claim: claimList){
			if (claim.getclaimID().equals(iD)){
				return claim;
			}
		}
		return null;
		
	}
	
}
