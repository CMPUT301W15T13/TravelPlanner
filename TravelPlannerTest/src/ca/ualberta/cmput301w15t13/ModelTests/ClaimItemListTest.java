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


package ca.ualberta.cmput301w15t13.ModelTests;

import java.util.ArrayList;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.Tag;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.DuplicateException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

public class ClaimItemListTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {


	public ClaimItemListTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testSetUp() throws InvalidDateException, InvalidNameException, InvalidUserPermissionException{
		ClaimList itemList = new ClaimList();
		assertNotNull("Item list is null", itemList);
		assertEquals("Item list is not empty",0, itemList.size());
		
		ArrayList<Claim> oldClaims = new ArrayList<Claim>();
		Claim claim1 = null, claim2 = null;
		claim1 = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		claim2 = new Claim("name3", new Date(2), new Date(4), "Des3t", new TravelItineraryList());

		oldClaims.add(claim1);
		oldClaims.add(claim2);

		ClaimList claimList = new ClaimList(oldClaims);
		assertNotNull("Item list is null", claimList);
		assertEquals("Item list is not empty", 2,  claimList.size());
		
		ArrayList<Claim> dupes = claimList.getClaimArrayList();
		assertNotNull("Dupes is null", dupes);
		assertNotSame("Dupes is empty", 0,  dupes.size());
		assertEquals("dupes is not size 2",2, dupes.size());
		assertTrue("dupes doesnt have claim 1", dupes.contains(claim1));
		assertTrue("dupes doesnt have claim 2", dupes.contains(claim2));
	}
	
	
	
	public void testAddRemove() throws InvalidDateException, InvalidNameException, InvalidUserPermissionException{
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		
		claimList.add(claim);
		assertEquals("ClaimList length is not 1", 1,claimList.size());
		assertTrue("Claim list doesn't containt claim 1", claimList.contains(claim));
		assertFalse("Claim list thinks it has claim 2", claimList.contains(claim2));
		
		claimList.add(claim2);
		assertEquals("ClaimList length is not 2",2, claimList.size());
		assertTrue("Claim list doesn't containt claim 1 with 2 elements", claimList.contains(claim));
		assertTrue("Claim list doesn't containt claim 2", claimList.contains(claim2));
		
		claimList.add(claim);
		assertEquals("Claim length reperesents the duplicate claim",2, claimList.size());
		assertTrue("Claim was added twice", claimList.contains(claim));
		
		claimList.remove(claim);
		assertEquals("ClaimList length is not 1", 1, claimList.size());
		assertFalse("Claim list contains claim 1", claimList.contains(claim));
		
		claimList.remove(claim);
		assertEquals("ClaimList length is not 1", 1, claimList.size());
		assertFalse("Claim list contains claim 1", claimList.contains(claim));
		
		claimList.remove(claim2);
		assertEquals("ClaimList length is not 0", 0, claimList.size());
		assertFalse("Claim list contains claim 2", claimList.contains(claim2));
	}

	public void testIndexing() throws InvalidDateException, InvalidNameException, InvalidUserPermissionException{
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		
		claimList.add(claim);
		claimList.add(claim2);
		
		assertEquals("Claim index is not 0",claim,  claimList.getClaimAtIndex(0));
		assertEquals("Claim2 index is not 1", claim2, claimList.getClaimAtIndex(1));
		
		claimList.removeClaimAtIndex(0);
		assertFalse("Claimlist still has claim 1", claimList.contains(claim));
		
		claimList.add(claim);
		assertEquals("Claim index is not 1, now that it's readded",claim,  claimList.getClaimAtIndex(1) );
		
		claimList.removeClaimAtIndex(1);
		assertFalse("Claimlist still has claim 1", claimList.contains(claim));
		
		claimList.removeClaimAtIndex(0);
		assertFalse("Claimlist still has claim 2", claimList.contains(claim2));
		assertEquals("claim list is not empty",0, claimList.size() );

	}

	public void testListeners(){
		ClaimList claimList = new ClaimList();
		claimList.addListener(new Listener(){
			@Override
			public void update(){
				assertTrue("Passed!" , true);
			}
		});
	
		claimList.notifyListeners();
	}
	
	public void testFilter() throws InvalidDateException, InvalidNameException, InvalidUserPermissionException, DuplicateException {
		TagManager tm = new TagManager();
		
		Tag tag1 = new Tag("Ugent");
		Tag tag2 = new Tag("Money");
		Tag tag3 = new Tag("To do");
		Tag tag4 = new Tag("Souvenir");
		
		ClaimList claimList = new ClaimList();
		
		ArrayList<Tag> filterTags = new ArrayList<Tag>();
		
		ArrayList<String> test1 = new ArrayList<String>();
		ArrayList<String> test2 = new ArrayList<String>();
		ArrayList<String> test3 = new ArrayList<String>();
		
		Claim claim = new Claim("Name", new Date(1), new Date(2), null, null);
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), null, null);
		
		claim.addTag(tag1);
		tm.add(tag1, claim.getclaimID());
		
		claimList.add(claim);
		claimList.setTagMan(tm);
		
		
		filterTags.add(tag1);
		test1.add(claim.getclaimID());
		
		//filter by tag1
		assertEquals("Claim1 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(1, claimList.filter(filterTags).size());
		
		claim.addTag(tag2);
		tm.add(tag2, claim.getclaimID());
		claimList.setTagMan(tm);
		
		//filter by tag1 again
		assertEquals("Claim1 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(1, claimList.filter(filterTags).size());
		
		//filter by new tag (tag2)
		filterTags.set(0, tag2);
		assertEquals("Claim1 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(1, claimList.filter(filterTags).size());
		
		//filter by both tags
		filterTags.add(tag1);
		assertEquals("Claim1 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(1, claimList.filter(filterTags).size());
		
		//filter multiple tags
		claim2.addTag(tag2);
		tm.add(tag2, claim2.getclaimID());
		
		claimList.add(claim2);
		claimList.setTagMan(tm);
		// claim1 -- tag1, tag2
		// claim2 -- tag2
		filterTags.remove(1);
		// filterTags = (tag2)
		test1.add(claim2.getclaimID());
		// test1 = (claim1 id, claim2 id)
		assertEquals("Claim1 and Claim2 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(2, claimList.filter(filterTags).size());
		
		
	}
	

}
