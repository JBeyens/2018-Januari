package model.idmodule;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.GenericDAO;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel Manages users and their remotes. Will control for who the gate opens
 */
public class UserManager {
	private EntityManagerFactory emFactory;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;
	/**
	 * 
	 */
	public UserManager() 
	{
		/*
		 * EntityManagerFactory thread safe/heavy resource
		 * Only 1 creating
		 */
		emFactory = Persistence.createEntityManagerFactory("Afstandsbediening");
		
		personDAO = new GenericDAO<>(Person.class, emFactory);
		remoteDAO = new GenericDAO<>(Remote.class, emFactory);
		addressDAO = new GenericDAO<>(Address.class, emFactory);
	}
	
	/**
	 * Adds new user. Returns true if succesfull, else false.
	 **/
	public boolean AddNewUser(Person person)
	{
		try {
			personDAO.create(person);
			return true;
		} 
		catch (Exception e) {
			return false;
		}		
	}
	
	public boolean RegisterUserRemote(Person person, Remote remote) 
	{
		try {
			person.setRemote(remote);
			remote.setIsActive(true);
			personDAO.update(person);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean DeactivateUserRemote(Person person) 
	{
		try {
			person.getRemote().setIsActive(false);
			personDAO.update(person);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void SendNewFrequency(long frequency)
	{
		
	}
}
