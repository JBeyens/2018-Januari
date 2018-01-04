package model.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.interfaces.IGateObserver;
import model.business.interfaces.IGateSubject;
import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import values.DefaultSettings;

/*
 * Business layer for retrieval & storage of data
 */

//remove interface
public final class DataManager implements IGateSubject{
	// FIELDS
	private static Logger log = DefaultSettings.getLogger(DataManager.class.getSimpleName());

	
	// DATABASE READ OPERATIONS
	public static Address getAddress(Integer id) {
		log.trace("Asking datalayer to retrieve 'Address' with id = " + id);
		return EntityDAO.ADDRESS_DAO.findOne(id);
	}	
	public static Person getPerson(Integer id) {
		log.trace("Asking datalayer to retrieve 'Person' with id = " + id);
		return  EntityDAO.PERSON_DAO.findOne(id);
	}	
	public static Remote getRemote(Integer id) {
		log.trace("Asking datalayer to retrieve 'Remote' with id = " + id);
		return  EntityDAO.REMOTE_DAO.findOne(id);
	}
	
	public static ArrayList<Address> getAllAddresses() {
		log.debug("Asking datalayer to retrieve all addresses");
		return (ArrayList<Address>) EntityDAO.ADDRESS_DAO.findAll();
	}
	public static ArrayList<Person> getAllPersons() {
		log.debug("Asking datalayer to retrieve all persons");
		return (ArrayList<Person>) EntityDAO.PERSON_DAO.findAll();
	}
	public static ArrayList<Remote> getAllRemotes() {
		log.debug("Asking datalayer to retrieve all remotes");
		return (ArrayList<Remote>) EntityDAO.REMOTE_DAO.findAll();
	}
	
	// Returns inactive remotes by namedquery JPA
	public static ArrayList<Remote> getUnusedRemotes(){
		log.debug("Asking datalayer to retrieve all unused remotes");
		return (ArrayList<Remote>) EntityDAO.REMOTE_DAO.executeNamedQuery("findUnusedRemotes");
	}
	// Returns unused addresses by namedquery JPA
	public static ArrayList<Address> getUnusedAddress(){
		log.debug("Asking datalayer to retrieve all unused addresses");
		return (ArrayList<Address>) EntityDAO.ADDRESS_DAO.executeNamedQuery("findUnusedAddress");
	} 	

	
	// DATABASE UPDATE OPERATIONS
	public static void updatePerson(Person person) {
		log.debug("Asking datalayer to update " + Person.class.getSimpleName() + " '" + person.toString() + "'");
		EntityDAO.PERSON_DAO.update(person);
	}
	public static void updateRemote(Remote remote) {
		log.debug("Asking datalayer to update " + Remote.class.getSimpleName() + " '" + remote.toString() + "'");
		EntityDAO.REMOTE_DAO.update(remote);
	}	
	

	//Remove
	@Override
	public void registerGate(IGateObserver gateModule) {
		gateModules.add(gateModule);
		notifyGateObservers();
	}
	//Remove
	@Override
	public void unregisterGate(IGateObserver gateModule) {
		gateModules.add(gateModule);
		notifyGateObservers();
	}
	//Remove
	private void notifyGateObservers() {
		long newFrequency = DefaultSettings.RANDOM.nextLong();
		
		for (IGateObserver gate : gateModules) {
			gate.handleNotification(newFrequency, persons);
		}
	}	
	
	/**
	 *  Register new person
	 **/
	public void registerPerson(Person person) {
		//remove setactive other class
		persons.add(person); 
		if (person.getRemote() != null)
			person.getRemote().setIsActive(true);
		
		//entitydao call functie
		updatePerson(person); // Database
		
		//remove
		notifyGateObservers();
	}
	
	/**
	 * Remove existing person (if registered)
	 */
	public void deActivatePerson(Person person) {
		//remove zie vorig
		persons.remove(person);
		if (person.getRemote() != null)
			person.getRemote().setIsActive(false);
		
		//use entitydao => geen nood aan andere functie op te roepen
		updatePerson(person);
		
		//remove
		notifyGateObservers();
	}	
}
