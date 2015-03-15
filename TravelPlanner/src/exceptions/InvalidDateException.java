package exceptions;

/**
 * This Exception will occur when a date is not valid
 * @author eorod_000
 *
 */
public class InvalidDateException extends Exception {
	public InvalidDateException(String message){
		super(message);
	}
}
