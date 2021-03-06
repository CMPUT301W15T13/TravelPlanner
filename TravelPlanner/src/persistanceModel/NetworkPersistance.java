package persistanceModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import persistanceData.SearchCommand;
import persistanceData.SearchHit;
import persistanceData.SearchResponse;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * This class contains all the network persistance calls
 * The elastic search calls from the elastic search lab were reused and changed to fit the needs of this app
 * @author eorod_000
 *
 */
public class NetworkPersistance{

	private static final String SAVE_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/";
	private static final String SAVE_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/";
	
	@SuppressWarnings("unused")
	private static final String UPDATE_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/_update";
	@SuppressWarnings("unused")
	private static final String UPDATE_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/_update";
	
	private static final String SEARCH_CLAIM_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/claim/_search";
	@SuppressWarnings("unused")
	private static final String SEARCH_EXPENSE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t13/expense/_search";
	
	@SuppressWarnings("unused")
	private static final String SAVE_CLAIM_URL_TEST = "http://cmput301.softwareprocess.es:8080/testing/";
	@SuppressWarnings("unused")
	private static final String SAVE_EXPENSE_URL_TEST = "http://cmput301.softwareprocess.es:8080/testing/_search";
	
	
	/**
	 * This will save 1 claim to the network
	 */
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
			@SuppressWarnings("unused")
			String status = response.getStatusLine().toString();
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
			@SuppressWarnings("unused")
			String status = response.getStatusLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemID;
	}
		

	

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
	
	
	public ExpenseItem loadExpense(String expenseUUID) {
		Gson gson = new Gson();
		SearchHit<ExpenseItem> sr = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(SAVE_CLAIM_URL + expenseUUID);

		HttpResponse response = null;

		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e1) {
			throw new RuntimeException(e1);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		Type searchHitType = new TypeToken<SearchHit<ExpenseItem>>() {}.getType();

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
	
	
	private ArrayList<String> getExpenseIDList(String claimID){
		Gson gson = new Gson();
		/**
		 * Creates a search request from a search string and a field
		 */
		HttpPost searchRequest = new HttpPost(SEARCH_CLAIM_URL);
		SearchCommand command = new SearchCommand("claimID:"+claimID);
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
		Type searchResponseType = new TypeToken<SearchResponse<ExpenseItem>>() {
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
		ArrayList<String> expenseIDList = new ArrayList<String>();
		if (esResponse.getHits().getTotal() >0){
			int totalHits = esResponse.getHits().getTotal();
			for (int index = 0; index < totalHits; index++){
				expenseIDList.add(esResponse.getHits().getHits().get(index).get_id());
			}
		}
		return expenseIDList;
	}
	
	
	
	/**
	 * This will load a claim based on the userName
	 * @param userName
	 * @return
	 */
	public void loadExpenseByClaimID(String claimID){

		ArrayList<String> expenseIDList = this.getExpenseIDList(claimID);
		@SuppressWarnings("unused")
		ArrayList<ExpenseItem> expenseList = new ArrayList<ExpenseItem>();
		
		for (int index=0; index < expenseIDList.size(); index++){
			String expenseID = expenseIDList.get(index);
			ExpenseItem expense = this.loadExpense(expenseID);
			ClaimListSingleton.addExpenseToClaim(claimID, expense);
			
		}

		
	}
	
	
	/**
	 * This will load a claim based on the userName
	 * @param userName
	 * @return
	 */
	public void loadClaimByUser(String userName){

		ArrayList<String> claimIDList = this.getClaimIDList(userName);
		@SuppressWarnings("unused")
		ArrayList<Claim> claimList = new ArrayList<Claim>();
		
		for (int index=0; index < claimIDList.size(); index++){
			String claimID = claimIDList.get(index);
			Claim fetchedClaim = this.loadClaim(claimID);
			ClaimListSingleton.addClaim(fetchedClaim);
		}
			
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


	private ArrayList<String> loadAllClaimID() {
		Gson gson = new Gson();
		/**
		 * Creates a search request from a search string and a field
		 */
		HttpPost searchRequest = new HttpPost(SEARCH_CLAIM_URL);

		searchRequest.setHeader("Accept", "application/json");

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


	public void loadAll() {
		ArrayList<String> claimIDList = this.loadAllClaimID();
		@SuppressWarnings("unused")
		ArrayList<Claim> claimList = new ArrayList<Claim>();
		
		for (int index=0; index < claimIDList.size(); index++){
			String claimID = claimIDList.get(index);
			Claim fetchedClaim = this.loadClaim(claimID);
			ClaimListSingleton.addClaim(fetchedClaim);
		}
	}


	public void updateClaim(Claim claim) {
		String itemID = claim.getclaimID();
		Gson gson = new Gson();
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPut  addRequest = new HttpPut(SAVE_CLAIM_URL + itemID + "/_update");

			StringEntity itemAsString = new StringEntity(gson.toJson(claim));
			addRequest.setEntity(itemAsString);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			@SuppressWarnings("unused")
			String status = response.getStatusLine().toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	

	

	
	


	
	
	
}

