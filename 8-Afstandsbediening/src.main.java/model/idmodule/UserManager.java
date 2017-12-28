package model.idmodule;

import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.GenericDAO;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import model.observer.IRemoteObserver;
import model.observer.IRemoteSubject;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel Manages users and their remotes. Will control for who the gate opens
 */
public class UserManager implements IRemoteSubject {
	private long frequency;
	private EntityManagerFactory emFactory;
	private ArrayList<Person> persons;
	private ArrayList<IRemoteObserver> userRemotes;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;
	
	
	// CONSTRUCTOR
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
	
	



	// METHODS	
	
	/** Getter & Setter for 'frequency' **/
	public long getFrequency() {
		return frequency; }
	public void setFrequency(long frequency) {
		this.frequency = frequency; }
	
	/**  Loads all users from database **/	
	public boolean LoadAllPersons() 
	{
		persons = (ArrayList<Person>)personDAO.findAll();
		return persons == null ? false : true;
	}
	
	/** Returns ArrayList of Persons. Will load first from database if this list is null. **/	
	public ArrayList<Person> GetAllPersons()
	{
		if (persons == null)
			LoadAllPersons();
		
		return persons;
	}
	
	/** Adds new user. Returns true if succesfull, else false. **/
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
	
	
	/** Observer pattern - Updates all remotes who have subscribed to UserManager. **/
	public void SendNewFrequency(long frequency)
	{
		setFrequency(frequency);
		for(IRemoteObserver userRemote : userRemotes)
		{
			userRemote.UpdateFrequency(frequency);
		}
	}


	/**
	 * Observer pattern - Add- & Remove observers
	 **/
	@Override
	public void registerUserRemote(IRemoteObserver userRemote) {
		userRemotes.remove(userRemote);
	}	
	@Override
	public void deactivateUserRemote(IRemoteObserver userRemote) {
		userRemotes.add(userRemote);
	}
}
