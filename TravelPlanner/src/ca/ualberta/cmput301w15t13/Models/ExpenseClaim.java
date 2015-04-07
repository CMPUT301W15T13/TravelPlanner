package ca.ualberta.cmput301w15t13.Models;


/**
 * This is the Expense Claim interface
 * It provides a minimum shared functionality
 * for both claims and expenses
 * 
 * It should be used to define the minimal functionality
 * of an expenseClaim and it's constituent
 * expenseItems
 * 
 * Classes it works with:
 * Claim, ExpenseItem
 */
public interface ExpenseClaim {

	public String getclaimID();
	
	public String getID();
	
}
