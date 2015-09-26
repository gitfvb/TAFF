package gdi1sokoban.exceptions;

/*============================================================================*/

/**
 * Exception that is thrown whenever a method's parameter falls out of the scope
 * of allowed values
 * 
 * @author Steven Arzt, Oren Avni, Guido Roessling
 * @version 1.0
 */
public class InvalidOperationException extends Exception {

	/* ======================================================================== */

	private static final long serialVersionUID = 1L;

	/* ======================================================================== */

	/**
	 * Creates a new instance of the InvalidOperationException class
	 * 
	 * @param errorMessage
	 *            A string description of the reason why this operation is
	 *            invalid
	 */

	public InvalidOperationException(String errorMessage) {
		super(errorMessage);
	}

	/* ======================================================================== */

}

/* ============================================================================ */
