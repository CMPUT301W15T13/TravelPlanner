package Persistance;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;

public class NetworkPersistance extends Persistance{

	private static final String SAVE_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/";
	private static final String SAVE_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/";
	
	private static final String SEARCH_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/_search";
	private static final String SEARCH_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/_search";
	
	private static final String RESOURCE_URL_TEST = "http://cmput301.softwareprocess.es:8080/testing/";
	private static final String SEARCH_URL_TEST = "http://cmput301.softwareprocess.es:8080/testing/_search";
	
	
	@Override
	public String saveClaim(Claim claim) {
		String itemID = claim.getID();
		Gson gson = new Gson();
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(SAVE_CLAIM_URL + itemID);

			StringEntity itemAsString = new StringEntity(gson.toJson(claim));
			addRequest.setEntity(itemAsString);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			//Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemID;
	}

	
	public String saveExpense(ExpenseItem expense) {
		String itemID = expense.getID();
		Gson gson = new Gson();
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(SAVE_EXPENSE_URL + itemID);

			StringEntity itemAsString = new StringEntity(gson.toJson(expense));
			addRequest.setEntity(itemAsString);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			//Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemID;
	}
		

	

	@Override
	public String LoadClaim(String claimUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private void loadClaimExpenses(String claimUUID){
		
	}
	
	


	
	
	
}


/*
* This function will be the API for saving a claim/expense
 * Uses the Facade Design pattern

public class SaveItem <Item> implements SAVELOAD {

	protected Item itemToSave;
	
	public SaveItem (Item ce){
		itemToSave = ce;
	}
	
	
	private void setItem(Item ce){
		itemToSave = ce;
	}
	


	public static <Item extends ExpenseClaim> String saveItemOnServer(Item ce){
		String itemID = ce.getID();
		Gson gson = new Gson();
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL_TEST + itemID);

			StringEntity itemAsString = new StringEntity(gson.toJson(ce));
			addRequest.setEntity(itemAsString);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			//Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemID;
	}
	
	
	
	*/
	
/* REFERENCE!!!!
 * FROM LAB!!!
 * DO NOT UNCOMMENT!!!!!
 * 
	public void addMovie(Movie movie) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(movies.getResourceUrl() + movie.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(movie));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMovie(int movieId) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpDelete deleteRequest = new HttpDelete(movies.getResourceUrl() + movieId);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/