package gdi1sokoban.core;

import gdi1sokoban.core.highscore.*;
import java.io.File;
import java.io.IOException;

public class Highscore extends FileOperations {

	private File scoreFile;
	private ImprovedDatabase base = new ImprovedDatabase();

	/*
	 * 
	 * Reihenfolge der Spalten in Datei: 1. Level-Nummer 2. Spielername 3.
	 * ben�tigte Schritte 4. ben�tigte Zeit in Sekunden
	 */

	/*
	 * Ablauf:
	 * 
	 * 1. Es wird eine Highscore-Datei mit einem Testdatensatz erstellt. 2. Es
	 * wird ein Levelverzeichnis geladen. Damit muss euer Code auch automatisch
	 * die zugeh�rige Highscore-Datei laden. 3. Die Datei wird gel�scht. 4. Der
	 * Testadapter wird angewiesen, seine Highscore-Daten zu schreiben. Wenn er
	 * korrekt implementiert wurde, hat er die urspr�nglichen Daten noch im
	 * Speicher und schreibt sie als Teil der neuen Highscore-Datei wieder
	 * heraus. 5. Diese Datei wird nun auf Existenz und korrekten Inhalt
	 * gepr�ft.
	 */

	/**
	 * 
	 * Einstellen des Highscore
	 * 
	 */
	public Highscore() {
	}
	
	/**
	 * 
	 * Einstellen des Highscore
	 * 
	 */
	public Highscore(File scoreFile) {
		this.scoreFile = scoreFile;
	}
	
	/**
	 * @return the scoreFile
	 */
	public File getScoreFile() {
		return this.scoreFile;
	}

	/**
	 * @param scoreFile
	 *            the scoreFile to set
	 */
	public void setScoreFile(File scoreFile) {
		this.scoreFile = scoreFile;
	}

	/**
	 * 
	 * Laden des Highscores. Existiert die Datei nicht, wird sie neu erstellt.
	 * 
	 */
	public void loadHighscore() {
		this.checkFile();		
		this.getBase().dropDatabase();
		this.loadEntries();
	}

	/**
	 * 
	 * Pr�ft, ob Datei schon existiert. Wenn dies nicht so ist, wird die Datei erstellt.
	 * 
	 */
	private void checkFile() {
		// Wenn Datei noch nicht existiert, wird sie erstellt
//		if (getScoreFile().exists() == false)
//			try {
//				getScoreFile().createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		// TODO wenigstens das �berschreiben erm�glichen
	}
	
	/**
	 * 
	 * Speichern eines neuen Eintrages in der Highscore-Liste
	 * 
	 * @param entry
	 *            Eintrag als String-Array
	 * 
	 */
	@SuppressWarnings("unused")
	private void saveEntry(String[] entry) {
		for (String e : entry) {
			this.saveEntry(e);
		}

	}

	/**
	 * 
	 * Speichern eines Strings
	 * 
	 * @param entry
	 *            Eintrag als String
	 */
	private void saveEntry(String entry) {
		// Deklaration
//		String filePath = null;
//
//		// Pfad der Datei
//		try {
//			filePath = getScoreFile().getCanonicalPath();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		// Erg�nzen der neuen Zeile
		try {
			appendStringln(getScoreFile(), entry);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Highscore-Datei und Datenbank leeren
	 * 
	 */
	public void clear() {
		this.getBase().dropDatabase();
		this.clearFile();
	}
	
	/**
	 * 
	 * Highscore-Datei leeren
	 * 
	 */
	private void clearFile() {
		this.checkFile();
		try {
			writeString(getScoreFile(), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Reinladen der Highscore-Eintr�ge aus der Datei in den Speicher
	 * 
	 */
	private void loadEntries() {

		// Auslesen des Highscores
		StringBuffer sbuf = new StringBuffer();
		try {
			sbuf = readFrom2(getScoreFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Splitten des Highscores in Zeilen
		String[] sArray = sbuf.toString().split("\n");

		// Splitten des Highscores in einzelne Teile und Einspielen in Database
		
		for (String s : sArray) {
			String[] st = s.split("\t");
			if (st.length == 3) {
				Entry e = new Entry(st[0], st[1], st[2]);
				base.addEntry(e);
			}
			if (st.length >= 4) {
				Entry e = new Entry(st[0], st[1], st[2], st[3]);
				base.addEntry(e);
			} else {
				// invalid Entry
			}
		}

	}

	/**
	 * 
	 * Speichern der Eintr�ge aus dem Highscore im File
	 * 
	 */
	public void writeHighscore() {
		// Leeren der Datei
		this.clearFile();

		// Auslesen der Datenbank
		Entry[] entries = this.getBase().selectXFrom(Column.LEVEL_NUMBER,
				"");

		// F�llen der Datei
		for (Entry e : entries) {
			this.saveEntry(e.toString());
		}
	}

	/**
	 * @return the base
	 */
	public ImprovedDatabase getBase() {
		return base;
	}
	
	/**
	 * 
	 * Eintrag hinzuf�gen mit 4 Parametern
	 * 
	 * @param playername Spielername
	 * @param levelNumber Level-Nummer
	 * @param neededSteps gebrauchte Schritte
	 * @param neededTime gebrauchte Zeit
	 * @return boolean, ob Eintrag gespeichert werden konnte
	 */
	public boolean addEntry(String playername, int levelNumber, int neededSteps, int neededTime) {
		
		// Pr�fen des neuen Eintrages
		this.getBase().sort(Column.NEEDED_STEPS); // Vorher Sortierung
		Entry[] eList = this.getBase().selectXFrom(Column.LEVEL_NUMBER, Integer.toString(levelNumber));
		
		// Erst einen Eintrag l�schen, falls bereits 10 Werte vorhanden sind
		if (eList.length >= 10) {
			// Falls der zehnte Eintrag einen kleineren Wert hat, wird er rausgeschmissen
			if (Integer.valueOf(eList[9].getNeededSteps()) > neededSteps) {
				this.getBase().deleteEntry(eList[9]);
			} else {
				// Der Wert passt nicht mehr in die Liste, er wird nicht gespeichert
				return false;
			}
		}
			
		// Schreiben des Highscores
		Entry e = new Entry(Integer.toString(levelNumber), playername, Integer.toString(neededSteps), Integer.toString(neededTime));
		boolean b = this.getBase().addEntry(e);
		this.writeHighscore();
		return b;
		
	}

	/**
	 * 
	 * Eintrag hinzuf�gen mit 3 Parametern
	 * 
	 * @param playername Spielername
	 * @param levelNumber Level-Nummer
	 * @param neededSteps gebrauchte Schritte
	 * @return boolean, ob Eintrag gespeichert werden konnte
	 */
	public boolean addEntry(String playername, int levelNumber, int neededSteps) {
		return this.addEntry(playername, levelNumber, neededSteps, 0);
	}
		
		
		
	/**
	 * 
	 * Auslesen des besten Spielers des aktuellen Levels
	 * 
	 * @return Name des aktuell besten Spielers
	 */
	public String getBestPlayerName() {
		this.getBase().sort(Column.NEEDED_STEPS);
		Entry[] e = this.getBase().selectXFrom(Column.LEVEL_NUMBER, "");
		if (e.length > 0) {
			return e[0].getPlayerName();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * R�ckgabe einer sortierten Liste
	 * 
	 * @return Doppelter String-Array. Erste Dimension = Level, zweite Dimension = 10 Top-Spieler
	 */
	public String[][] getSortedHighscoreList() {
		
		int begin = 0, end = 0, j = 1;
		String header = "Level\tSpielername\tSchritte\tZeit";
		
		// maximale Level-Anzahl ermitteln
		this.getBase().sort(Column.LEVEL_NUMBER);
		if (this.getBase().getSize() > 0) {
			begin = Integer.valueOf(this.getBase().getEntry(0).getLevelNumber()); 
			end = Integer.valueOf(this.getBase().getEntry(this.getBase().getSize() - 1).getLevelNumber());
		}
		
		// Falls nichts vorhanden ist -> null
		if (begin == end && begin == 0) {
			return null;
		}
		
		// Sortierung einstellen
		Entry[] entries = null;
		String[][] sList = new String[end + 1][10];
		sList[0][0] = header; // �berschrift einbinden
		this.getBase().sort(Column.NEEDED_STEPS);
		for (int i = 1; i <= end; i++) {
			entries = this.getBase().selectXFrom(Column.LEVEL_NUMBER, Integer.toString(i));
			j = 0;
			for (Entry e : entries) {
				sList[i][j] = e.toString();
				j++;
			}
		}
		
		// R�ckgabe
		return sList;
		
	}
	
	
	
	
	
}
