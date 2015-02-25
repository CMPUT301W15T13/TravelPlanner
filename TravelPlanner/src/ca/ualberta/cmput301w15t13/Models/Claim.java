/*
 * Copyright 2015 James Devito
 * Copyright 2015 Matthew Fritze
 * Copyright 2015 Ben Hunter
 * Copyright 2015 Ji Hwan Kim
 * Copyright 2015 Edwin Rodriguez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import Expceptions.ClaimPermissionException;
import Expceptions.DuplicateException;
import Expceptions.InvalidDateException;
import Expceptions.InvalidFieldEntryException;
import Expceptions.InvalidNameException;

public class Claim {
	
	
	protected String userName = null;
	protected Date startDate = null; 
	protected Date endDate = null;
	protected String description = null;
	protected TravelItineraryList travelList = null;
	protected HashMap<String, String> approverComments = null;
	
	protected ClaimStatus status = null;

	protected String claimID = null;
	
	
	
	
/**
 * 
 * @param username
 * @param startDate
 * @param endDate
 * @param description
 * @param travelList
 * @throws InvalidDateException 
 * @throws InvalidNameException 
 */
	public Claim(String username, Date startDate, Date endDate, String description,TravelItineraryList travelList) throws InvalidDateException, InvalidNameException {

		//this checks the user Name for errs and sets the user name
		this.setUserName(username);
		
		//this checks the dates for errs and sets the dates
		this.setClaimDates(startDate, endDate);

		//this sets the description
		this.setDescription(description);
			
			
		//inits the claim status to INPROGRESS (and editable)
		this.status = new ClaimStatus();
		
		//this sets the travel List. If null, it makes an empty list
		this.setTravelList(travelList);

		this.approverComments = new HashMap<String,String>();
		
		claimID = UUID.randomUUID().toString();
	}

	
	
	public String getclaimID(){
		return this.claimID;
	}

	public String getUserName() {
		return userName;
	}


	/**
	 * This sets the user Name
	 * @param userName = Username for claim
	 * @throws InvalidNameException = Exception that is thrown for invalid user names
	 */
	public void setUserName(String userName) throws InvalidNameException {
		
		//This checks to see that the username is not empty or null
		if (userName.isEmpty() || userName == null)
			throw new InvalidNameException("Invalid UserName Entered");
		else
		this.userName = userName;
	}


	/**
	 * This sets up the claim dates. This does the error checking to make sure that startDate < EndDate
	 * This method is prefered over setStartDate and SetEndDate as this one checks for possible exceptions.
	 * @param startDate
	 * @param endDate
	 * @throws InvalidDateException
	 */
	public void setClaimDates(Date startDate, Date endDate) throws InvalidDateException{
		
		//this checks to see that the entered start date is not after the entered end date
		if (startDate.after(endDate))
			 throw new InvalidDateException("Start Date is after End Date");
		else
		{
			this.setStartDate(startDate);
			this.setEndDate(endDate);
		}
		
		
	}
	
	public Date getStartDate() {
		return startDate;
	}


	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	
	
	public String getDescription() {
		return description;
	}


	/**
	 * This sets the Claim description
	 * @param description
	 */
	public void setDescription(String description) {
		
		if (description == null || description.isEmpty())
			this.description = "";
		else
			this.description = description;
	}


	
	/**
	 * This sets 
	 * @return
	 */
	public TravelItineraryList getTravelList() {
		return travelList;
	}


	/**
	 * This sets up the travel List.
	 * If null, it makes a new Travel Itenerary List
	 * @param travelList
	 */
	public void setTravelList(TravelItineraryList travelList) {
		
		//This makes sure that the travel List is not null
		if (travelList == null)
			this.travelList = new TravelItineraryList();
		else
			this.travelList = travelList;
		}


	/**
	 * Returns the status of a claim
	 * @return
	 */
	public int getStatus() {
		return this.status.getStatus();
	}

	/**
	 * This sets the status of a claim
	 * @param status
	 */
	public void setStatus(int status) {
		this.status.setStatus(status);
	}
	
	
	
	/**
	 * This adds a travel Itenerary
	 * @throws DuplicateException 
	 * @throws InvalidFieldEntryException 
	 */

	public void addTravelDestination(String destination, String description) throws DuplicateException, InvalidFieldEntryException, ClaimPermissionException{
		
		TravelItinerary travelItinerary = new TravelItinerary(destination, description);
		
		if ((destination.isEmpty() || destination == null))
			{
				throw new InvalidFieldEntryException("Travel Destination is null or empty");
			}
		
		if ((description.isEmpty() || description == null))
				{
					throw new InvalidFieldEntryException("Travel Description is null or empty");
				}
		
		if (this.travelList.contains(travelItinerary))
			{
				throw new DuplicateException("Destination Already Exists in Travel Itenerary");
			}
		else
			this.travelList.addTravelDestination(travelItinerary);
	}
	
	
	public int numberOfDestinations(){
		
		return this.travelList.numberofDestinations();
	}

	
	public TravelItinerary getTravelDestination(int index){
		
		return this.travelList.getTravelDestinationAtIndex(index);
	}




	public void editTravelDescription(int i, String destination, String description) throws InvalidFieldEntryException, DuplicateException {

		if ((destination.isEmpty() || destination.isEmpty())){
			throw new InvalidFieldEntryException("destination at index " +i + " can not be editted. Destination is empty");
		}
		if ((description.isEmpty() || description.isEmpty())){
			throw new InvalidFieldEntryException("description at index " +i + " can not be editted. Description is empty");
		}
		
		TravelItinerary travelItinerary = new TravelItinerary(destination, description);
		
		if (travelList.contains(travelItinerary))
		{
			throw new DuplicateException("Cann not edit destination. Destination Already Exists");
			
		}
		
	}




	public void deleteTravelDestination(int i) {
		// TODO Auto-generated method stub
	}




	public String getlastApproverName() {
		// TODO Auto-generated method stub
		return null;
	}




	public void addComment(String comment) {
		// TODO Auto-generated method stub
		
	}




	public ArrayList<String> getComments() {
		// TODO Auto-generated method stub
		return null;
	}




	public void clearComments() {
		// TODO Auto-generated method stub
		
	}




	public void addTag(String vtag) throws DuplicateException{
	
		Tag newTag = new Tag(vtag, claimID);
		
		
		
	}




	public String[] getTags() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Tag getTag(int Index) {
		// TODO Auto-generated method stub
		return null;
	}

	public Tag setTag(int Index, String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeTag(String tag) {
		// TODO Auto-generated method stub
		
	}



	public void addExpenseItem(ExpenseItem expenseItem) {
		// TODO Auto-generated method stub
		
	}



	public ArrayList<ExpenseItem> getExpenseItems() {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}



	public void removeExpenseItem(ExpenseItem expenseItem) {
		// TODO Auto-generated method stub
		
	}







}
