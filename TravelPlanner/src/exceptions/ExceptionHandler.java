package exceptions;

public class ExceptionHandler {

	
	public enum FIELD{
		USERNAME, DATE
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
		
		if (field == FIELD.USERNAME){
			return "Invalid User Name";
		}
		return "";
	}
	
	

	private boolean fieldIsEmpty(String field){
		
		if (field == null ||field.trim().isEmpty() ) 
			return true;
		else 
			return false;
	}
	
	
}
