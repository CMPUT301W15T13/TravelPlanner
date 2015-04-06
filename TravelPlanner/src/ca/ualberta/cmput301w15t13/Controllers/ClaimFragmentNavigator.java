package ca.ualberta.cmput301w15t13.Controllers;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Fragments.ClaimDetailViewerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import dialogs.ClaimantHomeLocationDialog;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;

@SuppressLint("CommitTransaction") 
public class ClaimFragmentNavigator {
	private static FragmentManager fm;
	private static FragmentTransaction ft;
	private static ClaimViewerFragment claimViewerFragment;
	private static ClaimManagerFragment claimManagerFragment;
	private static ClaimDetailViewerFragment claimDetailViewerFragment;
	private static ClaimActivity claimActivity;

	public static void createInstance(FragmentManager f, ClaimActivity activity){
		fm = f;
		claimViewerFragment = new ClaimViewerFragment();
		claimManagerFragment = new ClaimManagerFragment();
		claimDetailViewerFragment = new ClaimDetailViewerFragment();
		claimActivity = activity;
	}
	
	/**
	 * Switches the fragment/layout
	 * to the claim viewer.
	 */
	public static void showClaimViewer(){
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, claimViewerFragment, "ClaimViewer");
		ft.commit();
	}
	
	/**
	 * Switch to the claim manager
	 * fragment/layout
	 */
	public static void showClaimManager(){
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, claimManagerFragment, "ClaimManager");
		ft.commit();
	}
	
	/**
	 * Switch to the fragment
	 * that views claim details
	 */
	public static void showClaimDetails(int claimIndex){
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, claimDetailViewerFragment, "ClaimDetailsViewer");
		ft.commit();
		
		claimDetailViewerFragment.setClaim(claimIndex);
	}
	
	/**
	 * Opens the claim manager fragment
	 * sets the claim index to the corresponding
	 * position in the array adapter and
	 * tells the claim manager that it should edit,
	 * not create.
	 * @param index
	 */
	public static void editClaim(int claimIndex){
		showClaimManager();
		claimManagerFragment.setStateAsEditing(true);
		claimManagerFragment.setClaimIndex(claimIndex);
	}

	/**
	 * Starts a new claim and switches
	 * the fragment layout to the 
	 * Claim Manager Layout
	 */
	public static void newClaim(){
		showClaimManager();
		claimManagerFragment.setStateAsEditing(false);
	}

	/**
	 * Create a new claim object,
	 * then return to the viewing fragment. 
	 * Whenever claim details are being set, if it's a new claim,
	 * you cannot finish until everything is filled, so it will only
	 * allow you to change fragments when createClaim() returns true.
	 * @throws InvalidUserPermissionException 
	 * @throws InvalidNameException 
	 * @throws InvalidDateException 
	 * @throws EmptyFieldException 
	 */
	public static void finishClaim() throws InvalidDateException, InvalidUserPermissionException, EmptyFieldException{
		claimManagerFragment.updateReferences();
		boolean newData = true;
		if(claimManagerFragment.isEditing()){ //check if we're updating a claim or creating a claim
			claimManagerFragment.updateClaim();
		}
		else{
			newData = claimManagerFragment.createClaim();
		} 
		if(newData){
			ClaimListSingleton.getClaimList().notifyListeners();
			showClaimViewer();
		}
	}

	/**
	 * Will open a date picker dialog
	 * but passes the proper start date text view id
	 * such that it can be updated.
	 */
	public static void openStartDateDialog(TextView textId){
		claimManagerFragment.openDateDialog(textId);
	}
	
	/**
	 * Will open a date picker dialog
	 * but passes the proper end date text view id
	 * such that it can be updated.
	 */
	public static void openEndDateDialog(TextView textId){
		claimManagerFragment.openDateDialog(textId);
	}
	
	public static void filterClaim() {
		claimViewerFragment.openFilterDialog();
	}
	
	/**
	 * Opens a destination dialog
	 * for adding the location and reason
	 * for travel.
	 */
	public static void addDestination() {
		claimManagerFragment.openDestinationDialog();
	}

	public static void openTag(){
		claimManagerFragment.openTagDialog();
	}

	/**
	 * Opens the location dialog which gives
	 * the user options to set home location
	 * to either their gps coordinates, or a
	 * selected location from a map. 
	 */
	public static void openLocationDialog() {
		ClaimantHomeLocationDialog dialog = new ClaimantHomeLocationDialog();
	    dialog.show(fm, "Select Location");
	}
	
	/**
	 * Updates the list view that
	 * holds the data for the destinations.
	 */
	public static void updateDestinations(){
		claimManagerFragment.updateDestinationList();
	}

	/**
	 * Gets the Travel Itinerary Item so that the
	 * Dialog can update the item's location.
	 * @param travelItemIndex index of the TravelItinerary item
	 * @return The corresponding TravelItinerary item.
	 */
	public static TravelItinerary getFragmentManagerTravelItinenary(
			int travelItemIndex) {
		return claimManagerFragment.getTravelItineraryItem(travelItemIndex);
	}

	public static User getUser() {
		return claimActivity.getUser();
	}
	/**
	 * Allows global access to /res files,
	 * such as strings and colors.
	 * @return Android Resources object.
	 */
	public static Resources getResources() {
		return claimActivity.getResources();
	}
}
