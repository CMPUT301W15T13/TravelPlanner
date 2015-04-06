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

package ca.ualberta.cmput301w15t13.ModelTests;

import java.util.Date;

import persistanceController.DataManager;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;

/** 
 * This test suite tests the claimStatus functionality.
 * Specifically, it tests that a claimStatus can be
 * made, that certain claimStatus' affect edit-ability, 
 * and that a claim cannot be given an invalid status
 * 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 *
 * All tests should pass
 */
public class ClaimStatusTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimStatusTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DataManager.setTestMode();
	}
	
	// This tests that a claimStatus can be made without error
	public void testSetUp(){
		ClaimStatus status = new ClaimStatus();
		assertTrue("ClaimStatus is null", status!=null);
		assertEquals("Claim status is not inprogress", statusEnum.INPROGRESS, status.getStatus());
	}
	
	/** 
	 * This tests that claimStatus affects edit-ability appropriately.
	 */
	
	public void testEditable() {
		ClaimStatus status = new ClaimStatus();
		assertTrue("Claim Status should be editable", status.isEditable());
		
		status.setStatus(statusEnum.RETURNED);
		assertTrue("Claim Status should be editable", status.isEditable());
		
		status.setStatus(statusEnum.SUBMITTED);
		assertFalse("Claim Status shouldnt be editable", status.isEditable());
		
		status.setStatus(statusEnum.APPROVED);
		assertFalse("Claim Status shouldnt be editable", status.isEditable());
		
	}
	
	/**
	 * Tests that the user can only edit
	 * the claim when they are supposed to
	 * using an arbitrary date edit.
	 * Tests US01.04.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	public void testStatusEdit() throws EmptyFieldException, InvalidDateException {
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Date old = claim.getStartDate();
		
		//Test you can edit when allowed
		claim.giveStatus(statusEnum.INPROGRESS);
		try {
			if (claim.isEditable()) {
				claim.setClaimDates(new Date(10), new Date(11));
			}
		} catch (RuntimeException e) {}
		
		assertNotSame("Claim wasn't edited",0, old.compareTo(claim.getStartDate()));
		
		claim.setClaimDates(new Date(50), new Date(51));
		old = claim.getStartDate();
		
		claim.giveStatus(statusEnum.RETURNED);
		try {
			if (claim.isEditable()) {
				claim.setClaimDates(new Date(10), new Date(11));
			}
		} catch (RuntimeException e) {}
		
		assertNotSame("Claim wasn't edited",0, old.compareTo(claim.getStartDate()));
		
		//Test you can't edit 
		old = claim.getStartDate();
		
		claim.giveStatus(statusEnum.APPROVED);
		try {
			if (claim.isEditable()) {
				claim.setClaimDates(new Date(50), new Date(51));
			}
		} catch (RuntimeException e) {}
		
		assertSame("Claim was edited",0, old.compareTo(claim.getStartDate()));
		
		claim.giveStatus(statusEnum.SUBMITTED);
		try {
			if (claim.isEditable()) {
				claim.setClaimDates(new Date(12), new Date(13));
			}
		} catch (RuntimeException e) {}
		
		assertSame("Claim was edited", 0, old.compareTo(claim.getStartDate()));
	}
	
	/**
	 * Tests that the user can only delete
	 * the claim when they are supposed to
	 * using an arbitrary date edit.
	 * Tests US01.05.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException
	 */
	public void testStatusDelete() throws EmptyFieldException, InvalidDateException {
		ClaimList cl = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		cl.add(claim);
		
		//Test you can delete when allowed
		claim.giveStatus(statusEnum.INPROGRESS);
		try {
			if (claim.isEditable()) {
				cl.remove(claim);
			}
		} catch (RuntimeException e) {}
		
		assertEquals("Claim wasn't deleted", 0, cl.size());
		
		cl.add(claim);
		claim.giveStatus(statusEnum.RETURNED);
		try {
			if (claim.isEditable()) {
				cl.remove(claim);
			}
		} catch (RuntimeException e) {}
		
		assertEquals("Claim wasn't deleted", 0, cl.size());
		
		//Test you can't delete
		cl.add(claim);
		claim.giveStatus(statusEnum.APPROVED);
		try {
			if (claim.isEditable()) {
				cl.remove(claim);
			}
		} catch (RuntimeException e) {}
		
		assertEquals("Claim was deleted", 1, cl.size());
		
		cl.add(claim);
		claim.giveStatus(statusEnum.SUBMITTED);
		try {
			if (claim.isEditable()) {
				cl.remove(claim);
			}
		} catch (RuntimeException e) {}
		
		assertEquals("Claim was deleted", 1, cl.size());
		
	}

}
