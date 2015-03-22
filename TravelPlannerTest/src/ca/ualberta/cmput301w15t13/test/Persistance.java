package ca.ualberta.cmput301w15t13.test;

import java.util.Date;

import Persistance.DataManager;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
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
public void tessstNetworkSave() throws EmptyFieldException, InvalidDateException{
	Claim claim = new Claim("Group13",new Date(100), new Date(200), "testing", null);

	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
	claim = new Claim("Group13",new Date(300), new Date(500), "testing2", null);
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
	claim = new Claim("Group13",new Date(300), new Date(600), "testing3", null);
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
	claim = new Claim("GroupBlah",new Date(300), new Date(600), "testing4", null);
	DataManager.setOnlineMode();
	DataManager.saveClaim(claim);
	
}


public void testLoadClaim(){

	int test = DataManager.loadClaimsByUserName("group13");
	
	assertEquals(1, test);
	
	assertEquals("expected is 1", 3, test);
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
