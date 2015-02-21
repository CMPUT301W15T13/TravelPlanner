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

import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.ClaimModel;
import android.test.ActivityInstrumentationTestCase2;

public class ExpenseClaimsStatusesTests extends ActivityInstrumentationTestCase2<ClaimModel>{
	public ExpenseClaimsStatusesTests() {
		super(ClaimModel.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//07.01.01
	//Claimant submits a claim can no longer edit
	public void testClaimantSubmit(){
		LoginActivity.setUserType("claimant"); 
		Claim claim = new Claim;
		LoginActivity.submit(claim);
		assertEquals("Claim is submitted", claim.getStatus(), "Submitted");
		assertEquals("Claim cannot be editted by claimant", claim.getPermission(), 1);
	}
	
	//07.03.01
	//Claimant can edit a returned claim
	public void testClaimantOnReturned(){
		LoginActivity.setUserType("Claimant");
		Claim claim = new Claim;
		claim.setStatus("Returned");
		assertEquals("Claim can be editted by claimant", claim.getPermission(), 0);
		
	}
	
	//07.04.01
	//Claim has approved status and can no longer be editted
	public void testClaimStatusApproved(){
		LoginActivity.setUserType("Claimant");
		Claim claim = new Claim;
		claim.setStatus("Approved");
		assertEquals("Claim is approved and can't be editted", claim.getPermission(),2);
	}

}
