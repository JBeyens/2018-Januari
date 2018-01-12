package model.business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import database.EntityDAO;
import model.business.interfaces.AdminSubject;
import model.business.DataManager;
import model.entities.Person;
import model.entities.Remote;
import utility.Utility;
import values.UserRegistrationResult;
import values.UserDeactivationResult;

public class Administrator implements AdminSubject{
	/** FIELDS **/
	private Logger log;
	private long frequency;
	private ArrayList<PersonWrapper> users;
	
	/** CONSTRUCTOR **/
	public Administrator(){
		log = Utility.getLogger(this.getClass().getSimpleName());
		this.users = new ArrayList<>();
		loadUsersFromDB();
		this.setFrequency(Utility.RANDOM.nextInt(999999));
	}

	
	
	/** PUBLIC METHODS **/
	/**
	 * Getter for list of subscribed users
	 **/
	public ArrayList<PersonWrapper> getListeners() {
		log.debug("Getting list of users");
		return users;
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

	/**
	 * Checks the Id of the inputted user. If it is registered & has valid contract, the user frequency will be correctly updated.
	 **/
	public boolean checkIdForUpdate(PersonWrapper unknownUser){
		PersonWrapper userFromList = findUserInList(unknownUser); // If found in list --> then is ACTIVE
		//if ( userFromList == null ) // If not found in list --> then is NOT ACTIVE
			//return false;;
		if (userFromList != null && isDateInFuture( userFromList.getEndOfContract() ) ){
			unknownUser.update(frequency);
			return true;
		}
		return false;
	}
	
	/**
	 * Add function for observer pattern. Registers inputted person (if not registered). 
	 * @return AddPersonResult - Enum which contains possible outcomes of the situation
	 **/
	@Override
	public UserRegistrationResult registerUser(PersonWrapper user) { 
		// DO NOT return the string of this enum. Playing with MAGIC STRINGS in business code is bad practice!
		if (findUserInList(user) != null) // Check if person is already in list
			return UserRegistrationResult.alreadyInList;
		
		if (user.getRemote() == null)
			return UserRegistrationResult.noRemote;
		
		Remote remote = EntityDAO.REMOTE_DAO.findOne(user.getRemote().getId());
		remote.setIsActive(true);
		DataManager.updateRemote(remote); 
		
		users.add( user );
		return UserRegistrationResult.succesfull;
	}
	
	/**
	 * Remove function for observer pattern. Deactivates & removes inputted person.
	 * @return AddRemovePersonResult - Enum which contains possible outcomes of the situation
	 */
	@Override
	public UserDeactivationResult deactivateUser(PersonWrapper user) {
		// DO NOT return the string of this enum. Playing with MAGIC STRINGS in business code is bad practice!
		PersonWrapper userFromList = findUserInList(user);
		
		if (userFromList == null)
			return UserDeactivationResult.notFound;
		
		// 2x in case they don't reference the same user instance
		Remote remote = EntityDAO.REMOTE_DAO.findOne(user.getRemote().getId());
		remote.setIsActive(false);
		DataManager.updateRemote(remote); 
		
		users.remove(userFromList);
		return UserDeactivationResult.succesfull;
	}	
	
	/**
	 * Throws away all registered users and reloads the list of users from database
	 */
	public void refreshUsersFromDB() {
		users.clear();
		loadUsersFromDB();
	}
	
	
	/** PRIVATE METHODS **/

	/**
	 * Notify function for observer pattern
	 **/
	private void notifyAllObservers() {
		for (PersonWrapper user : users) {
			user.update(getFrequency());
		}
	}
	
	/**
	 * Loads all persons from database and adds Users for each person with Remote and with Remote set to active
	 **/
	public void loadUsersFromDB() {
		ArrayList<Person> allPersons = DataManager.getAllPersonsWithActiveRemote();
		
		for (Person person : allPersons) 
			users.add(new PersonWrapper(person, this));				
	}
	
	/**
	 * @return Boolean - Is the inputted date is in the future
	 **/
	private boolean isDateInFuture(Date date){
		int result = date.compareTo(Date.valueOf(LocalDate.now()));		
		return result >= 0;
	}	
	
	/**
	 * @return User - Finds User in list that has the same reference to person as the inputted parameter
	 **/
	private PersonWrapper findUserInList(Person person)
	{	
		for (PersonWrapper user : users) {
			if (user.getId() != person.getId()) // if (User object does not reference the same person)
				continue;
			
			// We found the correct User! 
			return user;
		}
		
		return null;
	}
}
