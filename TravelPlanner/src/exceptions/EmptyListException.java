package exceptions;

/**
 * This Exception will occur when there is an empty list being manipulated
 * @author eorod_000
 *
 */
public class EmptyListException extends Exception {
	public EmptyListException(String message){
		super(message);
	}
	
}
