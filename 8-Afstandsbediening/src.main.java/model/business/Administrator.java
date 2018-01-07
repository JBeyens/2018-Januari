package model.business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.interfaces.AdminSubject;
import model.business.DataManager;
import model.entities.Person;
import values.RegisterPersonResult;
import values.DefaultSettings;
import values.DeactivatePersonResult;

public class Administrator implements AdminSubject{
	/** FIELDS **/
	private Logger log;
	private long frequency;
	private ArrayList<User> users;
	
	/** CONSTRUCTOR **/
	public Administrator(){
		log = DefaultSettings.getLogger(this.getClass().getSimpleName());
		this.users = new ArrayList<>();
		loadUsersFromDB();
		this.setFrequency(DefaultSettings.RANDOM.nextInt(999999));
	}

	
	
	/** PUBLIC METHODS **/
	/**
	 * Getter for list of subscribed users
	 **/
	public ArrayList<User> getListeners() {
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
	public void checkIdForUpdate(User unknownUser){
		User userFromList = findUserInList(unknownUser.getPerson()); // If found in list --> then is ACTIVE
		if ( userFromList == null ) // If not found in list --> then is NOT ACTIVE
			return;
		if ( isDateInFuture( userFromList.getPerson().getEndOfContract() ) )
			unknownUser.update(frequency);
	}
	
	/**
	 * Add function for observer pattern. Registers inputted person (if not registered). 
	 * @return AddPersonResult - Enum which contains possible outcomes of the situation
	 **/
	@Override
	public RegisterPersonResult registerUser(User user) { 
		// DO NOT return the string of this enum. Playing with MAGIC STRINGS in business code is bad practice!
		if (findUserInList(user.getPerson()) != null) // Check if person is already in list
			return RegisterPersonResult.alreadyInList;
		
		if (user.getRemote() == null)
			return RegisterPersonResult.noRemote;
		
		user.getRemote().setIsActive(true);
		DataManager.updateRemote(user.getRemote()); 
		
		users.add( user );
		return RegisterPersonResult.succesfull;
	}
	
	/**
	 * Remove function for observer pattern. Deactivates & removes inputted person.
	 * @return AddRemovePersonResult - Enum which contains possible outcomes of the situation
	 */
	@Override
	public DeactivatePersonResult deactivateUser(User user) {
		// DO NOT return the string of this enum. Playing with MAGIC STRINGS in business code is bad practice!
		User userFromList = findUserInList(user.getPerson());
		
		if (userFromList == null)
			return DeactivatePersonResult.notFound;
		
		user.getRemote().setIsActive(false);
		DataManager.updateRemote(user.getRemote());
		
		users.remove(userFromList);
		return DeactivatePersonResult.succesfull;
	}	

	
	
	/** PRIVATE METHODS **/

	/**
	 * Notify function for observer pattern
	 **/
	private void notifyAllObservers() {
		for (User user : users) {
			user.update(getFrequency());
		}
	}
	
	/**
	 * Loads all persons from database and adds Users for each person with Remote and with Remote set to active
	 **/
	private void loadUsersFromDB() {
		ArrayList<Person> allPersons = DataManager.getAllPersonsWithActiveRemote();
		
		for (Person person : allPersons) 
			users.add(new User(person, person.getRemote(), this));				
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
	private User findUserInList(Person person)
	{	
		for (User user : users) {
			if (user.getPerson().getId() != person.getId()) // if (User object does not reference the same person)
				continue;
			
			// We found the correct User! 
			return user;
		}
		
		return null;
	}
}
