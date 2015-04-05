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

/* 
 * TagManager class controls the interface between
 * claims and tags. It associates tags with claim
 * Ids.
 */

public class TagManager {
	
	public static HashMap<Tag, ArrayList<String>> manager;
	
	public TagManager() {
		TagManager.manager = new HashMap<Tag, ArrayList<String>>();
	}
	
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
	
	public ArrayList<String> getAssociatedClaims(Tag tag) {
		ArrayList<String> claims = manager.get(tag);
		return claims;
	}
	
	public HashMap<Tag, ArrayList<String>> getManager() {
		return TagManager.manager;
	}
	
	public void remove(Tag tag, String claimID) {
		if (manager.containsKey(tag)) {
			manager.get(tag).remove(claimID);
		}
	}
	
	public int size() {
		HashMap<Tag, ArrayList<String>> map = manager;
		int size = 0;
    	for (@SuppressWarnings("unused") Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
    		size += 1;
    	}
    	return size;
	}
	
	public ArrayList<Tag> getTags() {
		HashMap<Tag, ArrayList<String>> map = manager;
		ArrayList<Tag> tags = new ArrayList<Tag>();
    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
    		tags.add(entry.getKey());
    	}
    	return tags;
    	
	}
	
	public void removeFromManager(Tag tag) {
		HashMap<Tag, ArrayList<String>> map = manager;
		for (String cid : map.get(tag)) {
			Claim c = ClaimListSingleton.getClaimList().getClaimByID(cid);
			c.tags.remove(tag);
    	}
		map.remove(tag);
	}
}
