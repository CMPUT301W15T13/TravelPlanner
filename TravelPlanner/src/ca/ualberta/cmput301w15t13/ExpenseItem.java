package ca.ualberta.cmput301w15t13;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseItem {
	protected String ExpenseItemName = null;
	protected String ExpenseCategory = null;
	protected Date StartDate = null;
	protected Date EndDate = null;
	protected ArrayList<String> Destination = null;
	// every expense Item has a destination. This was from the UI Storyboard
	// destination can be multiple
	protected String ExpenseDescription = null;
	protected int Amount = (Integer) null;
	
	
	/**
	 * 
	 * @param Name
	 * @param Category
	 * @param StartDate
	 * @param EndDate
	 * @param Destination
	 * @param ExpenseDescription
	 * @param Amount
	 */
	public ExpenseItem(String Name, String Category, Date StartDate, Date EndDate, ArrayList<String> Destination, String ExpenseDescription, int Amount){
		this.ExpenseItemName = Name;
		this.ExpenseCategory = Category;
		this.StartDate = StartDate;
		this.EndDate = EndDate;
		this.Destination = Destination;
		this.ExpenseDescription = ExpenseDescription;
		this.Amount = Amount;
		
	}
}
