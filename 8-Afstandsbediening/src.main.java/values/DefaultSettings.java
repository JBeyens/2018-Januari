package values;

import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class DefaultSettings {
	public static Random RANDOM = new Random();
	private static Logger logger;
	
	public static Logger getLogger(String name) {
		if (logger != null)
			return logger;

		// Configure log4j settings:
		DOMConfigurator.configure("src.main.java/log4j.xml");
		logger = Logger.getLogger(name);
		logger.setLevel(Level.OFF);
		return logger;
	}
}
