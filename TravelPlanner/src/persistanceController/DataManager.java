package persistanceController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import persistanceModel.DeleteASyncTask;
import persistanceModel.LoadASyncTask;
import persistanceModel.LoadAllASyncTask;
import persistanceModel.LocalPersistance;
import persistanceModel.NetworkPersistance;
import persistanceModel.SaveASyncTask;
import persistanceModel.UpdateASyncTask;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.ExpenseItemList;
import ca.ualberta.cmput301w15t13.Models.Tag;


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
	private static Context currentContext;
	private static boolean isTesting = false;
	
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
	
	public static void setTestMode(){
		isTesting = true;
	}
	
	/**
	 * This will save 1 Claim
	 * @param claim
	 * @return
	 */
	public static boolean saveClaim(Claim claim){
		//This will go through a claim and save all the expenses
		if (isTesting == false){
			DataHelper helper = new DataHelper();	
			helper.saveClaim(claim);
			return false;
		}else{
			return false;
		}
		
	}
	

	
	public static void saveClaims(ArrayList<Claim> claimList){
		if (isTesting == false){
			for(Claim claim: claimList){
				saveClaim(claim);
			}
		}

	}
	
	/**
	 * This will delete 1 claim
	 * @param claimID: Claim ID
	 */
	public static void deleteClaim(String claimID){
		//This will go through a claim and save all the expenses
		if (isTesting == false){
			DataHelper helper = new DataHelper();	
			helper.DeleteClaim(claimID);
		}
	}
	
	
	/**
	 * This will load all the claims made by a specific User
	 * @param userName
	 * @return
	 */
	public static void loadClaimsByUserName(String userName){
		if (isTesting == false){
			DataHelper helper = new DataHelper();
			helper.loadClaimsByUserName(userName);
			//This goes through all the claims and reloads the tags
			ArrayList<Tag> tagList;
			for (Claim claim: ClaimListSingleton.getClaimList().getClaimArrayList()){
				tagList = claim.getTags();
				ClaimListSingleton.getClaimList().resetTagManager();
				for (int index = 0; index < tagList.size(); index++){
					
					ClaimListSingleton.getClaimList().tagManager.add(tagList.get(index), claim.getclaimID());
					
				}
			}
		}
	}
	
	/**
	 * This updates the contexts so that the context is always current. It is required for local persistance
	 * @return
	 */
	public static void setCurrentContext(Context context){
		if (isTesting == false){
			currentContext = context;
		}
	}
	
	/**
	 * This gets the contexts so that the context is always current. It is required for local persistance
	 * @return
	 */
	public static Context getCurrentContext(){
		return currentContext ;
	}
	
	public static void loadAllClaims() throws InterruptedException, ExecutionException{
		if (isTesting == false){
			DataHelper helper = new DataHelper();
			helper.loadAllClaims();
		}
	}

	public static void updateClaim(Claim claim) {
		if (isTesting == false){
			DataHelper helper = new DataHelper();
			helper.updateClaim(claim);
		}
		
	}

}

////////////////////////////////////////////////////////////////////////////


/**
 * This class is a helper class to the Data Manager class. It expands on the API above and determines functionality based on network status.
 *
 * @author eorod_000
 *
 */
class DataHelper{
	
	LocalPersistance local = new LocalPersistance();
	NetworkPersistance network = new NetworkPersistance();
	

	/**
	 * This will check to see if the phone has a network signal
	 * Taken from: http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
	 * @return: true if its connected to the internet
	 */
	private void isNetworkConnected(){
		ConnectivityManager cm =
		        (ConnectivityManager)DataManager.getCurrentContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		
		if(isConnected){
			DataManager.setOnlineMode();
			Toast.makeText(DataManager.getCurrentContext(), "Is connected", Toast.LENGTH_SHORT).show();
		}else{
			DataManager.setOfflineMode();
			Toast.makeText(DataManager.getCurrentContext(), "Is NOT connected", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * This saves a claim. If a local save is required... All claims are saved
	 * @param claim
	 */
	public void saveClaim(Claim claim) {
		if (DataManager.isNetworkAvailable()){
			new SaveASyncTask().execute(claim.getclaimID());
		}
		local.saveClaims(ClaimListSingleton.getClaimList().getClaimArrayList(), DataManager.getCurrentContext());
	}


	/**
	 * Updates by overwritting the last saved files
	 * @param claim
	 */
	public void updateClaim(Claim claim) {
		this.isNetworkConnected();
		if (DataManager.isNetworkAvailable()){
			//new UpdateASyncTask().execute(claim.getclaimID());
			new SaveASyncTask().execute(claim.getclaimID());
		}
		local.saveClaims(ClaimListSingleton.getClaimList().getClaimArrayList(), DataManager.getCurrentContext());
	}


	public void loadAllClaims() throws InterruptedException, ExecutionException {
		if (DataManager.isNetworkAvailable()){
			new LoadAllASyncTask().execute("");
		}else{
			local.LoadClaims(DataManager.getCurrentContext());
		}
	}

	/**
	 * Deletes the claim from the network. If offline, it saves the current state
	 * @param claimID
	 */
	public void DeleteClaim(String claimID){
		if (DataManager.isNetworkAvailable()){
			new DeleteASyncTask().execute(claimID);
		}
		local.saveClaims(ClaimListSingleton.getClaimList().getClaimArrayList(), DataManager.getCurrentContext());
	}
	
	/**
	 * This saves all the claim expenses from a claim. If local, it saves the current state of the app.
	 * @param expenseList
	 */
	public void saveClaimExpenses(ExpenseItemList expenseList){
		if (DataManager.isNetworkAvailable()){
			for (ExpenseItem expense: expenseList.getExpenseList()){
				network.saveExpense(expense);
			}
		}
		local.saveClaims(ClaimListSingleton.getClaimList().getClaimArrayList(), DataManager.getCurrentContext());
	}
	
	/**
	 * This will load a user's claimList from the network
	 * @param userName
	 * @return
	 */
	public void loadClaimsByUserName(String userName) {
		if (DataManager.isNetworkAvailable()){
			//Start an Async task to load claims
			new LoadASyncTask().execute(userName);
		}else{
			local.LoadClaims(DataManager.getCurrentContext());
		}
	}


	/**
	 * This will determine what type of saving method to use depending on the network status.
	 * @param claim
	 */
	public String LoadLocalClaims() {
		if (!DataManager.isNetworkAvailable()){
			local.LoadClaims();
		}
		return null;
	}
	

	

	

}



