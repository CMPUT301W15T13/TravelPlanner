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

/* TODO
 * US05.01.01
 * As a claimant, I want to list all the expense items for a claim, in order of entry,
 * showing for each expense item: the date the expense was incurred, the category,
 * the textual description, amount spent, unit of currency, whether there is a photographic receipt,
 * and incompleteness indicator.
 * 
 */

package ca.ualberta.cmput301w15t13.test;

import java.util.ArrayList;
import java.util.Date;

import persistanceController.DataManager;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.Receipt;
import ca.ualberta.cmput301w15t13.Models.Tag;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;

public class ExpenseItemListTests extends ActivityInstrumentationTestCase2<LoginActivity> {
  public ExpenseItemListTests() {
    super(LoginActivity.class);
  }
  
  @Override
  protected void setUp() throws Exception{
	  super.setUp();
  }
  
  /**
   * Test that the ExpenseItem list maintains a sorted integrity
   * Tests US05.01.01 - without geolocation
   * @throws EmptyFieldException
   * @throws InvalidDateException
   */
  public void testListing() throws EmptyFieldException, InvalidDateException {
	  DataManager.setTestMode();
	  
	  Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
	  //create 2 arbitrary expense items
	  ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
	  ExpenseItem expenseItem2 = new ExpenseItem("air", new Date(5000), "test" , 100.12, "cdn", claim.getclaimID());
	  
	  claim.addExpenseItem(expenseItem);
	  claim.addExpenseItem(expenseItem2);
	  
	  ArrayList<ExpenseItem> test = claim.getExpenseItems();
	  
	  assertEquals("Wrong number of expense items added",2, test.size());
	  //Per the description: As a claimant, I want to list all the expense items for a claim, in order of entry
	  //Thus we expect the list to maintain the order its items were added in 
	  assertEquals("Wrong order of expenseItems",expenseItem, test.get(0));
	  
	  ExpenseItem expenseItem3 = new ExpenseItem("air", new Date(200), "test2" , 10.12, "cdn", claim.getclaimID());
	  claim.addExpenseItem(expenseItem3);
	  
	  //Test that a new claim is put at the the end of the list (3rd entered, 3rd in the list)
	  assertEquals("Wrong order of expenseItems",expenseItem, test.get(0));
	  assertEquals("Wrong order of expenseItems",expenseItem2, test.get(2));
  }
  
  /**
   * Test that as a claimant, I can "view" 
   * an expense item and its details. 
   * Tests US04.05.01, US05.01.01 - without geolocation
   * @throws EmptyFieldException 
   * @throws InvalidDateException 
   */
  public void testView() throws EmptyFieldException, InvalidDateException {
	  ClaimList cl = ClaimListSingleton.getClaimList();
	  Claim claim1 = new Claim("Name", new Date(1), new Date(2), "Desc", new TravelItineraryList());
	  ExpenseItem expenseItem1 = new ExpenseItem("air", new Date(100), "yolo" , 10.00, "USD", claim1.getclaimID());
	  Bitmap bitmapLarge = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888 );
	  Receipt receipt = new Receipt(bitmapLarge);
	  expenseItem1.addReceipt(receipt);
	  claim1.addExpenseItem(expenseItem1);
	  cl.add(claim1);
	  
	  //Test that you can get all needed information from the controller
	  assertNotNull("ExpenseItems List not populating",cl.getExpenseList(0));
	  assertNotNull("Claimant cannot view eCategory", cl.getClaimAtIndex(0).getExpenseItems().get(0).getExpenseCategory());
	  assertNotNull("Claimant cannot view eCurrency", cl.getClaimAtIndex(0).getExpenseItems().get(0).getExpenseCurrency());
	  assertNotNull("Claimant cannot view eDate", cl.getClaimAtIndex(0).getExpenseItems().get(0).getPurchaseDate());
	  assertNotNull("Claimant cannot view eDescription", cl.getClaimAtIndex(0).getExpenseItems().get(0).getExpenseDescription());
	  assertNotNull("Claimant cannot view eAmount", cl.getClaimAtIndex(0).getExpenseItems().get(0).getAmount());
	  
	  //Additional tests regarding ExpenseItem listing
	  assertNotNull("ExpenseItem doesn't show reciept", cl.getClaimAtIndex(0).getExpenseItems().get(0).getReceipt());
	  assertNotNull("ExpenseItem doesn't show incompleteness indicator", cl.getClaimAtIndex(0).getExpenseItems().get(0).isComplete());
  }
  
  
}
