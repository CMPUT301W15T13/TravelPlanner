package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;
import java.util.Date;

public class Claim {

	protected static int EDITABLE = 1;
	protected static int NONEDITABLE = 0;
	
	protected static int INPROGRESS = 0;
	protected static int SUBMITTED = 1;
	protected static int APPROVED = 2;
	protected static int RETURNED = 3;
	
	protected String userName = null;
	protected Date startDate = null; 
	protected Date endDate = null;
	protected String description = null;
	protected TravelItineraryList travelList = null;
	
	protected int editible = NONEDITABLE;
	protected int status = INPROGRESS;
	
	
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
		
	}
	
	
	




}
