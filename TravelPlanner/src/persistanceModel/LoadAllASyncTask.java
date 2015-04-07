package persistanceModel;

import persistanceController.DataManager;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;

/**
 * This Async task is used to load all the claims
 * @author eorod_000
 *
 */
public class LoadAllASyncTask extends AsyncTask<String, Void, String> {

    @SuppressWarnings("unused")
	private Exception exception;

    protected String doInBackground(String... users) {
        try {
	        ClaimListSingleton.clearList();	
	        NetworkPersistance networkPersistance = new NetworkPersistance();
	        networkPersistance.loadAll();
	        return "";
        } catch (Exception e) {
	        this.exception = e;
	       	return null;
        }
    }

    protected void onPostExecute(String result) {
    	ClaimListSingleton.getClaimList().notifyListeners();
    	//Toast.makeText(DataManager.getCurrentContext(), "Synchronization complete", Toast.LENGTH_SHORT).show();
    }
}