package exceptions;

/**
 * This will occur when a user attempts access an invalid method
 * @author eorod_000
 *
 */
@SuppressWarnings("serial")
public class InvalidUserPermissionException extends Exception {
	public InvalidUserPermissionException(String message){
		super(message);
	}
}
