package persistanceModel;

import android.os.AsyncTask;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * This Async task is required to update a claim on the network
 */
public class UpdateASyncTask  extends AsyncTask<String, Void, String> {

    @SuppressWarnings("unused")
	private Exception exception;

    protected String doInBackground(String... claimID) {
        	String claimToUpdate = claimID[0];
        try{
        	Claim claim = ClaimListSingleton.getClaimByID(claimToUpdate);
        	NetworkPersistance networkPersistance = new NetworkPersistance();
        	networkPersistance.deleteClaim(claimToUpdate);
        	networkPersistance.saveClaim(claim);
            return "";
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {
    }
}