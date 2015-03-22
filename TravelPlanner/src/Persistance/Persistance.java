package Persistance;

import ca.ualberta.cmput301w15t13.Models.Claim;

public abstract class Persistance {

	public abstract String loadClaim(String claimUUID);
	public abstract String saveClaim(Claim claim);
	
	
}
