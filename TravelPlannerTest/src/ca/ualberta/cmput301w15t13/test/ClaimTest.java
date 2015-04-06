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
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
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
	
	public void testMakeRegularClaim() throws EmptyFieldException, InvalidDateException {
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
	public void testAddClaim() throws EmptyFieldException, InvalidDateException {
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
	public void testMakeInvalidClaimDate() throws EmptyFieldException  {
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
	public void testMakeInvalidClaimEmpty() throws InvalidDateException {
		Claim claim = null;
		try {
			// If claim is working properly, it will throw an InvalidNameException
			claim = new Claim("   ", new Date(100), new Date(120), null, null);
		} catch (EmptyFieldException e) {
		}
		// Ensure claim is unchanged
		assertNull("Claim made with invalid name", claim);
	}
	
	public void testMakeInvalidClaimNull() throws InvalidDateException  {
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
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void testAddTravelDestination() throws EmptyFieldException, InvalidDateException, InvalidFieldEntryException {
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		claim.addTravelDestination("Russia","Bear wrestling");
		// look to see if destination was added
		assertEquals("Claim has has no Travel Destinations",1, claim.numberOfDestinations());
		// test to see if the contents of the destination collection are correct
		assertEquals("Claim has invalid Travel Destination", "Russia",claim.getTravelDestination(0).getDestinationName());
		assertEquals("Claim has invalid Travel Destination", "Bear wrestling",claim.getTravelDestination(0).getDestinationDescription());

	}

	/**
	 * Use Case A2
	 * Tests to see if the travel destination entry is valid
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws ClaimPermissionException 
	 * @throws InvalidUserPermissionException 
	 * @throws InvalidFieldEntryException 
	 */
	
	public void testAddInvalidTravelDestination() throws EmptyFieldException, InvalidDateException, InvalidFieldEntryException {
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		claim.addTravelDestination("Aruba", "Jamaica");
		
		try {
			// if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination(null, "Reason for travel");
		} catch (EmptyFieldException e) {
		}
		assertEquals("Null destination added", "Aruba", claim.getTravelDestination(0).getDestinationName());
		
		try {
			// if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("", "Vacation");
		} catch (EmptyFieldException e) {
		}
		assertEquals("Empty destination added", "Aruba", claim.getTravelDestination(0).getDestinationName());
		
		try {
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("London", null);
		} catch (EmptyFieldException e){
		}
		assertEquals("Null destination added", "Jamaica", claim.getTravelDestination(0).getDestinationDescription());	

		try {
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("London", "");
		} catch (EmptyFieldException e) {
		}
		assertEquals("Null destination added", "Jamaica", claim.getTravelDestination(0).getDestinationDescription());			
	}
	
	/** 
	 * Use case A3
	 * Tests that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws EmptyFieldException 
	 * @throws InvalidDateException 
	 */
	public void testEditSimpleClaim() throws EmptyFieldException, InvalidDateException {
		String name = "Bill Smith", name2 = "Joe Brown";
		Date startDate = new Date(100), startDate2 = new Date(200);
		Date endDate = new Date(120) , endDate2 = new Date(220);
		String description = "Claim for trip to Rome";
		String description2 = "trip to Italy";
		
		// Create a claim with certain attributes, edit them. then test the edits
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
	}

	/** 
	 * Use case A3
	 * Tests that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws EmptyFieldException 
	 * @throws InvalidDateException 
	 */
	public void testEditInvalidClaim() throws EmptyFieldException, InvalidDateException{
		String name = "Bill Smith";
		Date startDate = new Date(100);
		Date endDate = new Date(120);
		String description = "Claim for trip to Rome";
		
		Claim claim = new Claim(name, startDate, endDate, null, null);
		claim.setDescription(description);
		
		try {
			//If this works properly, it will throw an InvalidNameException
			claim.setUserName(null);
		} catch (EmptyFieldException e) {
		}
		assertNotNull("Name set to Null", claim.getUserName());
		
		try {
			//If this works properly, it will throw an InvalidNameException
			claim.setUserName("");
		} catch (EmptyFieldException e) {
		}
		assertEquals("Name set to empty string", "Bill Smith", claim.getUserName());
		
		try {
			//If this works properly, it will throw an InvalidDateException (ie start date is after end date)
			claim.setClaimDates(new Date(121), new Date(120));
		} catch (InvalidDateException e) {	
		}
		assertEquals("Improper start date set", startDate, claim.getStartDate());
		assertEquals("Improper end dates set", endDate, claim.getEndDate());
	}
	
	/** 
	 * Use case A3
	 * This will test to see if we can edit a travel destination
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws EmptyFieldException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 */
	public void testEditTravelDestinations() throws EmptyFieldException, InvalidDateException, InvalidFieldEntryException {
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
		} catch (EmptyFieldException e) {	
		}
		assertNotSame("Null name was added", null, claim.getTravelDestination(1).getDestinationName());

		try{
			//If this works it will throw an InvalidFieldEntryExeption
			claim.editTravelDescription(1,  "Canada",null);
		}
		catch (EmptyFieldException e) {
		}
		assertNotSame("Null description was added", null, claim.getTravelDestination(1).getDestinationDescription());
	}
	
	/** 
	 * Use case A3
	 * This will test to see if we can delete a Travel Destination
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws EmptyFieldException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 */
	
	public void testDeleteTravelDestinations() throws EmptyFieldException, InvalidDateException, InvalidFieldEntryException  {
		Claim claim = new Claim("name", new Date(100), new Date(120), null, null);
		claim.addTravelDestination("Russia", "Bear hunting");
		claim.addTravelDestination("China", "Relic hunting");

		// Test deleting a travel destination
		claim.deleteTravelDestination(0);		
		assertEquals("Delete Travel Destination is of invalid size",1,  claim.numberOfDestinations());
		assertEquals("Delete Travel Destination failed: Check Location", "China", claim.getTravelDestination(0).getDestinationName());
		assertEquals("Delete Travel Destination failed: Check Description", "Relic hunting", claim.getTravelDestination(0).getDestinationDescription());
	}
	
	/** Use Case A4
	 * Tests that you can delete a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 */
	public void testDeleteClaim() throws EmptyFieldException, InvalidDateException{
		// Create a claim List with 2 claims
		Claim claim = new Claim("test", new Date(1), new Date(2), null, null);
		Claim claim2 = new Claim("test2", new Date(5), new Date(6), null, null);
		ClaimList claimlist = new ClaimList();
		claimlist.add(claim);
		claimlist.add(claim2);		
		assertTrue("Claim was not entered", claimlist.contains(claim));
		// Test removing it
		claimlist.remove(claim);
		assertFalse("Claim was not removed", claimlist.contains(claim));
		assertTrue("Claim removed too many claims", claimlist.contains(claim2));
		assertEquals("Removed wrong claim", "test2", claimlist.getClaimAtIndex(0).getUserName());
	}
}
