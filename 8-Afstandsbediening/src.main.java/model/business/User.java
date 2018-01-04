package model.business;

import model.business.interfaces.AdminObserver;
import model.entities.Person;

public class User implements AdminObserver{
	private Person person;

	public User(Person person){
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}
	
	public boolean openGate(){
		return false;
		
	}

	public boolean giveId(){
		return false;
		//checkId functie in Admin? met person als parameter om op alle gegevens te checken?
	}

	@Override
	public void update(long frequency) {
		this.person.getRemote().setFrequency(frequency);
	}
}
