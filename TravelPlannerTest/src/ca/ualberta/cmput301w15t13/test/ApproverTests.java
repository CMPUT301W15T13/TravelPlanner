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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;

public class ApproverTests extends ActivityInstrumentationTestCase2<LoginActivity>{

	public ApproverTests() {
		super(LoginActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/*
	* Test that as an approver you're able to change the status of a claim
	* to returned or approved if it's submitted and not if it's anything but.
	* 
	* US08.07.01
	* https://github.com/CMPUT301W15T13/TravelPlanner/issues/78
	* US08.08.01
	* https://github.com/CMPUT301W15T13/TravelPlanner/issues/79
	*/
	public void testClaimReturns(){
		Claim claim = new Claim();
		claim.setStatus(SUBMITTED);
		Approver approver = new Approver();
		approver.returnClaim(claim);
		assertTrue("Claim status isn't returned", claim.getStatus() == RETURNED);
		claim.setStatus(SUBMITTED);
		approver.approveClaim(claim);
		assertTrue("Claim status isn't submitted", claim.getStatus() == SUBMITTED);
		
		claim.setStatus(INPROGRESS);
		approver.returnClaim(claim);
		assertTrue("Approver was able to return an INPROGRESS claim", claim.getStatus == INPROGRESS);
		claim.setStatus(INPROGRESS);
		approver.approveClaim(claim);
		assertTrue("Approver was able to approve an INPROGRESS claim", claim.getStatus == INPROGRESS);
		
		claim.setStatus(RETURNED);
		approver.returnClaim(claim);
		assertTrue("Approver was able to return a RETURNED claim", claim.getStatus == INPROGRESS);
		claim.setStatus(RETURNED);
		approver.approveClaim(claim);
		assertTrue("Approver was able to approve a RETURNED claim", claim.getStatus == INPROGRESS);
		
		claim.setStatus(APPROVED);
		approver.returnClaim(claim);
		assertTrue("Approver was able to return an APPROVED claim", claim.getStatus == INPROGRESS);
		claim.setStatus(APPROVED);
		approver.approveClaim(claim);
		assertTrue("Approver was able to approve an APPROVED claim", claim.getStatus == INPROGRESS);
		
	}
	
	/*
	 * Approver can set one or more comments on a claim that is submitted,
	 * and cannot modify a claim that is not submitted.
	 * US08.06.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/77
	 */
	
	public void testComment(){
		Claim claim = new Claim();
		Approver approver = new Approver();
		String comment = "Test";
		
		claim.setStatus(SUBMITTED);
		
		approver.addComment(claim, comment);
		ArrayList<String> comments = claim.getComments();
		assertTrue("Claim comments are null", comments != null);
		assertTrue("There are no claim comments", comments.size() > 0);
		assertTrue("Comment isn't added", comments.contains(comment));
		
		claim.setStatus(INPROGRESS);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to an inprogress claim",0 , comments.size());
		
		claim.setStatus(RETURNED);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to a returned claim",0 , comments.size());
		
		claim.setStatus(APPROVED);
		claim.clearComments();
		approver.addComment(claim, comment);
		comments = claim.getComments();
		assertEquals("A comment was added to an approved claim",0 , comments.size());	
		
	}
	

}
