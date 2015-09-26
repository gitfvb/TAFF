package gdi1sokoban.core.saves;

public class Saves {

	private int Levelnr;
	private String moves;
	private String playername;
	private int time;
	protected String separator = "\t";

	/**
	 * 
	 * Spielernamen auslesen
	 * 
	 * @return Spielername
	 */
	public String getPlayername() {
		return playername;
	}

	/**
	 * 
	 * Spielername festlegen
	 * 
	 * @param playername
	 */
	public void setPlayername(String playername) {
		this.playername = playername;
	}

	
	
	// /Time
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	
	
	
	// /Levelnr
	public int getLevelnr() {
		return Levelnr;
	}

	public void setLevelnr(int levelnr) {
		Levelnr = levelnr;
	}

	
	
	
	// /moves
	public String getMoves() {
		return moves;
	}

	public void setMoves(String moves) {
		this.moves = moves;
	}

	
	
//	public void saveGameToFile() {
//
//	}
//
//	public void loadGameFromFile() {
//
//		// test sets
//		setMoves("DUULDURDDDLURRLLRLRLRLURDDUUUULDR");
//		setPlayername("test");
//		setLevelnr(4);
//		setTime("13:43");
//	}
//
//	public void loadSaveGame() {
//		loadGameFromFile();
//
//		String moves = getMoves();
//		GameLogic g = new GameLogic();
//		for (int i = 0; i < moves.length(); i++) {
//			g.moveWorker(moves.charAt(i));
//		}
//	}
	
}
