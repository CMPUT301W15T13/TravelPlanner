package persistanceModel;

import persistanceController.DataManager;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;

/**
 * Required class for network load
 * 
 * http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
 * @author eorod_000
 *
 */
public class LoadASyncTask extends AsyncTask<String, Void, String> {

    @SuppressWarnings("unused")
	private Exception exception;

    protected String doInBackground(String... users) {
        try {
        	
        	ClaimListSingleton.clearList();
        	
        	String name = users[0];
        	
        	NetworkPersistance networkPersistance = new NetworkPersistance();
        	networkPersistance.loadClaimByUser(name);

            return "";
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {


    	ClaimListSingleton.getClaimList().notifyListeners();
    	
    	Toast.makeText(DataManager.getCurrentContext(), "Synchronization complete", Toast.LENGTH_SHORT).show();


    }
}