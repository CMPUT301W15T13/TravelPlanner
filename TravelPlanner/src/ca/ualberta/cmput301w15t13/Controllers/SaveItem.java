package ca.ualberta.cmput301w15t13.Controllers;

public class SaveItem <Item> {

	protected Item itemToSave;
	
	public SaveItem (Item ce){
		itemToSave = ce;
	}
	
	
	private void setItem(Item ce){
		itemToSave = ce;
	}
	
	
	public static String saveClaimOrExpense(){

		return "";
	}
	
	
}
