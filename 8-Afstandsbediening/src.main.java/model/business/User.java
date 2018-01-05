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
	
	public boolean openGate(Administrator gateAdmin){
		return gateAdmin.askFrequency(giveId()) == getPerson().getRemote().getFrequency();		
	}

	public String giveId(){
		return getPerson().getRemote().getSerialNumber();
		//checkId functie in Admin? met person als parameter om op alle gegevens te checken?
	}

	@Override
	public void update(long frequency) {
		getPerson().getRemote().setFrequency(frequency);
	}
}
