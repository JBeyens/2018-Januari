package model.business;

import model.business.interfaces.AdminObserver;
import model.entities.Person;
import model.entities.Remote;

public class User implements AdminObserver{
	private Person person;
	private Administrator gate;
	private Remote remote;

	public User(Person person, Remote remote, Administrator gate){
		this.person = person;
		this.gate = gate;
		this.remote = remote;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Remote getRemote() {
		return remote;
	}

	public void setRemote(Remote remote) {
		this.remote = remote;
	}
	
	public boolean openGate(){
		giveId();
		
		return gate.getFrequency() == remote.getFrequency();		
	}

	//can be private
	public void giveId(){
		gate.checkId(this);
	}

	@Override
	public void update(long frequency) {
		this.remote.setFrequency(frequency);
	}
	
}
