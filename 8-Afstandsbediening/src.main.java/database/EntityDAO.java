package database;

import java.util.ArrayList;

import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

public class EntityDAO {
	private static EntityDAO entityDAO;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;

	private EntityDAO() {		
		personDAO = new GenericDAO<>(Person.class);
		remoteDAO = new GenericDAO<>(Remote.class);
		addressDAO = new GenericDAO<>(Address.class);
	}
	
	public static EntityDAO createEntityDAO() {
		if (entityDAO == null)
			entityDAO = new EntityDAO();
		
		return entityDAO;		
	}
	
	// PERSON
	public boolean createPerson(Person person) {
		try {
			personDAO.create(person);
			return true;
		} 
		catch (Exception e) {
			return false;
		}	
	}
	public ArrayList<Person> readAllPersons() {
		return (ArrayList<Person>)personDAO.findAll();
	}
	
	// REMOTE	
	/* Read*/
	public ArrayList<Remote> readAllRemotes(){
		return (ArrayList<Remote>)remoteDAO.findAll();
	}
	/* Update */
	public boolean updateRemote(Remote remote) {
		try {
			remoteDAO.update(remote);
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	// ADRESS	
	public Address readAdress(int id) {
		return addressDAO.findOne(id);
	}
}
