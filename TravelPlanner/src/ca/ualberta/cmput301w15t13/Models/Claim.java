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

import android.text.format.DateFormat;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import exceptions.ClaimPermissionException;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.ExceptionHandler;
import exceptions.ExceptionHandler.FIELD;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidNameException;
import exceptions.InvalidUserPermissionException;


public class Claim {
	
	
	protected String userName = null;
	protected Date startDate = null; 
	protected Date endDate = null;
	protected String description = null;
	protected TravelItineraryList travelList = null;
	protected HashMap<String, ArrayList<String>> approverComments = new HashMap<String, ArrayList<String>>();
	protected String lastApproverName = null;
	protected ClaimStatus status = null;
	
	protected ExpenseItemList expenseItems = null;
	
	public ArrayList<Tag> tags = new ArrayList<Tag>();
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
 * @throws InvalidUserPermissionException 
 * @throws EmptyFieldException 
 */
	public Claim(String username, Date startDate, Date endDate, String description,TravelItineraryList travelList) throws InvalidDateException, EmptyFieldException {

		//initializes the claim status to INPROGRESS (and editable)
		this.status = new ClaimStatus();
		
		//this checks the user Name for errs and sets the user name
		this.setUserName(username);
		
		//this checks the dates for errs and sets the dates
		this.setClaimDates(startDate, endDate);

		//this sets the description
		this.setDescription(description);
			
		//this sets the travel List. If null, it makes an empty list
		this.setTravelList(travelList);

		this.approverComments = new HashMap<String,ArrayList<String>>();
		
		claimID = UUID.randomUUID().toString();
		this.expenseItems = new ExpenseItemList();
	}

	
	/**
	 * This will return the claim ID (UUID and elastic search index)
	 * @return Claim UUID
	 */
	public String getclaimID(){
		return this.claimID;
	}

	/**
	 * This will return the user name associated with the claim
	 * @return The user who made the claim
	 */
	public String getUserName() {
		return this.userName;
	}


	/**
	 * This sets the user Name
	 * @param userName = Username for claim
	 * @throws EmptyFieldException 
	 */
	public void setUserName(String userName) throws EmptyFieldException {

		new ExceptionHandler().throwExeptionIfEmpty(userName, FIELD.USERNAME);
		//userName = "Bill Smith";

		this.userName = userName;
	}

	/**
	 * This sets up the claim dates. This does the error checking to make sure that startDate < EndDate
	 * This method is preferred over setStartDate and SetEndDate as this one checks for possible exceptions.
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
	
	/**
	 * This will return the start Date for the claim
	 * @return
	 */
	public Date getStartDate() {
		return this.startDate;
	}


	/**
	 * This will set the start Date for the claim
	 */
	private void setStartDate(Date startDate) {
			this.startDate = startDate;

	}

	/**
	 * This will return the end Date for the claim
	 * @return
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * This will set the end Date for the claim
	 */
	private void setEndDate(Date endDate) {
			this.endDate = endDate;

	}


	
	/**
	 * This will return the description of the claim
	 * @return
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * This sets the Claim description
	 * @param description 
	 */
	public void setDescription(String description)  {

			if (description == null || description.trim().isEmpty())
				this.description = "";
			else
				this.description = description;	
	}
	
	
	
	/**
	 * This returns the travel Itenerary list of the claim
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
	 * @throws InvalidUserPermissionException 
	 */
	public void giveStatus(int status)  {
			this.status.setStatus(status);

	}
	
	
	
	/**
	 * This adds a travel Itenerary. 
	 * It checks to see if a travel destination exists, and if the fields are valid
	 * @throws DuplicateException 
	 * @throws EmptyFieldException 
	 */

	public void addTravelDestination(String destination, String description) throws  EmptyFieldException{
		
		if( this.status.getStatus() == ClaimStatus.SUBMITTED || this.status.getStatus() == ClaimStatus.APPROVED){
			throw new EmptyFieldException("Non-Editable");
		} else {
			
			TravelItinerary travelItinerary = new TravelItinerary(destination, description);
			this.travelList.addTravelDestination(travelItinerary);
		}

	
	}
	
	
	/**
	 * THis will return the number of travel destinations
	 * @return
	 */
	public int numberOfDestinations(){
		
		return this.travelList.numberofDestinations();
	}

	
	/**
	 * This will return the Travel Itinerary based on the index specfied
	 * @param index
	 * @return
	 * @throws InvalidFieldEntryException
	 */
	public TravelItinerary getTravelDestination(int index) throws InvalidFieldEntryException{
		
		return this.travelList.getTravelDestinationAtIndex(index);
	}


	/**
	 * This will edit the Travel Itinerary based on the index, and data passed in
	 * @param index
	 * @param destination
	 * @param description
	 * @throws InvalidFieldEntryException
	 * @throws EmptyFieldException 
	 */
	public void editTravelDescription(int index, String destination, String description) throws InvalidFieldEntryException, EmptyFieldException {
		
			TravelItinerary travelItinerary = new TravelItinerary(destination, description);
			
			travelList.editTravelDestination(index, travelItinerary);

		
	}

/**
 * This will delete a travel Itinerary at the specified index
 * @param i
 */
	public void deleteTravelDestination(int i) {
			this.travelList.deleteTravelDestination(i);

	}

/**
 * This will get the name of the last approver
 * @return
 */
	public String getlastApproverName() {
		return this.lastApproverName;
	}


	/**
	 * This will set the name of the last approver
	 * @param name
	 */
	public void setLastApproverName(String name){
		this.lastApproverName = name;
	}

	/**
	 * This will add a comment
	 * @param comment
	 * @param name
	 */
	public void addComment(String comment, String name) {
		ArrayList<String> comments;
		setLastApproverName(name);
		
		if(approverComments.containsKey(name))
		{
			comments = approverComments.get(name);
		}
		else
		{
			comments = new ArrayList<String>();
		}
		comments.add(comment);
		this.approverComments.put(name, comments);
	}



	/**
	 * Creates a new ArrayList<String>
	 * of all comments from all approvers
	 * and returns them all.
	 */
	public ArrayList<String> getComments() {
		ArrayList<String> comments = new ArrayList<String>();
		
		for(String key: approverComments.keySet())
		{
			ArrayList<String> tempComments = approverComments.get(key);
			
			for(String comment : tempComments)
			{
				comments.add(comment);
			}
		}
		return comments;
	}

	/**
	 * This will clear the comments from a claim
	 */
	public void clearComments() {
		this.approverComments = new HashMap<String, ArrayList<String>>();
		this.lastApproverName = null;
	}


	/**
	 * This will add a specified tag to a class?
	 * @param tag
	 * @throws DuplicateException
	 */
	//adds a tag to  the instances arrayList of tags, but only if new
	public void addTag(Tag tag) throws DuplicateException{	
		if (this.tags.contains(tag)) {
			throw new DuplicateException("Duplicate Tag Added");
		} else {
			this.tags.add(tag);
		}
		
	}

	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	public Tag getTag(int Index) {
		return this.tags.get(Index);
	}

	public void setTag(int index, String tname) {
		Tag tag = tags.get(index);
		tag.setTagName(tname);
	}


	public void removeTag(String tag) {
		this.tags.remove(tag);
		
	}


	public void addExpenseItem(ExpenseItem expenseItem) {
		this.expenseItems.add(expenseItem);
	}



	public ArrayList<ExpenseItem> getExpenseItems() {
		return this.expenseItems.getExpenseList();
	}



	/**
	 * This will return the editability of the claim
	 * @return
	 */
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return this.status.isEditable();
	}



	public void removeExpenseItem(ExpenseItem expenseItem) {
		this.expenseItems.delete(expenseItem);

	}

	/**
	 * This will format a the start date as a string
	 * 
	 * Code taken from Eorodrig Travel Logger (Claim class)
	 * @return
	 */
	public String getStartDateAsString(){
		
		//we need this item to format our dates
		DateFormat dateFormat = new DateFormat();
		
		Date newDate = this.startDate;
		newDate.setYear(this.startDate.getYear()-1900);
		
		//this is where we format the start and end dates
		return dateFormat.format("dd-MMM-yyyy", newDate).toString();
	}
	
	
	/**
	 * This will format a the end date as a string
	 * 
	 * Code taken from Eorodrig Travel Logger (Claim class)
	 * @return
	 */
	public String getEndDateAsString(){
		
		//we need this item to format our dates
		DateFormat dateFormat = new DateFormat();
		
		Date newDate = this.endDate;
		newDate.setYear(this.endDate.getYear()-1900);
		
		//this is where we format the start and end dates
		return dateFormat.format("dd-MMM-yyyy", newDate).toString();
	}
	
	
	public String getTravelItineraryAsString(){
		
		return travelList.toString();
	}





}
