package ca.ualberta.cmput301w15t13.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.LoginActivity;

public class ApproverTests extends ActivityInstrumentationTestCase2<LoginActivity>{

	public ApproverTests(Class<LoginActivity> activityClass) {
		super(activityClass);
	}
	
	public void testClaimReturns(){
		Claim claim = new Claim();
		claim.setStatus(SUBMITTED);
		Approver approver = new Approver();
		approver.returnClaim(claim);
		assertTrue("Claim status isn't returned", claim.getStatus() == RETURNED);
		claim.setStatus(SUBMITTED);
		approver.approveClaim(claim);
		assertTrue("Claim status isn't submitted", claim.getStatus() == SUBMITTED);
	}
	
	public void testComment(){
		Claim claim = new Claim();
		Approver approver = new Approver();
		
		String comment = "Test";
		approver.addComment(claim, comment);
		ArrayList<String> comments = claim.getComments();
		assertTrue("Claim comments are null", comments != null);
		assertTrue("There are no claim comments", comments.size() > 0);
		assertTrue("Comment isn't added", !comments.contains(comment));
	}

}
