package Persistance;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
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
		DataHelper helper = new DataHelper();	
		helper.saveClaim(claim);
		
		return false;
	}
	
	public static void deleteClaim(String claimID){
		//This will go through a claim and save all the expenses
		DataHelper helper = new DataHelper();	
		helper.DeleteClaim(claimID);
	}
	
	
	public static int loadClaimsByUserName(String userName){
		
		DataHelper helper = new DataHelper();
		return helper.loadClaimsByUserName(userName);
	}
}

////////////////////////////////////////////////////////////////////////////


class DataHelper{
	
	LocalPersistance local = new LocalPersistance();
	NetworkPersistance network = new NetworkPersistance();
	
	/**
	 * This will determine what type of saving method to use depending on the network status.
	 * @param claim
	 */
	public void saveClaim(Claim claim) {
		if (DataManager.isNetworkAvailable()){
			network.saveClaim(claim);
		}
		else{
			local.saveClaim(claim);
		}
	}
	
	
	public int loadClaimsByUserName(String userName) {
		return network.loadClaimByUser(userName);
	}


	/**
	 * This method is for network persistance. It will Save a claim's Expenses.
	 * @param expenseList
	 */
	public void saveClaimExpenses(ExpenseItemList expenseList){
		for (ExpenseItem expense: expenseList.getExpenseList()){
			network.saveExpense(expense);
		}
	}
	

	/**
	 * This will determine what type of saving method to use depending on the network status.
	 * @param claim
	 */
	public String LoadNetworkClaim(String claimID) {
		if (DataManager.isNetworkAvailable()){
			network.loadClaim(claimID);
		}
		return null;
	}
	
	/**
	 * This will determine what type of saving method to use depending on the network status.
	 * @param claim
	 */
	public String LoadLocalClaims() {
		if (!DataManager.isNetworkAvailable()){
			local.LoadClaim();
		}
		return null;
	}
	
	
	public void DeleteClaim(String claimID){
		if (DataManager.isNetworkAvailable()){
			network.deleteClaim(claimID);
		}
	}
	
	
	public void searchClaimsForUserName(String userName){
		
	}
	
	public void searchClaimsByID(String claimID){
		
	}
	
	public void searchExepensesByClaimID(String claimID){
		
	}
	
	public void searchExpenseByExpenseID(String expenseID){
		
	}

}



