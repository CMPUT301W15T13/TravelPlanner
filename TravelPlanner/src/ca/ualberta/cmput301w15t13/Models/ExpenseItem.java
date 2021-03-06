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

import android.location.Location;
import android.net.Uri;
import android.text.format.DateFormat;
import exceptions.InvalidFieldEntryException;

/**
 * Expense item class models the functionality
 * of an expense Claim. It should be used to store
 * relevant expenseItem information such as 
 * a photo receipt or a puchaseDate.
 * 
 * Classes it works with:
 * Currency,Category,Receipt,
 */
public class ExpenseItem implements ExpenseClaim {
	
  /**
   * these are the allowed variables that will show up on the Activities
   */
  private ArrayList<String> allowedCategories = new ArrayList<String>();
  private ArrayList<String> allowedCurrencies = new ArrayList<String>();
	
  /**
   * an ExpenseItem requires the user 
   * to fulfill these fields (via the UI)
   */
  protected String ExpenseName = null;
  protected String ExpenseCategory = null;
  protected Date purchaseDate = null;
  protected String ExpenseDescription = null;
  protected double Amount = 0.00;
  protected String currency;
  protected Currency expenseCurrency;
  protected Location location = null;
  protected String ClaimID = null;
  protected String ExpenseID = null;
  protected Uri receiptUri = null;
  public Receipt receipt = null;
  public boolean complete = false;
  

  public ExpenseItem(String Category, Date purchaseDate, String ExpenseDescription, double Amount, String Currency, String ClaimID) {
    /** 
     * TODO for project 5
     * this looks through the singleton list to see if the claim is edit-able
	 * if so, it makes the expense
	 */ 
    this.ClaimID = ClaimID;
    this.ExpenseID = UUID.randomUUID().toString();
    this.ExpenseCategory = Category;
    this.purchaseDate = purchaseDate;
    this.ExpenseDescription = ExpenseDescription;    
    this.setExpenseCurrency(Amount, Currency);

    /** 
     * These variables are the expense item categories that are visible on the activity 
     */
    this.allowedCategories.add("Air Fare");  this.allowedCategories.add("Ground Transport");  this.allowedCategories.add("Vehicle Rental");  this.allowedCategories.add("Private Automobile");
	this.allowedCategories.add("Fuel"); this.allowedCategories.add("Parking"); this.allowedCategories.add("Registration"); this.allowedCategories.add("Accommodation");
	this.allowedCategories.add("Meal"); this.allowedCategories.add("Supplies");
			
	/**
	 * These variables are tge expense item currencies that are visible on the activity
	 */
	this.allowedCurrencies.add("CAD"); this.allowedCurrencies.add("USD"); this.allowedCurrencies.add("EUR");
	this.allowedCurrencies.add("GBP"); this.allowedCurrencies.add("CHF"); this.allowedCurrencies.add("JPY");
	this.allowedCurrencies.add("CNY");
		
  }
  

  private void setExpenseCurrency(double amount, String currencyType) {
	    this.Amount = amount;
	    this.currency = currencyType;
	    
	    this.expenseCurrency = new Currency(currencyType, amount);
  }
  
  public Currency getExpenseCurrency(){
	  return this.expenseCurrency;
  }
  
  public boolean hasReceipt() {
	  return !(receipt.equals(null));
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
     this.ExpenseCategory = expenseCategory;
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
    
    this.setExpenseCurrency(this.Amount, this.currency);
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) throws InvalidFieldEntryException {
     this.currency = currency;
     this.setExpenseCurrency(this.Amount, this.currency);
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

  public void addReceipt() {
    this.receipt = new Receipt();
  }

  public void removeReceipt() {
	  this.receipt = null;
  }

  public boolean isComplete() {
    return complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }
  
  /**
   * Copied by
   * James Devito, http://stackoverflow.com/a/3973886
   * on March 16 2015
   * 
   * this method translates java date format into the string format
   * the format is done so that we can analyze as we pass the result as string format
   */
	@SuppressWarnings({ "deprecation", "static-access" })
	public String getPurchseDateAsString() {
		//we need this item to format our dates
		DateFormat dateFormat = new DateFormat();
		Date newDate = (Date) this.purchaseDate.clone();
		int year = newDate.getYear();
		//For some reason this is necessary
		newDate.setYear(year-1900);
		
		/**this is where we format the start and end dates
		 * 
		 */
		return dateFormat.format("dd-MMM-yyyy", newDate).toString();
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
