package model.business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.interfaces.AdminObserver;
import model.business.interfaces.AdminSubject;
import model.business.interfaces.IRemoteModule;
import model.entities.Person;
import model.entities.Remote;
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
			return RegisterPersonResult.alreadyInList;
		
		if (person.getRemote() == null)
			return RegisterPersonResult.noRemote;
		
		person.getRemote().setIsActive(true);
		DataManager.updatePerson(person); 
		
		listeners.add(new User(person));
		return RegisterPersonResult.succesfull;
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
	

	
	/** 
	 * Method to check wether a remote with certain serialNumber can receive the correct frequency
	 * @return long - frequency of the gate, is zero if the serialNumber is invalid or not authorized
	 **/
	public long askFrequency(String serialNumber)
	{		
		log.info("Remote asking access to gate has serial number: " + serialNumber);
		User user = findUserInList(serialNumber);
		if (user == null) {
			log.info("Not registered, zero frequency returned!" );
			return 0;
		}
		
		Person person = user.getPerson();
		Remote remote = person.getRemote();
		Date now  = Date.valueOf(LocalDate.now());			
		if (!remote.getIsActive()) {
			log.trace("- Remote registered to " + person.toString() + ", but not activated!");
		} else if (person.getEndOfContract().before(now)) {
			log.trace("- Remote registered to " + person.toString() + ", but user contract has expired!");
		} else {
			log.trace("- Remote registered to " + person.toString() + ", contract valid! Updating remote!");
			user.update(getFrequency());
			return getFrequency();
		}				
		return 0;
	}
	
	/**
	 * Finds User in list that has the same reference to person as the inputted parameter
	 **/
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

	
	/**
	 * Finds User in list that has a remote with the same serial number registered to it
	 **/
	private User findUserInList(String serialNumber)
	{	
		for (AdminObserver o : listeners) {
			User user = (User) o;
			
			if (user == null || user.getPerson() == null || user.getPerson().getRemote() == null) 
				continue;
			String serial = user.getPerson().getRemote().getSerialNumber();
			if ( serial == null || !serial.equals(serialNumber)) { // if (User object does not reference the same person)
				log.trace("- Different serial number for remote registered to " + user.getPerson().toString()
						+ ". Serial number is " + serial == null ? "" : serial.toString());	
				continue;
			}
			
			// We found the correct User!
			log.trace("- Serial number registered to " + user.getPerson().toString() + "!");	 
			return user;
		}

		log.trace("- No user found with remote with serial number " + serialNumber.toString() + " linked to it!");	
		return null;
	}
}
