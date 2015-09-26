package gdi1sokoban.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;


@SuppressWarnings("serial")
public class StatusBar extends JLabel {
	/*============================================================================*/
	//Windowobjekt
    private Window parent;
    /*============================================================================*/
    
   /**
    * Konstruktor: Erstellt neue Statusbar
    */
    public StatusBar(final Window parent) {
        super(); // Aufruf Konstruktor von JLabel
        super.setPreferredSize(new Dimension(100, 16));
        
        this.setParent(parent); // Setzen des Parentobjekts (Window)
        setMessage("Ready"); //Statusbar fertig mit laden

        //Statusbar an GameLogicTimer anbinden
        this.getParent().getLogic().getTimer().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	reloadStatus(); // reloadstatus bei jedem Timer-Event ausführen
            }
         });
    }
	
    /**
     * Setzt das übergeordnete Objekt in das Klassenattribut parent
     * @param parent
     */
    public void setParent(Window parent) {
		this.parent = parent;
	}
    
    /**
     * Gibt das übergeordnnete Objekt zurück
     * @return parent (Window)
     */
	public Window getParent() {
		return parent;
	}
    
    /**
     * Setzt den Text in die Statusbar
     * @param message - String
     */
	private void setMessage(String message) {
        this.setText(" "+message);        
    }
    
    /**
     * Lädt die einzelnen Daten aus der GameLogic und schreibt diese in die Statusbar
     */
    public void reloadStatus(){
    	String playername = this.getParent().getLogic().getPlayer().getPlayerName();
		int schritte = this.getParent().getLogic().getPlayer().getStepCount();
		double time = this.getParent().getLogic().getTime();
		this.setMessage("Zeit: "+time+"     Player: "+playername+"     Schritte: "+schritte);
		this.getParent().refreshScreen(); // Spielpanel neu zeichnen
    }
}