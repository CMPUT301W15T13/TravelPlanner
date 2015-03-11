package exceptions;

public class ExceptionHandler {

	
	public enum FIELD{
		USERNAME, DATE, TRAVEL_DESTINATION,TRAVEL_DESCRIPTION
	}
	


	public void throwExeptionIfEmpty(String fieldContents, FIELD field) throws EmptyFieldException {

		String message = null;
		
		if (this.fieldIsEmpty(fieldContents)){
			
			message = this.getMessage(field);
			throw new EmptyFieldException(message);
	
		}

		FIELD.USERNAME.toString();
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
		
		if (field == null ||field.trim().isEmpty() ) 
			return true;
		else 
			return false;
	}
	
	
}
