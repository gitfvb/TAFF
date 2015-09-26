package gdi1sokoban.exceptions;

@SuppressWarnings("serial")
public class FileCannotBeFoundException extends Exception {

	public FileCannotBeFoundException(String cause) {
		System.out.println(cause);
	}
	
}
