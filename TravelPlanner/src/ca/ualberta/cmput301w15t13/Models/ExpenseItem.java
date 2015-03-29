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

package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;
import android.text.format.DateFormat;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidUserPermissionException;


/**
 * This is an expense item. It defines the model characteristics of an expense
 */
public class ExpenseItem implements ExpenseClaim {
	

  /**these are the allowed variables that will show up on the Activities
   * 
   */
  private static ArrayList<String> allowedCategories = new ArrayList<String>();
  private static ArrayList<String> allowedCurrencies = new ArrayList<String>();
	
  /**these are the input fields for the expense
   * 
   */

  protected String ExpenseName = null;
  protected String ExpenseCategory = null;
  protected Date purchaseDate = null;
  protected String ExpenseDescription = null;
  protected double Amount = 0.00;
  protected String currency;

  protected String ClaimID = null;
  protected String ExpenseID = null;

  /**this does not need to be initialized
   * 
   */
  protected Currency currencyEnum;
  public Receipt receipt = null;

  public boolean complete = false;
	
  /**
   * 
   * @param ClaimID
   * @param Category
   * @param purchaseDate
   * @param Amount
   * @param Currency
   * @throws InvalidUserPermissionException 
   */
  public ExpenseItem(String Category, Date purchaseDate, String ExpenseDescription, double Amount, String Currency, String ClaimID) {
    /** TODO for project 5
     * this looks through the singleton list to see if the claim is edit-able
	 * if so, it makes the expense
	 */ 
    this.ClaimID = ClaimID;
    this.ExpenseID = UUID.randomUUID().toString();
    this.ExpenseCategory = Category;
    this.purchaseDate = purchaseDate;
    this.ExpenseDescription = ExpenseDescription;
    this.Amount = Amount;
    this.currency = Currency;

    /** 
     * These variables are the expense item categories that are visible on the activity 
     * 
     */
    this.allowedCategories.add("Air Fare");  this.allowedCategories.add("Ground Transport");  this.allowedCategories.add("Vehicle Rental");  this.allowedCategories.add("Private Automobile");
	this.allowedCategories.add("Fuel"); this.allowedCategories.add("Parking"); this.allowedCategories.add("Registration"); this.allowedCategories.add("Accommodation");
	this.allowedCategories.add("Meal"); this.allowedCategories.add("Supplies");
			
	/**
	 * These variables are tge expense item currencies that are visible on the activity
	 * 
	 */
	this.allowedCurrencies.add("CAD"); this.allowedCurrencies.add("USD"); this.allowedCurrencies.add("EUR");
	this.allowedCurrencies.add("GBP"); this.allowedCurrencies.add("CHF"); this.allowedCurrencies.add("JPY");
	this.allowedCurrencies.add("CNY");
		
  }
  
  public String getExpenseName() {
	  return this.ExpenseName;
  }
  
  public void setExpenseName(String name) {
	  this.ExpenseName = name;
  }
  
  public String getExpenseCategory() {
    return this.ExpenseCategory;
  }
	
  /**
   * this method sets the expenseCategory and if it is not valid then it throws an error
   * @param expenseCategory
   * @throws InvalidFieldEntryException
   */
  public void setExpenseCategory(String expenseCategory) throws InvalidFieldEntryException  {
    if (!this.allowedCategories.contains(expenseCategory)){
      throw new InvalidFieldEntryException("Not a valid Category");
    } else {
      this.ExpenseCategory = expenseCategory;
    }
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getExpenseDescription() {
    return ExpenseDescription;
  }

  public void setExpenseDescription(String expenseDescription) {
    ExpenseDescription = expenseDescription;
  }

  public double getAmount() {
    return Amount;
  }

  public void setAmount(double amount) {
    Amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) throws InvalidFieldEntryException {
    if (!allowedCurrencies.contains(currency)) {
      throw new InvalidFieldEntryException("Unsupported Currency");
    } else {
      this.currency = currency;
    }
  }
	
  public String getclaimID()  {
    return ClaimID;
  }
	
  public String getID(){
    return ExpenseID;
  }

  public void setLinkedToclaimID(String linkedToclaimID) {
    this.ClaimID = linkedToclaimID;
  }

  public Receipt getReceipt() {
    return receipt;
  }

  /**
   *  method for adding a receipt for the expense item, this is to be implemented for project 5
   * @param receipt
   */
  public void addReceipt(Receipt receipt) {
    this.receipt = receipt;
  }

  /**
   * method for removing a receipt for the expense item, this is to be implemented for project 5
   * @param bitmap
   */
  public void removeReceipt(Bitmap bitmap) {
    /**
     *  TODO for project 5
     */
  }

  /**
   *  this method will determine if the expense item is complete or not
   * 
   * @return
   */
  public boolean isComplete() {
    return complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  /**
   * method that is to be implemented for project 5
   */
  public void setIncompletenessIndicator() {
    /**
     *  TODO for project 5
     */
  }

  /**
   * method that is to be implemented for project 5
   */
  public void removeIncompletenessIndicator() {
    /**
     *  TODO for project 5
     */
  }
  
  /**
   * Copied from
   * James Devito, http://stackoverflow.com/a/3973886
   * on March 16 2015
   * 
   * this method translates java date format into the string format
   * the format is done so that we can analyze as we pass the result as string format
   */
	public String getPurchseDateAsString() {
		//we need this item to format our dates
		DateFormat dateFormat = new DateFormat();
		Date newDate = (Date) this.purchaseDate.clone();
		int year = newDate.getYear();
		newDate.setYear(year-1900);
		
		/**this is where we format the start and end dates
		 * 
		 */
		return dateFormat.format("dd-MMM-yyyy", newDate).toString();
	}
}
