package values;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class DefaultSettings {
	private static Logger logger;
	
	public static Logger getLogger() {
		if (logger != null)
			return logger;

		// Configure log4j settings:
		DOMConfigurator.configure("src.main.java/log4j.xml");
		logger = Logger.getLogger("Log");
		logger.setLevel(Level.ALL);
		return logger;
	}
}
