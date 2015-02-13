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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.LoginActivity;

public class ClaimTagsTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimTagsTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Tests that you're able to add a tag to a claim
	 * and that the tag MUST be alphanumeric
	 * US03.01.01
	 * US03.02.02 - Adding a tag
	 */
	
	public void testAddTag(){
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		String[] validTags = {"valid","VALID", "a1", "HOr3to"},
				invalidTags = {"",  " ", "aoeu ", "a-", "-*&", "1.23"};
		
		for(String vtag: validTags){
			claim.addTag(vtag);
			assertTrue("Valid tag wasn't added", claim.getTags().contains(vtag));
		}
		String[] tags = claim.getTaigs();
		assertTrue("Tags is null", tags != null);
		assertTrue("Not all tags added", tags.length == validTags.length);
		
		claim = new Claim("Name", new Date(1), new Date(2));
		for(String itag: invalidTags){
			claim.addTag(itag);
			assertFalse("inValid tag was added", claim.getTags().contains(itag));
		}

	}
	
	/**
	 * Test that you can remove a tag
	 * US03.01.02
	 * US03.02.03 - Deleting a tag
	 */
	public void testRemoveTag(){
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		String tag = "test";
		
		claim.addTag(tag);
		assertTrue("Claim doesnt contain tag", claim.getTags().contains(tag));
		
		claim.removeTag(tag);
		assertFalse("Tag was not removed", claim.getTags().contains(tag));

	}
	
	/**
	 * Test listing available tags
	 * US03.02.01
	 */
	public void testListTags(){
		//stub, This is may be a view/ui test
	}
	
	/**
	 * Test that you cannot add duplicate tags
	 * US03.01.03
	 */
	
	public void testNoDuplicates(){
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		String[] validTags = {"valid","VALID", "a1", "HOr3to"},
		
		for(String vtag: validTags){
			claim.addTag(vtag);
			try{
				claim.addTag(vtag);
				fail("Tag added twice");
			}	catch (DuplicateException e){
				
			}
		}

	}
	/**
	 * Test Renaming a tag
	 * US03.02.04
	 */
	public void testRename(Claim, tagIndex, String newTag){
		Claim.tag[tagIndex] = newTag;
		assertEquals("Claim has the new name", Claim.tag[tagIndex], newName);
		
	}

}
