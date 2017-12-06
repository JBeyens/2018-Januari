package values;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/11/2017
 * @Project Afstandsbediening
 * @Doel Holding default values
 */

public enum DefaultSettings {
	propertiesPath("resources/config.properties"),
	hibernateConfigPath("/resources/hibernate.config.xml");

	private final String name;
	
	DefaultSettings(String name){
		this.name = name;
	}
	
	public String getValue(){
		return name;
	}
	
	@Override
	public String toString(){
		return this.name();
	}
	
}
