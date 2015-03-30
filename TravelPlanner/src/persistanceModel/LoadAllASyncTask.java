package persistanceModel;

import persistanceController.DataManager;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;

public class LoadAllASyncTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... users) {
        try {

        	//Toast.makeText(DataManager.getCurrentContext(), "Searching for user Claims", Toast.LENGTH_SHORT).show();
        	
        	ClaimListSingleton.clearList();
        	
        	NetworkPersistance networkPersistance = new NetworkPersistance();
        	networkPersistance.loadAll();
        	
        	//ClaimListSingleton.getClaimList().notifyListeners();
            return "";
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String result) {

    	//LoginActivity.LOAD_ALL_RESULT = "done";

    }
}