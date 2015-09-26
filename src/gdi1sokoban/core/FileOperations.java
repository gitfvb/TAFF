package gdi1sokoban.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

public class FileOperations {

	/**
	 * 
	 * Methode zur Rückgabe eines Pfades einer angegebenen Datei. Der
	 * Wurzel-Punkt der Angabe ist immer das Projektverzeichnis.
	 * 
	 * @param filePath
	 *            Pfad für die gewünschte Datei (z.B. "levels/level_01.txt")
	 * @return absoluter Pfad im Dateisystem für die gewünschte Datei
	 */
	public static String buildFilePath(String filePath) {

		File f = new File(filePath);
		String filePathAbs = ""; // Leeren des Filenames zur Fehlervermeidung

		try {
			filePathAbs = f.getCanonicalPath();
		} catch (Exception ex) {
		}

		return filePathAbs;

	}

	/**
	 * 
	 * Auslesen einer kleineren Textdatei (bis 1024 Zeichen)
	 * 
	 * @param fileName
	 *            Dateiname und Pfad der Datei
	 * @throws IOException
	 * @return Rückgabe eines IntBuffers mit den Inhalten der Datei
	 * 
	 */
	public static IntBuffer readFrom(File fileName) throws IOException {

		// Deklaration
		myAccessClass m = new myAccessClass();
		System.out.println(fileName.getParent());
		InputStream in = (InputStream) m.getURLFor("levels/", fileName.getName()).getContent(); // TODO levels hier noch rauskriegen
		IntBuffer ibuf = IntBuffer.allocate(1024);
		int b;

		// Speicherung der Daten im Buffer
		while ((b = in.read()) != -1)
			ibuf.put(b);

		// Schliessen des Files
		in.close();

		return ibuf;
	}

	/**
	 * 
	 * Auslesen einer Textdatei in einen StringBuffer. Damit sind auch größere
	 * Dateien möglich. Der StringBuffer vergrößert sich dynamisch
	 * 
	 * @param fileName
	 *            Dateiname und Pfad der Datei
	 * @return Rückgabe eines StringBuffers mit den Inhalten der Datei
	 * @throws IOException
	 */
	public static StringBuffer readFrom2(File fileName) throws IOException {

		// Deklaration
		myAccessClass m = new myAccessClass();
		InputStream in = (InputStream) m.getURLFor("levels/", fileName.getName()).getContent(); //TODO levels hier noch rauskriegen
		StringBuffer sbuf = new StringBuffer(256);
		int i;

		// Speicherung der Daten im Buffer
		while ((i = in.read()) != -1)
			sbuf.append((char) i);

		// Schliessen des Files
		in.close();

		return sbuf;
	}

	/**
	 * 
	 * Schreiben von Daten, der neue String ersetzt dabei alle Dateiinhalte
	 * 
	 * @param filename
	 *            Dateiname der zu beschreibenden Datei
	 * @param output
	 *            zu schreibender String in Datei
	 * @throws IOException
	 * 
	 */
	public static void writeString(File filename, String output)
			throws IOException {

		FileWriter out = new FileWriter(filename);
		out.write(output);
		out.close();

	}
	
	/**
	 * 
	 * Schreiben von Daten, der neue String-Array ersetzt dabei alle Dateiinhalte
	 * 
	 * @param filename
	 *            Dateiname der zu beschreibenden Datei
	 * @param output
	 *            zu schreibender String in Datei
	 * @throws IOException
	 * 
	 */
	public static void writeString(File filename, String[] output, String separator)
			throws IOException {
		try {
			writeString(filename, concatString(output, separator));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * Schreiben von Daten, neuer String wird an den gesamten Text angehängt
	 * 
	 * @param filename
	 *            Dateiname der zu beschreibenden Datei
	 * @param output
	 *            zu schreibender String in Datei
	 * @throws IOException
	 * 
	 */
	public static void appendString(File filename, String output)
			throws IOException {

		StringBuffer sbuf = readFrom2(filename);
		sbuf.append(output);

		writeString(filename, sbuf.toString());

	}
	
	/**
	 * 
	 * Schreiben von Daten, neuer String wird an den gesamten Text angehängt
	 * 
	 * @param filename
	 *            Dateiname der zu beschreibenden Datei
	 * @param output
	 *            zu schreibender String in Datei
	 * @param separator Trenner für die einzelnen String-Elemente
	 * @throws IOException
	 * 
	 */	
	public static void appendString(File filename, String[] output, String separator) {
		try {
			appendString(filename, concatString(output, separator));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Zusammenfügen eines String-Array mit einem Trennzeichen
	 * 
	 * @param output String-Array, der aufgelöst werden soll
	 * @param separator Trennzeichen
	 *h@return zusammengefügter String
	 */
	private static String concatString(String[] output, String separator) {
		StringBuffer sbuf = new StringBuffer(256);
		for (String item: output) {
			sbuf.append(item).append(separator);
		}
		return sbuf.toString();
	}

	/**
	 * 
	 * Schreiben von Daten, neuer String wird an den gesamten Text nach einem
	 * Zeilenumbruch angehängt
	 * 
	 * @param filename
	 *            Dateiname der zu beschreibenden Datei
	 * @param output
	 *            zu schreibender String in Datei
	 * @throws IOException
	 * 
	 */
	public static void appendStringln(File filename, String output)
			throws IOException {
		appendString(filename, output + "\n");
	}
	
	/**
	 * 
	 * Schreiben von Daten, neuer String wird an den gesamten Text angehängt
	 * 
	 * @param filename
	 *            Dateiname der zu beschreibenden Datei
	 * @param output
	 *            zu schreibender String in Datei
	 * @param separator Trenner für die einzelnen String-Elemente
	 * @throws IOException
	 * 
	 */	
	protected static void appendStringln(File filename, String[] output, String separator) {
		try {
			appendStringln(filename, concatString(output, separator));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
