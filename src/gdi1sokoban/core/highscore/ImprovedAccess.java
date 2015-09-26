package gdi1sokoban.core.highscore;


/**
 * This interface provides extended database functionality
 * 
 * @author Oren Avni / Guido Roessling
 * @version 1.0
 */
public interface ImprovedAccess {
  /**
   * Returns the array of entries that match string str in
   * column col.
   * 
   * Example: selectXFrom(Column.PHONE_NUMBER, "0173") will
   * return all entries that contain "0173" in their phone
   * number field.
   * 
   * @param col the column to be used for the select operation
   * @param str the String to be matched. The matching is
   * case-insensitive and looks for substrings, not complete
   * matches or only those at the start.
   * @return the array of all matching entries where column col
   * contains the (case-insensitive) match of str "somewhere"
   * in the column.
   */
  public Entry[] selectXFrom(Column col, String str);

  /**
   * Sorts the entries of the database according to the
   * String order of the entries in column col
   * 
   * @param col the column to be used for sorting the entries.
   */
  public void sort(Column col);
}
