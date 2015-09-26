package gdi1sokoban.core;

import java.lang.String;

public class StringExtended {

	/**
	 * 
	 * Suchen eines Strings innerhalb eines Strings
	 * 
	 * @param searchString
	 *            String, in dem gesucht werden soll
	 * @param pattern
	 *            String, der gesucht werden soll
	 * @return Anzahl der Vorkommnisse
	 */
	public static int countPattern(String searchString, String pattern) {
		int pos = -1; // -1, damit die Suche gleich mit pos+1 an Position 0
						// beginnt
		int count = 0;
		while ((pos = searchString.indexOf(pattern, pos + 1)) > -1) {
			count++;
		}
		return count;
	}

	/**
	 * 
	 * Suchen eines Chars innerhalb eines Strings
	 * 
	 * @param searchString
	 *            String, in dem gesucht werden soll
	 * @param pattern
	 *            char, der gesucht werden soll
	 * @return Anzahl der Vorkommnisse
	 */
	public static int countPattern(String searchString, char pattern) {
		Character charCast = new Character(pattern);
		return countPattern(searchString, charCast.toString());
	}

}
