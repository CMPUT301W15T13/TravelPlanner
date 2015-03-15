package ca.ualberta.cmput301w15t13.Controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.google.gson.Gson;
import ca.ualberta.cmput301w15t13.Models.ExpenseClaim;
import android.content.Context;

public class SaveItem <Item> {

	protected Item itemToSave;
	
	public SaveItem (Item ce){
		itemToSave = ce;
	}
	
	
	private void setItem(Item ce){
		itemToSave = ce;
	}
	
	
	public static <Item extends ExpenseClaim> String saveClaimOrExpense(Item ce){
		String itemID = ce.getID();
		Gson gson = new Gson();
		
		//try to save
		try {	
			//this will create the new file and use GSON to save the claimlist to it
			FileOutputStream fos = context.openFileOutput(itemID, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(ce, osw);
			
			//this will force it to write
			osw.flush();
			//this will close the streams
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

}
