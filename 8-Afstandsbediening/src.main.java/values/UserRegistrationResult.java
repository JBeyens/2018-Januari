package values;

public enum UserRegistrationResult {
	succesfull("User was succesfully registered!"),
	noRemote("User could not be registered since no remote is linked to the user!"),
	alreadyInList("User could not be registered again, since it was already registered!");
	
	private final String registerResult;
	
	UserRegistrationResult(String registerResult){
		this.registerResult = registerResult;
	}
	
	@Override
	public String toString(){
		return registerResult;
	}
}
