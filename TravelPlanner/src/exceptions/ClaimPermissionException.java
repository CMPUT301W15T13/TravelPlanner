package exceptions;

/**
 * This will occur when a user attempts access an invalid method
 * @author eorod_000
 *
 */
public class ClaimPermissionException extends Exception {
	public ClaimPermissionException(String message){
		super(message);
	}
}
