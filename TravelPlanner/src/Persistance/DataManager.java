package Persistance;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.ExpenseItemList;


/**
 * This is the Data Manager class.
 * It servers as as API for the save/Load functionality of the app.
 * 
 * It uses the following design patterns:
 * 		Facade: It provides the user with a simple save/load API that hides the complexity of the code.
 * 		Singleton: Static method with static calls.
 * @author eorod_000
 *
 */
public class DataManager {

	private static boolean networkAvailable = false;
	
	/**
	 * This returns the status of the network
	 * @return
	 */
	public static boolean isNetworkAvailable(){
		return networkAvailable;
	}
	
	public static void setOfflineMode(){
		networkAvailable = false;
	}
	
	public static void setOnlineMode(){
		networkAvailable = true;
	}
	
	public static boolean saveClaim(Claim claim){
		
		//This will go through a claim and save all the expenses
		SaveDataHelper helper = new SaveDataHelper();	
		helper.saveClaim(claim);
		
		return false;
	}
	
}

////////////////////////////////////////////////////////////////////////////


class SaveDataHelper{
	
	LocalPersistance local = new LocalPersistance();
	NetworkPersistance network = new NetworkPersistance();
	
	public void saveClaim(Claim claim) {
		if (DataManager.isNetworkAvailable()){
			network.saveClaim(claim);
		}
		else{
			local.saveClaim(claim);
		}
	}
	
	
	public void saveClaimExpenses(ExpenseItemList expenseList){
		
		for (ExpenseItem expense: expenseList.getExpenseList()){
			network.saveExpense(expense);
		}
		
		
	}

	

}

///////////////////////////////////////////////////////////////////

class LoadDataHelper{
	
}
