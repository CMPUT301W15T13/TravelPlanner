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

import persistanceController.DataManager;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.ExpenseItemList;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

/** 
 * Test the general functionality of an
 * Expense Item
 * 
 * User Stories Tested:
 * US04.01.01, US04.02.01, US04.03.01
 * US04.04.01, US04.06.01, US04.08.01
 * 
 * General use case can be found on the wiki at
 * https://github.com/CMPUT301W15T13/TravelPlanner/wiki/User-Stories-and-Requirements
 */
public class ExpenseItemOverallTest extends ActivityInstrumentationTestCase2<LoginActivity>  {
  public ExpenseItemOverallTest() {
    super(LoginActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    DataManager.setTestMode();
  }
	
	/**
	 * This is part of Use case D1
	 * Tests US04.01.01
	 * This adds and tests the addition of expense items
	 * @throws EmptyFieldException
	 * @throws InvalidNameException
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */

  public void testaddExpenseItem() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
    Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
    String category = "air fare", description = "Desc", currency = "CAD";
    double price = 12.00;
    Date date = new Date(120);
    /*Constructor for all default items of an expense item */
    ExpenseItem expenseItem; // = new ExpenseItem(date, category, description, price, currency);
    ExpenseItemList eList = new ExpenseItemList(claim.getclaimID());
    for(int i = 0; i < 3; i++){
    expenseItem = new ExpenseItem(category, date, description, price, currency, claim.getclaimID());
      price ++;
      eList.add(expenseItem);
    }
    ArrayList<ExpenseItem> expenseList = eList.getExpenseList();
		
    //ArrayList<ExpenseItem> expenseList = claim.getExpenseItems();
    assertNotNull("Expense list is null", expenseList);
    assertEquals("Expenselist is empty", 3, expenseList.size() );
    for (ExpenseItem e : expenseList){
      assertEquals("Expense Item has wrong date",date,  e.getPurchaseDate());
      assertEquals("Expense Item has wrong category",category,  e.getExpenseCategory());
      assertEquals("Expense Item has wrong Description",description,  e.getExpenseDescription());
      assertEquals("Expense Item has wrong currency",currency, e.getCurrency());
	}
  }
	
	/** This is part of Use case D1
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/82
	 * Test that a category must be one of the proper categories
	 * Tests US04.01.01, US04.02.01
	 * @throws EmptyFieldException
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws InvalidUserPermissionException 
	 */
	
  public void testaddWrongCategory() throws InvalidDateException, EmptyFieldException, InvalidFieldEntryException, InvalidUserPermissionException {
    Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
    String[] validCategories = {"Air Fare", "Ground Transport", "Vehicle Rental", "Private Automobile", "Fuel", "Parking", "Registration", "Accommodation", "Meal", "Supplies"};
    /*A default constructor which doesn't initialize values */
    ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
		
    try {
      expenseItem.setExpenseCategory("NONVALID");	
    } catch (InvalidFieldEntryException e) {}
    assertEquals("Category was set to a nonValid item", "air", expenseItem.getExpenseCategory());
		
    for(String cat: validCategories){
      try {
        expenseItem.setExpenseCategory(cat);
      } catch (InvalidFieldEntryException e) {}
      assertEquals("Valid category wasn't set",cat, expenseItem.getExpenseCategory());
    }
  }
  
	/** This is part of Use case D1
	 * Test that you can only add the valid currencies
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/83
	 * Tests US04.01.01, US04.03.01
	 * @throws EmptyFieldException
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidFieldEntryException 
	 * @throws InvalidUserPermissionException 
	 */
	
  public void testaddWrongCurrency() throws InvalidDateException, EmptyFieldException, InvalidFieldEntryException, InvalidUserPermissionException{
    Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
    String[] validCurrencies = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CNY"};
    /*A default constructor which doesn't initialize values */
    ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "CAD", claim.getclaimID());
    
    try {
      expenseItem.setCurrency("NONVALID");
    } catch (InvalidFieldEntryException e) {}	
    assertEquals("Currency was set to a nonValid item", "CAD", expenseItem.getCurrency());
		
    for(String cur: validCurrencies){
      try {
        expenseItem.setCurrency(cur);		
      } catch (InvalidFieldEntryException e) {}
      assertEquals("Valid currency wasn't changed", cur ,expenseItem.getCurrency());
    }
  }

	/** 
	 * Use case D2
	 * This test is to Check the incompleteness indicator in the expense Item
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/84
	 * Test that you can flag and un-flag an expenseItem 
	 * Tests US04.04.01
	 * @throws InvalidDateException
	 * @throws InvalidNameException 
	 * @throws InvalidUserPermissionException 
	 * @throws EmptyFieldException 
	 */

  public void testFlagExpenseItem() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
    Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
    ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
    
    expenseItem.setComplete(false);
    assertFalse("Expense item flag wasn't set", expenseItem.isComplete());
    
    expenseItem.setComplete(true);    
    assertTrue("Expense item flag is set when it should be off", expenseItem.isComplete());
  }

	/** Use Case D3
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/85
	 * Test that you're only allowed to manage an expenseItem when 
	 * the claim is editable
	 * Tests US04.06.01
	 * @throws EmptyFieldException
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */

  public void testEditExpenseItem() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException {
    Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
    ExpenseItem expenseItem = new ExpenseItem("air", new Date(120), "yolo" , 10.43, "cdn", claim.getclaimID());
    
    claim.addExpenseItem(expenseItem);
    
    claim.giveStatus(statusEnum.SUBMITTED);
    assertFalse("Expense is editable while submitted", claim.isEditable());
    
    claim.giveStatus(statusEnum.APPROVED);
    assertFalse("Expense is editable while approved", claim.isEditable());
    
    claim.giveStatus(statusEnum.INPROGRESS);
    assertTrue("Expense is not editable while in progress", claim.isEditable());
    
    claim.giveStatus(statusEnum.RETURNED);
    assertTrue("Expense is not editable while returned", claim.isEditable());
  }
	
	/** Use case D4
	 * https://github.com/CMPUT301W15T13/TravelPlanner/issues/86
	 * Test that you can delete an expense Item from a claim
	 * Tests US04.07.01
	 * @throws EmptyFieldException
	 * @throws InvalidNameException
	 * @throws InvalidDateException 
	 * @throws InvalidUserPermissionException 
	 */
  public void testDeleteExpenseItem() throws InvalidDateException, EmptyFieldException, InvalidUserPermissionException{
	  Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
	  ExpenseItem expenseItem = new ExpenseItem("air", new Date(100), "yolo" , 10.50, "USD", claim.getclaimID());

	  claim.addExpenseItem(expenseItem);
	  assertTrue("EXpense item was not added", claim.getExpenseItems().contains(expenseItem));
		
	  // (ER) Added - We need to check to see if we delete too many claims
	  claim.addExpenseItem(new ExpenseItem("taxi", new Date(100), "Swag" , 50.50, "USD", claim.getclaimID()));
	  claim.addExpenseItem(new ExpenseItem("hotel", new Date(110), "Swagger" , 150.50, "USD", claim.getclaimID()));

	  claim.removeExpenseItem(expenseItem);
	  assertFalse("Expense item was not removed", claim.getExpenseItems().contains(expenseItem));
		
	  //(ER) Assert for Tests
	  assertEquals("Too many expenses deleted", 2, claim.getExpenseItems().size());
  	}
  
  /**
   * "Tests" minimal navigation re: adding expense items
   *  you may notice that this test doesn't do anything.
   *  This is because minimal navigation is a subjective
   *  idea, and can't be quantified. This test represents
   *  that we have looked at the user story and fulfilled it 
   *  to our satisfaction.
   *  Tests US04.08.01
   * @throws EmptyFieldException
   * @throws InvalidDateException
   */
  public void testNavigation() throws EmptyFieldException, InvalidDateException {
	  Claim claim = new Claim("Yolo", new Date(100), new Date(120), null, null);
	  ExpenseItem expenseItem = new ExpenseItem("air", new Date(100), "yolo" , 10.50, "USD", claim.getclaimID());
	  claim.addExpenseItem(expenseItem);
  }	
}