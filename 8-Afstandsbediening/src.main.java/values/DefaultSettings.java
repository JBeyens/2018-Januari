package values;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DefaultSettings {
	private static Logger logger;
	
	public static Logger getLogger() {
		if (logger != null)
			return logger;
		
		logger = Logger.getLogger("Log");
		logger.setLevel(Level.ALL);
		return logger;
	}
}
