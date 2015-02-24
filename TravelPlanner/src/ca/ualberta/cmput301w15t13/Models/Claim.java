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

import java.util.Date;

public class Claim {
	protected String userName = null;
	protected Date startDate = null; 
	protected Date endDate = null;
	protected String description = null;
	protected TravelItineraryList travelList = null;
	protected ClaimStatus status = null;
	
	
	
	
	/**
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param travelList
	 */
	public Claim(String name, Date startDate, Date endDate, String description,TravelItineraryList travelList) {

		this.userName = name;
		this.startDate = startDate;
		this.endDate= endDate;
		this.description = description;
		this.travelList = travelList;
		this.status = new ClaimStatus();
		
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public TravelItineraryList getTravelList() {
		return travelList;
	}


	public void setTravelList(TravelItineraryList travelList) {
		this.travelList = travelList;
	}


	public int getStatus() {
		return this.status.getStatus();
	}


	public void setStatus(int status) {
		this.status.setStatus(status);
	}
	
	
	




}
