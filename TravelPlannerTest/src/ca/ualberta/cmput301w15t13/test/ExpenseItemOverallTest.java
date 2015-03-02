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
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;


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
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	public void testAddExpense() throws InvalidDateException, InvalidNameException{
		
		this.addExpenseItem();
		this.addWrongCategory();
		this.addWrongCurrency();
	}
	
	
	/**
	 * This is part of Use case D1
	 * 
	 * This adds and tests the addition of expense items
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	public void addExpenseItem() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
		String category = "air fare", description = "Desc", currency = "CAD";
		double price = 12.00;
		Date date = new Date(120);
		
		/*Constructor for all default items of an expense item */
		ExpenseItem expenseItem; // = new ExpenseItem(date, category, description, price, currency);
	
		for(int i = 0; i < 3; i++){
			expenseItem = new ExpenseItem(category, date, description, price, currency, claim.getclaimID());
			price ++;
			claim.addExpenseItem(expenseItem);
		}
		
		ArrayList<ExpenseItem> expenseList = claim.getExpenseItems();
		assertNotNull("Expense list is null", expenseList);
		assertEquals("Expenselist is empty", 3, expenseList.size() );
		for(ExpenseItem e : expenseList){
			assertEquals("Expense Item has wrong date",date,  e.getPurchaseDate());
			assertEquals("Expense Item has wrong category",category,  e.getExpenseCategory());
			assertEquals("Expense Item has wrong Description",description,  e.getExpenseDescription());
			assertEquals("Expense Item has wrong currency",currency, e.getCurrency());
		}
		
	}

	
	/** This is part of Use case D1
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/82
	 * Test that a category must be one of the proper categories
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	
	public void addWrongCategory() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
		String[] validCategories = {"air fare", "ground transport", "vehicle rental", "private automobile", "fuel", "parking", "registration", "accommodation", "meal",  "supplies"};
		/*A default constructor which doesn't initialize values */
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		expenseItem.setExpenseCategory("NONVALID");
		assertNull("Category was set to a nonValid item", expenseItem.getExpenseCategory());
		
		for(String cat: validCategories){
			expenseItem.setExpenseCategory(cat);
			assertEquals("Valid category wasn't added",cat, expenseItem.getExpenseCategory());
		}
	}
	/** This is part of Use case D1
	 * Test that you can only add the valid currencies
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/83
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	
	public void addWrongCurrency() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
		String[] validCurrencies = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CNY"};
		/*A default constructor which doesn't initialize values */
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		expenseItem.setCurrency("NONVALID");
		assertNull("Currency was set to a nonValid item", expenseItem.getExpenseCategory());
		
		for(String cur: validCurrencies){
			expenseItem.setCurrency(cur);
			assertEquals("Valid currency wasn't added", cur ,expenseItem.getCurrency());
		}
	}

	/** Use case D2
	 *
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/84
	 * Test that you can flag and unflag an expenseItem 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */

	public void testFlagExpenseItem() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		expenseItem.setIncompletenessIndicator();
		assertTrue("Expense item flag wasn't set", expenseItem.isComplete());
		expenseItem.removeIncompletenessIndicator();
		assertFalse("Expense item flag is set when it should be off", expenseItem.isComplete());
	}


	/** Use Case D3
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/85
	 * Test that you're only allowed to manage an expenseItem when 
	 * the claim is editable
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */

	public void testEditExpenseItem() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		ArrayList<ExpenseItem> expenseList = null;
		claim.addExpenseItem(expenseItem);

		claim.setStatus(ClaimStatus.SUBMITTED);
		
		assertFalse("Expense is editable while submitted", claim.isEditable());
		
		claim.setStatus(ClaimStatus.APPROVED);
		
		assertFalse("Expense is editable while approved", claim.isEditable());
		
		claim.setStatus(ClaimStatus.INPROGRESS);
		assertTrue("Expense is not editable while in progress", claim.isEditable());

		claim.setStatus(ClaimStatus.RETURNED);
		assertTrue("Expense is not editable while returned", claim.isEditable());

	}
	
	/** Use case D4
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/86
	 * Test that you can delete an expense Item from a claim
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 */
	public void testDeleteExpenseItem() throws InvalidDateException, InvalidNameException{
		Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
		ExpenseItem expenseItem = new ExpenseItem("air", new Date(100), "yolo" , 10.50, "USD", claim.getclaimID());

		claim.addExpenseItem(expenseItem);
		assertTrue("EXpense item was not added", claim.getExpenseItems().contains(expenseItem));
		
		//  (ER) Added - We need to check to see if we delete too many claims
		claim.addExpenseItem(new ExpenseItem("taxi", new Date(100), "Swag" , 50.50, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("hotel", new Date(110), "Swagger" , 150.50, "USD", claim.getclaimID()));

		
		claim.removeExpenseItem(expenseItem);
		assertFalse("Expense item was not removed", claim.getExpenseItems().contains(expenseItem));
		
	//  (ER) Assert for Tests
		assertEquals("Too many expenses deleted", 2, claim.getExpenseItems().size());
		
		
	}
	

	
}
