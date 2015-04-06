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
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/*
 * This test suite tests the functionality of the application
 * when the user is an approver. Specifically, that an approver
 * can approve claims, comment on claims, and return claims.
 *
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 * 
 * All Tests should pass
 */

public class ApproverTests extends ActivityInstrumentationTestCase2<LoginActivity>{

	public ApproverTests() {
		super(LoginActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	/** Test Case H2
	 * Tests that as an approver you are able to return a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/78
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testReturnClaim() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());;
		Approver approver = new Approver("Catbert");
		
		// Test the approvers ability to return claims based on their status
		claim.giveStatus(statusEnum.SUBMITTED);
		approver.returnClaim(claim);
		assertEquals("Claim status isn't returned", statusEnum.RETURNED, claim.getStatus());
		assertEquals("Approver name not set", "Catbert", claim.getlastApproverName());
		
		claim.giveStatus(statusEnum.INPROGRESS);
		approver.returnClaim(claim);
		assertEquals("Approver was able to return an INPROGRESS claim",statusEnum.INPROGRESS, claim.getStatus());
		
		claim.giveStatus(statusEnum.APPROVED);
		approver.returnClaim(claim);
		assertEquals("Approver was able to return an APPROVED claim",statusEnum.APPROVED, claim.getStatus());
		
		// Perhaps redundant
		claim.giveStatus(statusEnum.RETURNED);
		approver.returnClaim(claim);
		assertEquals("Approver was able to return a RETURNED claim",statusEnum.RETURNED, claim.getStatus() );
	}
	
	
	/** Test Case H3
	 * Tests that as an approver you are able to approve a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/79
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testClaimApprove() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Approver approver = new Approver("Catbert");
		assertEquals("Approver name incorrect", "Catbert", approver.getName());
		
		claim.giveStatus(statusEnum.SUBMITTED);
		approver.approveClaim(claim);
		assertEquals("Claim status isn't approved",statusEnum.APPROVED, claim.getStatus());
		assertEquals("Approver name not set", "Catbert", claim.getlastApproverName());
		
		claim.giveStatus(statusEnum.INPROGRESS);
		approver.approveClaim(claim);
		assertEquals("Approver was able to approve an INPROGRESS claim",statusEnum.INPROGRESS, claim.getStatus());
		
		claim.giveStatus(statusEnum.RETURNED);
		approver.approveClaim(claim);
		assertEquals("Approver was able to approve a RETURNED claim",ClaimStatus.statusEnum.RETURNED, claim.getStatus());
		
		// Also may be redundant
		claim.giveStatus(statusEnum.APPROVED);
		approver.approveClaim(claim);
		assertEquals("Approver was able to approve an APPROVED claim",statusEnum.APPROVED, claim.getStatus());
		
	}
	
	/** Use Case H4
	 * Approver can set one or more comments on a claim that is submitted,
	 * and cannot modify a claim that is not submitted.
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/77
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void testComment() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Approver approver = new Approver("Catbert");
		String comment = "Test";
		
		claim.giveStatus(statusEnum.SUBMITTED);
		
		approver.addComment(claim, comment);
		ArrayList<String> comments = claim.getComments();
		assertNotNull("Claim comments are null", comments);
		assertEquals("There are no claim comments",1,  comments.size());
		assertTrue("Comment isn't added", comments.contains(comment));
		assertEquals("Approver name not set", "Catbert", claim.getlastApproverName());
		
		claim.giveStatus(statusEnum.INPROGRESS);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to an inprogress claim",0 , comments.size());
		
		claim.giveStatus(statusEnum.RETURNED);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to a returned claim",0 , comments.size());
		
		claim.giveStatus(statusEnum.APPROVED);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to an approved claim",0 , comments.size());	
		
	}
	
	/**
	 * Test that the Approver sees the correct submitted claims
	 * Tests US08.01.01 - partly
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	
	public void testApproverView() throws EmptyFieldException, InvalidDateException {
		Approver a = new Approver("andry");
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Claim claim1 = new Claim("name1", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Claim claim2 = new Claim("name2", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Claim claim3 = new Claim("name3", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		
		// submit two claims
		claim.giveStatus(statusEnum.SUBMITTED);
		claim1.giveStatus(statusEnum.SUBMITTED);
		
		ArrayList<Claim> allClaims = new ArrayList<Claim>();
		allClaims.add(claim);
		allClaims.add(claim1);
		allClaims.add(claim2);
		allClaims.add(claim3);
		
		// Test to see that the approver only sees the submitted claims
		assertEquals("Approver sees wrong number of claims",2, a.getPermittableClaims(allClaims).size());
		assertEquals("Approver sees wrong claim", claim, a.getPermittableClaims(allClaims).get(0));
		assertEquals("Approver sees wrong claim", claim1, a.getPermittableClaims(allClaims).get(1));	
	}
	
	/**
	 * Tests that the claims the approver sees are sorted as wanted
	 * Tests US08.02.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	
	public void testSorted() throws EmptyFieldException, InvalidDateException {
		Approver a = new Approver("andry");
		// Create 2 claims with distinct date differences
		// Note that claim1 is made with an older start date and should
		// be sorted to the top of the list
		Claim claim = new Claim("name", new Date(1000), new Date(1100), "Dest", new TravelItineraryList());
		Claim claim1 = new Claim("name1", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		
		//submit two claims
		claim.giveStatus(statusEnum.SUBMITTED);
		claim1.giveStatus(statusEnum.SUBMITTED);
		
		ArrayList<Claim> allClaims = new ArrayList<Claim>();
		allClaims.add(claim);
		allClaims.add(claim1);
		assertEquals("submitted claims weren't sorted", claim1, a.getPermittableClaims(allClaims).get(0));
		
		// Create and submit a claim that should go in between the list
		Claim claim2 = new Claim("name1", new Date(25), new Date(25), "Dest", new TravelItineraryList());
		claim2.giveStatus(statusEnum.SUBMITTED);
		allClaims.add(claim2);
		assertEquals("submitted claims weren't sorted", claim2, a.getPermittableClaims(allClaims).get(1));
		assertEquals("submitted claims weren't sorted", claim, a.getPermittableClaims(allClaims).get(2));
		
		// Create and submit a claim that should bubble up to the top
		Claim claim3 = new Claim("name1", new Date(0), new Date(0), "Dest", new TravelItineraryList());
		claim3.giveStatus(statusEnum.SUBMITTED);
		allClaims.add(claim3);
		assertEquals("submitted claims weren't sorted", claim3, a.getPermittableClaims(allClaims).get(0));
	}
}
