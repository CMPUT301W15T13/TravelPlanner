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

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.Tag;

/**
 * Singleton pattern for the ClaimList 
 * class. It provides sorting method
 * which sorts the claims and their nested
 * ExpenseLists by start date. It should be used 
 * to manage all the claims of the app.
 * 
 * Classes it works with:
 * Claim,ExpenseItem,Tag,ClaimList
 * 
 * Sample use:
 * ClaimList cl = ClaimListSingleton.getClaimList();
 */

public class ClaimListSingleton {
	
	private static ClaimList claimList; 
	public static String testIndex;
	
	/**
	 * Method used throughout the app to
	 * get the same claimList instance.
	 * @return
	 */
	static public ClaimList getClaimList() {
		if (claimList == null) {
			claimList = new ClaimList();
		}
		return claimList;
	}
	
	static public void setClaimList(ArrayList<Claim> claims) {
		if (claimList == null) { 
			claimList = new ClaimList();
		}
		claimList = new ClaimList(claims);
	}
	
	static public void addClaim(Claim claim) {
		if (claimList == null) {
			claimList = new ClaimList();
		}
		claimList.add(claim);
	}
	
	/**
	 * Based on the possible statuses
	 * and their implications, this 
	 * function returns whether a claim
	 * can be edited
	 * @param claimID
	 * @return
	 */
	public static boolean isClaimEditable(String claimID) {
		if (claimList == null) {
			claimList = new ClaimList();
		}
		return claimList.isClaimEditable(claimID);
	}
	
	public static boolean isEmpty(){
		if (claimList == null) {
			claimList = new ClaimList();
		}
	
		if (claimList.size() <0) {
			return true;
		} 
		return false;
	}
	
	//This is temporary
	//TODO Send the expense list instead
	public static ArrayList<ExpenseItem> getExpenseItemArrayList() {
		ArrayList<ExpenseItem> expenselist = new ArrayList<ExpenseItem>();
		return expenselist;
	}
	
	/**
	 * FilterClaimList works with use of tagManagers getAssociatedClaims method
	 * which returns an ArrayList of claimIds (strings) associated with a given tag.
	 * Input an arrayList of tags and it returns a new arrayList of claimIds, keeping only
	 * the common claimIds
	 * */
	public ArrayList<String> filterClaimList(ArrayList<Tag> tags, TagManager tm) {
		// make an array of all claimIds associated with first tag
		ArrayList<String> claimIds = new ArrayList<String>();
		claimIds.addAll(tm.getAssociatedClaims(tags.get(0)));
		
		if (tags.size() > 1) {
			for (int i = 0; i < tags.size(); i++) {
				ArrayList<String> tmp = tm.getAssociatedClaims(tags.get(i));		
				for (String object : claimIds) {
					if (tmp.contains(object)) { // this means the claimId is associated with all tags
						continue;
					} else {						  
						claimIds.remove(object);
					}		
				}
			}
		}
		return claimIds;
	}
	
	public static void clearList(){
		claimList.clearList();
	}
	
	/**
	 * Returns a specific claim in the
	 * claimList via its unique ID
	 * @param ID
	 * @return
	 */
	public static Claim getClaimByID(String ID){
		return claimList.getClaimByID(ID);
	}

	/**
	 * Adds an expense Item to a claim
	 * in the claimList
	 * @param claimID
	 * @param expense
	 */
	public static void addExpenseToClaim(String claimID, ExpenseItem expense) {
		Claim newClaim;
		for (int index = 0; index < claimList.size(); index++){
			newClaim = claimList.getClaimAtIndex(index);
			if (newClaim.getclaimID().equals(claimID)){
				newClaim.addExpenseItem(expense);
				//currentClaimIndex = index;
				claimList.replaceClaimAtIndex(0, newClaim);
			}
		}
	}
		
	
}
