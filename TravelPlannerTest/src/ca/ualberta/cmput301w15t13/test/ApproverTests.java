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

import persistanceController.DataManager;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.Receipt;
import ca.ualberta.cmput301w15t13.Models.Tag;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/**
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
		DataManager.setTestMode();
	}
	
	/** 
	 * Test Case H2
	 * Tests that as an approver you are able to return a claim
	 * Tests US08.07.01
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
	
	/** 
	 * Test Case H3
	 * Tests that as an approver you are able to approve a claim.
	 * In addition, our interface is designed in such a way that
	 * if you are logged in as a claimant, there is no option to 
	 * approve your own claims. This has been confirmed through
	 * manual testing.
	 * Tests US08.08.01,US08.09.01
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
	
	/** 
	 * Use Case H4
	 * Approver can set one or more comments on a claim that is submitted,
	 * and cannot modify a claim that is not submitted.
	 * Tests US08.06.01
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
	
	/**
	 * Tests the approvers ability to get the expense Items 
	 * of a claim, and that they show up in the right order.
	 * Tests US08.04.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	public void testExpenseItemList() throws EmptyFieldException, InvalidDateException {
		Approver a = new Approver("andry");
		Claim claim = new Claim("name", new Date(1000), new Date(1100), "Dest", new TravelItineraryList());
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		ExpenseItem expenseItem2 = new ExpenseItem("air", new Date(120), "yolo" , 22.43, "cdn", claim.getclaimID());
		claim.addExpenseItem(expenseItem);
		claim.addExpenseItem(expenseItem2);
		
		ArrayList<Claim> allClaims = new ArrayList<Claim>();
		allClaims.add(claim);
		claim.giveStatus(statusEnum.SUBMITTED);
		ArrayList<Claim> approverClaims = a.getPermittableClaims(allClaims);
		
		// Test that the approver's claims can get its constituent expense items
		assertNotNull("Expense items don't exist", approverClaims.get(0).getExpenseItems());
		assertEquals("Too many or too few expense items added",2,approverClaims.get(0).getExpenseItems().size());
		assertEquals("Expense items not in order added",expenseItem,approverClaims.get(0).getExpenseItems().get(0));

	}
	
	/**
	 * Tests the approver interface, that they can "view"
	 * the correct information for a submitted claim, it is
	 * displayed correctly, and that the same is true for a
	 * claim's expense items. We have manually confirmed that
	 * the information is displayed.
	 * Tests US08.01.01, US08.03.01, US08.04.01, US08.05.01
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 * @throws InvalidFieldEntryException 
	 */
	public void testView() throws EmptyFieldException, InvalidDateException, DuplicateException, InvalidUserPermissionException, InvalidFieldEntryException {
		Claimant claimant = new Claimant("boniface");
		Approver approver = new Approver("Anexus Lavardjus");
		
		ClaimList cl = ClaimListSingleton.getClaimList();
		Claim claim1 = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		claim1.addTravelDestination("Russia", "Bear hunting");
		claim1.addTravelDestination("Japan", "Sushi hunting");
		cl.add(claim1);
		cl.add(claim2);
		
		//only submit one claim
		claimant.submitClaim(claim1);
		//Get approver claims via the claimList controller
		ArrayList<Claim> approverClaims = approver.getPermittableClaims(cl.getClaimArrayList());
		
		//Test that you can get all needed information from the controller
		assertEquals("Approver Claimlist not populating correctly",1, approverClaims.size());
		assertNotNull("Approver cannot view details", approverClaims.get(0).getUserName());
		assertNotNull("Approver cannot view details", approverClaims.get(0).getStartDate());
		assertNotNull("Approver cannot view details", approverClaims.get(0).getEndDate());
		assertNotNull("Approver cannot view details", approverClaims.get(0).getDescription());
		assertNotNull("Approver cannot view details", approverClaims.get(0).getTravelItineraryAsString());
		
		ExpenseItem expenseItem1 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "USD", claim1.getclaimID());
		ExpenseItem expenseItem2 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "USD", claim1.getclaimID());
		ExpenseItem expenseItem3 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "CDN", claim1.getclaimID());
		
		Bitmap bitmapLarge = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888 );
		Receipt receipt = new Receipt(bitmapLarge);
		expenseItem1.addReceipt(receipt);
		
		approver.returnClaim(claim1);
		claim1.addExpenseItem(expenseItem1);
		claim1.addExpenseItem(expenseItem2);
		claim1.addExpenseItem(expenseItem3);
		claimant.submitClaim(claim1);
		
		approverClaims = approver.getPermittableClaims(cl.getClaimArrayList());
		
		//Tests the following:
		//As an approver, I want to view a list of all the expense claims that were submitted for approval,
		//which have their claim status as submitted, showing for each claim: the claimant name, the starting 
		//date of travel, the destination(s) of travel, the claim status, total currency amounts, and any approver name.
		
		assertNotNull("Claim doesn't show startDate", approverClaims.get(0).getStartDate());
		assertNotNull("Claim doesn't show destinations", approverClaims.get(0).getTravelDestination(0));
		assertEquals("Claim doesn't show all destinations", 2,approverClaims.get(0).getTravelList().numberofDestinations());
		assertNotNull("Claim doesn't show status", approverClaims.get(0).getStatus());
		assertEquals("Claim doesn't show correct status", statusEnum.SUBMITTED,approverClaims.get(0).getStatus());
		assertNotNull("Claim doesn't show tags",approverClaims.get(0).getTags());
		assertNotNull("Claim doesn't show amounts",approverClaims.get(0).getCost());
		assertEquals("Claim doesn't show currency totals of 2 different currency types","20.00 USD\n10.00 CNY\n", approverClaims.get(0).getCost());
	
		//Tests the following:
		//As an approver, I want to list all the expense items for a submitted claim, in order of entry, 
		//showing for each expense item: the date the expense was incurred, the category, the textual 
		//description, amount spent, unit of currency, and whether there is a photographic receipt.
		
		approver.returnClaim(claim1);
		claimant.submitClaim(claim1);
		approverClaims = approver.getPermittableClaims(cl.getClaimArrayList());
		
		assertNotNull("ExpenseItem doesn't show startDate", approverClaims.get(0).getExpenseItems().get(0).getPurchaseDate());
		assertNotNull("ExpenseItem doesn't show category", approverClaims.get(0).getExpenseItems().get(0).getExpenseCategory());
		assertNotNull("ExpenseItem doesn't show description", approverClaims.get(0).getExpenseItems().get(0).getExpenseDescription());
		assertNotNull("ExpenseItem doesn't show amount", approverClaims.get(0).getExpenseItems().get(0).getAmount());
		assertNotNull("ExpenseItem doesn't show currency", approverClaims.get(0).getExpenseItems().get(0).getExpenseCurrency());
		assertTrue("ExpenseItem doesn't show photo", approverClaims.get(0).getExpenseItems().get(0).hasReciept());
		
		//Tests the ability of the approver to view the photo
		assertNotNull("ExpenseItem doesn't show photo", approverClaims.get(0).getExpenseItems().get(0).getReceipt());
	}
	
}
