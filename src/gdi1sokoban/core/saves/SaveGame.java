package gdi1sokoban.core.saves;

import java.io.File;
import java.io.IOException;

import gdi1sokoban.core.FileOperations;

public class SaveGame extends Saves {

	/**
	 * 
	 * Konstruktor zum Speichern des Spielstandes
	 * 
	 * @param playerName
	 *            aktueller Spielername
	 * @param currentLevel
	 *            aktueller Level
	 * @param steps
	 *            bisherige Schritte
	 * @param time
	 *            bisher vergangene Zeit
	 */
	public SaveGame(String playerName, int currentLevel, String steps, int time) {
		this.setLevelnr(currentLevel);
		this.setPlayername(playerName);
		this.setTime(time);
		this.setMoves(steps);
	}

	/**
	 * 
	 * Speichern eines Spielstandes
	 * 
	 * @param file
	 */
	public void save(File file) {

		// Speicher-Reihenfolge:
		// Spielername, aktueller Level, Schritte

		// Lšschen des vorher vorhandenen Spielstandes
		if (file.exists())
			file.delete();

		// Umwandlung in korrektes Format
		String[] s = new String[4];

		// Abfragen der nštigen Parameter
		s[0] = this.getPlayername();
		s[1] = Integer.toString(this.getLevelnr());
		s[2] = this.getMoves();
		s[3] = Integer.toString(this.getTime());

		// Speichern der Datei mit Parameter file
		try {
			FileOperations.writeString(file, s,
					super.separator);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
