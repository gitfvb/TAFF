package gdi1sokoban.gamelogic;

import gdi1sokoban.core.Level;
import gdi1sokoban.core.StringExtended;;
/**
 * 
 * @author TAFF
 *
 */
public class Player extends Level {
	
	int X,Y;
	String name = "Default";
	StringBuffer steps = new StringBuffer(256);
	private Level level = new Level();
	private char sign = '.';
	
	public Player() {		
	}
	
	/**
	 * 
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
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
	
	public void clearSteps() {
		this.steps = new StringBuffer(256);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX() {
		return X;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		X = x;
	}

	/**
	 * 
	 * @return
	 */
	public int getY() {
		return Y;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		Y = y;
	}
	
	/**
	 * returns all regular steps
	 * @example RLRRLDDU
	 * @return StringBuffer with Steps
	 */
	public StringBuffer getSteps() {
		return steps;
	}
	
	/**
	 * appends a new step to steps
	 * @param c
	 *  R U L or D
	 */
	public void setSteps(Character c) {
		this.steps = steps.append(c);
		System.out.println(steps.toString() + ":" + getStepCount());
	}
	
	/**
	 * returns the number of regular steps
	 * 
	 * @return steps
	 */
	public int getStepCount() {
		return steps.length();
	}
	
	
	
	/**
	 * searchPlayer gibt die Spielerposition auf dem Spielfeld an
	 *  @param a (char[][]) spielfeld
	 * 
	 */
	public void searchPlayer(char[][] a) {
	
		//Spieler auf freiem Feld
		char searchkey = '@';
		//oder Spieler auf Zielfeld
		char searchkey2 = '+';

		for (int m = a.length-1; m >= 0; m--) {
		    for (int n = a[0].length-1; n >= 0; n--) {
				if (a[m][n] == searchkey || a[m][n] == searchkey2) {
					
					this.setX(m);
					this.setY(n);

				}

			}

		}
	
		
		
	}
	 

	/**
	 * 
	 * Suchen von Playern innerhalb eines Levels. Dient der Validierung eines
	 * Levels, da es nur einen Spieler geben soll
	 * 
	 * @return Anzahl der Player-Figuren im Feld
	 */
	public int getPlayerCount() {
		return StringExtended.countPattern(this.getLevel().currentLevelToString(), this.sign) + StringExtended.countPattern(this.getLevel().currentLevelToString(), '+');
	}

	/**
	 * 
	 * Einstellen eines Spielernamens
	 * 
	 */
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * Auslesen der Spielernamens
	 * 
	 * @return der Spielername
	 */
	public String getPlayerName() {
		return this.name;
	}
	
}


