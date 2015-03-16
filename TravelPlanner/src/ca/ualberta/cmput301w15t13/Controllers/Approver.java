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
package ca.ualberta.cmput301w15t13.Controllers;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;

/**
 * Approver child class.
 * Currently only approves and 
 * returns, but as more details
 * are released, as will more
 * functionality
 */

public class Approver extends User {

	public Approver(String name) {
		super(name);
	}
	
	public void approveClaim(Claim claim) {
		if (claim != null && claim.getStatus() == statusEnum.SUBMITTED) {
			claim.setLastApproverName(this.name);
			claim.giveStatus(statusEnum.APPROVED);
		}
	}
	
	public void returnClaim(Claim claim) {
		if (claim != null && claim.getStatus() == statusEnum.SUBMITTED) {
			claim.setLastApproverName(this.name);
			claim.giveStatus(statusEnum.RETURNED);
		}
	}

	public void addComment(Claim claim, String comment) {
		if (claim.getStatus() == statusEnum.SUBMITTED && comment != null) {
			claim.addComment(comment, this.name);
		}
	}

}
