package gdi1sokoban.gui;

import gdi1sokoban.exceptions.InternalFailureException;

/**
 * Sokoban: Klasse zum Starten des Spiels
 */
public class Sokoban {
	
	
	/**
	 * Main-Methode: Zur Ausführung von Sokoban
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Window window = new Window("TAFF - Sokoban :)");
			window.setVisible(true);
		} catch (InternalFailureException e) {
			e.printStackTrace();
		}
	}
}