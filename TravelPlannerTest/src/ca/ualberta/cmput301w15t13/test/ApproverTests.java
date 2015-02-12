package ca.ualberta.cmput301w15t13.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.LoginActivity;

public class ApproverTests extends ActivityInstrumentationTestCase2<LoginActivity>{

	public ApproverTests(Class<LoginActivity> activityClass) {
		super(activityClass);
	}
	
	
	/*
	* Test that as an approver you're able to change the status of a claim
	* to returned or approved if it's submitted and not if it's anything but.
	* 
	* US08.07.01
	* US08.08.01
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
