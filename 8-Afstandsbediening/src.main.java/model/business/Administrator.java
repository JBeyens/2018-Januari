package model.business;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import model.business.interfaces.AdminObserver;
import model.business.interfaces.AdminSubject;
import model.entities.Person;
import model.repository.DataManager;
import utility.Utility;
import values.UserRegistrationResult;
import values.UserDeactivationResult;

public class Administrator implements AdminSubject{
	/** FIELDS **/
	private Logger log;
	private long frequency;
	private HashSet<AdminObserver> observers;
	
	/** CONSTRUCTOR **/
	public Administrator(){
		log = Utility.getLogger(this.getClass().getSimpleName());
		this.observers = new HashSet<>();
		setRandomFrequency();
		loadUsersFromDB();
	}

	
	
	/** PUBLIC METHODS **/
	/**
	 * Getter for list of subscribed users
	 **/
	public HashSet<AdminObserver> getListeners() {
		log.debug("Getting list of users");
		return observers;
	}
	
	/**
	 * Getter & setter for frequency. Setter will also notify all registered users.
	 **/
	public long getFrequency() {
		log.debug("Getting frequency");
		return frequency;
	}
	public void setFrequency(long frequency) {
		log.debug("Setting frequency");
		this.frequency = frequency;
		notifyAllObservers();
	}
	public void setRandomFrequency() {
		this.setFrequency(Utility.RANDOM.nextInt(999999));
	}

	/**
	 * Checks the Id of the inputted user. If it is registered & has valid contract, the user frequency will be correctly updated.
	 **/
	public boolean checkIdForUpdate(AdminObserver user){
		if (!findUserInList(user))
			return false;
		
		user.update(getFrequency());
		return true;
	}
	
	/**
	 * Add function for observer pattern. Registers inputted person (if not registered). 
	 * @return AddPersonResult - Enum which contains possible outcomes of the situation
	 **/
	@Override
	public UserRegistrationResult registerUser(PersonWrapper user) { 
		log.debug("Registering user");
		if (findUserInList(user)) // Check if person is already in list
			return UserRegistrationResult.alreadyInList;
		
		if (user.getRemote() == null)
			return UserRegistrationResult.noRemote;
		
		DataManager.activateRemote(user.getRemote().getId(), frequency); 
		Person newPerson = user.getId() != 0 ? DataManager.getPerson(user.getId()) : DataManager.updatePerson(user);
		observers.add( new PersonWrapper(newPerson, this) );
		return UserRegistrationResult.succesfull;
	}
	
	/**
	 * Remove function for observer pattern. Deactivates & removes inputted person.
	 * @return AddRemovePersonResult - Enum which contains possible outcomes of the situation
	 */
	@Override
	public UserDeactivationResult deactivateUser(PersonWrapper userWithRemote) {
		log.debug("Deactivating user");
		AdminObserver observerToRemove = null;
		//observers.remove(user); -> does not work since 'user' has no object reference from list 'observers'
		for (AdminObserver observer : observers) {
			if (!observer.getSerial().equals(userWithRemote.getSerial()))
				continue;
			
			observerToRemove = observer;
			break;
		}
		
		DataManager.deActivateRemote(userWithRemote.getRemote().getId());
		return observers.remove(observerToRemove) ? UserDeactivationResult.succesfull : UserDeactivationResult.notFound;
	}	

	/**
	 * Throws away all registered users and reloads the list of users from database
	 */
	public void refreshUsersFromDB() {
		observers.clear();
		loadUsersFromDB();
	}
	
	
	/** PRIVATE METHODS **/

	/**
	 * Notify function for observer pattern
	 **/
	private void notifyAllObservers() {
		for (AdminObserver user : observers) {
			user.update(getFrequency());
		}
	}
	
	/**
	 * Loads all persons from database and adds Users for each person with Remote and with Remote set to active
	 **/
	private void loadUsersFromDB() {
		ArrayList<Person> allPersons = DataManager.getAllPersonsWithActiveRemote();
		
		for (Person person : allPersons) 
			this.registerUser(new PersonWrapper(person, this));
	}
	
	/**
	 * @return Boolean - Is the inputted AdminObserver found in the list of observers?
	 **/
	private boolean findUserInList(AdminObserver o) {
		return observers.stream().anyMatch(x -> x.getSerial().equals(o.getSerial()));
	}
}
