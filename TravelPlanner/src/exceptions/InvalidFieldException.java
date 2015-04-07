package exceptions;


/**
 * This will occur when there is an invalid field being used
 * @author eorod_000
 *
 */
@SuppressWarnings("serial")
public class InvalidFieldException extends Exception  {
	public InvalidFieldException(String message){
		super(message);
	}
	
}
