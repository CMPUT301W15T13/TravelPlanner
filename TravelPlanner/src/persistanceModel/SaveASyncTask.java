package persistanceModel;


import android.os.AsyncTask;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * Required class for network save
 * 
 * http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
 * @author eorod_000
 *
 */
public class SaveASyncTask extends AsyncTask<String, Void, String> {

    @SuppressWarnings("unused")
	private Exception exception;

    protected String doInBackground(String... claimID) {
        String name = claimID[0];
        try{
        	Claim claim = ClaimListSingleton.getClaimByID(name);
        	new NetworkPersistance().saveClaim(claim);
        	return "";
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {
    }
}