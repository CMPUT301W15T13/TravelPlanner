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

/**
 * This class is used to model
 * the status of an expense claim.
 * It provides the functionality of 
 * knowing if the claim is editable
 * and provides the public constants
 * for consistency and readability.
 * 
 * It should be used to denote a
 * Claim object with a predefined
 * status
 * 
 * 
 */

public class ClaimStatus {	
	
	public enum statusEnum {
		INPROGRESS, RETURNED, SUBMITTED, APPROVED
	}
	
	protected statusEnum currentStatus;
	
	/**
	 * Constructor for the class. 
	 * The default status is "In progress"
	 */
	
	public ClaimStatus(){
		this.currentStatus = statusEnum.INPROGRESS;
	}

	public statusEnum getStatus() {
		return currentStatus;
	}

	public void setStatus(statusEnum newStatus) {
			this.currentStatus = newStatus;
	}

	/**
	 * Returns true if the claim can be edited
	 * @return
	 */
	public boolean isEditable() {
		if ((currentStatus == statusEnum.INPROGRESS) || (currentStatus == statusEnum.RETURNED)) {
			return true;
		}
		return false;
	}
}
