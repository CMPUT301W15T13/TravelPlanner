package ca.ualberta.cmput301w15t13.Controllers;

import java.util.ArrayList;
import java.util.Collections;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;

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
		claimList = new ClaimList(claims);
	}
	
	static public void addClaim(Claim claim){
		claimList.add(claim);
	}
	
}
