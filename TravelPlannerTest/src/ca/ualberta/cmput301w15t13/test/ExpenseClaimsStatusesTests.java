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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import exceptions.ClaimPermissionException;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;


/**
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 */
public class ExpenseClaimsStatusesTests extends ActivityInstrumentationTestCase2<LoginActivity>{
	public ExpenseClaimsStatusesTests() {
		super(LoginActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**Use case G1
	 * 	
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/65
	 * Claimant submits a claim...claim can no longer edit
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */
	public void testClaimantSubmit() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
		//LoginActivity.setUserType("claimant"); 
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		
		Claimant claimant = new Claimant("hey");
		claimant.submitClaim(claim);
		//LoginActivity.submit(claim);
	
		assertEquals("Claim is submitted",ClaimStatus.SUBMITTED, claim.getStatus());
		assertEquals("Claim cannot be editted by claimant", ClaimStatus.SUBMITTED, claim.getStatus());
		
		
	
			

	}
	
	/**Use case G2
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/67
	 * Claimant can edit a returned claim
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 * @throws EmptyFieldException 
	 */
	public void testClaimantOnReturned() throws EmptyFieldException, InvalidDateException, InvalidUserPermissionException{
		//LoginActivity.setUserType("Claimant");
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		claim.giveStatus(ClaimStatus.RETURNED);
		assertEquals("Claim can be editted by claimant", ClaimStatus.RETURNED, claim.getStatus());
		

		
	}
	
	/**Use case G3
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/68
	 * Claim has approved status and can no longer be editted
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws DuplicateException 
	 * @throws InvalidUserPermissionException 
	 */

	public void testClaimStatusApproved() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		//LoginActivity.setUserType("Claimant");
		Claim claim = new Claim("userName", new Date(100),new Date(120), null, null);
		claim.giveStatus(ClaimStatus.APPROVED);
		assertEquals("Claim is approved and can't be editted",ClaimStatus.APPROVED, claim.getStatus());
		

		
	}

}
