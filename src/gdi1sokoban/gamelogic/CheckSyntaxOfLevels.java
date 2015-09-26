/**
 * @author TAFF
 * @version final 
 * 

 */


package gdi1sokoban.gamelogic;

import gdi1sokoban.exceptions.InvalidLevelException;

public class CheckSyntaxOfLevels {
/**
	 * checkSyntax
	 * ¸berpr¸ft auf syntaktische Korrektheit des Levels
	 * 
	 * ‹berpr¸ft, ob die Wandelemente einen geschlossenen Ring ergeben.
	 * Es befinden sich nur zul‰ssige Zeichen im Spielfeld.
	 * Es befindet sich genau ein Arbeiter auf dem Spielfeld.
	 * Es befindet sich mindestens eine Kiste auf dem Spielfeld.
	 * Es befinden sich genauso viele Kisten wie Zielpositionen (Goals) auf dem Spielfeld.
	 * 
	 * @param field -> das Spielfeld (char[][])
	 * @return  true wenn korrektes Level, false wenn syntaktisch unkorrekt
	 * @throws InvalidLevelException
	 */
public static boolean checkSyntax(char[][] field) throws InvalidLevelException {

	int Xx = 0,Yy = 0;
	char searchkey = '@';
	char searchkey2 = '+';
	
	
//player suchen, von dem aus geflooooootet werden kann
	for (int m = field.length-1; m >= 0; m--) {
	    for (int n = field[0].length-1; n >= 0; n--) {
			if (field[m][n] == searchkey || field[m][n] == searchkey2) {
				Xx=m;
				Yy=n;
			}
		}
	}
//Spielfeld clonen, damit wir nicht das original flooten
	  char[][] newArr = new char [field.length][field[0].length];
	  for (int i = 0; i < field.length; i++){
	    newArr [i] = field[i].clone ();}
	  
	  
//checkt, ob die Wandumgrenzung dicht ist -> lˆst exception aus, wenn nicht
		 checkWallBounding(newArr, Xx, Yy);
		 
		
		if (checkFields(field) == true && onlyOneMan(field) == true
			&& existsBox(field) == true && boxAndAim(field) == true
				
				) {
			return true;

		}
		
		throw new InvalidLevelException("Kein syntaktisch Korrektes Level!!!");

	}
	
	
	
/**
* checkFields
* ¸berpr¸ft, ob im level nur zulassige Zeichen vorhanden sind
*   
* @param field --- char array
* @return true wenn nur zulassige zeichen...false wenn unzul‰ssig
*/
	private static boolean checkFields(char[][] field) {
		char searchkey = '$'; // 1
		char searchkey2 = '.'; // 1
		char searchkey3 = '+'; // 0
		char searchkey4 = ' '; // 11
		char searchkey5 = '#'; // 20
		char searchkey6 = '@'; // 1
		char searchkey7 = '*'; // 0
		char searchkey8 = (char) 0;

		int i = 0;
		for (int m = field.length - 1; m >= 0; m--) {
			for (int n = field[0].length - 1; n >= 0; n--) {
				if (field[m][n] == searchkey || field[m][n] == searchkey2
						|| field[m][n] == searchkey3
						|| field[m][n] == searchkey4
						|| field[m][n] == searchkey5
						|| field[m][n] == searchkey6
						|| field[m][n] == searchkey7
						|| field[m][n] == searchkey8) {
					i++;
				}

			}
		}
		int breite = field.length;
		int lange = field[0].length;
		int anzahlderfelder = breite * lange;

		if (i == anzahlderfelder) {
			return true;
		}

		return false;

	}



/**
 * OnyleOneMan
 * ¸berpr¸ft, dass nur ein Spielersymbol im Level vorhanden ist
 * 
 * @param field
 * @return true wenn nur ein spieler vorhande, false wenn mehrere vorhanden
 */
	private static boolean onlyOneMan(char[][] field) {
		char searchkey = '@';
		char searchkey2 = '+';
	
		int i = 0;
		
		for (int m = field.length - 1; m >= 0; m--) {
			for (int n = field[0].length - 1; n >= 0; n--) {
				if (field[m][n] == searchkey ||	
						field[m][n]==searchkey2)
				    {
					i++;
				}
			}
		}
		if (i == 1)
		
			
			return true;
		else
			return false;

	}
	
	
/**
 * existsBox
 * ¸berpr¸ft, ob eine Kiste im Level vorhanden ist
 * 
 * @param field
 * @return true wenn eine Kiste enhalten ist, false wenn nicht
 */
	private static boolean existsBox(char[][] field) {
		char searchkey = '$';
		char searchkey2= '*';
		int i = 0;
		for (int m = field.length - 1; m >= 0; m--) {
			for (int n = field[0].length - 1; n >= 0; n--) {
				if (field[m][n] == searchkey) {
					i++;
				}
			}
		}
		if (i >= 1)
			return true;
		else
			
			//wenn keine Box vorhanden, dann kann es sein, dass eine Box bereits auf dem Zielfeld steht
			if (i<1){				
				for (int m = field.length - 1; m >= 0; m--) {
					for (int n = field[0].length - 1; n >= 0; n--) {
						if (field[m][n] == searchkey2)
					{
							return true;
						}
			}}}
		
			return false;

	}
	
	/**
	 * boxAndAim
	 * ¸berpr¸ft, ob gleichviele Kisten wie Zielfelder vorhanden sind
	 *
	 * @param field
	 * @return true wenn Anzahl gleich, false wenn nicht
	 */
	
	private static boolean boxAndAim(char[][] field) {
		char searchkey = '$';
		char searchkey2 = '.';
		char searchkey3 = '*';
		int i = 0;
		int z = 0;
	
		
		for (int m = field.length - 1; m >= 0; m--) {
			for (int n = field[0].length - 1; n >= 0; n--) {
				if (field[m][n] == searchkey) {
					i++;
				}
			}
		}
		for (int m = field.length - 1; m >= 0; m--) {
			for (int n = field[0].length - 1; n >= 0; n--) {
				if (field[m][n] == searchkey2) {
					z++;
				}
			}
		}
		// es kann sein, dass eine Box bereits auf dem Zielfeld steht
		// z>0 schlieﬂt aus, dass ein zielfeld duch spieler belegt
		if (z == i && z > 0) {
			return true;
		} else {
			for (int m = field.length - 1; m >= 0; m--) {
				for (int n = field[0].length - 1; n >= 0; n--) {
					if (field[m][n] == searchkey3) {
						return true;
					}
				}
			}
		}

		return false;

	}
	
	/**
	 * CheckWallBounding
	 * 
	 * testet ob die Spielfeldbegrenzung '#' dicht ist.
	 * dazu wird vom Player ausgehend das Spielfeld gef¸llt. Nur die Wandelemente
	 * bleiben stehen. Es wird gepr¸ft, ob ein Randelement des Spielfeldes ein
	 * gef¸lltes Symbol ist. Wenn ja, dann ist der Ring lˆchrig!!!
	 * 
	 * @param area
	 * @param x xcoordinate des Players
	 * @param y xcoordinate des Players
	 * @throws InvalidLevelException
	 * @example 
	 * 			##b##
	 * 			#bbb#
	 * 			#####    -> ring ist undicht -> Exception
	 */
	
	
	  
	public static void checkWallBounding (char [][] newArr, int x, int y) throws InvalidLevelException {
		
//wenn auﬂerhalb oder Wanelement oder schon geflutet, breche ab,
//andernfals flute das teil
			if (x<0 || x>newArr.length || y<0 || y>=newArr[0].length) return; 
			if (newArr[x][y]=='#') return ;
			if (newArr[x][y]=='b') return ;
			
	//flute
			newArr[x][y]='b';
		
	//rekursiver aufruf ¸ber die nachbarn
			   checkWallBounding(newArr,x, y + 1);
			      checkWallBounding(newArr,x, y - 1);
			   checkWallBounding(newArr,x + 1, y);
			      checkWallBounding(newArr,x - 1, y);
			     
//wenn ein flutelent am Spielfeldrand gefunden wird, werfe exception	      
	      for (int m = newArr.length - 1; m >= 0; m--) {
			   	for (int n = newArr[0].length - 1; n >= 0; n--) {
			    	if (newArr[m][0] == 'b' 
			    				|| newArr[m][newArr[0].length-1] == 'b'
			    				|| newArr[0][n]=='b'
			    				|| newArr[newArr.length-1][n]=='b')
			    				{
			    				throw new InvalidLevelException("Kein syntaktisch Korrektes Level!!!"); 
	}}}}	
	}
