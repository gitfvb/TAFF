package gdi1sokoban.gamelogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import gdi1sokoban.core.Highscore;
import gdi1sokoban.core.Level;
import gdi1sokoban.core.saves.LoadGame;
import gdi1sokoban.core.saves.SaveGame;
import gdi1sokoban.exceptions.FileCannotBeFoundException;
import gdi1sokoban.exceptions.InvalidLevelException;
import gdi1sokoban.exceptions.InvalidSaveGame;
import gdi1sokoban.playingfield.Crate;
import gdi1sokoban.playingfield.CrateOnGoal;
import gdi1sokoban.playingfield.Floor;
import gdi1sokoban.playingfield.Goal;
import gdi1sokoban.playingfield.PlayerOnGoal;
import gdi1sokoban.playingfield.Wall;
import gdi1sokoban.exceptions.InvalidMoveException;



public class GameLogic {
	
	char GameArray[][];
	Player player;
	String skinname ="simpsons";                //Standardskin: simpsons
	boolean levelFinished = false;
	StringBuffer cheat = new StringBuffer(256);
	StringBuffer CheatX = new StringBuffer(256);
	StringBuffer CheatY = new StringBuffer(256);
	StringBuffer RedoStorage = new StringBuffer(256);
	boolean lastMoveWasUndo = false;
	private Level level = new Level();
	private Highscore highscore;
	private Crate crate = new Crate();
	private CrateOnGoal crateOnGoal = new CrateOnGoal();
	private Floor floor = new Floor();
	private Goal goal = new Goal();
	private PlayerOnGoal playerOnGoal = new PlayerOnGoal();
	private Wall wall = new Wall();
	double time = 0.0;
	
	/**
	 * Konstruktor fŸr diese Klasse
	 */
	public GameLogic() {
		// Spieler
		this.setPlayer(new Player());
		// Timer
		this.startTimer();
		// Objekte
		this.crate.setLevel(getLevel());
		this.crateOnGoal.setLevel(getLevel());
		this.floor.setLevel(getLevel());
		this.goal.setLevel(getLevel());
		this.playerOnGoal.setLevel(getLevel());
		this.wall.setLevel(getLevel());
		this.player.setLevel(getLevel());
	}
	
	/**
	 * @return the crate
	 */
	public Crate getCrate() {
		return crate;
	}
	/**
	 * @param crate the crate to set
	 */
	public void setCrate(Crate crate) {
		this.crate = crate;
	}
	/**
	 * @return the crateOnGoal
	 */
	public CrateOnGoal getCrateOnGoal() {
		return crateOnGoal;
	}
	/**
	 * @param crateOnGoal the crateOnGoal to set
	 */
	public void setCrateOnGoal(CrateOnGoal crateOnGoal) {
		this.crateOnGoal = crateOnGoal;
	}
	/**
	 * @return the floor
	 */
	public Floor getFloor() {
		return floor;
	}
	/**
	 * @param floor the floor to set
	 */
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	/**
	 * @return the goal
	 */
	public Goal getGoal() {
		return goal;
	}
	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}
	/**
	 * @return the playerOnGoal
	 */
	public PlayerOnGoal getPlayerOnGoal() {
		return playerOnGoal;
	}
	/**
	 * @param playerOnGoal the playerOnGoal to set
	 */
	public void setPlayerOnGoal(PlayerOnGoal playerOnGoal) {
		this.playerOnGoal = playerOnGoal;
	}
	/**
	 * @return the wall
	 */
	public Wall getWall() {
		return wall;
	}
	/**
	 * @param wall the wall to set
	 */
	public void setWall(Wall wall) {
		this.wall = wall;
	}
	/**
	 * @return the highscore
	 */
	public Highscore getHighscore() {
		return highscore;
	}
	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(Highscore highscore) {
		this.highscore = highscore;
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
	
	public StringBuffer getCheat() {
		return cheat;
	}
	public void setCheat(Character c) {
		this.cheat = cheat.append(c);
	}
	public void clearCheat() {                  //Cheatsequenz wird zurückgesetzt, ebenso eventuell vorhandene X- und Y-Werte
        this.cheat = new StringBuffer(256);
		this.CheatX = new StringBuffer(256);
		this.CheatY = new StringBuffer(256);
	}
	
	/**
	 * @return the levelFinished
	 */
	public boolean isLevelFinished() {
		return levelFinished;
	}
	/**
	 * @param levelFinished the levelFinished to set
	 */
	public void setLevelFinished(boolean levelFinished) {
		this.levelFinished = levelFinished;
	}
	
	
	javax.swing.Timer t = new javax.swing.Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            time = time + 1;
        }
     });
	
	public double getTime(){
		return this.time;
	}
	
	public javax.swing.Timer getTimer(){
		return this.t;
	}
	
	private void resetTimer(){
		this.time = 0.0;
	}
	
	public void startTimer(){
		this.t.start();
	}
	
	public void stopTimer(){
		this.t.stop();
	}
	
	
	public String getSkinname() {
		return skinname;
	}
	public void setSkinname(String skinname) {
		this.skinname = skinname;
	}

	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public char[][] getGameArray() {
		return GameArray;
	}

	public void setGameArray(char[][] gameArray) {
		GameArray = gameArray;
	}


	/**
	 * Moves the worker after a keypress to the specified direction
	 * 
	 * @param direction
	 *            Char R L U D (right, left, up, down)
	 *            
	 * @return returns "done" if worker could get moved and "error" if not (e.g. next to wall)
	 */
	public String moveWorker(char direction) {		
		
		int Y = getPlayer().getX();
		int X = getPlayer().getY();
		char Worker = GameArray[Y][X];
		int calc_y, calc_x;                       //Anpassung der Koordinaten an die jeweilige Richtung
		if (direction == 'D') {                   // --> Vermeidung redundanten Codes ;-) 
			calc_y = 1;
			calc_x = 0;
		} else if (direction == 'U') {
			calc_y = -1;
			calc_x = 0;
		} else if (direction == 'R') {
			calc_y = 0;
			calc_x = 1;
		} else { // == if (direction == 'L'), Eclipse meldet aber sonst Fehler
			calc_y = 0;
			calc_x = -1;
		}
		
		this.getLevel().setLevelChar(GameArray);
		
		char fieldNextToPlayer = GameArray[Y + calc_y][X + calc_x]; //Speichern des Feldes neben dem Worker (in der angegebenen) Richtung in einer Variable
		
		if (fieldNextToPlayer == '#') { //Player next to wall
			
			//Play Sound
			playSound("wall");
			
			return "error";              // --> return error
		}
		
		if (fieldNextToPlayer == ' ' || fieldNextToPlayer== '.') { // Test ob neben worker in freies Feld || Zielfeld ist
			if (calc_y != 0) this.getPlayer().setX(Y + calc_y); // Verschiebung des Players.
			if (calc_x != 0) this.getPlayer().setY(X + calc_x);
			
			if (fieldNextToPlayer == ' ') GameArray[Y + calc_y][X + calc_x] = '@'; //Wenn fieldNextToPlayer gleich leeres Feld, wird der Player als @ draufgesetzt
			else GameArray[Y + calc_y][X + calc_x] = '+'; //Analog, nur Zielfeld zu +
			if (Worker == '@') GameArray[Y][X] = ' '; //Je nachdem ob Worker auf einem freien Feld oder auf einem Zielfeld stand, wird das Zeichen an seine ehemalige Postition gesetzt 
			else GameArray[Y][X] = '.';               //@ -> ' ' ; + -> .
			
			player.setSteps(direction);               //Gibt die Richtung in den "Schrittzähler" ein
			return "done";
		}

		if (fieldNextToPlayer == '$' || fieldNextToPlayer == '*') { //Bedingung: Neben dem Player ist eine Box (entweder auf Zielfeld oder auf freiem Feld)
			
			char fieldNextToBox = GameArray[Y + calc_y + calc_y][X + calc_x + calc_x];
			if (fieldNextToBox == ' ' || fieldNextToBox == '.') {
				if (calc_y != 0) this.getPlayer().setX(Y + calc_y); // Verschiebung des Players.
				if (calc_x != 0) this.getPlayer().setY(X + calc_x);
				
				if (fieldNextToBox == ' ') GameArray[Y + calc_y + calc_y][X + calc_x + calc_x] = '$'; //Test ob Feld neben der Kiste ein freies Feld (' ') ist
				else GameArray[Y + calc_y + calc_y][X + calc_x + calc_x] = '*';                       //oder ob es ein Zielfeld ist. Dementsprechend Anpassung der Kistendarstellung.
				
				
				if (fieldNextToPlayer == '$') GameArray[Y + calc_y][X + calc_x] = '@'; //Anpassung der Workerdarstellung. Wenn die Kiste auf einem Zielfeld stand, steht der Worker nun ebenfalls auf einem Zielfeld.
				else GameArray[Y + calc_y][X + calc_x] = '+';
				
				if (Worker == '@') GameArray[Y][X] = ' '; //Je nachdem ob Worker auf einem freien Feld oder auf einem Zielfeld stand, wird das Zeichen an seine ehemalige Postition gesetzt 
				else GameArray[Y][X] = '.';               //@ -> ' ' ; + -> .
				

				player.setSteps(direction);
				checkIfFinished();                 //finish Prüfung
				deadlock(GameArray);               //deadlock Prüfung
				
				//Play Sound
				playSound("walk");
				
				return "done";
			}
			else {
				playSound("wall");
				return "error"; // Feld neben der Box ist entweder
											// Wand oder Kiste 
			}
		}

		else
			return "this should never happen"; //else Zweig der bei korrekten Vorfunktionen nicht ausgeführt werden dürfte ..

	}
	
	/**
	 * 
	 * ZurŸcksetzen eines Levels
	 * 
	 */
	public void resetLevel(){
		this.getLevel().reset();
		this.setGameArray(this.getLevel().getLevelChar());
		player.searchPlayer(this.getLevel().getLevelChar());
		this.getPlayer().clearSteps();
	}
	
	/**
	 * testet, ob alle Kisten auf die Zielpositionen geschoben wurden
	 * 
	 * @return false wenn level noch nicht beendet, true wenn gewonnen
	 */
	public boolean checkIfFinished() {
		char[][] a = this.getGameArray();
		char searchkey = '$';
		char searchkey2 = '.';

		for (int m = a.length - 1; m >= 0; m--) {
			for (int n = a[0].length - 1; n >= 0; n--) {
				if (a[m][n] == searchkey || a[m][n] == searchkey2) {
					//wenn noch kisten oder zielfelder vorhanden
					//System.out.println("spiel noch nicht erfolgreich beendet");
					return false;

				}
			}
		}
		
		//wenn keine Kisten oder Zielfelder vorhanden -> gewonnen
		System.out.println("you win");
		//Play Sound
		playSound("finish");
		
		// Highscore-Eintragen
		this.getHighscore().addEntry(this.getPlayer().getPlayerName(), this.getLevel().getLevelNumber(), this.getPlayer().getStepCount(), (int) this.getTime());
		
		// gelšst-Flag fŸr Window setzen (dieses ruft den nŠchsten Level auf und setzt es wieder zurŸck)
		this.setLevelFinished(true);
		
		return true;
		
	}
	
	/**
	 * 
	 * Einladen eines neuen Levels
	 * 
	 */
	public void loadLevel() {
		this.resetTimer();
		this.setGameArray(this.getLevel().getLevelChar());
		player.searchPlayer(this.getLevel().getLevelChar());
		this.getPlayer().clearSteps();
	}
	
	/**
	 * 
	 * Neu-Laden eines Levels
	 * 
	 * @param idx
	 *            Level-Nummer als Integer-Wert
	 * @throws InvalidLevelException 
	 * @throws FileCannotBeFoundException 
	 * 
	 */
	public void loadLevel(int idx) throws InvalidLevelException, FileCannotBeFoundException {
		this.getLevel().setLevelNumber(idx);
		this.loadLevel();
	}
	
	/**
	 * 
	 * Level-Laden
	 * 
	 * @param file
	 * @throws InvalidLevelException
	 */
	public void loadLevel(File file) throws InvalidLevelException {
		this.getLevel().loadLevel(file); 
		this.loadLevel();
		
		// Highscore
		try {
			this.setHighscore(new Highscore(new File(file.getParentFile().getCanonicalPath() + "/highscore.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getHighscore().loadHighscore();
	}
		
	/**
	 * 
	 * Laden eines neuen Level-Verzeichnisses -> alles wird zurŸckgesetzt
	 * @throws InvalidLevelException 
	 * 
	 */
	public void setLevelDir(File levelDir) {
		
		// Level
		try {
			this.getLevel().setLevelDir(levelDir);
		} catch (InvalidLevelException e) {
			e.printStackTrace();
		}
		
		// Highscore
//		try {
			this.setHighscore(new Highscore(new File(levelDir, "highscore.txt")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		this.getHighscore().loadHighscore();
	}
	
	/**
	 * 
	 * NŠchsten Level reinladen
	 * 
	 * @throws InvalidLevelException Level wird vorerst noch geprŸft
	 * @throws FileCannotBeFoundException 
	 */
	public void setNextLevel() throws InvalidLevelException, FileCannotBeFoundException {
		this.getLevel().startNextLevel();
		this.loadLevel();
	}
	
	public void playSound(String filename) {
		filename = ClassLoader.getSystemClassLoader().getResource("sounds").getPath() + "/" + skinname + "/" + filename + ".wav";
		WavePlayer.playThemeSound(filename);
	}
	
	/**
	 * Funktion für die einfache Maussteuerung. 
	 * Nimmt den X & Y Wert des Mausklicks entgegen und ruft moveWorker(direction) auf, 
	 *  wenn der Mausklick direkt neben dem Worker in ein freies Feld gesetzt ist.
	 * @param x
	 * @param y
	 */
	public void moveWorkerSequence(String sequence) throws InvalidMoveException {
		for (int i = 0; i < sequence.length(); i++) {
			if (moveWorker(sequence.charAt(i)) == "error"){
				throw new InvalidMoveException("Diese Bewegung kann nicht ausgeführt werden.");
			}
						
		}
	}
	
	/**
	 * Funktion für die einfache Maussteuerung.
	 * Aus dem Framework werden bei einem Linksklick die x und y Koordinaten übergeben,
	 *   nach Prüfung ob ein Feld NEBEN dem Worker angeklickt wurde und ob es ein FREIES
	 *   Feld (leeres Feld oder Zielfeld) ist, wird der Worker dorthinbewegt.
	 * @param x
	 * @param y
	 *  Koordinaten
	 */
	public void clickEvent(int x, int y) {
		int PlayerX = getPlayer().getY();
		int PlayerY = getPlayer().getX();
		if ((x == PlayerX +1) && (y == PlayerY)) {
			moveWorker('R');
		}
		else if ((x == PlayerX -1) && (y == PlayerY)) {
			moveWorker('L');
		}
		else if ((x == PlayerX) && (y == PlayerY+1)) {
			moveWorker('D');
		}
		else if ((x == PlayerX) && (y == PlayerY-1)) {
			moveWorker('U');
		}
	}
	
	/**
	 * 
	 * Speichern eines Spielstandes
	 * 
	 * @param file Datei zum Abspeichern
	 * @return boolean, ob Speichervorgang erfolgreich war
	 */
	public boolean saveGame(File file) {
		// Problem -> Double und int
		SaveGame s = new SaveGame(this.getPlayer().getPlayerName(), this.getLevel().getLevelNumber(), this.getPlayer().getSteps().toString(), (int) this.getTime());
		s.save(file);
		return true;		
	}
	
	/**
	 * 
	 * Laden eines Spielstandes
	 * 
	 * @param file Datei zum reinladen
	 * @return boolean, ob Ladevorgang erfolgreich war
	 * @throws FileCannotBeFoundException 
	 */
	public boolean loadGame(File file) throws FileCannotBeFoundException {
		
		LoadGame l = new LoadGame();
		
		// Laden des Spieles
		try {
			l.loadGame(file);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidSaveGame e) {
			e.printStackTrace();
		}
		
		// Setzen des Level-Verzeichnisses
		try {
			this.getLevel().setLevelDir(new File(file.getParent()));
		} catch (InvalidLevelException e1) {
			e1.printStackTrace();
		}
		
		// Reinladen der SpielstŠnde
		try {
			this.loadLevel(l.getLevelnr());
		} catch (InvalidLevelException e) {
			e.printStackTrace();
		}
		
		this.getPlayer().setPlayerName(l.getPlayername());
		
		try {
			this.moveWorkerSequence(l.getMoves());
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		
		this.time = l.getTime();
		
		return true;
		
	}
	
	/**
	 *  Überprüft ob die eingegebene Tastenfolge ein Cheat nach dem Format "osjx,y#" ist.
	 *  Wenn ja, wird überprüft ob das Feld frei ist (isvalidCoordinate()) 
	 *  und ggf. die Teleport-Funktion aufgerufen.
	 *  
	 * @param key
	 *  Die im Window gedrückte Taste
	 */	
	public void cheat(Character key) {
		StringBuffer cheat = getCheat();
		if (key == 'o') {                                //wenn o gedrückt wird
			clearCheat();                                //CheatSequenz resetten
			setCheat(key);                               //und neu anlegen
			return;
		}
		if (key == 's' && cheat.toString().equals("o")){ //wenn s nach o gedrückt wird
			setCheat(key);
			return;
		}
		else if (key == 'j' && cheat.toString().equals("os")){ //wenn j nach o und s gedrückt wird
			setCheat(key);
			return;
		}
		else if ((key == ',') &&                      //Wenn Komma gedrückt wird, danach mind. eine Zahl
				(cheat.toString().equals("osj")) &&   //eingegeben wurde und am Anfang die Tastenfolge osj
				CheatX.length() > 0) {                //wird nur ausgeführt, wenn schon ein Wert für X vorhanden ist.
				setCheat(key);
				return;
		}
		else if (Character.isDigit(key) &&                 //Prüfung ob eine Zahl eingegeben wurde
				(cheat.length() >= 3) &&                            //ob überhaupt schon mind. osj gedrückt wurde
				(cheat.toString().substring(0,3).equals("osj"))) {  //obere Prüfung wichtig, da substring sonst eine Exception auslöst.
			if (whichPosition() == 'x') this.CheatX = CheatX.append(key);       //Überprüfung ob eingegebene Zahl X oder Y zugeordnet werden soll
			else if (whichPosition() == 'y') this.CheatY = CheatY.append(key);
			return;
		}
		else if ((key == '#') &&            //Raute taste gedrückt
				(CheatX.length() > 0) &&    //Sowohl für den X als auch für den Y-Wert
				(CheatY.length() > 0) &&    //sind Werte vorhanden
				(isValidCoordinate())) {    //dann prüfe ob hierhin teleportiert werden kann
			teleportWorker();               //ja -> führe Teleport aus.  
		}
		clearCheat(); //Wenn die Tastenfolge nicht stimmt, wird die Cheatsequenz resettet
	}
	
	
	/**
	 * Überprüft ob die eingegebenen X und Y Werte im Array überhaupt vorkommen und
	 *  frei (bzw. ein Zielfeld) sind.
	 *  
	 * @return gibt true zurück wenn ein Teleport zu dieser Koordinate stattfinden kann,
	 *         ansonsten false.
	 */
	public boolean isValidCoordinate() {
		int x = Integer.valueOf(CheatX.toString());
		int y = Integer.valueOf(CheatY.toString());
		if (getCheat().toString().equals("osj,") &&
				(this.floor.isAtPosition(y, x) && this.floor.isFree(y, x))      //ist das Feld ein freies Feld (Floor.isFree)
				|| (this.goal.isAtPosition(y, x) && this.goal.isFree(y, x))) {  //ist das Feld ein freies Zielfeld (Goal.isFree) 
			return true;

		}
		return false;
	}
	
	/**
	 * Es wird ein Teleport des Workers durchgeführt, d.h. der Player auf die im Cheat
	 *  eingegebenen Koordinaten gesetzt.
	 *  Prüfungen auf Korrektheit entfallen, da die Funktion nur aufgerufen wird, wenn
	 *  eine Teleportation möglich ist.
	 */
	public void teleportWorker() {
		int teleportX = Integer.valueOf(CheatX.toString());
		int teleportY = Integer.valueOf(CheatY.toString());
		int Y = getPlayer().getX();
		int X = getPlayer().getY();
		
		char Worker = GameArray[Y][X];                         //Worker Position
		char target = GameArray[teleportY][teleportX];         //Teleportationsfeld   

		if (Worker == '@') GameArray[Y][X] = ' '; //Je nachdem ob Worker auf einem freien Feld oder auf einem Zielfeld stand, wird das Zeichen an seine ehemalige Postition gesetzt 
		else GameArray[Y][X] = '.';               //@ -> ' ' ; + -> .
		
		if (target == ' ') GameArray[teleportY][teleportX] = '@';  //Anpassung der Workerdarstellung. Wenn die Kiste auf einem Zielfeld stand, 
		else GameArray[teleportY][teleportX] = '+';                //steht der Worker nun ebenfalls auf einem Zielfeld usw.
		
		this.getPlayer().setX(teleportY);
		this.getPlayer().setY(teleportX);
		
		//Play Sound
		playSound("cheat");
		
	}
	
	/**
	 * Die Funktion weist cheat() an, die Zahleneingaben für den Cheat richtig handzuhaben.
	 *  D.h. sie gibt zurück, ob der Wert für die X-Achse oder für die Y-Achse bestimmt ist.
	 *  Alles was vor der Eingabe des Kommas (",") eingegeben wurde gehört zum X-Wert, 
	 *  alles was danach kommt zum Y-Wert.
	 *  Nicht betrachtet wird, ob es dieses Feld überhaupt im Array gibt, dafür ist die 
	 *   "isvalidCoordinate()" Funktion zuständig, die vor der Teleportation aufgerufen wird.
	 *    
	 * @return char 'x' oder 'y', je nachdem wohin die Zahl gehört.
	 */
	public Character whichPosition() {
		if (getCheat().toString().equals("osj")) return 'x';
		else if (getCheat().toString().equals("osj,")) return 'y';
		else {
			System.err.println("This should never happen!");
			return null;
		}
	}

	/**
	 * deadlock:
	 * Erkennt, ob es sich um einen Deadlock handelt.
	 * Zwei Fälle werden betrachtet:
	 * 
	 *   * mindestens eine Kiste wurde in eine Ecke geschoben, d.h. die Kiste grenzt nun an drei Wänden an.
	 *   * vier Objekte bilden ein Quadrat (z.B. vier Kisten oder drei Kisten und eine Wand) an einer beliebigen Stelle auf dem Spielfeld.
	 * 
	 * @return true -> wenn deadlock , false -> wenn noch spielbar
	 * @param char[][] area
	 * 
	 */
	public boolean deadlock(char[][] area) {
		char searchkey = '$';

		//sucht kiste im Feld
		for (int m = area.length - 1; m >= 0; m--) {
			for (int n = area[0].length - 1; n >= 0; n--) {
				if (area[m][n] == searchkey) {

					// #$
					// ##
			
					if ((area[m + 1][n] == '#' || area[m + 1][n] == '$' || area[m + 1][n] == '*' )
						&& (area[m][n - 1] == '#' || area[m][n - 1] == '$'|| area[m][n - 1] == '*')
						&& (area[m + 1][n - 1] == '#'|| area[m + 1][n - 1] == '$'|| area[m + 1][n - 1] == '*')) {
						System.out.println("deadlock du loser!!!");
						playSound("stuck");
						return true;
						
						
					}
					else
					// $#
					// ##
					if ((area[m + 1][n] == '#' || area[m + 1][n] == '$'|| area[m + 1][n] == '*')  
						&&(area[m][n + 1] == '#' || area[m][n + 1] == '$'|| area[m][n + 1] == '*')
						&& (area[m + 1][n + 1] == '#'||area[m + 1][n + 1] == '$'||area[m + 1][n + 1] == '*')) {
						System.out.println("deadlock du loser!!!");
						playSound("stuck");
						return true;
					}
					// ##
					// $#
					else
					if ((area[m - 1][n] == '#' || area[m - 1][n] == '$' || area[m - 1][n] == '*')
						&& (area[m][n + 1] == '#' || area[m][n + 1] == '$'|| area[m][n + 1] == '*')
						&& (area[m - 1][n + 1] == '#'||area[m - 1][n + 1] == '$'||area[m - 1][n + 1] == '*')) {
						System.out.println("deadlock du loser!!!");
						playSound("stuck");
						return true;
					}
								
					// ##
					// #$
					else
						
					if ((area[m - 1][n] == '#'||area[m - 1][n] == '$')
						&& (area[m][n - 1] == '#'||area[m][n - 1] == '$')
						&& (area[m - 1][n - 1] == '#'||area[m - 1][n - 1] == '$')) {
						System.out.println("deadlock du loser!!!");
						playSound("stuck");
						return true;
					}
					

					}
				}}
				
		return false;
		
		}

	
	/**
	 * @return the redoStorage
	 */
	public StringBuffer getRedoStorage() {
		return RedoStorage;
	}
	/**
	 * @param redoStorage the redoStorage to set
	 */
	public void setRedoStorage(StringBuffer redoStorage) {
		RedoStorage = redoStorage;
	}

	/**
	 * 
	 * ZurŸcksetzen des letzten Zuges
	 * @throws InvalidMoveException 
	 * 
	 */
	public void undo() throws InvalidMoveException {
		// Reinladen der Bewegungen
		String moves = this.getPlayer().getSteps().toString();
		
		// FŸllen des Redo-Speichers
		if (this.getRedoStorage().toString().indexOf(this.getPlayer().getSteps().toString()) == -1) {
			this.setRedoStorage(this.getPlayer().getSteps());
		}
		
		// Level wird neu reingeladen
		this.resetLevel();
		
		// Bewegungen werden erneut ausgefŸhrt 
		this.moveWorkerSequence(moves.substring(0, moves.length()-1));
		
	}
	
	
	/**
	 * 
	 * Wiederholen des letzten Zuges
	 * @throws InvalidMoveException 
	 * 
	 */
	public void redo() throws InvalidMoveException {
		// Wiederholen der ZŸge
		if (this.getRedoStorage().toString().length() > 0) {
			if (this.getRedoStorage().toString().indexOf(
					this.getPlayer().getSteps().toString()) == 0) {
				String diff = this.getRedoStorage().substring(
						this.getPlayer().getStepCount());
				this.moveWorkerSequence((diff.substring(0,1)));
			}
		}
	}
	
}
	  
