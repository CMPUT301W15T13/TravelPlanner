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
	public void testAddRegularClaim(){
		
		String name = "Bill Smith";
		Date startDate = new Date(100), endDate = new Date(120);
		
		//Claim constructor with string name, Date startDate, Date endDate
		Claim claim = new Claim(name, startDate, endDate);
		
		assertTrue("Claim is null", claim != null);
		assertTrue("Claim name is wrong", claim.getName().equals(name));
		assertTrue("Claim startDate is wrong", claim.getStartDate().equals(startDate));
		assertTrue("Claim name is wrong", claim.getEndDate().equals(endDate));
	}
	
	
	/**
	 * Test that you can't add an end date that occurs after the start date. 
	 * US01.01.02
	 */
	public void testAddInvalidDate(){
		
		String name = "Bill Smith";
		Date startDate = new Date(120), endDate = new Date(100);
		Claim claim = new Claim(name, startDate, endDate);
		
		assertTrue("Start date occurs after end date", claim.getStartDate.before(claim.getEndDate()));
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
}
