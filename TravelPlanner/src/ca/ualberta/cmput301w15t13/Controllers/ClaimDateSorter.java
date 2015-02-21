package ca.ualberta.cmput301w15t13.Controllers;

import java.util.Comparator;

import ca.ualberta.cmput301w15t13.Models.Claim;

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
