package persistanceModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.Claim;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This is the local persistance class. It holds all the calls required for local persistance 
 * @author eorod_000
 *
 */

public class LocalPersistance {

	private static final String FILE_NAME = "TravelPlanerData.sav";

	public void LoadClaims() {
	}

	public void saveClaims(ArrayList<Claim> claimArrayList, Context context) {
		Gson gson = new Gson();
		//try to save
		try {
			//this will create the new file and use GSON to save the claimlist to it
			FileOutputStream fos = context.openFileOutput(FILE_NAME, 0);//context.openFileOutput(FILE_NAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claimArrayList, osw);
			
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
	}

	public void LoadClaims(Context context){
		Gson gson = new Gson();
		
		try {
			
			//This will open the Save file
			FileInputStream fis = context.openFileInput(FILE_NAME);
			InputStreamReader isr = new InputStreamReader(fis);
			
			//this will get the type of the claimList
			Type claimListType = new TypeToken<ArrayList<Claim>>() {}.getType();
			
			//this will load the file
			ArrayList<Claim> arrayList= gson.fromJson(isr, claimListType);
			//this will close the streams
			isr.close();
			fis.close();
			
			ClaimListSingleton.clearList();
			for (Claim claim: arrayList){
				ClaimListSingleton.addClaim(claim);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
