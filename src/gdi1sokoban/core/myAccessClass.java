package gdi1sokoban.core;

import java.net.URL;

public class myAccessClass {

	@SuppressWarnings("unchecked")
	// das Folgende am besten als Attribut deklarieren!
	Class myAccessClass = null;

	/**
	 * Open a URL that will work with Java Web Start Note: requires the presence
	 * of an (empty) class <em>dir/ClassDummy</em> (.class file!) in the given
	 * directory.
	 * 
	 * @param dir
	 *            the directory name; separate directory names only by '/', not
	 *            by the Windows backslash!
	 * @param fileName
	 *            the name of the file to open.
	 */
	public URL getURLFor(String dir, String filename) {
		URL url = null;
		if (myAccessClass == null) {
			try {
				myAccessClass = Class.forName(dir.replace('/', '.')
						+ "ClassDummy");
			} catch (ClassNotFoundException cfe) {
				System.err.println("FileDummy could not be found!");
			}
		}
		// Get current classloader
		if (myAccessClass != null) {
			ClassLoader cl = myAccessClass.getClassLoader();
			if (cl != null) {
				// URL holen
				url = cl.getResource("levels/" + filename);
			}
		}
		return url;
	}

}
