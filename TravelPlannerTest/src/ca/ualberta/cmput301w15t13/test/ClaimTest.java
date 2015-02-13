package ca.ualberta.cmput301w15t13.test;

import java.util.Date;

import ca.ualberta.cmput301w15t13.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;


/**
 * These are the JUnit tests for the claim class
 * @author eorodrig
 *
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

	
	
	/**
	 * Test a regular name and a starting date preceding an end date is accepted 
	 * US01.01.01
	 */
	public void testMakeRegularClaim(){
		
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		
		//Claim constructor with string name, Date startDate, Date endDate
		Claim claim = new Claim(name, startDate, endDate);
	
		
		claim.addDescription("Trip to Rome");

		
		assertTrue("Claim is null", claim != null);
		assertTrue("Claim name is wrong", claim.getName().equals(name));
		assertTrue("Claim startDate is wrong", claim.getStartDate().equals(startDate));
		assertTrue("Claim name is wrong", claim.getEndDate().equals(endDate));
		assertTrue("Claim description is wrong", claim.getDescription().equals("Trip to Rome"));
		
	}
	
	

	/**
	 * Test that you can't add an end date that occurs after the start date. 
	 * US01.01.02
	 */
	public void testUseInvalidDate(){
		
		String name = "Bill Smith";
		Date startDate = new Date(120), endDate = new Date(100);
		Claim claim = new Claim(name, startDate, endDate);
		
		assertTrue("Invalid Claim: Start date occurs after end date", claim.getEndDate.before(claim.getStartDate()));
	}
	
	/**
	 * Tests that the claim can add travelDestination 
	 * US01.02.01
	 */
	public void testTravelDestination(){
		
		String name = "Bill Smith";
		Date startDate = new Date(120), endDate = new Date(100);
		Claim claim = new Claim(name, startDate, endDate);
		
		//this method adds the travel destination to a collection (possibly hash table/map)
		claim.addTravelDestination("Russia","Bear wrestling");
		
		//look to see if destination collection is larger than 0
		assertTrue("Claim has has no Travel Destinations", claim.numberOfDestinations() > 0);
		
		//test to see if the contents of the destination collection are correct
		assertTrue("Claim has invalid Travel Destination", claim.getTravelDestination(0).getDestinationName().equals("Russia"));
		assertTrue("Claim has invalid description", claim.getTravelDestination(0).getDestinationDescription().equals("Bear wrestling"));

	}
	
	
	/**
	 * Tests that the claim does not add duplicate entries
	 * US01.02.02
	 */
	public void testInvalidTravelDestination(){
		
		String name = "Bill Smith";
		Date startDate = new Date(120), endDate = new Date(100);
		Claim claim = new Claim(name, startDate, endDate);
		
		//this method adds the travel destination to a collection (possibly hash table/map)
		claim.addTravelDestination("Russia","Bear wrestling");
	
		//Uses an exception to catch duplicate entries
		try {
			claim.addTravelDestination("Russia","Bear wrestling");
			fail("Duplicate Travel Destination Entered");
		}
		catch (DuplicateException e){
		
		}
	}
	
	
	/**
	 *Tests that we can edit a claim
	 * US01.04.01
	 */
	public void testEditClaim(){
		
		String name = "Bill Smith", name2 = "Joe Brown";
		Date startDate = new Date(100), startDate2 = new Date(200);
		Date endDate = new Date(120) , endDate2 = new Date(220);
		String description = "Claim for trip to Rome", description2 = "trip to Italy";
		
		Claim claim = new Claim(name, startDate, endDate);
		claim.addDescription(description);
		
		claim.editName(name2);
		claim.editStartDate(startDate2);
		claim.editEndDate(endDate2);
		claim.editDescription("Trip to Italy");
		
		assertTrue("Edited claim is null", claim != null);
		assertTrue("Edited claim name is wrong", claim.getName().equals(name2));
		assertTrue("Edited claim startDate is wrong", claim.getStartDate().equals(startDate2));
		assertTrue("Edited claim name is wrong", claim.getEndDate().equals(endDate2));
		assertTrue("Edited claim description is wrong", claim.getDescription().equals(description2));

	}
	
	/** 
	 * Tests that you can delete a claim
	 * US01.05.01
	 */
	public void testDeleteClaim(){
		Claim claim = new Claim("test", new Date(1), new Date(2));
		ClaimListSingleton.getClaimList().addClaim(claim);
		
		assertTrue("Claim was not entered", ClaimListSingleton.getClaimList().contains(claim));
		
		ClaimListSingleton.getClaimList().removeClaim(claim);
		assertFalse("Claim was not removed", ClaimListSingleton.getClaimList().contains(claim));
	}
}
