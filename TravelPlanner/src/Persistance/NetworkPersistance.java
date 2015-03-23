package Persistance;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
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
	
	
	/**
	 * This will save 1 claim to the network
	 */
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

	
	/**
	 * This will save 1 Expense to the network
	 * @param expense
	 * @return
	 */
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
	public Claim loadClaim(String claimUUID) {
		Gson gson = new Gson();
		SearchHit<Claim> sr = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(SAVE_CLAIM_URL + claimUUID);

		HttpResponse response = null;

		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e1) {
			throw new RuntimeException(e1);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		Type searchHitType = new TypeToken<SearchHit<Claim>>() {}.getType();

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
	
	

	private ArrayList<String> getClaimIDList(String userName){
		Gson gson = new Gson();
		/**
		 * Creates a search request from a search string and a field
		 */
		HttpPost searchRequest = new HttpPost(SEARCH_CLAIM_URL);
		SearchCommand command = new SearchCommand("userName:"+userName);
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
		SearchResponse<Claim> esResponse;
		try {
			esResponse = gson.fromJson(
					new InputStreamReader(response.getEntity().getContent()),
					searchResponseType);
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ArrayList<String> claimIDList = new ArrayList<String>();
		if (esResponse.getHits().getTotal() >0){
			int totalHits = esResponse.getHits().getTotal();
			for (int index = 0; index < totalHits; index++){
				claimIDList.add(esResponse.getHits().getHits().get(index).get_id());
			}
		}
		return claimIDList;
	}
	
	
	/**
	 * This will load a claim based on the userName
	 * @param userName
	 * @return
	 */
	public void loadClaimByUser(String userName){

		ArrayList<String> claimIDList = this.getClaimIDList(userName);
		ArrayList<Claim> claimList = new ArrayList<Claim>();
		
		for (int index=0; index < claimIDList.size(); index++){
			String claimID = claimIDList.get(index);
			Claim fetchedClaim = this.loadClaim(claimID);
			ClaimListSingleton.addClaim(fetchedClaim);
		}
	//	ClaimListSingleton.setClaimList(claimList);
	}


	/**
	 * This will delete a claim from the network
	 * @param claimID
	 */
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
	
	
	/**
	 * This will delete an expense from the network
	 * @param expenseID
	 */
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

