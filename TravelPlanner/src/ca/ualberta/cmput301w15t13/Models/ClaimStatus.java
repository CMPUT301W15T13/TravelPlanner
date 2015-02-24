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

public class ClaimStatus {	
	/*
	 * This class is used to model
	 * the status of an expense claim.
	 * It provides the functionality of 
	 * knowing if the claim is editable
	 * and provides the public constants
	 * for consistency and readability.
	 */
	public final static int INPROGRESS = 0, RETURNED = 1, 
			SUBMITTED = 2, APPROVED = 3;
	
	
	
	private int status = null;   //holds the status of the claim
	
	
	/**
	 * Constructor for the class. 
	 * Inits it to be in progress
	 */
	public ClaimStatus(){
		this.status = INPROGRESS;
	}

	
	
	public int getStatus() {
		return status;
	}

	

	public void setStatus(int newStatus) {
		if(newStatus >= 0 && newStatus <= 3){
			this.status = newStatus;
		}
	}

	public boolean isEditable() {
		// The two status values less 
		//than 2==SUBMITTED are editable
		return status < SUBMITTED;
	}
	
	
	
	
}
