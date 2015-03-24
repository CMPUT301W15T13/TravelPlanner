package persistanceModel;

import ca.ualberta.cmput301w15t13.Models.Claim;

public abstract class Persistance {

	public abstract Claim loadClaim(String claimUUID);
	public abstract String saveClaim(Claim claim);
	
	
}
