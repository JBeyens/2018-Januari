package model.business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.interfaces.AdminSubject;
import model.business.DataManager;
import model.entities.Person;
import model.entities.Remote;
import net.bytebuddy.asm.Advice.Return;
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
		this.setFrequency(DefaultSettings.RANDOM.nextLong());
		this.users = new ArrayList<>();
		loadListenersFromDB();
	}

	/** METHODS **/
	/**
	 * Getter for list of subscribed users
	 **/
	public ArrayList<User> getListeners() {
		log.debug("Getting list of users");
		return users;
	}
	
	/**
	 * Getter & setter for frequency. Setter will also notify all saved 
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
	 * Loads all persons from database and adds Users for each person with Remote and with Remote set to active
	 **/
	private void loadListenersFromDB() {
		ArrayList<Person> allPersons = DataManager.getAllPersonsWithActiveRemote();
		
		for (Person person : allPersons) 
			users.add(new User(person, person.getRemote(), this));				
	}
	
	/**
	 * Add- & remove function for observer pattern
	 **/
	@Override
	public void addObserver(User o) {
		this.users.add(o);
	}
	@Override
	public void removeObserver(User o) {
		this.users.remove(o);
	}

	/**
	 * Notify function for observer pattern
	 **/
	private void notifyAllObservers() {
		for (User user : users) {
			user.update(getFrequency());
		}
	}

	public long checkIdForFrequency(User unknownUser){
		User userFromList = findUserInList(unknownUser);
		if ( isDateInFuture( userFromList.getPerson().getEndOfContract() ) )
			return getFrequency();

		return 0;
	}
	
	private User findUserInList(User unknownUser) {
		for (User user : users) {
			if (user.getRemote().getId() == unknownUser.getRemote().getId() 
					&& user.getPerson().getId() == unknownUser.getPerson().getId())
				return user;
		}
		return null;
	}
	
	/**
	 * Registers inputted person (if not registered). 
	 * @return AddPersonResult - Enum which contains possible outcomes of the situation
	 **/
	public String registerPerson(Person person) {
		if (findUserInList(person) != null) // Check if person is already in list
			return RegisterPersonResult.alreadyInList.toString();
		
		if (person.getRemote() == null)
			return RegisterPersonResult.noRemote.toString();
		
		person.getRemote().setIsActive(true);
		DataManager.updatePerson(person); 
		
		users.add(new User(person, person.getRemote(), this));
		return RegisterPersonResult.succesfull.toString();
	}
	
	/**
	 * Remove existing person (if registered). 
	 * @return AddRemovePersonResult - Enum which contains possible outcomes of the situation
	 */
	public DeactivatePersonResult deActivatePerson(Person person) {
		User user = findUserInList(person);
		
		if (user == null)
			return DeactivatePersonResult.notFound;
		
		user.getPerson().getRemote().setIsActive(false);
		DataManager.updatePerson(person);
		users.remove(user);
		return DeactivatePersonResult.succesfull;
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
			if (user.getPerson() != person) // if (User object does not reference the same person)
				continue;
			
			// We found the correct User! 
			return user;
		}
		
		return null;
	}
}
