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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;


/** 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 */
public class ExpenseItemOverallTest extends ActivityInstrumentationTestCase2<LoginActivity>{
	
	public ExpenseItemOverallTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**Use Case D1
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/81
	 * Test that you can add one or more expense items to an existing claim
	 * as a claimant.
	 */
	public void testAddExpense(){
		
		this.addExpenseItem();
		this.addWrongCategory();
		this.addWrongCurrency();
	}
	
	
	/**
	 * This is part of Use case D1
	 * 
	 * This adds and tests the addition of expense items
	 */
	public void addExpenseItem(){
		Claim claim = new Claim();
		String category = "air fare", description = "Desc", currency = "CAD";
		float price = 12.0;
		Date date = new Date(120);
		
		/*Constructor for all default items of an expense item */
		ExpenseItem expenseItem; // = new ExpenseItem(date, category, description, price, currency);
		
		for(int i = 0; i < 3; i++){
			expenseItem = new ExpenseItem(date, category, description, price, currency);
			price ++;
			claim.addExpenseItem(expenseItem);
		}
		
		ArrayList<ExpenseItem> expenseList = claim.getExpenseItems();
		assertNotNull("Expense list is null", expenseList);
		assertEquals("Expenselist is empty", 3, expenseList.size() );
		for(ExpenseItem e : expenseList){
			assertEquals("Expense Item has wrong date",date,  e.getDate());
			assertEquals("Expense Item has wrong category",category,  e.getCategory());
			assertEquals("Expense Item has wrong Description",description,  e.getDescription());
			assertEquals("Expense Item has wrong currency",currency, e.getCurrency());
		}
		
	}

	
	/** This is part of Use case D1
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/82
	 * Test that a category must be one of the proper categories
	 */
	
	public void addWrongCategory(){
		Claim claim = new Claim();
		String[] validCategories = {"air fare", "ground transport", "vehicle rental", "private automobile", "fuel", "parking", "registration", "accommodation", "meal",  "supplies"};
		/*A default constructor which doesn't initialize values */
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setCategory("NONVALID");
		assertNull("Category was set to a nonValid item", expenseItem.getCategory());
		
		for(String cat: validCategories){
			expenseItem.setCategory(cat);
			assertEquals("Valid category wasn't added",cat, expenseItem.getCategory());
		}
	}
	/** This is part of Use case D1
	 * Test that you can only add the valid currencies
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/83
	 */
	
	public void addWrongCurrency(){
		Claim claim = new Claim();
		String[] validCurrencies = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CNY"};
		/*A default constructor which doesn't initialize values */
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setCurrency("NONVALID");
		assertNull("Currency was set to a nonValid item", expenseItem.getCategory());
		
		for(String cur: validCurrencies){
			expenseItem.setCurrency(cur);
			assertEquals("Valid currency wasn't added", cur ,expenseItem.getCurrency());
		}
	}

	/** Use case D2
	 *
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/84
	 * Test that you can flag and unflag an expenseItem 
	 */

	public void testFlagExpenseItem(){
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setIncompletenessIndicator();
		assertTrue("Expense item flag wasn't set", expenseItem.isIncomplete());
		expenseItem.removeIncompletenessIndicator();
		assertFalse("Expense item flag is set when it should be off", expenseItem.isIncomplete());
	}


	/** Use Case D3
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/85
	 * Test that you're only allowed to manage an expenseItem when 
	 * the claim is editable
	 */

	public void testEditExpenseItem(){
		Claim claim = new Claim();
		ExpenseItem expenseItem = new ExpenseItem();
		ArrayList<ExpenseItem> expenseList = null;
		claim.addExpenseItem(expenseItem);

		claim.setStatus(SUBMITTED);
		
		assertFalse("Expense is editable while submitted", claim.isEditable());
		
		claim.setStatus(APPROVED);
		
		assertFalse("Expense is editable while approved", claim.isEditable());
		
		claim.setStatus(INPROGRESS);
		assertTrue("Expense is not editable while in progress", claim.isEditable());

		claim.setStatus(RETURNED);
		assertTrue("Expense is not editable while returned", claim.isEditable());

	}
	
	/** Use case D4
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/86
	 * Test that you can delete an expense Item from a claim
	 */
	public void testDeleteExpenseItem(){
		Claim claim = new Claim();
		ExpenseItem expenseItem = new ExpenseItem(new Date(100), "first", "Init Description", 10.50, "USD"));
		claim.addExpenseItem(expenseItem);
		assertTrue("EXpense item was not added", claim.getExpenseList().contains(expenseItem));
		
		//  (ER) Added - We need to check to see if we delete too many claims
		claim.addExpenseItem(new ExpenseItem(new Date(100), "test", "description", 50.50, "USD"));
		claim.addExpenseItem(new ExpenseItem(new Date(100), "test2", "descriptions", 150.50, "CAD"));
		
		claim.removeExpenseItem(expenseItem);
		assertFalse("Expense item was not removed", claim.getExpenseList().contains(expenseItem));
		
	//  (ER) Assert for Tests
		assertTrue("Too many expenses deleted", 2, claim.getExpenseList().size());
		
		
	}
	

	
}
