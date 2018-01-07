package model.business;

import model.business.interfaces.AdminObserver;
import model.entities.Person;
import model.entities.Remote;


/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 02/01/2018
 * @Project Afstandsbediening
 * @Doel Gebruiker van remote
 */
public class User implements AdminObserver{
	/** FIELDS **/
	private Person person;
	private Administrator gate;
	private Remote remote;

	/** CONSTRUCTOR **/
	public User(Person person, Remote remote, Administrator gate){
		this.person = person;
		this.gate = gate;
		this.remote = remote;
	}

	/** METHODS 
	 *  Getter & setter for 'Person'
	 **/
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Getter & setter for 'Remote'
	 **/
	public Remote getRemote() {
		return remote;
	}
	public void setRemote(Remote remote) {
		this.remote = remote;
	}

	/**
	 * Method to let the remote send a signal to the gate to open it
	 * @return Boolean - Is gate opening or not
	 **/
	public boolean openGate(){
		checkIdForUpdate();
		
		return gate.getFrequency() == remote.getFrequency();		
	}
	
	private void checkIdForUpdate(){
		gate.checkIdForUpdate(this);
	}

	/**
	 * Method for observer pattern where the frequency of the user remote can be updated
	 **/
	@Override
	public void update(long frequency) {
		this.remote.setFrequency(frequency);
	}
	
}
