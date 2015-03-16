package ca.ualberta.cmput301w15t13.Controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import ca.ualberta.cmput301w15t13.Models.ExpenseClaim;
import ca.ualberta.ssrg.movies.es.Movie;
import android.content.Context;
import android.util.Log;


/*
 * This function will be the API for saving a claim/expense
 * Uses the Facade Design pattern
 */
public class SaveItem <Item>  implements SAVELOAD{

	protected Item itemToSave;
	
	
	
	public SaveItem (Item ce){
		itemToSave = ce;
		String hey = RESOURCE_URL;
	}
	
	
	private void setItem(Item ce){
		itemToSave = ce;
	}
	
	
	public static <Item extends ExpenseClaim> String saveClaimOrExpense(Item ce){
		String itemID = ce.getID();
		
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
		
		
		Gson gson = new Gson();
		
		JsonElement claimJson = gson.toJsonTree(ce);
		
		
		//try to save
		try {	
			//this will create the new file and use GSON to save the claimlist to it
			FileOutputStream fos = context.openFileOutput(itemID, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(ce, osw);
			
			this will force it to write
			osw.flush();
			this will close the streams
			osw.close();
			fos.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itemID;
	}
	
	
	
	/**
	 * This will save the current claimList
	 * @param context
	 */
	public void save(Context context){
		
		
		
		 
	}
	
	
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

}
