package persistanceModel;

import android.os.AsyncTask;

/**
 * Async task to delete a claim
 * 
 * http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
 * @author eorod_000
 *
 */
public class DeleteASyncTask extends AsyncTask<String, Void, String> {

	    @SuppressWarnings("unused")
		private Exception exception;

	    protected String doInBackground(String... claimID) {
	    	String claimName = claimID[0];        	
	    	try{
	    		new NetworkPersistance().deleteClaim(claimName);
	    		return "";
        	} catch (Exception e) {
        		this.exception = e;
        		return null;
        	}
	    }

	    protected void onPostExecute(String feed) {
	     	//ClaimListSingleton.getClaimList().notifyListeners();
	    }
	}