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

import persistanceController.DataManager;
import android.text.format.DateFormat;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;
import exceptions.ExceptionHandler;
import exceptions.ExceptionHandler.FIELD;
import exceptions.InvalidDateException;
import exceptions.InvalidFieldEntryException;
import exceptions.InvalidUserPermissionException;

/**
 * Class for claim.
 * Defines data and overall functionality
 */
public class Claim implements Comparable<Claim>, ExpenseClaim {
	
	protected String claimID = null;
	protected String userName = null;
	protected Date startDate = null; 
	protected Date endDate = null;
	protected String description = null;
	protected TravelItineraryList travelList = null;
	/**
	 *  this HashMap approverComments will contain, <"name of an approver", "Array list of comments">
	 */
	protected HashMap<String, ArrayList<String>> approverComments = new HashMap<String, ArrayList<String>>();
	protected String lastApproverName = null;
	protected ClaimStatus status = null;
	protected ExpenseItemList expenseItems = null;
	public ArrayList<Tag> tags = new ArrayList<Tag>();
	protected double totalCost = 0;
	protected ClaimCurrencies totalCurrencies;
	/**
	 * returnedBefore will distinguish a claim that has been returned in the past or not
	 * if it has been returned in the past, then only approver who returned can approve the submitted claim
	 */
	protected boolean returnedBefore;

	public Claim(String username, Date startDate, Date endDate, String description,TravelItineraryList travelList) throws EmptyFieldException, InvalidDateException {
		//initializes the claim status to INPROGRESS (and edit-able)
		this.status = new ClaimStatus();
		this.setUserName(username);
		this.setClaimDates(startDate, endDate);
		this.setDescription(description);
		//this sets the travel List. If null, it makes an empty list
		this.setTravelList(travelList);
		this.approverComments = new HashMap<String,ArrayList<String>>();
		//this sets the UUID for the claim (identifier for saving/loading)
		claimID = UUID.randomUUID().toString();
		this.expenseItems = new ExpenseItemList();
	}
	
	/**
	 * This will return the claim ID (UUID and elastic search index)
	 * @return Claim UUID
	 */
	public String getclaimID() {
		return this.claimID;
	}
	
	/**
	 * This will return the claim ID (UUID and elastic search index)
	 * @return Claim UUID
	 */
	public String getID() {
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
	 * @throws InvalidUserPermissionException 
	 */
	public void setUserName(String userName) throws EmptyFieldException {
		//this throws an empty field exception if user name is empty
		new ExceptionHandler().throwExeptionIfEmpty(userName, FIELD.USERNAME);
		this.userName = userName;	
	}

	/**
	 * This sets up the claim dates. This does the error checking to make sure that startDate < EndDate
	 * This method is preferred over setStartDate and SetEndDate as this one checks for possible exceptions.
	 * @param startDate
	 * @param endDate
	 * @throws InvalidDateException
	 */
	public void setClaimDates(Date startDate, Date endDate) throws InvalidDateException {
		//this checks to see that the entered start date is not after the entered end date
		if (startDate.after(endDate)) {
			 throw new InvalidDateException("Start Date is after End Date");
		}
		//set the dates
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	public Date getStartDate() {
		return this.startDate;
	}

	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.trim().isEmpty()) {
			this.description = "";
		}
		else {
			this.description = description;	
		}
	}
	
	public TravelItineraryList getTravelList() {
		return travelList;
	}

	/**
	 * This sets up the travel List.
	 * If null, it makes a new Travel Itinerary List
	 * @param travelList
	 */
	public void setTravelList(TravelItineraryList travelList) {
		//This makes sure that the travel List is not null
		if (travelList == null) {
			this.travelList = new TravelItineraryList();
		} else {
			this.travelList = travelList;
		}
	}

	public statusEnum getStatus() {
		return this.status.getStatus();
	}

	/**
	 * This sets the status of a claim
	 * @param status
	 * @throws InvalidUserPermissionException 
	 */
	public void giveStatus(statusEnum status) {
		this.status.setStatus(status);
		DataManager.updateClaim(this);
	}
		
	/**
	 * This adds a travel Itinerary. 
	 * It checks to see if a travel destination exists, and if the fields are valid
	 * @throws EmptyFieldException 
	 */

	public void addTravelDestination(String destination, String description) throws EmptyFieldException {
		// This line will make the Travel Itinerary instance
		// if the fields are invalid, it will throw an Empty Field Exception
		TravelItinerary travelItinerary = new TravelItinerary(destination, description);
		this.travelList.addTravelDestination(travelItinerary);
	}
	
	public int numberOfDestinations() {
		return this.travelList.numberofDestinations();
	}

	/**
	 * This will return the Travel Itinerary based on the index specified
	 * @param index
	 * @return
	 * @throws InvalidFieldEntryException
	 */
	public TravelItinerary getTravelDestination(int index) throws InvalidFieldEntryException {
		return this.travelList.getTravelDestinationAtIndex(index);
	}

	/**
	 * This will edit the Travel Itinerary based on the index, and data passed in
	 * @param index
	 * @param destination
	 * @param description
	 * @throws EmptyFieldException 
	 */
	public void editTravelDescription(int index, String destination, String description) throws EmptyFieldException {
		TravelItinerary travelItinerary = new TravelItinerary(destination, description);	
		travelList.editTravelDestination(index, travelItinerary);
	}

	public void deleteTravelDestination(int i) {
		this.travelList.deleteTravelDestination(i);
	}

	/**
	 * Get the name of the last approver.
	 * Useful for keeping track of approvers
	 * overall
	 * @return
	 */
	public String getlastApproverName() {
		return this.lastApproverName;
	}

	public void setLastApproverName(String name) {
		this.lastApproverName = name;
		DataManager.updateClaim(this);
	}

	public void addComment(String comment, String name) {
		ArrayList<String> comments;
		setLastApproverName(name);
		if (approverComments.containsKey(name)) {
			comments = approverComments.get(name);
		} else {
			comments = new ArrayList<String>();
		}
		comments.add(comment);
		this.approverComments.put(name, comments);
		DataManager.updateClaim(this);
	}

	/**
	 * Creates a new ArrayList<String>
	 * of all comments from all approvers
	 * and returns them all.
	 */
	public ArrayList<String> getComments() {
		ArrayList<String> comments = new ArrayList<String>();
		
		for (String key: approverComments.keySet()) { 
			ArrayList<String> tempComments = approverComments.get(key);

			for(String comment : tempComments) {
				comments.add(comment);
			}
		}
		return comments;
	}

	public void clearComments() {
		this.approverComments = new HashMap<String, ArrayList<String>>();
		this.lastApproverName = null;
	}
	
	/**
	 * this method will let the fragments know if the claim has been returned before
	 * @return
	 */
	public boolean getReturnedBefore() {
		return this.returnedBefore;
	}
	
	public void setReturnedBefore(boolean returned) {
		this.returnedBefore = returned;
	}

	/**
	 * adds a tag to  the instances arrayList of tags, but only if new
	 * @param tag
	 * @throws DuplicateException
	 */
	public void addTag(Tag tag) throws DuplicateException {	
		if (this.tags.contains(tag)) {
			throw new DuplicateException("Duplicate Tag Added");
		} else {
			this.tags.add(tag);
		}
	}

	/**
	 * gets list of tags
	 * @param tag
	 */
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	/**
	 * Gets a specific tag at an index
	 * @param index
	 */
	public Tag getTag(int Index) {
		return this.tags.get(Index);
	}

	public void setTag(int index, String tname) {
		Tag tag = tags.get(index);
		tag.setTagName(tname);
	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);	
	}
	
	/**
	 * converts the claims Tags into
	 * an output-able form
	 */
	
	public String getTagsAsString() {
		String stringTags = "";
		if (this.tags.size() > 0) {
			stringTags = this.tags.get(0).getTagName();
		}
		for (int index = 1; index < this.tags.size(); index ++) {
			stringTags = stringTags +  "," + this.tags.get(index).getTagName();
		}
		return stringTags;
	}

	public void addExpenseItem(ExpenseItem expenseItem) {
		this.expenseItems.add(expenseItem);		
		DataManager.updateClaim(this);
	}

	public ArrayList<ExpenseItem> getExpenseItems() {
		return this.expenseItems.getExpenseList();
	}
	
	public ExpenseItemList getExpenseItemList() {
			return new ExpenseItemList();
	}

	/**
	 * This will return the edit-ability of the claim
	 * @return
	 */
	public boolean isEditable() {
		return this.status.isEditable();
	}
	
	/**
	 * remove a given expense item from
	 * a claim
	 * @param expenseItem
	 */
	public void removeExpenseItem(ExpenseItem expenseItem) {
		this.expenseItems.delete(expenseItem);
		DataManager.updateClaim(this);
	}

	/**
	 * This will format a the start date as a string
	 * 
	 * Code taken from Eorodrig Travel Logger (Claim class)
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public String getStartDateAsString() {
		//we need this item to format our dates
		DateFormat dateFormat = new DateFormat();
		Date newDate = (Date) this.startDate.clone();
		int year = newDate.getYear();
		newDate.setYear(year-1900);
		
		//this is where we format the start and end dates
		return dateFormat.format("dd-MMM-yyyy", newDate).toString();
	}
	
	/**
	 * This will format a the end date as a string
	 * 
	 * Code taken from Eorodrig Travel Logger (Claim class)
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public String getEndDateAsString() {
		//we need this item to format our dates
		DateFormat dateFormat = new DateFormat();
		Date newDate = (Date) this.endDate.clone();
		int year = newDate.getYear();
		newDate.setYear(year-1900);
		
		//this is where we format the start and end dates
		return dateFormat.format("dd-MMM-yyyy", newDate).toString();
	}
	
	
	public String getTravelItineraryAsString() {		
		return travelList.toString();
	}

	/**
	 * This is the compare method required by the Collections.sort method	
	 * Claim will return 1 if:
	 * 		Caller is before the parameter
	 * 	Claim will return -1 if:
	 * 		Caller is after the parameter
	 * else it will return 0 if both start date and end dates are the same
	 * 
	 * Used eorodrig code on "Claim.java"
	 */
	@Override
	public int compareTo(Claim rhs) {
		if (this.getStartDate().before(rhs.getStartDate())) {
			return -1;
		} else if (this.getStartDate().after(rhs.getStartDate())) {
			return 1;
		} else if (this.getStartDate().equals(rhs.getStartDate())) {
			if (this.getEndDate().before(rhs.getEndDate())) {
				return 1;
			}else if (this.getEndDate().after(rhs.getEndDate())) {
				return -1;
			}
		}
		return 0;
	}

	public String getCost() {
		// TODO Auto-generated method stub
		//return "0.00";
		this.totalCurrencies = new ClaimCurrencies();
		for (ExpenseItem expense:expenseItems.getExpenseList()){
			totalCurrencies.addCurrency(expense.getExpenseCurrency());
		}
		
		return totalCurrencies.getCurrenciesAsString();
		
	}
	
	public void addToCost(double newCost){
	
		
	}

	public void clearExpenses() {
		this.expenseItems = new ExpenseItemList();
		
	}
	
}
