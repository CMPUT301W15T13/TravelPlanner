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

import persistanceController.DataManager;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/**
 * Tests that a Claim can have statuses that affect it's operations
 * 
 * User Stories Tested:
 * US07.01.01, US07.03.01, US07.04.01, US07.05.01
 * 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 * Commented out code to be implemented for Project Part 5
 * All tests should pass
 */

public class ExpenseClaimsStatusesTests extends ActivityInstrumentationTestCase2<LoginActivity> {
	public ExpenseClaimsStatusesTests() {
		super(LoginActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DataManager.setTestMode();
	}
	
	/**
	 * Tests that a claimant can submit a claim, and that
	 * doing so changes it's status and edit-ability
	 * Use case G1
	 * Tests US07.01.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/65
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void testClaimantSubmit() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		Claimant claimant = new Claimant("hey");
		claimant.submitClaim(claim);
		
		assertEquals("Claim is submitted",statusEnum.SUBMITTED, claim.getStatus());
		assertEquals("Claim can be edited by claimant", statusEnum.SUBMITTED, claim.getStatus());
		assertFalse("Claim can be edited by claimant", claim.isEditable());
	}
	
	/**
	 * Use case G2
	 * Tests that a non-approved claim is given a "returned"
	 * status and a claimant can edit it.
	 * Tests US07.03.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/67
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 * @throws EmptyFieldException 
	 */
	public void testClaimantOnReturned() throws EmptyFieldException, InvalidDateException, InvalidUserPermissionException {
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		claim.giveStatus(statusEnum.RETURNED);
		
		assertEquals("Claim can't be edited by claimant", statusEnum.RETURNED, claim.getStatus());
		assertTrue("Clain can't be edited by claimant",claim.isEditable());
	}
	
	/**
	 * Use case G3
	 * Tests that an approved claim is given an "approved"
	 * status, and a claimant can't edit it.
	 * Tests US07.04.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/68
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */

	public void testClaimStatusApproved() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		claim.giveStatus(statusEnum.APPROVED);
		
		assertEquals("Claim isn't approved",statusEnum.APPROVED, claim.getStatus());
		assertFalse("Approved claim is editable", claim.isEditable());
		
	}
	
	/**
	 * Test that the claimant can see the name of their
	 * claims approver along with the associated
	 * comment(s)
	 * Tests US07.05.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 * @throws InvalidUserPermissionException
	 */
	public void testComment() throws EmptyFieldException, InvalidDateException, InvalidUserPermissionException {
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		Claim claim2 = new Claim("userName", new Date(100),new Date(120), null, null);
		Claimant claimant = new Claimant("c");
		Approver approver = new Approver("andy");
		
		claimant.submitClaim(claim);
		claimant.submitClaim(claim2);
	
		String message = "I really like the photo";
		String message1 = "Looks good bud";
		String message2 = "not enough";
		
		approver.addComment(claim, message);
		approver.addComment(claim, message1);
		approver.addComment(claim2, message2);
		
		approver.approveClaim(claim);
		approver.returnClaim(claim2);
		
		//Tests that the approver information translated correctly
		//and that the claimant can get all the information from the claim
		assertEquals("Wrong approver", approver.getName(),claim.getlastApproverName());
		assertEquals("More or less than 2 comments", 2 ,claim.getComments().size());
		assertEquals("More recent comment not first", message, claim.getComments().get(0));
		assertEquals("Wrong comment",message,claim.getComments().get(0));
		assertEquals("Wrong comment",message1,claim.getComments().get(1));
		
		assertEquals("Wrong returner", approver.getName(),claim2.getlastApproverName());
		assertEquals("Wrong comment",message2,claim2.getComments().get(0));
		assertEquals("More or less than 1 comments", 1 ,claim2.getComments().size());
	}
	
	public void testVisualWarning() throws EmptyFieldException, InvalidDateException, InvalidUserPermissionException {
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		Claimant claimant = new Claimant("hey");
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		expenseItem.setComplete(false);
		claim.addExpenseItem(expenseItem);
		
		//We can confirm through manual testing that a warning
		//dialog pops up upon trying this
		try {
			if (expenseItem.isComplete()) {
				claimant.submitClaim(claim);
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {}

		assertNotSame("Claim was submitted",statusEnum.SUBMITTED, claim.getStatus());
	}

}
