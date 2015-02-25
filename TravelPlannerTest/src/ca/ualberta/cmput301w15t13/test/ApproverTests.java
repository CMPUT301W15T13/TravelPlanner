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

import Expceptions.InvalidDateException;
import Expceptions.InvalidNameException;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;


/** 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 */
public class ApproverTests extends ActivityInstrumentationTestCase2<LoginActivity>{

	public ApproverTests() {
		super(LoginActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	

	
	/** Test Case H2
	 * Tests that as an approver you are able to return a claim
	* https://github.com/CMPUT301W15T13/TravelPlanner/issues/78
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	public void testReturnClaim() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());;
		claim.setStatus(ClaimStatus.RETURNED);
		Approver approver = new Approver("Catbert");
		
		approver.returnClaim(claim);
		assertEquals("Claim status isn't returned", ClaimStatus.RETURNED, claim.getStatus());
		assertEquals("Approver name not set", "Catbert", claim.getlastApproverName());
		
		claim.setStatus(ClaimStatus.INPROGRESS);
		approver.returnClaim(claim);
		assertEquals("Approver was able to return an INPROGRESS claim",ClaimStatus.INPROGRESS, claim.getStatus());
		
		
		//Is this one necessary?
		claim.setStatus(ClaimStatus.RETURNED);
		approver.returnClaim(claim);
		assertEquals("Approver was able to return a RETURNED claim",ClaimStatus.RETURNED, claim.getStatus() );
		
		claim.setStatus(ClaimStatus.APPROVED);
		approver.returnClaim(claim);
		assertEquals("Approver was able to return an APPROVED claim",ClaimStatus.APPROVED, claim.getStatus());
	}
	
	
	/** Test Case H3
	 * Tests that as an approver you are able to approve a claim
	* https://github.com/CMPUT301W15T13/TravelPlanner/issues/79
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	public void testClaimApprove() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		claim.setStatus(ClaimStatus.SUBMITTED);
		Approver approver = new Approver("Catbert");
		approver.approveClaim(claim);
		
		assertEquals("Claim status isn't approved",ClaimStatus.APPROVED, claim.getStatus() );
		assertEquals("Approver name not set", "Catbert", claim.getlastApproverName());
		
		claim.setStatus(ClaimStatus.INPROGRESS);
		approver.approveClaim(claim);
		assertEquals("Approver was able to approve an INPROGRESS claim",ClaimStatus.INPROGRESS, claim.getStatus());
		
		claim.setStatus(ClaimStatus.RETURNED);
		approver.approveClaim(claim);
		assertEquals("Approver was able to approve a RETURNED claim",ClaimStatus.RETURNED, claim.getStatus());
		
		//Is this one needed?
		claim.setStatus(ClaimStatus.APPROVED);
		approver.approveClaim(claim);
		assertEquals("Approver was able to approve an APPROVED claim",ClaimStatus.APPROVED, claim.getStatus());
		
	}
	
	/** Use Case H4
	 * Approver can set one or more comments on a claim that is submitted,
	 * and cannot modify a claim that is not submitted.
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/77
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	
	public void testComment() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Approver approver = new Approver("catbert");
		String comment = "Test";
		
		claim.setStatus(ClaimStatus.SUBMITTED);
		
		approver.addComment(claim, comment);
		ArrayList<String> comments = claim.getComments();
		assertNotNull("Claim comments are null", comments);
		assertEquals("There are no claim comments",1,  comments.size());
		assertTrue("Comment isn't added", comments.contains(comment));
		assertEquals("Approver name not set", "Catberg", claim.getlastApproverName());
		
		claim.setStatus(ClaimStatus.INPROGRESS);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to an inprogress claim",0 , comments.size());
		
		claim.setStatus(ClaimStatus.RETURNED);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to a returned claim",0 , comments.size());
		
		claim.setStatus(ClaimStatus.APPROVED);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to an approved claim",0 , comments.size());	
		
	}
	

}
