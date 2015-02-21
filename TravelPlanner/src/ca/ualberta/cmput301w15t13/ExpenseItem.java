package ca.ualberta.cmput301w15t13;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseItem {
	protected String ExpenseItemName = null;
	protected String ExpenseCategory = null;
	protected Date purchaseDate = null;
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
	public ExpenseItem(String Name, String Category, Date puchaseDate, ArrayList<String> Destination, String ExpenseDescription, int Amount){
		this.ExpenseItemName = Name;
		this.ExpenseCategory = Category;
		this.purchaseDate = puchaseDate;
		this.Destination = Destination;
		this.ExpenseDescription = ExpenseDescription;
		this.Amount = Amount;
		
	}
}
