package values;

public enum StatementsMySql {
	write_Object_Address("INSERT INTO address(street, nr, postalCode, city, country) VALUES (?, ?, ?, ? ,?)"),
	write_Object_Remote("INSERT INTO remote(serialNumber, frequency, person) VALUES (?, ?, ?)"),
	write_Object_Person("INSERT INTO person(firstname, lastname, address, endOfContract, email) VALUES(?, ?, ?, ?, ?)"),
	read_Objects_RemoteID_No_Person_Assigned("SELECT id from Remote where person is NULL");

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
