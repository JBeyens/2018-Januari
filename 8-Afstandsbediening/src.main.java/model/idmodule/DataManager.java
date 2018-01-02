package model.idmodule;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import values.DefaultSettings;

/*
 * Business layer for retrieval & storage of data
 */
public class DataManager {
	// FIELDS
	private Logger log;
	// FIELDS
	private ArrayList<Address> addresses;
	private ArrayList<Person> persons;
	private ArrayList<Remote> remotes;
	private ArrayList<Remote> activeRemotes;
	
	public DataManager() {
		log = DefaultSettings.getLogger("DataManager");
	}

	/*
	 * Returns unused addresses by comparing persons & addresses
	 */
	public ArrayList<Address> getUnusedAddress(){
		ArrayList<Address> unusedAdresses = new ArrayList<>();
		ArrayList<Integer> registeredAdressIds = new ArrayList<>();
		
		for (Person person : persons) {
			if (person.getAdress() != null)
				registeredAdressIds.add(person.getAdress().getId());			
		}
		
		for (Address adr : addresses) {
			if (!registeredAdressIds.contains(adr.getId()))
				unusedAdresses.add(adr);
		}
		
		return unusedAdresses;
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
		if (persons == null)
			readAllPersons();		
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
	 *   Loads all remotes from database and adds active remotes to seperate list. 
	 *   @Return Boolean - true if remotes has a list at end of operation, else false.
	 **/	
	private boolean readAllRemotes() {
		remotes = (ArrayList<Remote>) EntityDAO.REMOTE_DAO.findAll();
		for (Remote remote : remotes) {
			if(remote.getIsActive()) {
				activeRemotes.add(remote);
			}
		}
		return remotes != null;
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
	 *   Loads all addresses from database. 
	 *   @Return Boolean - true if list exists at end of operation, else false.
	 **/	
	private boolean readAllAddresses() {
		addresses = (ArrayList<Address>) EntityDAO.ADDRESS_DAO.findAll();

		return addresses != null;
	}
	
	// DATABASE UPDATE OPERATIONS
	public void updateRemote(Remote remote) {
		EntityDAO.REMOTE_DAO.update(remote);
	}	
	public void updatePerson(Person person) {
		EntityDAO.PERSON_DAO.update(person);
	}
	

	
}
