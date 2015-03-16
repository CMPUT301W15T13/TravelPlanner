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

import java.util.ArrayList;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus.statusEnum;
import exceptions.InvalidUserPermissionException;

/**
 * Claimant child class.
 * Currently only submits but 
 * as more details
 * are released, as will more
 * functionality
 */

public class Claimant extends User {
	
	public Claimant(String name) {
		super(name);
	}
	
	public void submitClaim(Claim claim) throws InvalidUserPermissionException{
		if (claim != null && claim.getStatus() == statusEnum.INPROGRESS) {
			claim.giveStatus(statusEnum.SUBMITTED);
		}
	}

	@Override
	public ArrayList<Claim> getPermittableClaims(ArrayList<Claim> claims) {
		ArrayList<Claim> newClaims = new ArrayList<Claim>();
		for(Claim c: claims){
			if(c.getUserName().equals(this.name)){
				newClaims.add(c);
			}
		}
		return newClaims;
	}
}
