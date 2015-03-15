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

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.Receipt;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/** 
 * This test suite tests the functionality of the applications
 * photographic receipt functionality
 * 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 * 
 * Not implemented for Project Part 4, should fail
 */
public class ExpenseRepeiptTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ExpenseRepeiptTest() {
		super(LoginActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Tests for use case F1
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 * 
	 */
	public void testBitmap() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		this.addBitmap();
		this.largeBitmap();
	}

	/**Part of use case F1
	 * Test that you can add a bitmap to a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/61
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void addBitmap() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888 );
		Receipt receipt = new Receipt(bitmap);
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(1), "Strut", 12.12, "CAD", claim.getclaimID());
		expenseItem.addReceipt(receipt);
		Bitmap returnedBitmap = expenseItem.getReceipt().toBitMap();
		
		assertNotNull("Bitmap is null", returnedBitmap);
		assertEquals("Bitmap has been changed", bitmap, returnedBitmap);
	}	
	
	/**Part of Use Case F1
	 * Test that you bitmaps are compressed before they're stored
	 * US06.04.01
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/64
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void largeBitmap() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		Bitmap bitmapLarge = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888 );
		Receipt receipt = new Receipt(bitmapLarge);
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(1), "Strut", 12.12, "CAD", claim.getclaimID());
		expenseItem.addReceipt(receipt);
		Bitmap returnedBitmap = expenseItem.getReceipt().toBitMap();
		
		if (returnedBitmap.getByteCount() > 65536) {
			fail("Addded a bitmap too large");
		}
		assertNotSame("Bitmap wasn't modified", bitmapLarge, returnedBitmap);
	}

	
	
	/**Use case F3
	 * Test that you can delete a bitmap from a claim
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/63
	 * @throws EmptyFieldException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
	
	public void testRemoveBitmap() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
		Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888 );
		Bitmap returnedBitmap;
		Receipt receipt = new Receipt(bitmap);
		Claim claim = new Claim("name", new Date(1), new Date(2), "Dest", new TravelItineraryList());
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(1), "Strut", 12.12, "CAD", claim.getclaimID());
		expenseItem.addReceipt(receipt);
		returnedBitmap = expenseItem.getReceipt().toBitMap();
		
		assertEquals("Bitmap has been changed", bitmap ,returnedBitmap);

		expenseItem.removeReceipt(bitmap);
		returnedBitmap = expenseItem.getReceipt().toBitMap();
		
		assertNull("Bitmap was not removed", returnedBitmap);
	}
	


}
