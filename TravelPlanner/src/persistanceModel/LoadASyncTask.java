package persistanceModel;

import android.os.AsyncTask;
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
        	String name = users[0];
        	
        	ClaimListSingleton.clearList();
        	
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
    	LoginActivity.LOAD_ALL_RESULT = "done";
    }
}