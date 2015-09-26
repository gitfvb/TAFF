package gdi1sokoban.exceptions;

public class InvalidMoveException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidMoveException(String errorMessage) {
		super(errorMessage);
	}

	public InvalidMoveException(Throwable cause) {
		super(cause);

	}

}