package Persistance;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;


import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;


public class NetworkPersistance extends Persistance{

	private static final String SAVE_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/";
	private static final String SAVE_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/";
	
	private static final String SEARCH_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/_search";
	private static final String SEARCH_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/_search";
	
	private static final String SAVE_CLAIM_URL_TEST = "http://cmput301.softwareprocess.es:8080/testing/";
	private static final String SAVE_EXPENSE_URL_TEST = "http://cmput301.softwareprocess.es:8080/testing/_search";
	
	
	@Override
	public String saveClaim(Claim claim) {
		String itemID = claim.getclaimID();
		Gson gson = new Gson();
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPut  addRequest = new HttpPut(SAVE_CLAIM_URL + itemID);

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
			HttpPut  addRequest = new HttpPut(SAVE_EXPENSE_URL + itemID);

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
	public String loadClaim(String claimUUID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ClaimList testLoad(String userName){
		SearchHit<ClaimList> sr = null;
		Gson gson = new Gson();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(SEARCH_CLAIM_URL + "?q=user:"+userName );

		HttpResponse response = null;

		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e1) {
			throw new RuntimeException(e1);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		Type searchHitType = new TypeToken<SearchHit<ClaimList>>() {}.getType();

		try {
			sr = gson.fromJson(
					new InputStreamReader(response.getEntity().getContent()),
					searchHitType);
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return sr.getSource();
		
	}
	
	public int loadClaimByUser(String userName){
		ClaimList claimList = new ClaimList();
		Claim claim;
		Gson gson = new Gson();
		/**
		 * Creates a search request from a search string and a field
		 */

		HttpPost searchRequest = new HttpPost(SEARCH_CLAIM_URL);
		
		String[] fields = null;
		if (fields != null) {
			throw new UnsupportedOperationException("Not implemented!");
		}

		SearchCommand command = new SearchCommand("?q=username:"+userName);

		String query = gson.toJson(command);


		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(query);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpResponse response = null;
		try {
			response = httpClient.execute(searchRequest);
			
		
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		/**
		 * Parses the response of a search
		 */
		Type searchResponseType = new TypeToken<SearchResponse<Claim>>() {
		}.getType();
		
		int test = 0;
		try {
			SearchResponse<Claim> esResponse = gson.fromJson(
					new InputStreamReader(response.getEntity().getContent()),
					searchResponseType);
			 test = esResponse.getHits().getTotal();
			
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return test;
	 
	}

	
	public void loadClaimExpenses(String claimUUID){
		
	}
	
	
	
	public void deleteClaim(String claimID){
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpDelete deleteRequest = new HttpDelete(SAVE_CLAIM_URL +claimID);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			System.out.println(status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteExpense(String expenseID){
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpDelete deleteRequest = new HttpDelete(SAVE_EXPENSE_URL +expenseID);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			System.out.println(status);

		} catch (Exception e) {
			e.printStackTrace();
		}
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