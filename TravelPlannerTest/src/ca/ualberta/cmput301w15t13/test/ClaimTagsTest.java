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

package ca.ualberta.cmput301w15t13.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.Tag;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;



/*
 * This test suite tests that a claim can have 0 or more tags,
 * which can be removed, renamed, added, and listed correctly.
 * 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 * 
 * All tests should pass
 */
public class ClaimTagsTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimTagsTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Part of Use Case C1
	 * Tests that you're able to add a tag to a claim
	 * and that the tag MUST be alphanumeric
	 * US03.01.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/56
	 * US03.02.02 - Adding a tag
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	
	@SuppressWarnings("unchecked")
	public void testaddTags() throws InvalidDateException, EmptyFieldException, DuplicateException, InvalidUserPermissionException{
		Claim claim1 = new Claim("Name", new Date(1), new Date(2), null, null);
		Claim claim2 = new Claim("Name", new Date(1), new Date(2), null, null);
		String[] validTags = {"valid","VALID", "a1", "HOr3to"};
		String[] invalidTags = {"",  " ", "aoeu ", "a-", "-*&", "1.23"};
		
		// Tests that valid tags can be added
		for (String vtag : validTags) {
			Tag tag = new Tag(vtag);
			claim1.addTag(tag);
			assertTrue("Valid tag wasn't added", claim1.getTags().contains(tag));
		}
		ArrayList<Tag> tags = claim1.getTags();
		assertNotNull("Tags is null", tags);
		assertEquals("Not all tags added",validTags.length, tags.size() );
		
		// Tests that invalid tags cannot be added
		for(String itag: invalidTags){
			Tag tag = new Tag(itag);
			claim2.addTag(tag);
			assertFalse("inValid tag was added", Arrays.asList(claim2.getTags()).contains(tag));
		}

	}
	
	/**Part of Use Case C1
	 * Test that you can remove a tag
	 * US03.01.02
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/56
	 * US03.02.03 - Deleting a tag
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	@SuppressWarnings("unchecked")
	public void testRemoveTags() throws InvalidDateException, EmptyFieldException, DuplicateException, InvalidUserPermissionException{
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		Tag t1 = new Tag("test");
		claim.addTag(t1);
		claim.removeTag(t1);
		
		// Ensure tags can be removed
		assertFalse("Tag was not removed", Arrays.asList(claim.getTags()).contains(t1));

	}
	
	/**Part of Use Case C1
	 * Test that you cannot add duplicate tags
	 * US03.01.03
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/56
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void testNoDuplicates() throws InvalidDateException, EmptyFieldException, DuplicateException, InvalidUserPermissionException{
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		String[] validTags = {"valid","VALID", "a1", "HOr3to"};
		
		// Add a valid tag, then try to add it again
		for(String vtag: validTags) {
			Tag tag = new Tag(vtag);
			claim.addTag(tag);
			int originalSize = claim.tags.size();
			
			try {
				claim.addTag(tag);
			} catch (DuplicateException e) {
				
			}
			
			assertEquals("Duplicate Tag was added", originalSize, claim.tags.size());
		}

	} 
	
	
	/**Part of Use Case C1
	 * Test Renaming a tag
	 * US03.02.04
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testRenameTags() throws InvalidDateException, EmptyFieldException, DuplicateException, InvalidUserPermissionException{	
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		String[] validTags = {"valid","VALID", "a1", "HOr3to"};
		int i = 0;
	
		// Add a tag and then try to rename it
		for (String vtag: validTags) {
			Tag tag = new Tag(vtag);
			claim.addTag(tag);
		}
		//Renames tags
		claim.setTag(i, "Hello");
		assertEquals("Claim has the new name", "Hello", claim.getTag(i).getTagName());
		i += 1;
	}

	/**
	 * Test listing available tags as well as the TagManager
	 * US03.02.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testTagManager() throws InvalidDateException, EmptyFieldException, DuplicateException, InvalidUserPermissionException{
		TagManager tm = new TagManager();
		Tag tag1 = new Tag("yolo");
		Tag tag2 = new Tag("Gift to self");
		Tag tag3 = new Tag("On Sale");
		Tag tag4 = new Tag("Aniversary gift");
		
		// Create a claim with tags, and also add those tags to the tagManager
		Claim claim1 = new Claim ("User1", new Date(10), new Date(200), null, null);
		String c1ID = claim1.getclaimID();
		claim1.addTag(tag1);
		tm.add(tag1, c1ID);
		claim1.addTag(tag2);
		tm.add(tag2, c1ID);
		ClaimListSingleton.addClaim(claim1);
		
		//Claim 1 --> tag1,tag2
		
		Claim claim2 = new Claim ("User2", new Date(10), new Date(200), null, null);
		String c2ID = claim2.getclaimID();
		claim2.addTag(tag1);
		tm.add(tag1, c2ID);
		claim2.addTag(tag3);
		tm.add(tag3, c2ID);
		ClaimListSingleton.addClaim(claim2);
		
		//Claim2 --> tag1,tag3
		
		Claim claim3 = new Claim ("User3", new Date(10), new Date(200), null, null);
		String c3ID = claim3.getclaimID();
		claim3.addTag(tag4);
		tm.add(tag4, c3ID);
		claim3.addTag(tag3);
		tm.add(tag3, c3ID);
		ClaimListSingleton.addClaim(claim3);
		ClaimListSingleton.getClaimList().setTagMan(tm);
		
		//Claim3 --> tag4, tag3
		
		// currently have 3 claims with a variety of tags
		// Tests that the tagManager contains the appropriate information
		
		HashMap<Tag, ArrayList<String>> manager = tm.getManager();

		assertEquals("Contains more or less than 4 tags",4, manager.keySet().size());
		assertTrue("yolo Tag not found", manager.containsKey(tag1));
		assertTrue("Gift to self Tag not found", manager.containsKey(tag2));
		assertTrue("On Sale Tag not found", manager.containsKey(tag3));
		assertTrue("Aniversary gift Tag not found", manager.containsKey(tag4));
		assertEquals("Incorrect number of tags for claim1",2, claim1.tags.size());
		assertEquals("Incorrect number of claims for tag1", 2, tm.getAssociatedClaims(tag1).size());
		
		ArrayList<Tag> filterBy = new ArrayList<Tag>();
		ArrayList<String> check = new ArrayList<String>();
		filterBy.add(tag2);
		check.add(claim1.getclaimID());
		ArrayList<String> filts = ClaimListSingleton.getClaimList().filter(filterBy);
		assertEquals("filtered too many",1,filts.size());
		assertEquals("filtered wrong",check,filts);
		
		
		// test tag removal
		claim1.removeTag(tag1);
		tm.remove(tag1, c1ID);
		assertEquals("Incorrect number of tags for claim1",1, claim1.tags.size());
	}
	
}
