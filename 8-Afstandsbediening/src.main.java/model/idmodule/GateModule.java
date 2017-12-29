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
public class GateModule implements IRemoteSubject {
	private long gateFrequency;
	private EntityManagerFactory emFactory;
	private ArrayList<Person> persons;
	private ArrayList<IRemoteObserver> userRemotes;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;
	
	
	// CONSTRUCTOR
	public GateModule() 
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
		return gateFrequency; }
	public void setFrequency(long frequency) {
		this.gateFrequency = frequency; }
	
	/**  Loads all users from database **/	
	public boolean loadAllPersons() 
	{
		persons = (ArrayList<Person>)personDAO.findAll();
		return persons == null ? false : true;
	}
	
	/** Returns ArrayList of Persons. Will load first from database if this list is null. **/	
	public ArrayList<Person> getAllPersons()
	{
		if (persons == null)
			loadAllPersons();
		
		return persons;
	}
	
	/** Adds new user. Returns true if succesfull, else false. **/
	public boolean addNewUser(Person person)
	{
		try {
			personDAO.create(person);
			return true;
		} 
		catch (Exception e) {
			return false;
		}		
	}
	
	/** Will check the id of the remote and add it to the observers if verified **/
	public void idModule(IRemoteObserver userRemote)
	{		
		for(IRemoteObserver remote : userRemotes) {
			if (userRemote.sendSerialId() != remote.sendSerialId())
				continue;
			
			registerUserRemote(userRemote);
			userRemote.updateFrequency(getFrequency());
			return;
		}
	}
	
	/** Observer pattern - Updates all remotes who have subscribed to UserManager. **/
	public void SendFrequencyToRemotes()
	{
		long freq = getFrequency();
		for(IRemoteObserver userRemote : userRemotes)
		{
			userRemote.updateFrequency(freq);
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
