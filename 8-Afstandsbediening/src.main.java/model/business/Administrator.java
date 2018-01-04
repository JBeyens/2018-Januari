package model.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.interfaces.AdminObserver;
import model.business.interfaces.AdminSubject;
import model.entities.Person;
import values.RegisterPersonResult;
import values.DefaultSettings;
import values.DeactivatePersonResult;

public class Administrator implements AdminSubject{
	private Logger log;
	private long frequency;
	private ArrayList<AdminObserver> listeners;
	
	public Administrator(){
		log = DefaultSettings.getLogger(this.getClass().getSimpleName());
		this.setFrequency(DefaultSettings.RANDOM.nextLong());
		this.listeners = new ArrayList<>();
		loadListenersFromDB();
	}

	/**
	 * Getter & setter for list of subscribed listeners
	 **/
	public ArrayList<AdminObserver> getListeners() {
		log.debug("Getting list of Listeners");
		return listeners;
	}
	public void setListeners(ArrayList<AdminObserver> listeners) {
		log.debug("Setting list of Listeners");
		this.listeners = listeners;
	}
	
	/**
	 * Getter & setter for frequency
	 **/
	public long getFrequency() {
		log.debug("Getting frequency");
		return frequency;
	}
	public void setFrequency(long frequency) {
		log.debug("Setting frequency");
		this.frequency = frequency;
	}

	
	/**
	 * Loads all persons from database and adds Users for each person with Remote and with Remote set to active
	 **/
	private void loadListenersFromDB() {
		ArrayList<Person> allPersons = DataManager.getAllPersons();
		for (Person person : allPersons) {
			if ( person.getRemote() != null && person.getRemote().getIsActive() )
				listeners.add(new User(person));	
		}		
	}
	
	/**
	 * Add- & remove function for observer pattern
	 **/
	@Override
	public void addObserver(AdminObserver o) {
		this.listeners.add(o);
	}
	@Override
	public void removeObserver(AdminObserver o) {
		this.listeners.remove(o);
	}

	/**
	 * Notify function for observer pattern
	 **/
	@Override
	public void notifyAllObservers() {
		for (AdminObserver adminObserver : listeners) {
			adminObserver.update(getFrequency());
		}
	}

	
	/**
	 * Registers inputted person (if not registered). 
	 * @return AddPersonResult - Enum which contains possible outcomes of the situation
	 **/
	public RegisterPersonResult registerPerson(Person person) {
		if (findUserInList(person) != null) // Check if person is already in list
			return RegisterPersonResult.AlreadyInList;
		
		if (person.getRemote() == null)
			return RegisterPersonResult.NoRemote;
		
		person.getRemote().setIsActive(true);
		DataManager.updatePerson(person); 
		
		listeners.add(new User(person));
		return RegisterPersonResult.Succesfull;
	}
	
	/**
	 * Remove existing person (if registered). 
	 * @return AddRemovePersonResult - Enum which contains possible outcomes of the situation
	 */
	public DeactivatePersonResult deActivatePerson(Person person) {
		User user = findUserInList(person);
		
		if (user == null)
			return DeactivatePersonResult.NotFound;
		
		if (user.getPerson().getRemote() == null) // if (User has a remote object)
			return DeactivatePersonResult.NoRemote;
		
		user.getPerson().getRemote().setIsActive(false);
		DataManager.updatePerson(person);
		listeners.remove(user);
		return DeactivatePersonResult.Succesfull;
	}	
	
	private User findUserInList(Person person)
	{	
		for (AdminObserver o : listeners) {
			User user = (User) o;
			
			if (user == null) // if (AdminObserver was not castable to User)
				continue;
			if (user.getPerson() != person) // if (User object does not reference the same person)
				continue;
			
			// We found the correct User! 
			return user;
		}
		
		return null;
	}
}
