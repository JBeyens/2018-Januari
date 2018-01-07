package values;

public enum DeactivatePersonResult {
	succesfull("Person was succesfully deactivated!"),
	notFound("Person could not be deactivated because person was not found as registered user!");
	
	private final String deactivatePersonResult;
	
	DeactivatePersonResult(String deactivatePersonResult){
		this.deactivatePersonResult = deactivatePersonResult;
	}
	
	@Override
	public String toString(){
		return deactivatePersonResult;
	}
}
