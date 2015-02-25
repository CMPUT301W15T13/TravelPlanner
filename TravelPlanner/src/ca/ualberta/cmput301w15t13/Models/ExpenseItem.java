package ca.ualberta.cmput301w15t13.Models;

import java.util.Date;


public class ExpenseItem {
	protected String ExpenseCategory = null;
	protected Date purchaseDate = null;
	protected String ExpenseDescription = null;
	protected double Amount = 0.00;
	protected String Currency = null;
	
	protected String linkedToclaimID = null;
	
	public Receipt receipt = null;
	public boolean complete = false;
	
	
	
	/**
	 * 
	 * @param Name
	 * @param Category
	 * @param purchaseDate
	 * @param Amount
	 * @param Currency
	 */
	public ExpenseItem( String Category, Date purchaseDate, String ExpenseDescription, double Amount, String Currency, String ClaimID){
		this.ExpenseCategory = Category;
		this.purchaseDate = purchaseDate;
		this.ExpenseDescription = ExpenseDescription;
		this.Amount = Amount;
		this.Currency = Currency;
		
		this.linkedToclaimID = ClaimID;
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
		return Currency;
	}



	public void setCurrency(String currency) {
		Currency = currency;
	}



	public String getLinkedToclaimID() {
		return linkedToclaimID;
	}



	public void setLinkedToclaimID(String linkedToclaimID) {
		this.linkedToclaimID = linkedToclaimID;
	}



	public Receipt getReceipt() {
		return receipt;
	}



	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
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
