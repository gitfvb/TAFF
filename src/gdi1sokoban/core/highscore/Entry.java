package gdi1sokoban.core.highscore;

/**
 * Class to store an complete entry
 * 
 * @author Florian Friedrichs - ff1010
 * 
 */
public class Entry {
	
	// Attributes
	private String levelNumber;
	private String playerName;
	private String neededSteps;
	private String neededTime;

	/**
	 * Constructor to fill a attributes of this class
	 * 
	 * @param levelNumber
	 *            Level-Nummer
	 * @param playerName
	 *            Spielername
	 * @param neededSteps
	 *            gebrauchte Anzahl an Schritten
	 * @param neededTime
	 *            gebrauchte Zeit
	 */
	public Entry(String levelNumber, String playerName, String neededSteps,
			String neededTime) {
		this.levelNumber = levelNumber;
		this.playerName = playerName;
		this.neededSteps = neededSteps;
		this.neededTime = neededTime;
	}
	
	/**
	 * Constructor to fill a attributes of this class
	 * 
	 * @param levelNumber
	 *            Level-Nummer
	 * @param playerName
	 *            Spielername
	 * @param neededSteps
	 *            gebrauchte Anzahl an Schritten
	 * @param neededTime
	 *            gebrauchte Zeit
	 */	
	public Entry(String levelNumber, String playerName, String neededSteps) {
		this.levelNumber = levelNumber;
		this.playerName = playerName;
		this.neededSteps = neededSteps;
		this.neededTime = "";
	}	

	/**
	 * Overwriting the method toString()
	 * 
	 * @return string build of all attributes like this template, separated by
	 *         tabs:<br>
	 *         levelNumber playerName neededSteps neededTime<br>
	 * 
	 */
	public String toString() {
		StringBuffer myBuffer = new StringBuffer(256);
		myBuffer.append(levelNumber).append("\t").append(playerName).append(
				"\t").append(neededSteps.toString());
		if (this.neededTime != "")
			myBuffer.append("\t").append(neededTime);
		return myBuffer.toString();
	}

	/**
	 * @return the levelNumber
	 */
	public String getLevelNumber() {
		return levelNumber;
	}

	/**
	 * @param levelNumber the levelNumber to set
	 */
	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the neededSteps
	 */
	public String getNeededSteps() {
		return neededSteps;
	}

	/**
	 * @param neededSteps the neededSteps to set
	 */
	public void setNeededSteps(String neededSteps) {
		this.neededSteps = neededSteps;
	}

	/**
	 * @return the neededTime
	 */
	public String getNeededTime() {
		return neededTime;
	}

	/**
	 * @param neededTime the neededTime to set
	 */
	public void setNeededTime(String neededTime) {
		this.neededTime = neededTime;
	}


}
