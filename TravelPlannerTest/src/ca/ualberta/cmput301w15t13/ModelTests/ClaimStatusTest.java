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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;

public class ClaimStatusTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimStatusTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testSetUp(){
		ClaimStatus status = new ClaimStatus();
		assertTrue("ClaimStatus is null", status!=null);
		assertEquals("Claim status is not inprogress", ClaimStatus.INPROGRESS, status.getStatus());
	}
	
	public void testEditable(){
		ClaimStatus status = new ClaimStatus();
		assertTrue("Claim Status should be editable", status.isEditable());
		
		status.setStatus(ClaimStatus.RETURNED);
		assertTrue("Claim Status should be editable", status.isEditable());
		
		status.setStatus(ClaimStatus.SUBMITTED);
		assertFalse("Claim Status shouldnt be editable", status.isEditable());
		
		status.setStatus(ClaimStatus.APPROVED);
		assertFalse("Claim Status shouldnt be editable", status.isEditable());
		
	}

	public void testInvalidSet(){
		ClaimStatus status = new ClaimStatus();
		status.setStatus(-1);
		assertEquals("Claim status was changed to -1", 0, status.getStatus());
		
		status.setStatus(4);
		assertEquals("Claim status was changed to 4", 0, status.getStatus());
		
	}
}
