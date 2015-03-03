package ca.ualberta.cmput301w15t13.Models;

import java.util.Date;

import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import exceptions.InvalidUserPermissionException;
import android.graphics.Bitmap;


public class ExpenseItem {
	
	//these are the input fields for the expense
	protected String ExpenseCategory = null;
	protected Date purchaseDate = null;
	protected String ExpenseDescription = null;
	protected double Amount = 0.00;
	protected String currency;

	
	protected String ClaimID = null;
	//this does not need to be initalized
	protected Currency currencyEnum;
	
	public Receipt receipt = null;
	
	public boolean complete = false;
	
	
	
	/**
	 * 
	 * @param Name
	 * @param Category
	 * @param purchaseDate
	 * @param Amount
	 * @param Currency
	 * @throws InvalidUserPermissionException 
	 */
	public ExpenseItem(String Category, Date purchaseDate, String ExpenseDescription, double Amount, String Currency, String ClaimID) throws InvalidUserPermissionException{
		
		//this looks through the singleton list to see if the claim is editable
		//if so, itr makes the expense
		if (ClaimListSingleton.isClaimEditable(ClaimID))
		{
			this.ClaimID = ClaimID;
			this.ExpenseCategory = Category;
			this.purchaseDate = purchaseDate;
			this.ExpenseDescription = ExpenseDescription;
			this.Amount = Amount;
			this.currency = Currency;
			
		}
		else
			throw new InvalidUserPermissionException("Expense can not be created, Claim is not editable");

		
	}




	public String getExpenseCategory() {
		return ExpenseCategory;
	}



	public void setExpenseCategory(String expenseCategory) {
		ExpenseCategory = expenseCategory;
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



	public void setCurrency(String currency) {
		currency = currency;
	}



	public String getLinkedToclaimID() {
		return ClaimID;
	}



	public void setLinkedToclaimID(String linkedToclaimID) {
		this.ClaimID = linkedToclaimID;
	}



	public Receipt getReceipt() {
		return receipt;
	}



	public void addReceipt(Receipt receipt) {
		this.receipt = receipt;
	}


	public void removeReceipt(Bitmap bitmap) {
		// TODO Auto-generated method stub
	}

	public boolean isComplete() {
		return complete;
	}



	public void setComplete(boolean complete) {
		this.complete = complete;
	}



	public void setIncompletenessIndicator() {
		// TODO Auto-generated method stub
		
	}



	public void removeIncompletenessIndicator() {
		// TODO Auto-generated method stub
		
	}








}
