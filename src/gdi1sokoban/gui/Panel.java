package gdi1sokoban.gui;

import gdi1sokoban.exceptions.InternalFailureException;
import gdi1sokoban.exceptions.InvalidOperationException;
import gdi1sokoban.exceptions.ParameterOutOfRangeException;
import gdi1sokoban.template.ui.GameWindow;

/**
 * Panel: Darstellung des Spielfelds
 * @author TAFF
 * @date: 27.2.2009
 */
@SuppressWarnings("serial")
public class Panel extends gdi1sokoban.template.ui.GamePanel {
	
	/*============================================================================*/
	// Name des aktuellen Skins
	String currSkinPath;
	
	// Referenz auf Windowobjekt
	Window parent;
	
	/*============================================================================*/
	
	/**
	 * Konstruktor
	 * @param theParentWindow (GameWindow)
	 */
	public Panel(GameWindow theParentWindow) {
		super(theParentWindow); // Aufruf des übergeodneten Konsturktors
		this.parent = (Window) theParentWindow; // ParentWindow ins Klassenattribut schreiben und zuvor als Window casten
		
		this.currSkinPath = "simpsons"; // Aktuellen Skinnamen in Klassenattribut schreiben
		this.registerImages(this.getCurrSkinPath()); //Bilder für aktuellen Skin registrieren
	}

	/**
	 * Setzt den aktuellen Namen des Skins in das Klassenattribut
	 * @param path (String): Name des aktuellen Skins
	 */
	private void setCurrSkinPath(String path){
		this.currSkinPath = path;
	}
	
	/**
	 * Gibt den aktuellen Skinnamen aus  dem Klassenattibut zurück
	 * @return skinname (String)
	 */
	public String getCurrSkinPath(){
		return this.currSkinPath;
	}
	
	/**
	 * Registriert die Bilder zum aktellen Skin
	 * @param path(String) Skinname
	 */
	private void registerImages(String path){
//		myAccessClass m = new myAccessClass();
		try {
			this.registerImage("floor", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/floor.png"));
			this.registerImage("box", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/box.png"));
			this.registerImage("boxongoal", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/boxongoal.png"));
			this.registerImage("goal", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/goal.png"));
			this.registerImage("player", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/player.png"));
			this.registerImage("playerongoal", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/playerongoal.png"));
			this.registerImage("wall", ClassLoader.getSystemClassLoader().getResource("sprites/"+path+"/wall.png"));
		} catch (ParameterOutOfRangeException e) {
			e.printStackTrace();
		} catch (InvalidOperationException e) {
			e.printStackTrace();
		}
		
		this.setCurrSkinPath(path);
	}
	
	/**
	 * Lädt einen neuen Skin anhand von dessen Namen
	 * @param name(String)
	 */
	protected void loadSkin(String name){
		//Unregister all imgs
		try {
			this.unregisterImage("floor");
			this.unregisterImage("box");
			this.unregisterImage("boxongoal");
			this.unregisterImage("goal");
			this.unregisterImage("player");
			this.unregisterImage("playerongoal");
			this.unregisterImage("wall");
		} catch (ParameterOutOfRangeException e1) {
			e1.printStackTrace();
		} catch (InvalidOperationException e1) {
			e1.printStackTrace();
		}
		
		if(name == "Original"){
			this.registerImages("original");
			this.parent.getLogic().setSkinname("original");
		}else if(name == "Die Simpsons"){
			this.registerImages("simpsons");
			this.parent.getLogic().setSkinname("simpsons");
		}else if(name == "Sendung mit der Maus"){
			this.registerImages("maus");
			this.parent.getLogic().setSkinname("maus");
		}else if(name == "American Dad"){
			this.registerImages("americandad");
			this.parent.getLogic().setSkinname("americandad");
		}
		try {
			this.redraw();
		} catch (InternalFailureException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Eventhandler wenn ein Feld auf dem Spielfeld angeklickt wurde
	 * param: postionX(int): x-Koordinate; positionY(int): y-koordinate
	 */
	@Override
	protected void entityClicked(int positionX, int positionY) {
		this.parent.getLogic().clickEvent(positionX, positionY);//Weiterleiten an GameLogic
		((Window)this.getParentWindow()).checkIfFinished(); //Pürfen ob Level beendet ist
		((Window)this.getParentWindow()).refreshScreen(); // Spielfeld neu zeichnen
	}

	/**
	 * Window neu dimensionieren
	 */
	@Override
	protected void panelResized() {
		getParentWindow().setSize(this.getPreferredSize());
	}

	/**
	 * Gibt den passenden Namen für einen FeldChar zurück
	 * @param chr(char): Feldchar
	 * @return selection(String): String mit Namen des Images
	 */
	private String getImgForChar(char chr) {
		String selection = "";
		switch (chr) {
		case '#':
			selection = "wall";
			break;
		case '$':
			selection = "box";
			break;
		case '.':
			selection = "goal";
			break;
		case '*':
			selection = "boxongoal";
			break;
		case '@':
			selection = "player";
			break;
		case '+':
			selection = "playerongoal";
			break;
		case ' ':
			selection = "floor";
			break;
		default:
			selection = "floor";
			break;
		}

		return selection;
	}

	/**
	 * Fügt die Inhalte des Spielfeld-Arrays in den Panel ein.
	 */
	@Override
	protected void setGamePanelContents() {
		
		int levelHeight = 0, levelWidth = 0;
		
		// Falls Gamelogic noch nicht initialisiert ist, wird das Panel einfach nur neu gesetzt
		try {
			levelHeight = this.parent.getLogic().getLevel().getLevelHeight();
			levelWidth = this.parent.getLogic().getLevel().getLevelWidth();
		} catch (Exception e) {
			this.panelResized();//Fenstergröße hat sich geändert
		}
		
		// Fenstergrš§e setzen
		try {
			for (int y = 0; y < levelHeight; y++) {
				for (int x = 0; x < levelWidth; x++) {
					this.placeEntity(this.getImgForChar(this.parent.getLogic().getLevel().getLevelChar()[y][x]));//Setzen des Feldes
				}
			}
		} catch (ParameterOutOfRangeException e) {
			e.printStackTrace();
		}
		
		this.panelResized();//Fenstergröße hat sich geändert
	}

}
