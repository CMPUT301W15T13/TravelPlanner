package exceptions;


/**
 * This is a handler that checks for empty fields. If found it throws an emptyFieldException
 * @author eorod_000
 *
 */
public class ExceptionHandler {

	/**
	 * Enums for the types of empty fields
	 * @author eorod_000
	 *
	 */
	public enum FIELD{
		USERNAME, DATE, TRAVEL_DESTINATION,TRAVEL_DESCRIPTION
	}
	

	/**
	 * This will do 2 things:
	 * 1: format the message based on the field
	 * 2: throw an emptyfieldexception
	 * 
	 * @param fieldContents
	 * @param field
	 * @throws EmptyFieldException
	 */
	public void throwExeptionIfEmpty(String fieldContents, FIELD field) throws EmptyFieldException {

		String message = null;
		
		if (this.fieldIsEmpty(fieldContents)){
			message = this.getMessage(field);
			throw new EmptyFieldException(message);
		}
	}
	
	

	
	private String getMessage(FIELD field){
		
		String message = null;
		
		switch (field){
		case USERNAME:
			message = "Invalid User Name";
			break;
		case TRAVEL_DESCRIPTION:
			message = "Invalid Travel Description";
			break;
		case TRAVEL_DESTINATION:
			message = "Invalid Travel Destination";
			break;
		default:
			message = "Unspecified Message";
			break;	
		}
		return message;
	}
	
	

	private boolean fieldIsEmpty(String field){
		if (field == null ||field.trim().isEmpty()) {
			return true;
		}
			return false;
	}
	
}
