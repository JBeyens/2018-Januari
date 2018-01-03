package model.business;

import model.entities.Person;

public class User {
	private Person person;
	
	//this should replace remotemodule
	//if you want class RemoteModuleSoftware => just use class with functions and save reference in remote
	//gatemodule keeps lists of user as list of observers
	public User(Person person){
		this.person = person;
	}
	
	//Should be only function from Observer Interface => subject gateModule
	public void updateFrequency(long frequency){
		this.person.getRemote().setFrequency(frequency);
	}
	
	//may be boolean return type
	public void openGate(){
		
	}
	
	//may be boolean return type
	public void giveId(){
		
	}
}
