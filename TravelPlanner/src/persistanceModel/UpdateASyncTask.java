package persistanceModel;

import android.os.AsyncTask;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;

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
        	//networkPersistance.updateClaim(claim);
            return "";
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }
}