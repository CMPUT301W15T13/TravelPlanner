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

import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import exceptions.ClaimPermissionException;
import exceptions.DuplicateException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import android.test.ActivityInstrumentationTestCase2;


/**
 * These are the JUnit tests for the claim class
 * @author eorodrig
 *
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 */

//This is the test for claims
//the LoginActivity value used will have to change once the classes are finalized
public class ClaimTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	/**  Use Case A1
	 * 
	 * Test a regular name and a starting date preceding an end date is accepted 
	 * 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	
	public void testAddClaim() throws InvalidDateException, InvalidNameException{
			
		//this will test the creation of 1 claim
		this.makeRegularClaim();
		
		//test to see if we can make an invalid Claim (no name)
		this.makeInvalidClaimEmpty();
		
		//test to see if we can make an invalid Claim (null)
		this.makeInvalidClaimNull();
		
		//this will test to see if an invalid date is entered
		this.makeInvalidClaimDate();
		
		//this will check to see if a claim can be added
		this.addClaim();

	}
	
	
	/** Use Case A1
	 * 
	 * This will test to see if we can make a regular claim
	 * 	 * Test that you can't add an end date that occurs after the start date. 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	private void makeRegularClaim() throws InvalidDateException, InvalidNameException{
		
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		
		//ClaimList to hold claims
		ClaimList claimList = new ClaimList();
		
		//Claim constructor with string name, Date startDate, Date endDate
		Claim claim = new Claim(name, startDate, endDate, "", null);
	
		//Add description to claim
		claim.setDescription("Trip to Rome");

		
		assertNotNull("Claim is null", claim);
		assertEquals("Claim name is wrong", name, claim.getUserName());
		assertEquals("Claim startDate is wrong", startDate, claim.getStartDate());
		assertEquals("Claim endDate is wrong", endDate, claim.getEndDate());
		assertEquals("Claim description is wrong", "Trip to Rome",  claim.getDescription());

		
	}
	
	/**Use Case A1
	 * This will test to see if we can add 1-3 claims to a claim list 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	private void addClaim() throws InvalidDateException, InvalidNameException{
		

		//ClaimList to hold claims
		ClaimList claimList = new ClaimList();
		

			//Claim constructor with string name, Date startDate, Date endDate
			Claim claim1 = new Claim("Name1", new Date(100), new Date(120),null, null );
			
			
		claimList.add(claim1);
		
		//add single claim
		assertEquals("Claimlist size is not 1", 1, claimList.size());
		
	
		//add 2 more claims
		Claim claim2 = new Claim("Name2", new Date(100), new Date(120), null, null);
		Claim claim3 = new Claim("Name3", new Date(100), new Date(120), null, null);

		
		claimList.add(claim2);
		claimList.add(claim3);
		
		//check to see that 3 items were added
		assertEquals("Claimlist size is not 3 ", 3, claimList.size());
		

	}
		
	
	

	/**Use Case A1
	 * Test that you can't add an end date that occurs after the start date. 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidNameException 
	 */
	private void makeInvalidClaimDate() throws InvalidNameException{
		
		String name = "Bill Smith";
		Date startDate = new Date(120); 
		Date endDate = new Date(100);
		
		try{
			//This will throw an InvalidDateException if the claim model is working correctly
			Claim claim = new Claim(name, startDate, endDate, null, null);
			
			//if we make it here, we failed the test
			fail("Invalid Claim: Start date occures after end date");
		}
		catch(InvalidDateException e){
			
		}
		
	}
	
	/**Use Case A1
	 * Test that you can't add an end date that occurs after the start date. 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/48
	 * @throws InvalidDateException 
	 */
	private void makeInvalidClaimEmpty() throws InvalidDateException{
		
		try{
			//If claim is working properly, it will throw an InvalidNameException
			Claim claim = new Claim("   ", new Date(100), new Date(120), null, null);
			
			//if we make it here, we failed the test
			fail("Invalid Claim: Name is empty");
		}
		catch(InvalidNameException e){
			
		}
		
	}
	
	private void makeInvalidClaimNull() throws InvalidDateException {
		
		try{
		//If claim is working properly, it will throw an InvalidNameException
		Claim claim = new Claim(null, new Date(100), new Date(120), null, null);
		
		//if we make it here, we failed the test
		fail("Invalid Claim: Name is Null");
		
		}
		
		catch(InvalidNameException e){
		
		}
		
	}
	
	
	/**Use Case A2
	 * Tests that the claim can add travelDestination 
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 */
	public void testAddTravelDestination() throws InvalidDateException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException{
		
		//test valid travel destination
		this.addTravelDestination();
		
		//test duplicate travel destination
		this.addDuplicateTravelDestination();
		
		//test invalid travel destination
		this.addInvalidTravelDestination();
	}
	
	/**Use Case A2
	 * Tests to see if wwe can add a valid travel destination
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 */
	private void addTravelDestination() throws InvalidDateException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException{

		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		
		//this method adds the travel destination to a collection (possibly hash table/map)
		claim.addTravelDestination("Russia","Bear wrestling");
		
		//look to see if destination collection is larger than 0
		assertEquals("Claim has has no Travel Destinations",1, claim.numberOfDestinations());

		
		//test to see if the contents of the destination collection are correct
		assertEquals("Claim has invalid Travel Destination", "Russia",claim.getTravelDestination(0).getDestinationName());
		assertEquals("Claim has invalid Travel Destination", "Bear wrestling",claim.getTravelDestination(0).getDestinationDescription());

		
	}
	
	
	/**Use Case A2
	 * Tests that the claim does not add duplicate entries
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 */
	private void addDuplicateTravelDestination() throws InvalidDateException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException{
		
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		
		//this method adds the travel destination to a collection (possibly hash table/map)
		claim.addTravelDestination("Russia","Bear wrestling");
	
		//Uses an exception to catch duplicate entries
		try {
			//If this works properly, it will throw a DuplicateException
			claim.addTravelDestination("Russia","Bear wrestling");
			
			//if we make it here, we failed the test
			fail("Duplicate Travel Destination Entered");
		}
		catch (DuplicateException e){
		
		}

	}
	
	/**Use Case A2
	 * Tests to see if the travel destination entry is valid
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/49
	 * @throws DuplicateException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws ClaimPermissionException 
	 */
	private void addInvalidTravelDestination() throws DuplicateException, InvalidDateException, InvalidNameException, ClaimPermissionException{
		
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		Claim claim = new Claim(name, startDate, endDate, null, null);
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination(null, "Vacation");
			
			//if we make it here, we failed the test
			fail("Travel Destination is null");
			
		}
		catch(InvalidFieldEntryException e){
			
		}
		
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("  ", "Vacation");
			
			//if we make it here, we failed the test
			fail("Travel Destination is empty");
			
		}
		catch(InvalidFieldEntryException e){
			
		}
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("London", null);
			
			//if we make it here, we failed the test
			fail("Destination Description is null");
			
		}
		catch(InvalidFieldEntryException e){
			
		}
		
		
		try{
			//if this works correctly it will throw an InvalidFieldException
			claim.addTravelDestination("London", "  ");
			
			//if we make it here, we failed the test
			fail("Destination Description is empty");
			
		}
		catch(InvalidFieldEntryException e){
			
		}

		
	}
	
	
	/** Use case A3
	 * Test that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 */
	public void testEditClaim() throws InvalidDateException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException{
		
		//this test the editability of simple claim items (Name, dates, description)
		this.editSimpleClaim();
		
		//this will test invalid edits (invalid dates or empty claim name)
		this.editInvalidClaim();
		
		//this will test the editability of travel destinations
		this.editTravelDestinations();
		this.deleteTravelDetinations();
	}
	
	
	/** Use case A3
	 *Tests that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	private void editSimpleClaim() throws InvalidDateException, InvalidNameException{
		
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

	}

	
	/** Use case A3
	 *Tests that we can edit a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/51
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	private void editInvalidClaim() throws InvalidDateException, InvalidNameException{
		
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
			
		} catch (InvalidNameException e) {

		}
		
		try {
			//If this works properly, it will throw an InvalidNameException
			claim.setUserName("   ");
			
			//if we make it here, we failed the test
			fail("Invalid Name: Name is blank");
		} catch (InvalidNameException e) {

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
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 */
	private void editTravelDestinations() throws InvalidDateException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException{
		
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
			//If this works, it will throw a DuplicateException
			claim.editTravelDescription(1, "China", "Relic hunting");
			
			//if we make it here, we failed the test
			fail("Invalid Travel Destination: Duplicate entry");
		}
	catch(DuplicateException e){
			
		}
		
		try{
			//If this works it will throw an InvalidFieldEntryExeption
			claim.editTravelDescription(1,  null, "Relic hunting");
			
			//if we make it here, we failed the test
			fail("Invalid Travel Destination: invalid Destination");
		}
		catch(InvalidFieldEntryException e){
			
		}
		
		try{
			//If this works it will throw an InvalidFieldEntryExeption
			claim.editTravelDescription(1,  "Canada",null);
			
			//if we make it here, we failed the test
			fail("Invalid Travel Destination: invalid Description");
		}
		catch(InvalidFieldEntryException e){
			
		}

		
	}
	
	/** Use case A3
	 * This wil ltest to see if we can delete a Travel Destination
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 * @throws ClaimPermissionException 
	 */
	private void deleteTravelDetinations() throws InvalidDateException, InvalidNameException, DuplicateException, InvalidFieldEntryException, ClaimPermissionException{
		
		Claim claim = new Claim("name", new Date(100), new Date(120), null, null);
		claim.addTravelDestination("Russia", "Bear hunting");
		claim.addTravelDestination("China", "Relic hunting");
		
		claim.deleteTravelDestination(0);
		
		assertEquals("Delete Travel Destination is of invalid size",1,  claim.numberOfDestinations());
		assertEquals("Delete Travel Destination failed: Check Location", "China", claim.getTravelDestination(0).getDestinationName());
		assertEquals("Delete Travel Destination failed: Check Description", "Relic hunting", claim.getTravelDestination(0).getDestinationDescription());
		
	}
	
	
	
	
	
	
	/** Use Case A4
	 * Tests that you can delete a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/52
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	public void testDeleteClaim() throws InvalidDateException, InvalidNameException{
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

	}
}
