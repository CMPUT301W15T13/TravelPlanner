package exceptions;

/**
 * This Exception will occur when there is duplicate data
 * @author eorod_000
 *
 */
public class DuplicateException extends Exception {
	public DuplicateException(String message){
		super(message);
	}
	
}
