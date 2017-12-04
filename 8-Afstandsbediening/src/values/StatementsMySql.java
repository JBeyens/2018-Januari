package values;

public enum StatementsMySql {
	write_Object_Address("INSERT INTO adres(street, nr, postalCode, city, country) VALUES (?, ?, ?, ? ,?)"),
	read_Object_By_Id("SELECT object_value FROM java_objects WHERE id = ?");

	private final String name;
	
	StatementsMySql(String name){
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
