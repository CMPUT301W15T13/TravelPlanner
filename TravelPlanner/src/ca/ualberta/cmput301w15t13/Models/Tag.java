package ca.ualberta.cmput301w15t13.Models;

import java.util.Random;
import java.util.SortedSet;
import java.util.UUID;

public class Tag {
	
	String tagName = "";

	SortedSet<Tag> relatedClaimID = null;

	
	public Tag(String tagName, String claimID){
		tagName = tagName;
		relatedClaimID.add(claimID);
				
	}
	
}
