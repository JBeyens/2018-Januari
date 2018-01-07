package values;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class DefaultSettings {
	public static Random RANDOM = new Random();
	public static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static Logger logger;
	
	public static Logger getLogger(String name) {
		if (logger != null)
			return logger;

		// Configure log4j settings:
		DOMConfigurator.configure("src.main.java/log4j.xml");
		logger = Logger.getLogger(name);
		logger.setLevel(Level.INFO);
		return logger;
	}
}
