package model.business;

import model.entities.Person;

public class User implements AdminObserver{
	private Person person;

	public User(Person person){
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}
	
	public void openGate(){
		
	}

	public void giveId(){
		
	}

	@Override
	public void update(long frequency) {
		this.person.getRemote().setFrequency(frequency);
	}
}
