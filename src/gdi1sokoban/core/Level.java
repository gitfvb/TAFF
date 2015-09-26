package gdi1sokoban.core;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import gdi1sokoban.exceptions.*;
import gdi1sokoban.gamelogic.CheckSyntaxOfLevels;

public class Level extends FileOperations {

	/*
	 * ======================== DECLARATION ========================
	 */

	private int levelNumber = 0;
	private char[][] levelChar = new char[0][0];
	private File levelDir = null;
	private File currentLevelFile = null;

	private String levelPrefix = "level_";
	private String levelPostfix = ".txt";
	private DecimalFormat df = new DecimalFormat("00");

	/*
	 * ======================== KONSTRUKTOREN ========================
	 */

	public Level() {
		
	}
	
	/*
	 * ========================= GETTER / SETTER =========================
	 */

	/**
	 * @return the levelDir
	 */
	public File getLevelDir() {
		return this.levelDir;
	}

	/**
	 * @param levelDir
	 *            the levelDir to set
	 * @throws InvalidLevelException 
	 */
	public void setLevelDir(File levelDir) throws InvalidLevelException {
		
		// Pr�fen des �bergebenen Verzeichnisses
//		if (levelDir.isDirectory() == false) {
//			try {
//				throw new DirectoryCannotBeFoundException(levelDir
//						+ " does not exist");
//			} catch (DirectoryCannotBeFoundException e) {
//				e.printStackTrace();
//			}
//		}
		
		// Einstellen des Pfades
		this.levelDir = levelDir;
		
		// Zur�cksetzen der Level
		try {
			this.setLevelNumber(0);
		} catch (FileCannotBeFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the levelNumber
	 */
	public int getLevelNumber() {
		return this.levelNumber;
	}

	/**
	 * @param lnr
	 *            the lnr to set
	 * @throws InvalidLevelException 
	 * @throws FileCannotBeFoundException 
	 */
	public void setLevelNumber(int LevelNumber) throws InvalidLevelException, FileCannotBeFoundException {
		
		// �berpr�fung des Pfades
		if (this.getLevelDir() == null) {
			try {
				throw new DirectoryCannotBeFoundException("path is not set yet.");
			} catch (DirectoryCannotBeFoundException e) {
				e.printStackTrace();
			}
		}
		
		// Einstellen des Levels
		this.levelNumber = LevelNumber;

		if (this.getLevelNumber() > 0) {
			this.loadLevel();
		}
		
	}

	/**
	 * @return the levelChar
	 */
	public char[][] getLevelChar() {
		return this.levelChar;
	}

	/**
	 * @param levelChar
	 *            the levelChar to set
	 */
	public void setLevelChar(char[][] levelChar) {
		this.levelChar = levelChar;
	}

	/*
	 * ================================ METHODS ================================
	 */

	/**
	 * 
	 * Ausgabe des aktuell eingestellten Levels in die Console
	 * 
	 */
	public void Level2Console() {

		char[][] level = getLevelChar();

		for (int x = 0; x < level.length; x++) {
			for (int y = 0; y < level[x].length; y++) {
				System.out.print(level[x][y]);
			}
			System.out.println("");
		}

	}

	/**
	 * 
	 * Einladen des Levels in den Speicher
	 * @throws InvalidLevelException Check auf syntaktische Korrektheit des Levels 
	 * @throws FileCannotBeFoundException 
	 * 
	 */
	private void loadLevel() throws InvalidLevelException, FileCannotBeFoundException {

//		String fileName = buildLevelPath(this.levelNumber);
//		File file = new File(fileName);
		loadLevel(buildLevelPath(this.levelNumber));

	}
	
	/**
	 * 
	 * Laden eines Levels
	 * 
	 * @param file File-Objekt, das geladen werden soll
	 * @throws InvalidLevelException Check auf syntaktische Korrektheit des Levels
	 */
	public void loadLevel(File file) throws InvalidLevelException  {
		
		IntBuffer ibuf = null;

		// Level-Dir neu setzen
		this.setLevelDir(new File(file.getParent()));
		this.currentLevelFile = file;
		
		// Versuch, die Level-Nummer auszulesen
		String n = file.getName();
		n = n.replaceAll(levelPrefix, "").replaceAll(levelPostfix, "");
		try {
			this.levelNumber = Integer.valueOf(n);	
		} catch (NumberFormatException e) {
			this.levelNumber = 0;
		}
		
		// Auslesen der Datei
		try {
			ibuf = readFrom(file);
		} catch (IOException e) {
		}

		// Konvertierung in 2-dimensionales Array und Speichern in Variable
		this.setLevelChar(convert2Level(ibuf));
		
		// Checken auf korrekten Level
		CheckSyntaxOfLevels.checkSyntax(this.getLevelChar());
			
	}

	/**
	 * 
	 * Erstellen des absoluten Pfades zu einem Level
	 * 
	 * @param lnr
	 *            gew�nschte Levelnummer
	 * @return R�ckgabe des absoluten Dateinamens des gew�nschten Levels
	 * @throws FileCannotBeFoundException 
	 */
	private File buildLevelPath(int lnr) throws FileCannotBeFoundException {

		// Deklaration
//		String levelPath = "";
		
		// Einstellungen
//		System.out.println(getLevelDir().getPath());
		File levelFile = new File(getLevelDir(), levelPrefix + df.format(lnr) + levelPostfix);
//		if (levelFile.isFile() == false) {
//				throw new FileCannotBeFoundException("level not  found");
//		}

		// Verarbeitung
//		try {
//			levelPath = levelFile.getCanonicalPath();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		// Beenden
		return levelFile;

	}

	/**
	 * 
	 * Konvertieren der geladenen Datei in ein brauchbares char[][]-Array
	 * 
	 * @param ibuf
	 * @return char[y-Koordinate][x-Koordinate]-Array mit eingeladenem Level
	 */
	private char[][] convert2Level(IntBuffer ibuf) {

		// Deklaration
		int i, xCount = 0, yCount = 0, xWidth = 0, yWidth = 0;

		// Erfassen der x- und y-Dimensionen
		ibuf.rewind(); // zur�ckspulen
		while ((i = ibuf.get()) != 0) {
			xCount++;
			if (i == 13) {
				ibuf.get(); // noch eine Stelle vorr�cken, da nach der 13 immer
				// die 10 kommt
				yCount++;
				if (xCount > xWidth)
					xWidth = xCount - 1;
				xCount = 0;
			}
		}
		if (xCount > 0)
			yCount++; // falls das letzte Zeichen kein Zeilenumbruch ist und
		// noch Zeichen vorhanden sind, wird noch eine Zeile
		// mehr erg�nzt
		yWidth = yCount;

		// Erstellen des Arrays mit richtigen Dimensionswerten
		char[][] cLevel = new char[yWidth][xWidth];

		// F�llen des Array mit Buffer-Werten
		ibuf.rewind(); // zur�ckspulen
		int x = 0, y = 0;
		while ((i = ibuf.get()) != 0) {

			switch (i) {
			case 10:
				y++;
				x = 0;
				break;
			case 13:
				break;
			default:
				cLevel[y][x] = (char) i;
				x++;
			}

		}

		return cLevel;

	}

	/**
	 * 
	 * R�ckgabe eines Strings vom eingeladenen Level
	 * 
	 * @return R�ckgabe des aktuellen Levels als String
	 */
	public String currentLevelToString() {

		char[][] levelChar = getLevelChar();
		StringBuffer result = new StringBuffer(256);

		for (int y = 0; y < levelChar.length; y++) {
			for (int x = 0; x < levelChar[y].length; x++) {
				if ((int) levelChar[y][x] != 0) result.append(levelChar[y][x]);
			}
			result.append("\n");
		}

		return result.toString();

	}

	/**
	 * 
	 * Laden der Level-H�he
	 * 
	 * @return H�he des Levels
	 */
	public int getLevelHeight() {
		return levelChar.length;
	}

	/**
	 * 
	 * Laden der Level-Breite
	 * 
	 * @return Breite des Levels
	 */
	public int getLevelWidth() {
		return levelChar[0].length;
	}


	/**
	 * 
	 * �berpr�fung, ob Koordinate im Feld liegt.
	 * 
	 * @param y y-Koordinate
	 * @param x x-Koordinate
	 * @return boolean, falls Koordinate im Spielfeld liegt
	 */
	public boolean validCoordinate(int y, int x) {
		if (y >= 0 && y < this.getLevelHeight() && x >= 0
				&& x < this.getLevelWidth()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Diese Funktion gibt zur�ck, ob ein bestimmtes Zeichen an einer bestimmten
	 * Position enthalten ist.
	 * 
	 * @param y
	 *            y-Koordinate
	 * @param x
	 *            x-Koordinate
	 * @param sign
	 *            gesuchtes Zeichen
	 * @return boolean, ob Zeichen enthalten ist
	 */
	public boolean isCharAt(int y, int x, char sign) {
		if (this.validCoordinate(y, x) == true
				&& this.getLevelChar()[y][x] == sign) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Neu-Einladen des Levels in den Speicher
	 * 
	 */
	public void reset() {
		try {
			this.loadLevel();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.loadLevel(this.currentLevelFile);
			} catch (InvalidLevelException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * N�chsten Level starten
	 * 
	 * @throws InvalidLevelException Level wurde nicht gefunden
	 * @throws FileCannotBeFoundException 
	 */
	public void startNextLevel() throws InvalidLevelException, FileCannotBeFoundException {
		this.setLevelNumber(this.getLevelNumber() + 1);
	}

}
