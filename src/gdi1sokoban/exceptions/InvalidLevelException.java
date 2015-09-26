package gdi1sokoban.exceptions;

public class InvalidLevelException extends Exception {


	
	private static final long serialVersionUID = 1L;

	
	public InvalidLevelException(String errorMessage) {
		super(errorMessage);
		
	}

	public InvalidLevelException(Throwable cause) {
		super(cause);
		
	}

}