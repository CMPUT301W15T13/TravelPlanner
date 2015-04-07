package ca.ualberta.cmput301w15t13.Controllers;

import java.util.Comparator;

import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * This class is used to correctly
 * sort claims when they are listed.
 * It should be used when you want
 * to sort a claim object by start date
 * in order of most recent to oldest.
 * 
 * Classes it works with:
 * Claim
 * 
 * Sample use: (works because of overriding
 * Collections.sort(newClaims);
 * return newClaims;
 *
 */
public class ClaimDateSorter implements Comparator<Claim> {
	/*
	 * Sorts the Claim List by the
	 * start dates. 
	 */
	@Override
	public int compare(Claim lhs, Claim rhs) {
		return lhs.getStartDate().compareTo(rhs.getStartDate());
	}

}
