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
public class DataManager implements IGateSubject{
	// FIELDS
	private Logger log;
	// FIELDS
	//Remove fields
	private ArrayList<Person> persons;
	private ArrayList<IGateObserver> gateModules;
	
	public DataManager() {
		log = DefaultSettings.getLogger("DataManager");
		
		//Remove
		gateModules = new ArrayList<>();
		//remove
		readAllPersons();
	}
	
	/*
	 * Returns inactive remotes by namedquery JPA
	 */
	public ArrayList<Remote> getInactiveRemotes(){
		return (ArrayList<Remote>) EntityDAO.REMOTE_DAO.executeNamedQuery("inactiveRemotes");
	}

	/*
	 * Returns unused addresses by namedquery JPA
	 */
	public ArrayList<Address> getUnusedAddress(){
		return (ArrayList<Address>) EntityDAO.ADDRESS_DAO.executeNamedQuery("findUnusedAddress");
	} 
	
	/**
	 * Returns ArrayList of Addresses. Will load first from database if this list is null. 
	 **/	
	public ArrayList<Address> getAllAddresses() {
		return (ArrayList<Address>) EntityDAO.ADDRESS_DAO.findAll();
	}
		
	/**
	 *  Returns ArrayList of Persons. Will load first from database if this list is null. 
	 **/	
	public ArrayList<Person> getAllPersons() {
		persons = (ArrayList<Person>) EntityDAO.PERSON_DAO.findAll();
		//Remove
		notifyGateObservers();
		
		//add return to line above
		return persons;
	}	
	
	/**
	 *  Returns ArrayList of Persons. Will load first from database if this list is null.
	 **/	
	public ArrayList<Remote> getAllRemotes() {
		return (ArrayList<Remote>) EntityDAO.REMOTE_DAO.findAll();
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
	
	// DATABASE READ OPERATIONS
	public Address readAddress(Integer id) {
		log.trace("Asking datalayer to retrieve 'Address' with id = " + id);
		return EntityDAO.ADDRESS_DAO.findOne(id);
	}	
	public Person readPerson(Integer id) {
		log.trace("Asking datalayer to retrieve 'Person' with id = " + id);
		return  EntityDAO.PERSON_DAO.findOne(id);
	}	
	public Remote readRemote(Integer id) {
		log.trace("Asking datalayer to retrieve 'Remote' with id = " + id);
		return  EntityDAO.REMOTE_DAO.findOne(id);
	}

	/**
	 *   Loads all users from database. 
	 *   @Return Boolean - true if list exists at end of operation, else false.
	 **/
	//currently not necessary
	private boolean readAllPersons() {
		persons = (ArrayList<Person>) EntityDAO.PERSON_DAO.findAll();
		return persons != null;
	}


	
	// DATABASE UPDATE OPERATIONS
	public void updateRemote(Remote remote) {
		EntityDAO.REMOTE_DAO.update(remote);
	}	
	public void updatePerson(Person person) {
		EntityDAO.PERSON_DAO.update(person);
	}
}
