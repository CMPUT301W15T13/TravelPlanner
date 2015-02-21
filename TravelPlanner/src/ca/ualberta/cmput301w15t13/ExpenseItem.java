package ca.ualberta.cmput301w15t13;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseItem {
	protected String ExpenseItemName = null;
	protected String ExpenseCategory = null;
	protected Date purchaseDate = null;
	protected String ExpenseDescription = null;
	protected int Amount = (Integer) null;
	protected String Currency = null;
	public Receipt receipt = null;
	
	
	/**
	 * 
	 * @param Name
	 * @param Category
	 * @param purchaseDate
	 * @param Amount
	 * @param Currency
	 */
	public ExpenseItem(String Name, String Category, Date purchaseDate, String ExpenseDescription, int Amount, String Currency){
		this.ExpenseItemName = Name;
		this.ExpenseCategory = Category;
		this.purchaseDate = purchaseDate;
		this.ExpenseDescription = ExpenseDescription;
		this.Amount = Amount;
		this.Currency = Currency;
		
	}
}
