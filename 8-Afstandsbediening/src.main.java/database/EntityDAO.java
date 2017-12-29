package database;

import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

public class EntityDAO {
	private static EntityDAO entityDAO;
	private EntityManagerFactory emFactory;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;

	private EntityDAO() {
		/*
		 * EntityManagerFactory thread safe/heavy resource
		 * Only 1 creating
		 */
		emFactory = Persistence.createEntityManagerFactory("Afstandsbediening");
		
		personDAO = new GenericDAO<>(Person.class, emFactory);
		remoteDAO = new GenericDAO<>(Remote.class, emFactory);
		addressDAO = new GenericDAO<>(Address.class, emFactory);
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
	public ArrayList<Person> findAllPersons() {
		return (ArrayList<Person>)personDAO.findAll();
	}
	
	// REMOTE
	public boolean updateRemote(Remote remote) {
		try {
			remoteDAO.update(remote);
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	public Address getAdress(int id) {
		return addressDAO.findOne(id);
	}
}
