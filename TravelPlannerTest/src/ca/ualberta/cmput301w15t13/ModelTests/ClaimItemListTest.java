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

import persistanceController.DataManager;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimFragmentNavigator;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.Tag;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import ca.ualberta.cmput301w15t13.test.MockListener;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidUserPermissionException;


/* Tests the functionality of the claim List. 
 * 
 * It tests the claimList's ability to add claims, remove claims, filter by tags, 
 * index the list, remove claims at a specific index, that the listener interacts 
 * with it, and that it can be created.
 * 
 *  General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 *
 * All tests should pass
 */

public class ClaimItemListTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {


	public ClaimItemListTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 *  This tests that a claimList can be constructed correctly either from scratch
	 *  or from an existing ArrayList
	 *  Tests US01.01.01, US01.02.01
	 * @throws InvalidDateException
	 * @throws EmptyFieldException
	 * @throws InvalidUserPermissionException
	 */
	public void testSetUp() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		ClaimList itemList = new ClaimList();
		assertNotNull("Item list is null", itemList);
		assertEquals("Item list is not empty",0, itemList.size());
		
		// Create an ArrayList of 2 claims
		ArrayList<Claim> oldClaims = new ArrayList<Claim>();
		Claim claim1 = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Claim claim2 = new Claim("name3", new Date(2), new Date(4), "Des3t", new TravelItineraryList());
		oldClaims.add(claim1);
		oldClaims.add(claim2);

		// Make the claim list with ArrayList
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
	
	
	/** 
	 * This tests the claim list's ability to add and remove it's Claims
	 * Tests US01.01.01, US01.02.01
	 */
	public void testAddRemove() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		
		DataManager.setTestMode();
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		
		// Add claims and test
		claimList.add(claim);
		assertEquals("ClaimList length is not 1", 1,claimList.size());
		assertTrue("Claim list doesn't contain claim 1", claimList.contains(claim));
		assertFalse("Claim list thinks it has claim 2", claimList.contains(claim2));
		
		claimList.add(claim2);
		assertEquals("ClaimList length is not 2",2, claimList.size());
		assertTrue("Claim list doesn't containt claim 1 with 2 elements", claimList.contains(claim));
		assertTrue("Claim list doesn't containt claim 2", claimList.contains(claim2));
		
		claimList.add(claim);
		assertEquals("Claim length represents the duplicate claim",2, claimList.size());
		assertTrue("Original claim was removed", claimList.contains(claim));
		
		// Test removing the same claim twice
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

	/**
	 * Tests that a specific claim can be removed or accessed from the claim 
	 * list via index without error.
	 * Tests US01.05.01
	 * @throws InvalidDateException
	 * @throws EmptyFieldException
	 * @throws InvalidUserPermissionException
	 */
	
	public void testIndexRemoval() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		
		claimList.add(claim);
		claimList.add(claim2);
		
		// Test accessing the added claims.
		assertEquals("Claim index is not 0",claim,  claimList.getClaimAtIndex(0));
		assertEquals("Claim2 index is not 1", claim2, claimList.getClaimAtIndex(1));
		
		// Test removing the claims
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

	/**
	 *  Tests that the listener interacts with the claim list.
	 */
	public void testListeners(){
		ClaimList claimList = new ClaimList();
		MockListener l = new MockListener();
		claimList.addListener(new Listener(){
			@Override
			public void update(){
				assertTrue("Passed!" , true);
			}
		});
		claimList.notifyListeners();
		assertTrue("Listener Called", l.called);
	}
	
	/**
	 * Used for the filter function, to get
	 * the correct claim indices.
	 * We basically want to put in a subset of the whole
	 * list and get an array of the correct indexes of 
	 * that list, in order.
	 * 
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	public void testIndexList() throws EmptyFieldException, InvalidDateException {
		ClaimList cl = new ClaimList();
		//Add arbitrary claims to the list
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		Claim claim3 = new Claim("Name3", new Date(3), new Date(3), "Desc3", new TravelItineraryList());
		Claim claim4 = new Claim("Name4", new Date(4), new Date(4), "Desc4", new TravelItineraryList());
		cl.add(claim);
		cl.add(claim2);
		cl.add(claim3);
		cl.add(claim4);
		
		//mock filtered list
		ArrayList<Claim> filtered = new ArrayList<Claim>();
		filtered.add(claim2);
		filtered.add(claim4);
		
		//make the expected arrayList
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(1);
		expected.add(3);
		
		cl.setIndexList(filtered);
		assertEquals("Wrong indexes returned", expected, cl.getIndexList());
		assertEquals("Wrong amount of indexes returned", 2, cl.getIndexList().size());
		
		filtered.clear();
		filtered.add(claim);
		filtered.add(claim2);
		filtered.add(claim3);
		filtered.add(claim4);
		
		expected.clear();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		
		cl.setIndexList(filtered);
		assertEquals("Wrong indexes returned", expected, cl.getIndexList());
		assertEquals("Wrong amount of indexes returned", 4, cl.getIndexList().size());
		
	}
	
	/**
	 * Tests the filter function, such that
	 * given an array of Tags, the correct claims
	 * are returned.
	 * Tests US03.03.01
	 * @throws InvalidDateException
	 * @throws DuplicateException
	 * @throws EmptyFieldException
	 * @throws InvalidUserPermissionException
	 */
	// These are tests for the filtering of claims via user selected tags
	public void testFilter() throws InvalidDateException,DuplicateException, EmptyFieldException, InvalidUserPermissionException {
		TagManager tm = new TagManager();
		Tag tag1 = new Tag("Ugent");
		Tag tag2 = new Tag("Money");
		
		//acts as tagList for claim
		ArrayList<Tag> tags1 = new  ArrayList<Tag>();
		ArrayList<Tag> tags2 = new  ArrayList<Tag>();
		tags1.add(tag1);
		
		ClaimList claimList = ClaimListSingleton.getClaimList();
		Claim claim1 = new Claim("Name", new Date(1), new Date(2), null, null);
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), null, null);
		
		// filterTags is used for testing, and the other arrayList simulates the tags a claim would have
		ArrayList<Tag> filterTags = new ArrayList<Tag>();
		ArrayList<String> test1 = new ArrayList<String>();
		
		//Will make the claim list contain 1 claim with 1 tag
		filterTags.add(tag1);
		test1.add(claim1.getclaimID());
		
		tm.add(tag1, claim1.getclaimID());
		claim1.tags = tags1;
		claimList.add(claim1);
		claimList.setTagMan(tm);
		
		//filter by tag1
		assertEquals("Claim1 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(1, claimList.filter(filterTags).size());
		
		//Now claim list contains 1 claim with 2 tags
		tags1.add(tag2);
		claim1.tags = tags1;
		tm.add(tag2, claim1.getclaimID());
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
		
		tags2.add(tag2);
		claim2.tags = tags2;
		tm.add(tag2, claim2.getclaimID());
		
		claimList.add(claim2);
		claimList.setTagMan(tm);
		// claimList now contains 2 claims
		// claim1 -- tag1, tag2
		// claim2 -- tag2
		
		// filterTags now is (tag2)
		filterTags.clear();
		filterTags.add(tag2);
		test1.add(claim2.getclaimID());		// test1 = (claim1 id, claim2 id)
		
		//filter by 2 tags
		assertEquals("Claim1 and Claim2 not filtered", test1 ,claimList.filter(filterTags));
		assertEquals(2, claimList.filter(filterTags).size());
		
		test1.clear();
		test1.add(claim1.getclaimID());
		filterTags.clear();
		filterTags.add(tag1);
		
		assertEquals("Claim1 not filtered", test1 , claimList.filter(filterTags));
		assertEquals(1, claimList.filter(filterTags).size());			
	}
	
	/**
	 * Test the claim sort function.
	 * Tests US02.02.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	public void testSort() throws EmptyFieldException, InvalidDateException {
		ClaimList cl = new ClaimList();
		// create claims with a distinct date difference, and add in nonsorted order
		// Test to see if they are put in the right order.
		
		// Per the description: As a claimant, I want the list of expense claims to be sorted by 
		// starting date of travel, in order from most recent to oldest, so that ongoing or recent 
		// travel expenses are quickly accessible.
		
		Claim claim1 = new Claim("Name", new Date(1), new Date(1), null, null);
		Claim claim2 = new Claim("Name2", new Date(50), new Date(52), null, null);
		cl.add(claim1);
		cl.add(claim2);
		
		cl.sortClaimListByDate();
		ArrayList<Claim> sorted = cl.getClaimArrayList();
		
		// Since Date(X) is X milliseconds after Jan 1st 1970, I expect claim2 to be first in the
		// list as it is most recent
		assertEquals("Claims were not sorted", sorted.get(0).getStartDateAsString(), claim2.getStartDateAsString());
		
		// Create a claim that should go in between the two created
		Claim claim3 = new Claim("Name2", new Date(25), new Date(25), null, null);
		cl.add(claim3);
		
		cl.sortClaimListByDate();
		sorted = cl.getClaimArrayList();
		
		assertEquals("Claims were not sorted", sorted.get(1).getStartDateAsString(), claim3.getStartDateAsString());
		
		
	}
	
	/**
	 * Test that a user can "View" a claim
	 * and it's details
	 * Tests US01.03.01, US02.01.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 */
	public void testView() throws EmptyFieldException, InvalidDateException, InvalidFieldEntryException, DuplicateException {
		ClaimList cl = ClaimListSingleton.getClaimList();
		Claim claim1 = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		claim1.addTravelDestination("Russia", "Bear hunting");
		claim1.addTravelDestination("Japan", "Sushi hunting");
		int oldClaimListSize = cl.size();
		cl.add(claim1);
		
		//Test that you can get all needed information from the controller
		assertEquals("Claimlist not populating",oldClaimListSize+1, cl.size());
		assertNotNull("Claimant cannot view details", cl.getClaimAtIndex(2).getUserName());
		assertNotNull("Claimant cannot view details", cl.getClaimAtIndex(2).getStartDate());
		assertNotNull("Claimant cannot view details",cl.getClaimAtIndex(2).getEndDate());
		assertNotNull("Claimant cannot view details",cl.getClaimAtIndex(2).getDescription());
		assertNotNull("Claimant cannot view details",cl.getClaimAtIndex(2).getTravelItineraryAsString());
		
		//Tests the following:
		//As a claimant, I want to list all my expense claims, showing for each claim: the starting date of travel, 
		//the destination(s) of travel, the claim status, tags, and total currency amounts.
		
		ExpenseItem expenseItem1 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "USD", claim1.getclaimID());
		ExpenseItem expenseItem2 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "USD", claim1.getclaimID());
		ExpenseItem expenseItem3 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "CDN", claim1.getclaimID());
		Tag tag = new Tag("tagname");
		
		claim1.addTag(tag);
		claim1.addExpenseItem(expenseItem1);
		claim1.addExpenseItem(expenseItem2);
		claim1.addExpenseItem(expenseItem3);
		
		assertNotNull("Claim doesn't show startDate", cl.getClaimAtIndex(2).getStartDate());
		assertNotNull("Claim doesn't show destinations", cl.getClaimAtIndex(2).getTravelDestination(0));
		assertEquals("Claim doesn't show all destinations", 2,cl.getClaimAtIndex(2).getTravelList().numberofDestinations());
		assertNotNull("Claim doesn't show status", cl.getClaimAtIndex(2).getStatus());
		assertEquals("Claim doesn't show correct status", statusEnum.INPROGRESS,cl.getClaimAtIndex(2).getStatus());
		assertNotNull("Claim doesn't show tags",cl.getClaimAtIndex(2).getTags());
		assertNotNull("Claim doesn't show amounts",cl.getClaimAtIndex(2).getCost());
		assertEquals("Claim doesn't show currency totals of 2 different currency types","20.00 USD\n10.00 CNY\n", cl.getClaimAtIndex(2).getCost());
	}
	

}
