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

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AssertionFailedError;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import exceptions.ClaimPermissionException;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;


/* 
 * These are the JUnit tests for the claim class, testing
 * the general functionality of a claim.
 *
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 * 
 * All tests should pass
 */


// the LoginActivity value used will have to change once the classes are finalized
public class ClaimTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	/** 
	 * Use Case A1 
	 * This will test to see if we can make a regular claim correctly 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void testMakeRegularClaim() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		
		// Claim constructor with string name, Date startDate, Date endDate
		Claim claim = new Claim(name, startDate, endDate, null, null);
		// Add description to claim
		claim.setDescription("Trip to Rome");

		// Ensure the claim was made correctly
		assertNotNull("Claim is null", claim);
		assertEquals("Claim name is wrong", name, claim.getUserName());
		assertEquals("Claim startDate is wrong", startDate, claim.getStartDate());
		assertEquals("Claim endDate is wrong", endDate, claim.getEndDate());
		assertEquals("Claim description is wrong", "Trip to Rome",  claim.getDescription());
	}
	
	/**
	 * Use Case A1
	 * This will test to see if we can add 1-3 claims to a claim list 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testAddClaim() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		// ClaimList to hold claims
		ClaimList claimList = new ClaimList();
		Claim claim1 = new Claim("Name1", new Date(100), new Date(120),null, null );
		claimList.add(claim1);
		
		// Test claim was added
		assertEquals("Claimlist size is not 1", 1, claimList.size());
		// add 2 more claims
		Claim claim2 = new Claim("Name2", new Date(100), new Date(120), null, null);
		Claim claim3 = new Claim("Name3", new Date(100), new Date(120), null, null);
		claimList.add(claim2);
		claimList.add(claim3);
		// check to see that all items were added
		assertEquals("Claimlist size is not 3 ", 3, claimList.size());
	}
		
	/**
	 * Use Case A1
	 * Test that you can't add an end date that occurs after the start date. 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testMakeInvalidClaimDate() throws EmptyFieldException, InvalidUserPermissionException {
		String name = "Bill Smith";
		Claim claim = null;
		// Start date intentionally after end date
		Date startDate = new Date(120); 
		Date endDate = new Date(100);
		
		try {
			// This will throw an InvalidDateException if the claim model is working correctly
			claim = new Claim(name, startDate, endDate, null, null);	
		} catch (InvalidDateException e) {
			
		}
		assertNull("Claim with invalid dates was added",claim);	
	}
	
	/**
	 * Use Case A1
	 * The following 2 methods Test that you can't add a claim with an invalid name
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testMakeInvalidClaimEmpty() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		Claim claim = null;
		try {
			// If claim is working properly, it will throw an InvalidNameException
			claim = new Claim("   ", new Date(100), new Date(120), null, null);
		} catch (EmptyFieldException e) {
			
		}
		// Ensure claim is unchanged
		assertNull("Claim made with invalid name", claim);
	}
	
	public void testMakeInvalidClaimNull() throws InvalidDateException, InvalidUserPermissionException {
		Claim claim = null;
		try {
			// If claim is working properly, it will throw an InvalidNameException
			claim = new Claim(null, new Date(100), new Date(120), null, null);
		} catch(EmptyFieldException e) {
			
		}
		// Ensure claim is unchanged
		assertNull("Claim made with invalid name", claim);
	}
		
	/**
	 * Use Case A2
	 * Tests to see if we can add a valid travel destination
	 * 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testAddTravelDestination() throws InvalidDateException, EmptyFieldException, InvalidFieldEntryException, InvalidUserPermissionException {
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		claim.addTravelDestination("Russia","Bear wrestling");
		// look to see if destination was added
		assertEquals("Claim has has no Travel Destinations",1, claim.numberOfDestinations());
		// test to see if the contents of the destination collection are correct
		assertEquals("Claim has invalid Travel Destination", "Russia",claim.getTravelDestination(0).getDestinationName());
		assertEquals("Claim has invalid Travel Destination", "Bear wrestling",claim.getTravelDestination(0).getDestinationDescription());

		// The following tests giving a claim a status, and that adding 
		// a travel destination acts correctly according to it's status
		claim.giveStatus(ClaimStatus.SUBMITTED);
		assertEquals("status unchanged", 2, claim.getStatus());
		
		try {
			claim.addTravelDestination("Poland","Yolo");
		} catch (EmptyFieldException e) {
			assertEquals("Added Travel Destination when claim was submitted", 1, claim.numberOfDestinations());
		}
		
		claim.giveStatus(ClaimStatus.APPROVED);
		
		assertEquals("status unchanged", 3, claim.getStatus());
		
		try {
			
			claim.addTravelDestination("Poland","Yolo");
			
		} catch (EmptyFieldException e) {
			
			assertEquals("Added Travel Destination when claim was approved", 1, claim.numberOfDestinations());

		}


	}

	/**Use Case A2
	 * Tests to see if the travel destination entry is valid
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testAddInvalidTravelDestination() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination(null, "Vacation");
			
			//if we make it here, we failed the test
			fail("Travel Destination is null");
			
		}
		catch(EmptyFieldException e){
			
		}
		
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("  ", "Vacation");
			
			//if we make it here, we failed the test
			fail("Travel Destination is empty");
			
		}
		catch(EmptyFieldException e){
			
		}
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("London", null);
			
			//if we make it here, we failed the test
			fail("Destination Description is null");
			
		}
		catch(EmptyFieldException e){
			
		}
		
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("London", "  ");
			
			//if we make it here, we failed the test
			fail("Destination Description is empty");
			
		}
		catch(EmptyFieldException e){
			
		}

		
	}

	
	
	/** Use case A3
	 * Test that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testEditClaim() throws InvalidDateException, EmptyFieldException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException, InvalidUserPermissionException{
		
		//this test the editability of simple claim items (Name, dates, description)
		this.editSimpleClaim();
		
		//this will test invalid edits (invalid dates or empty claim name)
		this.editInvalidClaim();
		
		//this will test the editability of travel destinations
		this.editTravelDestinations();
		this.deleteTravelDestinations();
	}
	
	
	/** Use case A3
	 *Tests that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
	private void editSimpleClaim() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		
		String name = "Bill Smith", name2 = "Joe Brown";
		Date startDate = new Date(100), startDate2 = new Date(200);
		Date endDate = new Date(120) , endDate2 = new Date(220);
		String description = "Claim for trip to Rome", description2 = "trip to Italy";
		
		Claim claim = new Claim(name, startDate, endDate, null, null);
		claim.setDescription(description);
		
		claim.setUserName(name2);
		claim.setClaimDates(startDate2, endDate2);
		claim.setDescription(description2);
		
		assertNotNull("Edited claim is null", claim);
		

		assertEquals("Edited claim name is wrong",  name2, claim.getUserName());
		assertEquals("Edited claim startDate is wrong" , startDate2, claim.getStartDate());
		assertEquals("Edited claim name is wrong", endDate2, claim.getEndDate());
		assertEquals("Edited claim description is wrong", description2, claim.getDescription());
		
		claim.giveStatus(ClaimStatus.SUBMITTED);

		try {
			
			claim.setUserName("yolo");
		
		} catch (EmptyFieldException e) {
			
		}
		
		assertEquals("edited a claim when submitted", "Joe Brown", claim.getUserName());
		
		claim.giveStatus(ClaimStatus.APPROVED);
		
		try {
			
			claim.setUserName("yolo");
		
		} catch (EmptyFieldException e) {
			
		}
		
		assertEquals("edited a claim when Approved", "Joe Brown", claim.getUserName());

//			claim.setUserName("yolo");
//			//this will always fail
//			fail("deleted a claim when not editable");
	}

	
	/** Use case A3
	 *Tests that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
	private void editInvalidClaim() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		
		String name = "Bill Smith";
		Date startDate = new Date(100);
		Date endDate = new Date(120);
		String description = "Claim for trip to Rome";
		
		Claim claim = new Claim(name, startDate, endDate, null, null);
		claim.setDescription(description);
		
		try {
			//If this works properly, it will throw an InvalidNameException
			claim.setUserName(null);
			
			//if we make it here, we failed the test
			fail("Invalid Name: No name entered");
			
		} catch (EmptyFieldException e) {

		}
		
		try {
			//If this works properly, it will throw an InvalidNameException
			claim.setUserName("   ");
			
			//if we make it here, we failed the test
			fail("Invalid Name: Name is blank");
		} catch (EmptyFieldException e) {

		}
		
		try {
			
			//If this works properly, it will throw an InvalidDateException (ie start date is after end date)
			claim.setClaimDates(new Date(121), new Date(120));

			
			//if we make it here, we failed the test
			fail("Invalid Date: Start date occurs after end Date");
			
		} catch (InvalidDateException e) {
			
		}
		
		
	}
	
	/** Use case A3
	 * This will test to see if we can edit a travel destination
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 */
	private void editTravelDestinations() throws InvalidDateException, EmptyFieldException, InvalidFieldEntryException, InvalidUserPermissionException{
		
		Claim claim = new Claim("name", new Date(100), new Date(120), null, null);
		claim.addTravelDestination("Russia", "Bear hunting");
		claim.addTravelDestination("Japan", "Sushi hunting");
		
		//This will edit a description based on the index of the selected item
		claim.editTravelDescription(0, "China", "Relic hunting");
		
		assertEquals("Edit Travel Destination failed: Check Location", "China", claim.getTravelDestination(0).getDestinationName());
		assertEquals("Edit Travel Destination failed: Check Description", "Relic hunting", claim.getTravelDestination(0).getDestinationDescription());
		assertEquals("Edit Travel Destination failed: Changed wrong Location", "Japan", claim.getTravelDestination(1).getDestinationName());
		assertEquals("Edit Travel Destination failed: Changed wrong Description", "Sushi hunting", claim.getTravelDestination(1).getDestinationDescription());
		

		
		try{
			//If this works it will throw an InvalidFieldEntryExeption
			claim.editTravelDescription(1,  null, "Relic hunting");
			
			//if we make it here, we failed the test
			fail("Invalid Travel Destination: invalid Destination");
		}
		catch(EmptyFieldException e){
			
		}
		
		assertNotSame("Null name was added", null, claim.getTravelDestination(1).getDestinationName());

		
		try{
			//If this works it will throw an InvalidFieldEntryExeption
			claim.editTravelDescription(1,  "Canada",null);
			
			//if we make it here, we failed the test
			fail("Invalid Travel Destination: invalid Description");
		}
		catch(EmptyFieldException e){
			
		}
		
		assertNotSame("Null description was added", null, claim.getTravelDestination(1).getDestinationDescription());

		
		claim.giveStatus(ClaimStatus.SUBMITTED);
		
		try {

			claim.editTravelDescription(0, "Australia", "Yolo");
			
		} catch (EmptyFieldException e) {
			
		}
		
		assertEquals("Submitted claim was editted", "China", claim.getTravelDestination(0).getDestinationName());

		
		claim.giveStatus(ClaimStatus.APPROVED);
	
		try {

			claim.editTravelDescription(0, "Australia", "Yolo");
			
		} catch (EmptyFieldException e) {
			
		}
		
		assertEquals("Approved claim was editted", "China", claim.getTravelDestination(0).getDestinationName());

	}
	
	/** Use case A3
	 * This wil ltest to see if we can delete a Travel Destination
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 */
	private void deleteTravelDestinations() throws InvalidDateException, EmptyFieldException, InvalidFieldEntryException, InvalidUserPermissionException{
		
		Claim claim = new Claim("name", new Date(100), new Date(120), null, null);
		claim.addTravelDestination("Russia", "Bear hunting");
		claim.addTravelDestination("China", "Relic hunting");
		
		claim.deleteTravelDestination(0);
		
		assertEquals("Delete Travel Destination is of invalid size",1,  claim.numberOfDestinations());
		assertEquals("Delete Travel Destination failed: Check Location", "China", claim.getTravelDestination(0).getDestinationName());
		assertEquals("Delete Travel Destination failed: Check Description", "Relic hunting", claim.getTravelDestination(0).getDestinationDescription());
		
		
		claim.giveStatus(ClaimStatus.SUBMITTED);

		try {
			
			claim.deleteTravelDestination(0);
		
		} catch (InvalidUserPermissionException e) {
			
		}
		
		assertEquals("deleted a submitted claim", 1, claim.numberOfDestinations());
			

		
		claim.giveStatus(ClaimStatus.APPROVED);
		

		try {
			
			claim.deleteTravelDestination(0);
		
		} catch (InvalidUserPermissionException e) {
			
		}
		
		assertEquals("deleted an approved claim", 1, claim.numberOfDestinations());


	}
	
	
	
	
	
	
	/** Use Case A4
	 * Tests that you can delete a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testDeleteClaim() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		Claim claim = new Claim("test", new Date(1), new Date(2), null, null);
		Claim claim2 = new Claim("test2", new Date(5), new Date(6), null, null);
		ClaimList claimlist = new ClaimList();
		
		claimlist.add(claim);
		claimlist.add(claim2);
		
		assertTrue("Claim was not entered", claimlist.contains(claim));
		
		claimlist.remove(claim);
		assertFalse("Claim was not removed", claimlist.contains(claim));
		assertTrue("Claim removed too many claims", claimlist.contains(claim2));
		
		assertEquals("Removed wrong claim", "test2", claimlist.getClaimAtIndex(0).getUserName());
		
		
		claim2.giveStatus(ClaimStatus.SUBMITTED);
		
		try {
			
			claimlist.claimRemove(claim2);
			
		} catch (InvalidUserPermissionException e) {
			
		}
		
		assertEquals("deleted a claim when submitted", 1, claimlist.size());
		
		claim2.giveStatus(ClaimStatus.APPROVED);
		
		try {
			
			claimlist.claimRemove(claim2);
			
		} catch (InvalidUserPermissionException e) {
			
		}
		
		assertEquals("deleted a claim when approved", 1, claimlist.size());


	}
}
