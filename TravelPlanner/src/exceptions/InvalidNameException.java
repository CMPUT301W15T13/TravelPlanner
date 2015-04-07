package exceptions;

/**
 * This will occur when a user name is invalid
 * @author eorod_000
 *
 */
@SuppressWarnings("serial")
public class InvalidNameException extends Exception {
	public InvalidNameException(String message){
		super(message);
	}

}
