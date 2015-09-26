package gdi1sokoban.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class SokobanFileFilter extends FileFilter {
	/*============================================================================*/
	//Beschreibung
	String description;

	//evtl. Dateiendungen
	String extensions[];
	/*============================================================================*/

	
	/**
	 * Konstruktor
	 * @param: description (String): Beschreibung; extension (String): Dateiendung die angezeigt werden soll
	 */
	public SokobanFileFilter(String description, String extension) {
		this(description, new String[] { extension });
	}

	/**
	 * Konstruktor
	 * @param: description (String): Beschreibung; extension[] (String-Array): Dateiendungen die angezeigt werden sollen
	 */
	public SokobanFileFilter(String description, String extensions[]) {
		if (description == null) {
			this.description = extensions[0] + "{ " + extensions.length + "} ";
		} else {
			this.description = description;
		}
		this.extensions = (String[]) extensions.clone();
		toLower(this.extensions);
	}

	/**
	 * Wandelt alle Zeichen eines Strings in Kleinbuchstaben um
	 * @param array(String)
	 */
	private void toLower(String array[]) {
		for (int i = 0, n = array.length; i < n; i++) {
			array[i] = array[i].toLowerCase();
		}
	}

	/**
	 * Gibt das Klassenattribut Description zurück
	 * @return: description(String)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Prüft, ob Datei akzeptabel ist
	 * @param: file(File): Dateiobjekt
	 */
	public boolean accept(File file) {
		//Ist file ein Ordner
		if (file.isDirectory()) {
			return true;
		} else {
			String path = file.getAbsolutePath().toLowerCase();//Dateipfad speichern
			for (int i = 0, n = extensions.length; i < n; i++) {//Alle gültigen Endungen drchlaufen
				String extension = extensions[i];
				if ((path.endsWith(extension) && (path.charAt(path.length() // Prüfen ob gültige Endung mit Endung der Datei überinstimmt
						- extension.length() - 1)) == '.')) {
					return true;
				}
			}
		}
		return false;
	}
}
