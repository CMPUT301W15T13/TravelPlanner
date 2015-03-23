package ca.ualberta.cmput301w15t13.test;

import java.util.Date;

import Persistance.DataManager;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import junit.framework.TestCase;

public class Persistance extends ActivityInstrumentationTestCase2<LoginActivity> {
	  public Persistance() {
	    super(LoginActivity.class);
	  }
	  
	  
/**
 * Test Save	  
 * @throws InvalidDateException 
 * @throws EmptyFieldException 
 */
public void testNetworkSave() throws EmptyFieldException, InvalidDateException{
	TravelItinerary ti = new TravelItinerary("europe", "blah");
	TravelItineraryList tiList= new TravelItineraryList();
	tiList.addTravelDestination(ti);
	Claim claim = new Claim("group13",new Date(100), new Date(200), "testing",tiList);

	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
	claim = new Claim("group13",new Date(300), new Date(500), "testing2", tiList);
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
	claim = new Claim("group13",new Date(300), new Date(600), "testing3", tiList);
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
	claim = new Claim("groupblah",new Date(300), new Date(600), "testing4",tiList);
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
}


public void tesltLoadClaim(){

	//ClaimList cl = DataManager.loadClaimsByUserName("Group13");
	
	//assertEquals("expected is 1", 3, cl.size());
}

/**
 * Test Save	  
 * @throws InvalidDateException 
 * @throws EmptyFieldException 
 * @throws InterruptedException 
 */
public void tesstNetworkDete() throws EmptyFieldException, InvalidDateException, InterruptedException{
	Claim claim = new Claim("Group13",new Date(100), new Date(200), "testing", null);

	String claimID = claim.getclaimID();
	
	
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	try{
		Thread.sleep(1000);
		
	}catch(Exception e){
		
	}
	DataManager.deleteClaim(claimID);
	
}

	
}
