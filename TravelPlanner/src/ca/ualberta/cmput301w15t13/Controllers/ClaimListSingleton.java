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
	
}
