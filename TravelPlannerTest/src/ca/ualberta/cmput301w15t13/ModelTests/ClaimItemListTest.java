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

import java.util.ArrayList;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;

public class ClaimItemListTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimItemListTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testSetUp(){
		ClaimList itemList = new ClaimList();
		assertTrue("Item list is null", itemList != null);
		assertTrue("Item list is not empty", itemList.length() == 0);
		
		ArrayList<Claim> oldClaims = new ArrayList<Claim>();
		Claim claim1 = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		Claim claim2 = new Claim("name3", new Date(2), new Date(4), "Des3t", new TravelItineraryList());
		oldClaims.add(claim1);
		oldClaims.add(claim2);

		ClaimList claimList = new ClaimList(oldClaims);
		assertTrue("Item list is null", claimList != null);
		assertTrue("Item list is not empty", claimList.length() == 0);
		
		ArrayList<Claim> dupes = claimList.getClaimArrayList();
		assertTrue("Dupes is null", dupes != null);
		assertTrue("Dupes is empty", dupes.size() != 0);
		assertTrue("dupes is not size 2", dupes.size() == 2);
		assertTrue("dupes doesnt have claim 1", dupes.contains(claim1));
		assertTrue("dupes doesnt have claim 2", dupes.contains(claim2));
	}
	
	public void testAddRemove(){
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		
		claimList.add(claim);
		assertTrue("ClaimList length is not 1", claimList.length() == 1);
		assertTrue("Claim list doesn't containt claim 1", claimList.contains(claim));
		assertFalse("Claim list thinks it has claim 2", claimList.contains(claim2));
		
		claimList.add(claim2);
		assertTrue("ClaimList length is not 2", claimList.length() == 2);
		assertTrue("Claim list doesn't containt claim 1 with 2 elements", claimList.contains(claim));
		assertTrue("Claim list doesn't containt claim 2", claimList.contains(claim2));
		
		claimList.add(claim);
		assertTrue("Claim length reperesents the duplicate claim", claimList.length() == 2);
		assertTrue("Claim was added twice", claimList.contains(claim));
		
		claimList.remove(claim);
		assertTrue("ClaimList length is not 1", claimList.length() == 1);
		assertFalse("Claim list contains claim 1", claimList.contains(claim));
		
		claimList.remove(claim);
		assertTrue("ClaimList length is not 1", claimList.length() == 1);
		assertFalse("Claim list contains claim 1", claimList.contains(claim));
		
		claimList.remove(claim2);
		assertTrue("ClaimList length is not 0", claimList.length() == 0);
		assertFalse("Claim list contains claim 2", claimList.contains(claim2));
	}

	public void testIndexing(){
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
		Claim claim2 = new Claim("Name2", new Date(2), new Date(3), "Desc2", new TravelItineraryList());
		
		claimList.add(claim);
		claimList.add(claim2);
		
		assertTrue("Claim index is not 0", claimList.getClaimAtIndex(0) == claim);
		assertTrue("Claim2 index is not 1", claimList.getClaimAtIndex(1) == claim2);
		
		claimList.removeClaimAtIndex(0);
		assertFalse("Claimlist still has claim 1", claimList.contains(claim));
		
		claimList.add(claim);
		assertTrue("Claim index is not 1, now that it's readded", claimList.getClaimAtIndex(1) == claim);
		
		claimList.removeClaimAtIndex(1);
		assertFalse("Claimlist still has claim 1", claimList.contains(claim));
		
		claimList.removeClaimAtIndex(0);
		assertFalse("Claimlist still has claim 2", claimList.contains(claim2));
		assertTrue("claim list is not empty", claimList.length() == 0);

	}

	public void testListeners(){
		ClaimList claimList = new ClaimList();
		claimList.addListener(new Listener(){
			public void update(){
				assertTrue("Passed!" , true);
			}
		});
	
		claimList.notifyListeners();
	}
}
