package model.business;

import model.business.interfaces.AdminObserver;
import model.entities.Person;

public class PersonWrapper extends Person implements AdminObserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Administrator gate;
	
	public PersonWrapper(Person person, Administrator admin){
		//can add ctor in person
		//super(person);
		
		//OF
		
		setFirstname(person.getFirstname());
		setLastname(person.getLastname());
		setAdress(person.getAdress());
		setRemote(person.getRemote());
		setEndOfContract(person.getEndOfContract());
		
		this.gate = admin;
	}
	
	/**
	 * Getter for 'Gate/Administrator'
	 **/
	public Administrator getGate() {
		return gate;
	}

	/**
	 * Method to let the remote send a signal to the gate to open it
	 * @return Boolean - Is gate opening or not
	 **/
	public boolean openGate(){	
		return (checkIdForUpdate() && gate.getFrequency() == getRemote().getFrequency());		
	}
	
	private boolean checkIdForUpdate(){
		return gate.checkIdForUpdate(this);
	}

	/**
	 * Method for observer pattern where the frequency of the user remote can be updated
	 **/
	@Override
	public void update(long frequency) {
		getRemote().setFrequency(frequency);
	}

	@Override
	public String toString() {
		return super.toString() + ": remote " + getRemote().getSerialNumber();
	}

}
