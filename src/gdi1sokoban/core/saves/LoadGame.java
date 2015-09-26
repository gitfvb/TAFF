package gdi1sokoban.core.saves;

import gdi1sokoban.core.FileOperations;
import gdi1sokoban.exceptions.InvalidMoveException;
import gdi1sokoban.exceptions.InvalidSaveGame;

import java.io.File;
import java.io.IOException;

public class LoadGame extends Saves {
	
	/**
	 * 
	 * Reinladen eines Spielstandes
	 * 
	 * @param file
	 *            Datei für den Spielstand
	 * 
	 */
	public void loadGame(File file) throws InvalidMoveException, IOException,
			InvalidSaveGame {

		// Speicher-Reihenfolge:
		// Spielername, aktueller Level, Schritte

		// Rausladen der Daten, Datei wird von Funktion geprüft
		StringBuffer s = FileOperations.readFrom2(file);

		// Aufsplitten der Spielstandes
		String[] t = s.toString().split(super.separator);

		// Prüfung, ob Savegame fehlerhaft ist
		if (t.length != 4)
			throw new InvalidSaveGame("Fehler beim Einladen der Datei!");

		// Speichern der Variablen
		this.setPlayername(t[0]);
		this.setLevelnr(Integer.valueOf(t[1]));
		this.setMoves(t[2]);
		this.setTime(Integer.valueOf(t[3]));

	}

}
