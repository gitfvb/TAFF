package gdi1sokoban.exceptions;

@SuppressWarnings("serial")
public class DirectoryCannotBeFoundException extends Exception {

	/**
	 * 
	 * Handling for a directory cannot be found
	 * 
	 * @param cause
	 */
	public DirectoryCannotBeFoundException(String cause) {
		System.out.println(cause);
	}

}
