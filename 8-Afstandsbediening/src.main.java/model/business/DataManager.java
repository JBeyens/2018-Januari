package model.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import values.DefaultSettings;

/*
 * Business layer for retrieval/storage of data from/to database
 */

//remove interface
public final class DataManager {
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

	// Returns all persons with a remote
	public static ArrayList<Person> getAllPersonsWithRemote(){
		log.debug("Asking datalayer to retrieve all persons with remote");
		return (ArrayList<Person>) EntityDAO.PERSON_DAO.executeNamedQuery("Person.allPersonsWithRemote");
	}
	// Returns persons with active remotes by namedquery JPA
	public static ArrayList<Person> getAllPersonsWithActiveRemote(){
		log.debug("Asking datalayer to retrieve all persons with active remote");
		return (ArrayList<Person>) EntityDAO.PERSON_DAO.executeNamedQuery("Person.allPersonsWithActiveRemote");
	}	
	// Returns inactive remotes by namedquery JPA
	public static ArrayList<Remote> getUnusedRemotes(){
		log.debug("Asking datalayer to retrieve all unused remotes");
		return (ArrayList<Remote>) EntityDAO.REMOTE_DAO.executeNamedQuery("Remote.findUnusedRemotes");
	}
	// Returns unused addresses by namedquery JPA
	public static ArrayList<Address> getUnusedAddress(){
		log.debug("Asking datalayer to retrieve all unused addresses");
		return (ArrayList<Address>) EntityDAO.ADDRESS_DAO.executeNamedQuery("Address.findUnusedAddress");
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

	
	// DATABASE DELETE OPERATIONS
	public static void deleteAddress(Address address) {
		log.debug("Asking datalayer to delete " + Address.class.getSimpleName() + " '" + address.toString() + "'");
		EntityDAO.ADDRESS_DAO.delete(address);
	}	
	public static void deletePerson(Person person) {
		log.debug("Asking datalayer to delete " + Person.class.getSimpleName() + " '" + person.toString() + "'");
		EntityDAO.PERSON_DAO.delete(person);
	}
	public static void deleteRemote(Remote remote) {
		log.debug("Asking datalayer to delete " + Remote.class.getSimpleName() + " '" + remote.toString() + "'");
		EntityDAO.REMOTE_DAO.delete(remote);
	}	
}
