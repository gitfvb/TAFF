package gdi1sokoban.core.highscore;

/**
 * 
 * @author flow
 *
 */
public class Database {
	/* the private array containing the elements */
	private Entry[] entries;

	/**
	 * initializes the database to an empty entry collection
	 */
	public Database() {
		entries = new Entry[0];
	}

	/**
	 * initializes the database with an filled entry collection
	 * 
	 * @param entries
	 *            Entry-Array
	 */
	public Database(Entry[] entries) {
		this.entries = entries;
	}

	/**
	 * Drops the database and replaces it with one of size 0
	 * 
	 * @return true if database has been deleted correctly, else false.
	 */
	public boolean dropDatabase() {

		// declaration
		int i;
		int arrayLength = getSize();

		// deleting the first element of array until the array is empty
		for (i = 0; i < arrayLength; i++) {
			deleteEntry(entries[0]);
		}

		// checks, if array is empty
		if (getSize() == 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * deletes the entry e if it exists and updates the database
	 * 
	 * @param e
	 *            the Entry which should be deleted.
	 * @return true if Entry has been deleted successfully, else false.
	 */
	public boolean deleteEntry(Entry e) {

		// declaration
		int entryPos = getPos(e); // searching for given Entry e

		// Entry was found in array. Now the method deletes it.
		if (entryPos != -1) {
			entries[entryPos] = null;
			resize(entryPos);
			return true;
		}

		// Entry was not found... nothing to do
		else {
			return false;
		}
	}

	/**
	 * resizes the database after the entry at position deletedPos was removed
	 * 
	 * @param deletedPos
	 *            the position that was deleted
	 */
	public void resize(int deletedPos) {

		// creating a temporary array with a smaller size (array-size - 1)
		Entry[] myTempEntries = new Entry[getSize() - 1];

		// copying the left side of the old array into the temporary array
		if (deletedPos > 0) {
			System.arraycopy(entries, 0, myTempEntries, 0, deletedPos);
		}

		// copying the right side of the old array into the temporary array
		if (deletedPos != getSize() - 1) {
			System.arraycopy(entries, deletedPos + 1, myTempEntries,
					deletedPos, getSize() - deletedPos - 1);
		}

		// setting new reference to array
		entries = myTempEntries;
	}

	/**
	 * returns true if an entry matching the parameter exists in the database
	 * 
	 * @param e
	 *            the Entry that should exist.
	 * @return true if the Entry exists in the database, else false.
	 */
	public boolean entryExists(Entry e) {

		// if entry found -> true
		if (getPos(e) > -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * inserts an entry into the database. Note: entries that already exist will
	 * not be inserted again.
	 * 
	 * @param e
	 *            : the Entry that should be inserted to the database.
	 * @return true if Entry has been successfully inserted to the database,
	 *         else false (if the value is null or already existed).
	 */
	public boolean addEntry(Entry e) {

		// Entry = null... nothing to do
		if (e == null)
			return false;

		// checking, if entry already exists
//		if (entryExists(e)) {
//			return false;
//		} else {
			// creating a temporary array with a greater size (array-size + 1)
			Entry[] myTempEntries = new Entry[getSize() + 1];
			System.arraycopy(entries, 0, myTempEntries, 0, getSize()); // copy
																		// old
																		// array
			myTempEntries[getSize()] = e; // add new entry
			entries = myTempEntries; // set new reference
			return true;
//		}

	}

	/**
	 * returns the position of the entry in the database or -1 if not found
	 * 
	 * @param e
	 *            the Entry for which the position shall be determined.
	 * @return either a valid position or -1 if the entry is not contained or
	 *         the database is empty.
	 */
	public int getPos(Entry e) {
		int i;

		// checking each element of the array
		for (i = 0; i < getSize(); i++) {
			if (entries[i].equals(e))
				return i;
		}
		
		// at this point the loop was not successful
		return -1;

	}

	/**
	 * returns the size of this database
	 * 
	 * @return the size of this database.
	 */
	public int getSize() {
		return entries.length;
	}

	/**
	 * Swaps the elements at the two given indices, if both indices are valid
	 * 
	 * As no entries are changed, only their ordering, the database will stay
	 * consistent
	 * 
	 * @param pos1
	 *            the first position to be swapped
	 * @param pos2
	 *            the second position to be swapped
	 */
	protected void swap(int pos1, int pos2) {

		// checking the positions, checking plausibility
		if (pos1 >= 0 && pos2 >= 0 && pos1 < getSize() && pos2 < getSize()) {

			// save the entries in 
			Entry anEntry1 = entries[pos1];
			Entry anEntry2 = entries[pos2];

			// swap the entries
			entries[pos1] = anEntry2;
			entries[pos2] = anEntry1;

		}

	}

	/**
	 * Returns the complete String representation of this database
	 * 
	 * @return the contents of the database as one formated String.
	 */
	public String toString() {

		int i;
		int arrayLength = getSize();
		StringBuffer myBuffer = new StringBuffer(256);

		// database is empty
		if (arrayLength == 0) {
			return "<Database is empty>";
		}

		// concatenate each element of the array to one string
		for (i = 0; i < arrayLength; i++) {
			myBuffer.append(entries[i]).append("\n");
		}

		// return the string
		return myBuffer.toString();
	}

	public Entry getEntry(int i) {
		return entries[i];	
	}
	
}
