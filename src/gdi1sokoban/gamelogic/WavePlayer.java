package gdi1sokoban.gamelogic;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;



/**
 * WavePlayer aus dem GdI Forum; abgeändert um die Themesounds abspielen zu können
 * 
 * @site http://proffs.tk.informatik.tu-darmstadt.de/gdi1/de/node/8007
 * @author daniel
 *
 */
public class WavePlayer extends Thread {
	private String filename;
 	private Position curPosition;
 	private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
 	enum Position {
		LEFT, RIGHT, NORMAL
	};

	/**
	 * Überprüft ob für den gewählten Skin Soundfiles vorhanden sind und spielt diese dann ab
	 * 
	 * @param filename
	 * @author taff
	 *    Dateiname der angefragten WAV-Datei als String
	 */
	public static void playThemeSound(String filename) {
		File soundFile = new File(filename);
		if (!soundFile.exists()) {
			return;			
		}
		playFile(filename);		
	}
	
	
	/**
	 * Spielt die übergebene WAV-Datei ab.
	 * 
	 * @param filename
	 *    Dateiname der Wav-Datei als String
	 */
	public static void playFile(String filename) {
		WavePlayer player = new WavePlayer(filename);
		player.start();
	}
 
	public WavePlayer(String filename) {
		this.filename = filename;
		curPosition = Position.NORMAL;
	}
 
	public WavePlayer(String wavfile, Position p) {
		filename = wavfile;
		curPosition = p;
	}
 
	public void run() {
 
		File soundFile = new File(filename);
		if (!soundFile.exists()) {
			System.err.println("Wave file not found: " + filename);
			return;
		}
 
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
 
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
 
		if (auline.isControlSupported(FloatControl.Type.PAN)) {
			FloatControl pan = (FloatControl) auline
					.getControl(FloatControl.Type.PAN);
			if (curPosition == Position.RIGHT)
				pan.setValue(1.0f);
			else if (curPosition == Position.LEFT)
				pan.setValue(-1.0f);
		} 
 
		auline.start();
		int nBytesRead = 0;
		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}
 
	}
}