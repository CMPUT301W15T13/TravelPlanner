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
import android.content.Context;
import android.util.Log;


/*
 * This function will be the API for saving a claim/expense
 * Uses the Facade Design pattern
 */
public class SaveItem <Item> implements SAVELOAD {

	protected Item itemToSave;
	
	public SaveItem (Item ce){
		itemToSave = ce;
	}
	
	
	private void setItem(Item ce){
		itemToSave = ce;
	}
	
	/**
	 * This will save the current item on the server
	 * It is based on the save 
	 * @param ce
	 * @return
	 */
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
