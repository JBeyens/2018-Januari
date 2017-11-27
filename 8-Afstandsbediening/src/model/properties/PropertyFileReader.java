package model.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/11/2017
 * @Project Afstandsbediening
 * @Doel Read & Write properties to and from file
 */

public final class PropertyFileReader {
	/* Private constructor --> class not meant to be initialized */
	private PropertyFileReader() {}

	/* Method to store specified properties to a specified path. */
	public static boolean saveProperties(String path, Properties properties) {
		File file = getFile(path);
		try (OutputStream out = new FileOutputStream(file)) {
			properties.store(out, null);
		}
		catch (IOException ex) { // The exception FileNotFoundException is already caught by the alternative IOException
			return false;
		}
		return true;
	}

	/* Method to get properties from a specified path. */
	public static Properties loadProperties(String path, Properties properties) {	
		File file = getFile(path);						
		try(InputStream is = new FileInputStream(file)){
			properties.load(is);
		}	
		catch (IOException ex) { // The exception FileNotFoundException is already caught by the alternative IOException
			return null;
		}
		return properties;
	}
	
	/* Returns File object which is linked to the file you want to read/write to */
	private static File getFile(String path)
	{		
		File  file = null;
		try {
			file = new File(path);
			
			if (!file.exists()) {
				// Create new file based on absolute path
				if (file.getParentFile() != null)
					file.getParentFile().mkdirs(); // Will create missing folders if necessary
				file.createNewFile();
			}

		} catch (IOException|SecurityException e) {
			e.printStackTrace();
		} 
		return file;		
	}
}
