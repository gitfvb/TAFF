package gdi1sokoban.core.highscore;

/**
 * This interface provides extended database functionality
 * 
 * @author Oren Avni / Guido Roessling
 * @version 1.0
 */
public interface AdditionalOperations {

	/**
	 * Perform a set complement of the current database and the argument.
	 * 
	 * @param data
	 *            The database with which the current database will be merged by
	 *            the complement operator
	 * @return the number of elements in the current database after the
	 *         operation has been executed
	 */
	public int complement(ImprovedDatabase data);

	/**
	 * Perform a set intersection (German "Schnittmenge") of the current
	 * database and the argument.
	 * 
	 * @param data
	 *            The database with which the current database will be merged by
	 *            the intersection operator
	 * @return the number of elements in the current database after the
	 *         operation has been executed
	 */
	public int intersection(ImprovedDatabase data);

	/**
	 * Perform a set union (German "Vereinigung") of the current database and
	 * the argument.
	 * 
	 * @param data
	 *            The database with which the current database will be merged by
	 *            the union operator
	 * @return the number of elements in the current database after the
	 *         operation has been executed
	 */
	public int union(ImprovedDatabase data);
}
