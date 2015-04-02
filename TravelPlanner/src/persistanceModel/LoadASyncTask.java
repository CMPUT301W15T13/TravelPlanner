package persistanceModel;

import persistanceController.DataManager;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;

/**
 * Required class for network load
 * 
 * http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
 * @author eorod_000
 *
 */
public class LoadASyncTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... users) {
        try {
        	
        	//Toast.makeText(DataManager.getCurrentContext(), "Searching for user Claims", Toast.LENGTH_SHORT).show();
        	
        	String name = users[0];
        	
        	NetworkPersistance networkPersistance = new NetworkPersistance();
        	networkPersistance.loadClaimByUser(name);
        	
        	//ClaimListSingleton.getClaimList().notifyListeners();
            return "";
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception 
        // TODO: do something with the feed

    	ClaimListSingleton.getClaimList().notifyListeners();
    	LoginActivity.LOAD_ALL_RESULT = "done";
    }
}