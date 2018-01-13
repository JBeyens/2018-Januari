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
		setId(person.getId());
		setFirstname(person.getFirstname());
		setLastname(person.getLastname());
		setEndOfContract(person.getEndOfContract());

		if ( person.getAdress() != null)
			setAdress(person.getAdress());
		
		if (person.getRemote() != null)
			setRemote(person.getRemote());
		
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
		if (getRemote() == null)
			return false;
		
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
