package gdi1sokoban.playingfield;

import gdi1sokoban.core.Level;
import gdi1sokoban.core.StringExtended;


public class Crate {

	private static char sign = '$';
	private Level level = new Level();
	private Floor floor = new Floor();
	private Goal goal = new Goal();
	private CrateOnGoal crateOnGoal = new CrateOnGoal();

	/**
	 * Konstruktor ohne Level
	 */
	public Crate() {
	}
	
	/**
	 * Konstruktor mit Level
	 * 
	 * @param level Level
	 */
	public Crate(Level level) {
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
		this.floor.setLevel(level);
		this.goal.setLevel(level);
		this.crateOnGoal.setLevel(level);
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
		return StringExtended.countPattern(this.getLevel()
				.currentLevelToString(), this.getSign())
				+ crateOnGoal.getCount();
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
		return this.getLevel().isCharAt(y, x, this.getSign()) || this.crateOnGoal.isAtPosition(y, x);
	}
	
	/**
	 * 
	 * Prüfung, ob die Kiste in die gewünschte Richtung bewegt werden kann
	 * 
	 * @param y
	 *            y-Koordinate
	 * @param x
	 *            x-Koordinate
	 * @param direction
	 *            Richtung der gewünschten Bewegung (L, R, U, D)
	 * @return boolean, je nachdem ob Schritt möglich ist
	 */
	public boolean canMoveCrate(int y, int x, char direction) {
		int nX = 0, nY = 0;

		switch (direction) {
		case 'U':
			nY = -1;
			break;
		case 'D':
			nY = +1;
			break;
		case 'L':
			nX = -1;
			break;
		case 'R':
			nX = +1;
			break;
		}

		return (this.floor.isAtPosition(y + nY, x + nX) && this.floor.isFree(y + nY, x
				+ nX))
				|| (this.goal.isAtPosition(y + nY, x + nX) && this.goal.isFree(y + nY, x
						+ nX));

	}
	
}
