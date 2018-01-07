package values;

public enum UserDeactivationResult {
	succesfull("User was succesfully deactivated!"),
	notFound("User could not be deactivated because user was not found as registered user!");
	
	private final String deactivatePersonResult;
	
	UserDeactivationResult(String deactivatePersonResult){
		this.deactivatePersonResult = deactivatePersonResult;
	}
	
	@Override
	public String toString(){
		return deactivatePersonResult;
	}
}
