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
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.Tag;
import exceptions.DuplicateException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;



/**
 * 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 */
public class ClaimTagsTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimTagsTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	/**Use Case C1
	 * 
	 * Test that we can add ,edit and del
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 */
	public void testTags() throws InvalidDateException, InvalidNameException, DuplicateException{
	
		this.addTag();
		
		this.removeTag();
		
		this.renameTag();
		
		//tests to see if there are any duplicates
		this.noDuplicates();
	}
	
	
	
	/**Part of Use Case C1
	 * Tests that you're able to add a tag to a claim
	 * and that the tag MUST be alphanumeric
	 * US03.01.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/56
	 * US03.02.02 - Adding a tag
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 */
	
	public void addTag() throws InvalidDateException, InvalidNameException, DuplicateException{
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		String[] validTags = {"valid","VALID", "a1", "HOr3to"},
				invalidTags = {"",  " ", "aoeu ", "a-", "-*&", "1.23"};
		
		for(String vtag: validTags){
			claim.addTag(vtag);
			assertTrue("Valid tag wasn't added", Arrays.asList(claim.getTags()).contains(vtag));
		}
		String[] tags = claim.getTags();
		assertNotNull("Tags is null", tags);
		assertEquals("Not all tags added",validTags.length, tags.length );
		
		claim = new Claim("Name", new Date(1), new Date(2), null, null);
		for(String itag: invalidTags){
			claim.addTag(itag);
			assertFalse("inValid tag was added", Arrays.asList(claim.getTags()).contains(itag));
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
	 */
	public void removeTag() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		String tag = "test";
	
		
		claim.removeTag(tag);
		assertFalse("Tag was not removed", Arrays.asList(claim.getTags()).contains(tag));

	}
	

	
	/**Part of Use Case C1
	 * Test that you cannot add duplicate tags
	 * US03.01.03
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/56
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 */
	
	public void noDuplicates() throws InvalidDateException, InvalidNameException, DuplicateException{
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		String[] validTags = {"valid","VALID", "a1", "HOr3to"};
		
		for(String vtag: validTags){
			claim.addTag(vtag);
			try{
				claim.addTag(vtag);
				fail("Tag added twice");
			}	catch (DuplicateException e){
				
			}
		}

	} 
	
	/**Part of Use Case C1
	 * Test Renaming a tag
	 * US03.02.04
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 */
	public void renameTag() throws InvalidDateException, InvalidNameException, DuplicateException{
		
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		String[] validTags = {"valid","VALID", "a1", "HOr3to"},
				invalidTags = {"",  " ", "aoeu ", "a-", "-*&", "1.23"};
		
		for(String vtag: validTags){
			claim.addTag(vtag);
		}

		//Renames tags
		claim.setTag(1, "Hello");
		assertEquals("Claim has the new name", "Hello", claim.getTag(1));
		
	}

	
	
	/**
	 * Test listing available tags
	 * US03.02.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/57
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 */
	public void testListTags() throws InvalidDateException, InvalidNameException, DuplicateException{
		Claim claim = new Claim ("User1", new Date(10), new Date(200), null, null);
		claim.addTag("yolo");
		claim.addTag("Gift to self");
		ClaimListSingleton.addClaim(claim);
		
		claim = new Claim ("User2", new Date(10), new Date(200), null, null);
		claim.addTag("yolo");
		claim.addTag("On Sale");
		ClaimListSingleton.addClaim(claim);
		
		claim = new Claim ("User3", new Date(10), new Date(200), null, null);
		claim.addTag("Aniversary gift");
		claim.addTag("On Sale");
		ClaimListSingleton.addClaim(claim);
		
		ArrayList<Tag> tagList = TagManager.getTagList();
		
		assertEquals("Contains more or less than 4 tags",4, tagList.size());
		assertTrue("yolo Tag not found", tagList.contains("yolo"));
		assertTrue("Gift to self Tag not found", tagList.contains("Gift to self"));
		assertTrue("On Sale Tag not found",  tagList.contains("On Sale"));
		assertTrue("Aniversary gift Tag not found",  tagList.contains("Aniversary gift"));
	}
	
}
