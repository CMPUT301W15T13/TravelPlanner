package exceptions;


/**
 * This Exception will occur when there is empty fields
 * @author eorod_000
 *
 */
public class EmptyFieldException extends Exception {
	public EmptyFieldException(String message) {
		super(message);
	}

}
