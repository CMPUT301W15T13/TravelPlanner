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

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.Tag;

public class ClaimListSingleton {
	/*
	 * Singleton pattern for the ClaimList 
	 * class. It provides sorting method
	 * which sorts the claims and their nested
	 * ExpenseLists by start date
	 */
	private static ClaimList claimList; 
	
	static public ClaimList getClaimList(){
		if(claimList == null){
			claimList = new ClaimList();
		}
		return claimList;
	}
	
	static public void setClaimList(ArrayList<Claim> claims){
		if(claimList == null){
			claimList = new ClaimList();
		}
		claimList = new ClaimList(claims);
	}
	
	static public void addClaim(Claim claim){
		if(claimList == null){
			claimList = new ClaimList();
		}
		claimList.add(claim);
	}

	public static boolean isClaimEditable(String claimID) {
		if(claimList == null){
			claimList = new ClaimList();
		}

		
		return claimList.isClaimEditable(claimID);
	
		
	}
	
	public static boolean isEmpty(){
		if(claimList == null){
			claimList = new ClaimList();
		}
		
		if (claimList.size() <0)
			return true;
		else
			return false;
		
	}
	
	// FilterClaimList works with use of tagManagers getAssociatedClaims method
	// which returns an ArrayList of claimIds (strings) associated with a given tag.
	// Input an arrayList of tags and it creates a new arrayList of tags, adding only
	// the common claimIds
	public ArrayList<String> filterClaimList(ArrayList<Tag> tags, TagManager tm) {
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
	
	
}
