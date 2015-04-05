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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
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
   * Tests US05.01.01
   * @throws EmptyFieldException
   * @throws InvalidDateException
   */
  public void testListing() throws EmptyFieldException, InvalidDateException {
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
  
  
}
