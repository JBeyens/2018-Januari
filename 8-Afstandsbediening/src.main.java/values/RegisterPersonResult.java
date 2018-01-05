package values;

public enum RegisterPersonResult {
	succesfull("Person was succesfully registered!"),
	noRemote("Person could not be registered since no remote is linked to this person!"),
	alreadyInList("Person could not be registered again, since it was already registered!");
	
	private final String registerResult;
	
	RegisterPersonResult(String registerResult){
		this.registerResult = registerResult;
	}
	
	@Override
	public String toString(){
		return registerResult;
	}
}
