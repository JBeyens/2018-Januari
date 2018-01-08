package model.business;

import model.business.interfaces.AdminObserver;
import model.entities.Person;

public class PersonWrapper extends Person implements AdminObserver{
	private Administrator admin;
	
	public PersonWrapper(Person person, Administrator admin){
		//can add ctor in person
		//super(person);
		
		this.admin = admin;
	}
	
	
	@Override
	public void update(long frequency) {
		// TODO Auto-generated method stub
		getRemote().setFrequency(frequency);
	}

}
