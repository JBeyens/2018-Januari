package values;

import org.apache.log4j.xml.DOMConfigurator;

public final class DefaultLogger {
	
	public static void configureLogger() {
		// Configure log4j settings:
		DOMConfigurator.configure("src.main.java/log4j.xml");
		
	}	
}
