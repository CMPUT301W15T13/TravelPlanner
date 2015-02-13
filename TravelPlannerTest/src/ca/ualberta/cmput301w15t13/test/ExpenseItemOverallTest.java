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
import ca.ualberta.cmput301w15t13.LoginActivity;


public class ExpenseItemOverallTest extends ActivityInstrumentationTestCase2<LoginActivity>{
	
	public ExpenseItemOverallTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/*
	 * US04.01.01
	 * Test that you can add one or more expense items to an existing claim
	 * as a claimant.
	 */
	public void testAddExpenseItem(){
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
		assertTrue("Expense list is null" expenseList != null);
		assertTrue("Expenselist is empty", expenseList.size() > 0);
		for(ExpenseItem e : expenseList){
			assertTrue("Expense Item has wrong date", e.getDate() == date);
			assertTrue("Expense Item has wrong category", e.getCategory().equals(category));
			assertTrue("Expense Item has wrong Description", e.getDescription().equals(description));
			assertTrue("Expense Item has wrong currency", e.getCurrency().equals(currency));
		}
		
	}

	
	/*
	 * US04.02.01
	 * Test that a category must be one of the proper categories
	 */
	
	public void testWrongCategory(){
		Claim claim = new Claim();
		String[] validCategories = {"air fare", "ground transport", "vehicle rental", "private automobile", "fuel", "parking", "registration", "accommodation", "meal",  "supplies"};
		/*A default constructor which doesn't initialize values */
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setCategory("NONVALID");
		assertTrue("Category was set to a nonValid item", expenseItem.getCategory() == null);
		
		for(String cat: validCategories){
			expenseItem.setCategory(cat);
			assertTrue("Valid category wasn't added", expenseItem.getCategory().equals(cat));
		}
	}
	/*
	 * Test that you can only add the valid currencies
	 * US04.03.01
	 */
	
	public void testWrongCurrency(){
		Claim claim = new Claim();
		String[] validCurrencies = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CNY"};
		/*A default constructor which doesn't initialize values */
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setCurrency("NONVALID");
		assertTrue("Currency was set to a nonValid item", expenseItem.getCategory() == null);
		
		for(String cur: validCurrencies){
			expenseItem.setCurrency(cur);
			assertTrue("Valid currency wasn't added", expenseItem.getCurrency().equals(cur));
		}
	}

	/*
	 * US04.04.01
	 * Test that you can flag and unflag an expenseItem 
	 */

	public void testFlagExpenseItem(){
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setIncompletenessIndicator();
		assertTrue("Expense item flag wasn't set", expenseItem.isIncomplete());
		expenseItem.removeIncompletenessIndicator();
		assertFalse("Expense item flag is set when it should be off", expenseItem.isIncomplete());
	}


	/*
	 * US04.06.01
	 * Test that you're only allowed to manage an expenseItem when 
	 * the claim is editable
	 */
	
	public void testEditExpenseItem(){
		Claim claim = new Claim();
		ExpenseItem expenseItem = new ExpenseItem();
		ArrayList<ExpenseItem> expenseList = null;
		claim.addExpenseItem(expenseItem);

		claim.setStatus(SUBMITTED);
		if(claim.isEditable()){
			expenseList = claim.getExpenseItemList();
		}
		assertTrue("ExpenseItem was able to be retrieved for a SUBMITTED claim", expenseList == null);
		expenseList = null;
		
		claim.setStatus(APPROVED);
		if(claim.isEditable()){
			expenseList = claim.getExpenseItemList();
		}
		assertTrue("ExpenseItem was able to be retrieved for an APPROVED claim", expenseList == null);
		expenseList = null;
		
		claim.setStatus(INPROGRESS);
		if(claim.isEditable()){
			expenseList = claim.getExpenseItemList();
		}
		assertFalse("ExpenseItem was not retrieved for an INPROGRESS claim", expenseList == null);
		expenseList = null;
		
		claim.setStatus(RETURNED);
		if(claim.isEditable()){
			expenseList = claim.getExpenseItemList();
		}
		assertFalse("ExpenseItem was not retrieved for a RETURNED claim", expenseList == null);
	}
	
	/*
	 * US04.07.01
	 * Test that you can delete an expense Item from a claim
	 */
	public void testDeleteExpenseItem(){
		Claim claim = new Claim();
		ExpenseItem expenseItem = new ExpenseItem();
		claim.addExpenseItem(expenseItem);
		assertTrue("EXpense item was not added", claim.getExpenseList().contains(expenseItem));
		
		claim.removeExpenseItem(expenseItem);
		assertFalse("Expense item was not removed", claim.getExpenseList().contains(expenseItem));
	}
	

	
}
