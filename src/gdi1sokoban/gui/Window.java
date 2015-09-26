package gdi1sokoban.gui;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gdi1sokoban.exceptions.FileCannotBeFoundException;
import gdi1sokoban.exceptions.InternalFailureException;
import gdi1sokoban.exceptions.InvalidLevelException;
import gdi1sokoban.exceptions.InvalidMoveException;
import gdi1sokoban.exceptions.ParameterOutOfRangeException;
import gdi1sokoban.gamelogic.GameLogic;
import gdi1sokoban.template.ui.GamePanel;

/**
 * Window: Basis der GUI (Verwaltung von Events und Fenster Lock 'n Feel)
 * @author TAFF
 * @date: 27.2.2009
 *
 */
@SuppressWarnings("serial")
public class Window extends gdi1sokoban.template.ui.GameWindow implements ActionListener {
	/*============================================================================*/
	
	// GameLogic
	private GameLogic logic;
	
	// Statubar
	@SuppressWarnings("unused")
	private StatusBar statusBar;
	
	/*============================================================================*/
	
	/**
	 * Konstruktor
	 * Ruft den Konstruktor der Oberklasse GameWindow auf.
	 * Ansonsten wird hier Gamelogik instaziert und gespeichert.
	 * Weiter wird die Statusbar erzeugt und der erste Level geladen
	 * @param windowTitle
	 * @throws InternalFailureException
	 */
	public Window(String windowTitle) throws InternalFailureException {
		super(windowTitle);
		this.setLogic(new GameLogic());
		createMenuBar();
		createStatusbar();
		this.loadLevel(1);
	}

	/**
	 * Setzt die �bergebene Gamelogic in das Klassenattribut
	 * @param logic
	 */
	public void setLogic(GameLogic logic) {
		this.logic = logic;
			String path = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
			this.logic.setLevelDir(new File(path));
	}

	/**
	 * Gibt die im Klassentraibut logic gespeicherte GameLogic zur�ck
	 * 
	 * @return GameLogic "logic"
	 */
	public GameLogic getLogic() {
		return logic;
	}

	
	/**
	 * Erzeugt das eigentlich Spielfeld
	 * 
	 * @return GamePanel "panel"
	 */
	@Override
	protected GamePanel createGamePanel() {
		Panel panel = new Panel(this);
		add(panel);
		return panel;
	}

	/**
	 * Erzeugt die Men�leiste f�r das Spielfenster
	 */
	protected void createMenuBar() {
		
		// Menubar erstellen
		JMenuBar menuBar = new JMenuBar();
		
		// Hauptmen�eintr�ge und Submen�punkte eintragen und Eventsanbinden
		
		//Datei
		JMenu datei = new JMenu("Datei");
		JMenuItem loadLevel = new JMenuItem("Level laden");
		loadLevel.addActionListener(this);
		JMenuItem resetLevel = new JMenuItem("Level neu starten");
		resetLevel.addActionListener(this);
		JMenuItem load = new JMenuItem("Spielstand laden");
		load.addActionListener(this);
		JMenuItem save = new JMenuItem("Spielstand speichern");
		save.addActionListener(this);
		JMenuItem quit = new JMenuItem("Spiel beenden");
		quit.addActionListener(this);
		
		//Highscore
		JMenu highscore = new JMenu("Highscore");
		JMenuItem playerName = new JMenuItem("Spielername");
		playerName.addActionListener(this);
		JMenuItem topten = new JMenuItem("TopTen");
		topten.addActionListener(this);
		JMenuItem deltopten = new JMenuItem("Highscore l�schen");
		deltopten.addActionListener(this);
		
		//Skins
		JMenu optionen = new JMenu("Skins");
		JMenuItem skin1 = new JMenuItem("Original");
		skin1.addActionListener(this);
		JMenuItem skin2 = new JMenuItem("Sendung mit der Maus");
		skin2.addActionListener(this);
		JMenuItem skin3 = new JMenuItem("Die Simpsons");
		skin3.addActionListener(this);
		JMenuItem skin4 = new JMenuItem("American Dad");
		skin4.addActionListener(this);
		
		//Hilfe
		JMenu hilfe = new JMenu("Hilfe");
		JMenuItem rules = new JMenuItem("Regeln");
		rules.addActionListener(this);
		JMenuItem about = new JMenuItem("About/Credits");
		about.addActionListener(this);
		
		//Submen�punkte an Mainmenupunkte h�ngen
		datei.add(loadLevel);
		datei.add(resetLevel);
		datei.add(load);
		datei.add(save);
		datei.add(quit);
		highscore.add(playerName);
		highscore.add(topten);
		highscore.add(deltopten);
		optionen.add(skin1);
		optionen.add(skin2);
		optionen.add(skin3);
		optionen.add(skin4);
		hilfe.add(rules);
		hilfe.add(about);
		
		//Hauptmen�punkte an MenuBar h�ngen
		menuBar.add(datei);
		menuBar.add(highscore);
		menuBar.add(optionen);
		menuBar.add(hilfe);
		
		//Et Voila: MenuBar an das Fenster h�ngen. ;-)
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		
	}

	/**
	 * Hauptklasse die die Klickevents des Hauuptmen�s behandelt
	 */
	public void actionPerformed(ActionEvent object) {
		
		//Datei
		if(object.getActionCommand().equals("Level laden")){
			this.loadDialog();
		}else if(object.getActionCommand().equals("Level neu starten")){
			keyNewGamePressed();
		}else if(object.getActionCommand().equals("Spielstand laden")){
			this.loadSaveGameDialog();
		}else if(object.getActionCommand().equals("Spielstand speichern")){
			this.saveDialog();
		}else if(object.getActionCommand().equals("Spiel beenden")){
			this.quitGame();
		}else if(object.getActionCommand().equals("Spielername")){
			this.getPlayerName();
			
		//Highscore
		}else if(object.getActionCommand().equals("TopTen")){
			this.createHighscoreWindow();
		}else if(object.getActionCommand().equals("Highscore l�schen")){
			deleteHighscore();
		}else if(object.getActionCommand().equals("Original")){
			((Panel) this.getGamePanel()).loadSkin("Original");
		}else if(object.getActionCommand().equals("Sendung mit der Maus")){
			((Panel) this.getGamePanel()).loadSkin("Sendung mit der Maus");
		}else if(object.getActionCommand().equals("Die Simpsons")){
			((Panel) this.getGamePanel()).loadSkin("Die Simpsons");
		}else if(object.getActionCommand().equals("American Dad")){
			((Panel) this.getGamePanel()).loadSkin("American Dad");
			
		//Hilfe
		}else if(object.getActionCommand().equals("Regeln")){
			this.createRulesWindow();
		}else if(object.getActionCommand().equals("About/Credits")){
			this.createAboutWindow();
		}
   }
	
	/**
	 * Erstellt ein Objekt vom Typ Statusbar f�gt dieses an das Fenster
	 */
	protected void createStatusbar() {
		StatusBar statusBar = new StatusBar(this);
		add(statusBar, java.awt.BorderLayout.SOUTH);
		this.statusBar = statusBar;
	}
	
	/**
	 * L�schen des Highscores
	 */
	private void deleteHighscore() {
		this.pauseTimer(); // Timer anhalten
		
		int eingabe = JOptionPane.showConfirmDialog(null,
                "M�chten Sie den Highscore l�schen?",
                "Highscore l�schen",
                JOptionPane.YES_NO_OPTION);
		if(eingabe == 0){
			this.getLogic().getHighscore().clear(); // Highscore l�schen
		}
		this.startTimer(); //Timer l�uft weiter
	}

	/**
	 * Spielernamen �ndern: Eingabeaufforderung
	 */
	private void getPlayerName() {
		this.pauseTimer(); // Timer anhalten
		String eingabe = JOptionPane.showInputDialog(null,"Geben Sie bitte Ihren Namen ein",
                "Spielername",
                JOptionPane.PLAIN_MESSAGE);
		this.getLogic().getPlayer().setPlayerName(eingabe);//Setzen des neuen Spielernamens
		this.startTimer(); // Timer weiterlaufen lassen
	}

	/**
	 * Erstellt das Aboutfenster
	 */
	private void createAboutWindow(){
		this.pauseTimer();
		JFrame about = new JFrame("About");
		about.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		about.setBounds(30, 40, 300, 100);
		
		String credits = "TAFF - Toni, Andre, Florian und Florian";
		
		Label txt = new Label(credits);
		about.add(txt);
		
		about.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
               startTimer();
            }
        });
		
		about.setResizable(false);
		about.setVisible(true);
	}
	
	/**
	 * Erstellt das Regelfenster
	 */
	private void createRulesWindow(){
		this.pauseTimer();//Timer anhelten
		JFrame rules = new JFrame("Regeln");
		rules.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		rules.setBounds(30, 40, 400, 250);
		
		String rule = "Sokoban wird auf einem, in Quadrate aufgeteiltem Feld gespielt.\n Der Spieler steuert eine Figur in der Gr��e eines Feldes,  die\n wahlweise einen Zug nach oben, unten, links oder  rechts\n bewegt werden kann. Jedes Feld kann wahlweise als begehbares\n Feld, begehbares Zielfeld oder unbegehbare Wand festgelegt werden.\n Felder k�nnen au�erdem unbegehbare Kisten enthalten. Die Figur\n kann genau eine Kiste bewegen, indem sie diese in Laufrichtung\n vor sich herschiebt, unter der Bedingung, da� das Feld hinter der\n Kiste frei ist. Die Figur wird deshalb auch als Pusher bezeichnet.\n Ziel des Spiels ist es alle Kisten auf die angegebenen Zielfelder\n zu verschieben.";
		
		JTextArea txt = new JTextArea(rule);
		
		rules.add(new JScrollPane(txt));
		
		//Wenn Fenster geschlossen wird, dann soll Timer wieder gestartet werden
		rules.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
               startTimer();
            }
        });
		
		rules.setResizable(false);
		rules.setVisible(true);
	}
	
	/**
	 * Erstellt das Highscorefenster einblenden
	 */
	private void createHighscoreWindow(){
		this.pauseTimer(); //Timer anhalten
		
		JFrame highscore = new JFrame("Highscore");
		highscore.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		highscore.setBounds(30, 40, 400, 250);
		
		//Highscore vom Highscoremanager holen
		String[][] highscoreList = this.getLogic().getHighscore().getSortedHighscoreList();
		
		StringBuffer list = new StringBuffer();
		
		//P�rfen ob Highscore vorhanden
		if (highscoreList != null){
			//Highscore formatieren
			for(int lauf=1; lauf<highscoreList.length; lauf++){
				list.append(highscoreList[0][0]+"\n");
				
				for(int lauf2=0; lauf2<highscoreList[lauf].length; lauf2++){
					if(highscoreList[lauf][lauf2]!=null)
							list.append(highscoreList[lauf][lauf2]+"\n");
//					else
//						list.append("leer\n");
				}
				
				list.append("\n\n\n");
				
		}
		}else{
			list.append("Kein Highscore vorhanden");
		}
		
		JTextArea txt = new JTextArea(list.toString());
		txt.setEditable(false);
		
		highscore.add(new JScrollPane(txt));
		
		highscore.setResizable(false);
		highscore.setVisible(true);
		
		//Wenn Fenster geschlossen wird, soll Timer weiterlaufen
		highscore.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
               startTimer();
            }
        });
	}
	
	/**
	 * Datei -> Level laden: �ffnendialog um einen beliebigen Level laden zu k�nnen
	 */
	private void loadDialog(){
		this.pauseTimer(); // Timer anhalten
			String path = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
			// Dialog erstellen
			JFileChooser chooser = new JFileChooser(path);
			//JFileChooser chooser = new JFileChooser("src/levels");
			
			//Dateifilter erzeugen der nur txt-Dateien anzeigt
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			SokobanFileFilter filter = new SokobanFileFilter("txt", new String[] {"txt" });
			chooser.addChoosableFileFilter(filter);
			
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       this.loadLevel(chooser.getSelectedFile());//Ausgew�hlten Level laden
		    }
	    
	    this.startTimer();//Timer l�uft weiter
	}
	
	/**
	 * Datei -> Spielstand laden: L�dt einen zuvor gespeicherten Spielstand mit Spieler-
	 * position, Spielzeit, Schritte, etc.
	 */
	private void loadSaveGameDialog(){
		this.pauseTimer(); // Timer anhalten
		
		try {
			String path = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
			// Datei �ffen-Dialog erzeugen
			JFileChooser chooser = new JFileChooser(path);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			//Filter f�r Sok-Dateien erstellen
			SokobanFileFilter filter = new SokobanFileFilter("sok", new String[] {"sok" });
			chooser.addChoosableFileFilter(filter);
			
			int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +chooser.getSelectedFile().getName());
		       try {
				this.getLogic().loadGame(chooser.getSelectedFile());
			} catch (FileCannotBeFoundException e) {
				e.printStackTrace();
			} // Ausgew�hlten Level laden
		    }
			this.notifyLevelLoaded(this.getLogic().getLevel().getLevelWidth(), this.getLogic().getLevel().getLevelHeight()); // 
		} catch (ParameterOutOfRangeException e) {
			e.printStackTrace();
		} catch (InternalFailureException e) {
			e.printStackTrace();
		}
		
		
		this.refreshScreen();
	    this.startTimer(); // Timer l�uft weiter
	}
	
	/**
	 * Datei -> Spielstand speichern: Speichert den momentanen Spielstand mit
	 * Levelnummmer, Spielerposition, etc.
	 */
	private void saveDialog(){
		this.pauseTimer();//Timer pausieren
		
		String path;
			path = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
			//Speicherndialogfenster  erstellen
			JFileChooser chooser = new JFileChooser(path);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			//Dateifilter f�r Sok-Dateien erstellen
			SokobanFileFilter filter = new SokobanFileFilter("sok", new String[] {"sok" });
			chooser.addChoosableFileFilter(filter);
			
			
			int returnVal = chooser.showSaveDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	//Pr�fen ob im angebenen Dateinamen Dateiendung angegeben wurde
		    	if(chooser.getSelectedFile().toString().substring(chooser.getSelectedFile().toString().length()-4, chooser.getSelectedFile().toString().length()).equals(".sok"))
		    		this.getLogic().saveGame(new File(chooser.getSelectedFile().toString())); // Dateiendung muss nicht hinzugef�gt werden
		    	else
		    		this.getLogic().saveGame(new File(chooser.getSelectedFile()+".sok")); //Dateiendung hinzuf�gen
		    }

		
	    this.startTimer();//Timer: GO!!!
	}
	
	/**
	 * Pausescreen
	 */
	protected void createPauseScreen(){
		this.pauseTimer(); // Timer anhalten
		@SuppressWarnings("unused")
		int eingabe = JOptionPane.showConfirmDialog(null,
                "Das Spiel wurde pausiert...",
                "Pause",
                JOptionPane.CANCEL_OPTION);
		this.startTimer(); // Timer: Go!
	}
	
	/*============================================================================*/
	
	/**
	 * Beendendialog mit anschlie�endem Beenden der Software
	 */
	private void quitGame() {
		this.pauseTimer(); // Timer pausieren
		
		//Dialogfenster einblenden
		int eingabe = JOptionPane.showConfirmDialog(null,
                "M�chten Sie das Spiel beenden?",
                "Beenden",
                JOptionPane.YES_NO_OPTION);

		if(eingabe == 0){
			this.getLogic().playSound("quit"); // Sound abspielen
			try {
				Thread.sleep(1800); // Thread einfrieren bis der Sound abgespielt wurde
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0); // Software runterfahren
		}
		this.startTimer(); // Timer weiterlaufen lassen
	}

	
	/**
	 * Pr�fen ob Level beendet wurde
	 */
	protected void checkIfFinished() {
		if(this.getLogic().isLevelFinished()){//Pr�fen was GameLogic dazu sagt
			this.getLogic().setLevelFinished(false); // Flag zur�cksetzen
			try {
				this.getLogic().setNextLevel(); // Neuen Level setzen
			} catch (InvalidLevelException e) {
				e.printStackTrace();
				try {
					this.getLogic().setNextLevel(); // Neuen Level setzen
				} catch (InvalidLevelException e1) {
					e1.printStackTrace();
				} catch (FileCannotBeFoundException e1) {
					e1.printStackTrace();
				}
			} catch (FileCannotBeFoundException e) {
				//Wenn es keinen n�chsten Level gibt -> 1. Level laden
				this.loadLevel(1);
			}
			try {
				this.notifyLevelLoaded(this.getLogic().getLevel().getLevelWidth(), this.getLogic().getLevel().getLevelHeight()); //Die GUI �ber �nderungen informieren
			} catch (ParameterOutOfRangeException e) {
				e.printStackTrace();
			} catch (InternalFailureException e) {
				e.printStackTrace();
			}
			this.refreshScreen();
		}
	}

	
	/**
	 * Aktualisiert das Spielfeld
	 */
	protected void refreshScreen(){
		Panel panel = (Panel)this.getGamePanel();
		try {
			panel.redraw();
		} catch (InternalFailureException e) {
			e.printStackTrace();
		}
	}

	/**
	 * L�dt Level anhand von einem File-Objekt
	 * @param idx - Fileobj
	 */
	private void loadLevel(File idx){
		try {
			this.getLogic().loadLevel(idx); // Laden...
		} catch (InvalidLevelException e) {
			e.printStackTrace();
		}
		try {
			this.notifyLevelLoaded(this.getLogic().getLevel().getLevelWidth(), this.getLogic().getLevel().getLevelHeight());//Spielpanel �ber �nnderungen informieren
		} catch (ParameterOutOfRangeException e) {
			e.printStackTrace();
		} catch (InternalFailureException e) {
			e.printStackTrace();
		}
		this.refreshScreen(); // Panel neu zeichnen
	}
	
	/**
	 * L�dt einen neuen Level anhand von dem Levelindex
	 * @param idx - int
	 */
	private void loadLevel(int idx){
		try {
			this.getLogic().loadLevel(idx); // Laden...
		} catch (InvalidLevelException e) {
			e.printStackTrace();
		} catch (FileCannotBeFoundException e) {
			e.printStackTrace();
		}
		try {
			this.notifyLevelLoaded(this.getLogic().getLevel().getLevelWidth(), this.getLogic().getLevel().getLevelHeight()); // Panel �ber �nderungen informieren
		} catch (ParameterOutOfRangeException e) {
			e.printStackTrace();
		} catch (InternalFailureException e) {
			e.printStackTrace();
		}
		this.refreshScreen(); // Panel neu zeichnen
	}
	
	/**
	 * Timer anhalten
	 */
	private void pauseTimer(){
		this.getLogic().stopTimer();
	}
	
	/**
	 * Timer weiterlaufen lassen
	 */
	private void startTimer(){
		this.getLogic().startTimer();
	}
	
	/*============================================================================*/
	//EVENTHANDLER
	
	/**
	 * Methode die aufgerufen wird, wenn Pfeiltaste nach unten gedr�ckt wird
	 */
	@Override
	protected void keyDownPressed() {
		this.getLogic().moveWorker('D');// Spieler bewegen
		this.refreshScreen();// Panel neu zeichnen
		
		this.checkIfFinished();// Pr�fen ob Level beendet
	}

	/**
	 * Methode die aufgreufen wird, wenn Pfeiltaste nach links gedr�ckt wird
	 */
	@Override
	protected void keyLeftPressed() {
		this.getLogic().moveWorker('L');// Spieler bewegen
		this.refreshScreen();// Panel neu zeichnen
		
		this.checkIfFinished();// Pr�fen ob Level beendet
	}
	
	/**
	 * Methode die aufgreufen wird, wenn Pfeiltaste nach oben gedr�ckt wird
	 */
	@Override
	protected void keyUpPressed() {
		this.getLogic().moveWorker('U');// Spieler bewegen
		this.refreshScreen();// Panel neu zeichnen
		
		this.checkIfFinished(); // Pr�fen ob Level beendet
	}
	
	/**
	 * Methode die aufgreufen wird, wenn Pfeiltaste nach rechts gedr�ckt wird
	 */
	@Override
	protected void keyRightPressed() {
		this.getLogic().moveWorker('R'); // Spieler bewegen
		this.refreshScreen(); // Panel neu zeichnen
		
		this.checkIfFinished(); // Pr�fen ob Level beendet
	}

	/**
	 * Methode die aufgreufen wird, wenn 'n' gedr�ckt wird
	 */
	@Override
	protected void keyNewGamePressed() {
		this.pauseTimer(); // Timer anhalten
		
		//Optiondialog einblenden
		int eingabe = JOptionPane.showConfirmDialog(null,
                "M�chten Sie den Level wirklich neu starten?",
                "Level neu starten",
                JOptionPane.YES_NO_OPTION);
		
		if(eingabe == 0){
			this.loadLevel(this.getLogic().getLevel().getLevelNumber());
			this.refreshScreen();
		}
		
		this.startTimer(); // Timer: weiterlaufen
	}
	
	/**
	 * Methode die aufgreufen wird, wenn 'q' gedr�ckt wird
	 */
	protected void keyQuitGamePressed() {
		this.quitGame(); // Spiel beenden
	}
	

	/**
	 * Methode die aufgreufen wird, wenn Eingabetaste gedr�ckt wird
	 */
	@Override
	protected void keyRedoPressed() {
		try {
			this.getLogic().redo();
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Methode die aufgreufen wird, wenn Backspace gedr�ckt wird
	 */
	@Override
	protected void keyUndoPressed() {
		try {
			this.getLogic().undo();
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Methode die aufgreufen wird, wenn irgendeine Taste au�er den bereits belegten Tasten gedr�ckt wird
	 */
	@Override
	protected void keyOtherPressed(KeyEvent key) {
		this.getLogic().cheat(key.getKeyChar()); // Eingabe an Cheat�berwachung der GameLogic �bergeben
		this.refreshScreen(); // Panel neu zeichnen
	}

	
}
