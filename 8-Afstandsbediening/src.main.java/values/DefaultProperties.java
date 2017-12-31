package values;

public enum DefaultProperties {	
	propertiesPath("resourcs/config.properties");


    private String text;

    private DefaultProperties(String text) {
        this.text = text;
    }

    public String getValue() {
        return text;
    }
}
