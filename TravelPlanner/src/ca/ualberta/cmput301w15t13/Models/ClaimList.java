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


public class ClaimList {
	/* A wrapper class for ArrayList<Claim>
	 * with standard ArrayList methods, as well as 
	 * methods for sorting and parsing the list.
	 * Also contains a list of listeners such that 
	 * on updates, the appropriate lists are
	 * updated all at once.
	 * 
	 */
	ArrayList<Claim> claimList = null;

	public ClaimList(ArrayList<Claim> oldClaims) {
		if(oldClaims != null){
			claimList = oldClaims;
		}else{
			claimList = new ArrayList<Claim>();
		}
	}

	public ClaimList() {
		claimList = new ArrayList<Claim>();
	}

	public int length() {
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

	public ArrayList<Claim> getList() {
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

}
