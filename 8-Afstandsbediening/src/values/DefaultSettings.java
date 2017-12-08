package values;

public enum DefaultSettings {
	propertiesPath("resourcs/config.properties");


    private String text;

    private DefaultSettings(String text) {
        this.text = text;
    }

    public String getValue() {
        return text;
    }
}
