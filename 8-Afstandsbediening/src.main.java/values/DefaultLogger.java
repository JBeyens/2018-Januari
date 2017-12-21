package values;

import org.apache.log4j.xml.DOMConfigurator;

public final class DefaultLogger {
	
	public static void configureLogger() {
		// Configure log4j settings:
		DOMConfigurator.configure("log4j.xml");
		
	}	
}
