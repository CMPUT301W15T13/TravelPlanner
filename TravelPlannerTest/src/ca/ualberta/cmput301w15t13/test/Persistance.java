package ca.ualberta.cmput301w15t13.test;

import java.util.Date;

import persistanceController.DataManager;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import ca.ualberta.cmput301w15t13.Models.TravelItineraryList;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;

public class Persistance extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	public Persistance() {
		super(LoginActivity.class);
		DataManager.setTestMode();
	}

	/**
	 * Test Save Tests US01.06.01
	 * 
	 * @throws InvalidDateException
	 * @throws EmptyFieldException
	 */
	public void testNetworkSave() throws EmptyFieldException,
			InvalidDateException {
		TravelItinerary ti = new TravelItinerary("europe", "blah");
		TravelItineraryList tiList = new TravelItineraryList();
		tiList.addTravelDestination(ti);
		Claim claim = new Claim("group13", new Date(100), new Date(200),
				"testing", tiList);
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(100),
				"yolo", 10, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(105),
				"yolo", 11, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(110),
				"yolo", 12, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(115),
				"yolo", 13, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(120),
				"yolo", 14, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(125),
				"yolo", 15, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(130),
				"yolo", 16, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(135),
				"yolo", 17, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(140),
				"yolo", 18, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(145),
				"yolo", 19, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(150),
				"yolo", 20, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(155),
				"yolo", 21, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(160),
				"yolo", 22, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(165),
				"yolo", 23, "USD", claim.getclaimID()));
		DataManager.setOnlineMode();
		DataManager.saveClaim(claim);

		claim = new Claim("group13", new Date(300), new Date(500), "testing2",
				tiList);
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(100),
				"yolo", 10, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(105),
				"yolo", 11, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(110),
				"yolo", 12, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(115),
				"yolo", 13, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(120),
				"yolo", 14, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(125),
				"yolo", 15, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(130),
				"yolo", 16, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(135),
				"yolo", 17, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(140),
				"yolo", 18, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(145),
				"yolo", 19, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(150),
				"yolo", 20, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(155),
				"yolo", 21, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(160),
				"yolo", 22, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(165),
				"yolo", 23, "USD", claim.getclaimID()));
		DataManager.setOnlineMode();
		DataManager.saveClaim(claim);

		claim = new Claim("group13", new Date(300), new Date(600), "testing3",
				tiList);
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(100),
				"yolo", 10, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(105),
				"yolo", 11, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(110),
				"yolo", 12, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(115),
				"yolo", 13, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(120),
				"yolo", 14, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(125),
				"yolo", 15, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(130),
				"yolo", 16, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(135),
				"yolo", 17, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(140),
				"yolo", 18, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(145),
				"yolo", 19, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(150),
				"yolo", 20, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(155),
				"yolo", 21, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(160),
				"yolo", 22, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(165),
				"yolo", 23, "USD", claim.getclaimID()));
		DataManager.setOnlineMode();
		DataManager.saveClaim(claim);

		claim = new Claim("groupblah", new Date(300), new Date(600),
				"testing4", tiList);
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(100),
				"yolo", 10, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(105),
				"yolo", 11, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(110),
				"yolo", 12, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(115),
				"yolo", 13, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(120),
				"yolo", 14, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(125),
				"yolo", 15, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(130),
				"yolo", 16, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(135),
				"yolo", 17, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(140),
				"yolo", 18, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(145),
				"yolo", 19, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(150),
				"yolo", 20, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(155),
				"yolo", 21, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(160),
				"yolo", 22, "USD", claim.getclaimID()));
		claim.addExpenseItem(new ExpenseItem("Ground Transport", new Date(165),
				"yolo", 23, "USD", claim.getclaimID()));
		DataManager.setOnlineMode();
		DataManager.saveClaim(claim);

	}

	public void tesltLoadClaim() {

		// ClaimList cl = DataManager.loadClaimsByUserName("Group13");

		// assertEquals("expected is 1", 3, cl.size());
	}

	/**
	 * Test Save
	 * 
	 * @throws InvalidDateException
	 * @throws EmptyFieldException
	 * @throws InterruptedException
	 */
	public void testNetworkDelete() throws EmptyFieldException,
			InvalidDateException, InterruptedException {
		Claim claim = new Claim("Group13", new Date(100), new Date(200),
				"testing", null);

		String claimID = claim.getclaimID();

		DataManager.setOnlineMode();
		DataManager.saveClaim(claim);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		DataManager.deleteClaim(claimID);
	}
	
	/**
	 * Tests the ability of the claimant to work
	 * offline then push changes when re-connected
	 * Tests US09.01.01
	 */
	public void testOfflineMode() {
		//TODO finish
	}

}
