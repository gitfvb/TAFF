package gdi1sokoban.playingfield;

import gdi1sokoban.core.Level;
import gdi1sokoban.core.StringExtended;

public class CrateOnGoal {

	private char sign = '*';
	private Level level = new Level();
	
	/**
	 * Konstruktor ohne Level
	 */
	public CrateOnGoal() {
	}
	
	/**
	 * Konstruktor mit Level
	 * 
	 * @param level Level
	 */
	public CrateOnGoal(Level level) {
		this.setLevel(level);
	}
	
	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the sign
	 */
	public char getSign() {
		return sign;
	}
	
	/**
	 * 
	 * Suchen von Mauern innerhalb des Levels
	 * 
	 * @return Anzahl von Mauern im aktuellen Level
	 */
	public int getCount() {
		return StringExtended.countPattern(this.getLevel().currentLevelToString(), this.getSign());
	}
	
	/**
	 * 
	 * Überprüfung auf char mithilfe einer Koordinate
	 * 
	 * @param y y-Koordinate
	 * @param x x-Koordinate
	 * @return boolean (true oder false), wenn Zeichen an der Stelle enthalten ist
	 */
	public boolean isAtPosition(int y, int x) {
		return this.getLevel().isCharAt(y, x, this.getSign());
	}
	
}
