package model.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import model.interfaces.IGateObserver;
import model.interfaces.IGateSubject;
import values.DefaultSettings;

/*
 * Business layer for retrieval & storage of data
 */
public class DataManager implements IGateSubject{
	// FIELDS
	private Logger log;
	// FIELDS
	private ArrayList<Address> addresses;
	private ArrayList<Person> persons;
	private ArrayList<Remote> remotes;
	private ArrayList<IGateObserver> gateModules;
	
	public DataManager() {
		log = DefaultSettings.getLogger("DataManager");
		gateModules = new ArrayList<>();
		readAllPersons();
		readAllAddresses();
		readAllRemotes();
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
		if (addresses == null)
			readAllAddresses();		
		return addresses;
	}
		
	/**
	 *  Returns ArrayList of Persons. Will load first from database if this list is null. 
	 **/	
	public ArrayList<Person> getAllPersons() {
		if (persons == null) {
			readAllPersons();
			notifyGateObservers();
		}		
		return persons;
	}	
	
	/**
	 *  Returns ArrayList of Persons. Will load first from database if this list is null.
	 **/	
	public ArrayList<Remote> getAllRemotes() {
		if (remotes == null)
			readAllRemotes();		
		return remotes;
	}
	


	@Override
	public void registerGate(IGateObserver gateModule) {
		gateModules.add(gateModule);
		notifyGateObservers();
	}
	@Override
	public void unregisterGate(IGateObserver gateModule) {
		gateModules.add(gateModule);
		notifyGateObservers();
	}
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
		persons.add(person); 
		if (person.getRemote() != null)
			person.getRemote().setIsActive(true);
		
		updatePerson(person); // Database
		notifyGateObservers();
	}
	
	/**
	 * Remove existing person (if registered)
	 */
	public void deActivatePerson(Person person) {
		persons.remove(person);
		if (person.getRemote() != null)
			person.getRemote().setIsActive(false);
		
		updatePerson(person);
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
	 *   Loads all addresses from database. 
	 *   @Return Boolean - true if list exists at end of operation, else false.
	 **/	
	private boolean readAllAddresses() {
		addresses = (ArrayList<Address>) EntityDAO.ADDRESS_DAO.findAll();
		return addresses != null;
	}
	/**
	 *   Loads all users from database. 
	 *   @Return Boolean - true if list exists at end of operation, else false.
	 **/	
	private boolean readAllPersons() {
		persons = (ArrayList<Person>) EntityDAO.PERSON_DAO.findAll();
		return persons != null;
	}
	/**
	 *   Loads all remotes from database. 
	 *   @Return Boolean - true if remotes has a list at end of operation, else false.
	 **/	
	private boolean readAllRemotes() {
		remotes = (ArrayList<Remote>) EntityDAO.REMOTE_DAO.findAll();
		return remotes != null;
	}

	
	// DATABASE UPDATE OPERATIONS
	public void updateRemote(Remote remote) {
		EntityDAO.REMOTE_DAO.update(remote);
	}	
	public void updatePerson(Person person) {
		EntityDAO.PERSON_DAO.update(person);
	}
}
