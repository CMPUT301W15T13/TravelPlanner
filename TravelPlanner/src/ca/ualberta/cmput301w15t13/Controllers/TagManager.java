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
import java.util.HashMap;
import java.util.Map.Entry;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.Tag;

/** 
 * TagManager class controls the interface between
 * claims and tags. It associates tags with claim
 * Ids.It should be used to manage all tags, that is
 * ,whenever a tag is added, edit, or removed it,
 * the same operations should be done on the
 * TagManager
 * 
 * Classes it works with:
 * Tag,Claim
 * 
 * Sample use:
 * TagManager tm = new TagManager();
 * tm.add(Tag object, ClaimId)
 */

public class TagManager {
	
	public HashMap<Tag, ArrayList<String>> manager;
	
	public TagManager() {
		this.manager = new HashMap<Tag, ArrayList<String>>();
	}
	
	/**
	 * Method to add a tag to the manager/claim.
	 * This is where association occurs, as if
	 * the tag already exists, it adds the given
	 * claimID to the existing list, else it 
	 * creates a new mapping of the given tag to the
	 * given ClaimId
	 * @param tag
	 * @param claimID
	 */
	public void add(Tag tag, String claimID) {
		HashMap<Tag, ArrayList<String>> map = manager;
		
    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
    		if (tag.getTagName() == entry.getKey().getTagName()) {
    			manager.get(entry.getKey()).add(claimID);
    			return;
    		}
    	}
    	ArrayList<String> relatedClaimIds = new ArrayList<String>();
		relatedClaimIds.add(claimID);
		manager.put(tag, relatedClaimIds);
	}
	
	/**
	 * Given a tag this returns the associated
	 * List of ClaimIDs
	 * @param tag
	 * @return
	 */
	public ArrayList<String> getAssociatedClaims(Tag tag) {
		ArrayList<String> claims = manager.get(tag);
		return claims;
	}
	
	public HashMap<Tag, ArrayList<String>> getManager() {
		return this.manager;
	}
	
	public void remove(Tag tag, String claimID) {
		if (manager.containsKey(tag)) {
			manager.get(tag).remove(claimID);
		}
	}
	
	/**
	 * Bulky method count the number of 
	 * keys/Tags the tagManager has.
	 * @return
	 */
	@SuppressWarnings("unused")
	public int size() {
		HashMap<Tag, ArrayList<String>> map = manager;
		int size = 0;
    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
    		size += 1;
    	}
    	return size;
	}
	
	/**
	 * Get all tags so far created
	 * @return
	 */
	public ArrayList<Tag> getTags() {
		HashMap<Tag, ArrayList<String>> map = manager;
		ArrayList<Tag> tags = new ArrayList<Tag>();
    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
    		tags.add(entry.getKey());
    	}
    	return tags;
	}
	
	/**
	 * Method to remove a tag from the 
	 * manger, and 
	 * @param tag
	 */
	public void removeFromManager(Tag tag) {
		HashMap<Tag, ArrayList<String>> map = manager;
		for (String cid : map.get(tag)) {
			Claim c = ClaimListSingleton.getClaimList().getClaimByID(cid);
			c.tags.remove(tag);
    	}
		map.remove(tag);
	}
}
