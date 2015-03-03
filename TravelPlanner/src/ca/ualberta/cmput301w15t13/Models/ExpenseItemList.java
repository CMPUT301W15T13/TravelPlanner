// Ji Hwan Kim - 2015/02/22

package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;

public class ExpenseItemList {
	// First assign the array, ExpenseItemList, to be null
	ArrayList<ExpenseItem> ExpenseItemList = null;
	
	String claimID = null;
	
	// Initializing the ExpenseItemList
	public ExpenseItemList()
	{
		ExpenseItemList = new ArrayList<ExpenseItem>();
	}
	
	// Initializing the ExpenseItemList
	public ExpenseItemList(String ClaimID)
	{
		claimID =ClaimID; 
		ExpenseItemList = new ArrayList<ExpenseItem>();
	}
	
	// some methods used to control ExpenseItemList. i.e. add, delete, update, etc
	public void add(ExpenseItem Expense)
	{
		// No need to check for duplicate as there could be multiple identical expenses in a claim
		ExpenseItemList.add(Expense);
	}
	
	public void delete(ExpenseItem Expense)
	{
		if (ExpenseItemList.contains(Expense)==true){
			// there is an Expense in a list we can delete, then delete
			ExpenseItemList.remove(Expense);
		} else if (ExpenseItemList.contains(Expense)==false){
			// there is no item Expense in the list we can delete
			return;
		} else if (ExpenseItemList.isEmpty()==true){
			// if the list is empty, then return
			return;
		}
	}
	
	public int size(){
		return this.ExpenseItemList.size();
	}
	
	
	public ArrayList<ExpenseItem> getExpenseList(){
		return this.ExpenseItemList;
	}

}
