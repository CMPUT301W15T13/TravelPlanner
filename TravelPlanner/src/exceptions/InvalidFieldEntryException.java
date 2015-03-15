package exceptions;

/**
 * This will occur when a method accesses the wrong field
 * @author eorod_000
 *
 */
public class InvalidFieldEntryException extends Exception {
	public InvalidFieldEntryException(String message){
		super(message);
	}
}
