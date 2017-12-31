package model.idmodule;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import database.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import model.observer.IRemoteObserver;
import model.observer.IRemoteSubject;
import values.DefaultSettings;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel Manages users and their remotes. Will control for who the gate opens
 */
public class GateModule implements IRemoteSubject {
	// FIELDS
	private Logger log;
	private long gateFrequency;
	private ArrayList<Person> persons;
	private ArrayList<IRemoteObserver> userRemotes;
	private EntityDAO entityDAO;
	
	
	// CONSTRUCTOR
	public GateModule() {
		log = DefaultSettings.getLogger();
		entityDAO = EntityDAO.createEntityDAO();
		userRemotes = new ArrayList<IRemoteObserver>();
		loadAllPersons();
	}	
	

	// METHODS	
	
	/** Getter & Setter for 'frequency' **/
	public long getFrequency() {
		return gateFrequency; }
	public void setFrequency(long frequency) {
		this.gateFrequency = frequency; }
	
	/**  Loads all users from database **/	
	public boolean loadAllPersons() {
		persons = entityDAO.readAllPersons();
		for (Person person : persons) {
			if(person.getRemote().getIsActive())
				registerUserRemote(new RemoteModule(person.getRemote()));
		}
		return persons == null ? false : true;
	}
	
	/** Returns ArrayList of Persons. Will load first from database if this list is null. **/	
	public ArrayList<Person> getAllPersons() {
		if (persons == null)
			loadAllPersons();
		
		return persons;
	}
	
	/** Adds new user. Returns true if succesfull, else false. **/
	public boolean addNewUser(Person person) {
		return entityDAO.createPerson(person);
	}
	
	/** Will check the id of the remote and add it to the observers if verified **/
	public void idModule(IRemoteObserver userRemote)
	{		
		log.info("Remote asking access to gate: " + userRemote.toString());
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
		userRemotes.add(userRemote);
		updateRemoteIsActive(userRemote.getRemote(), true);
	}	
	@Override
	public void deactivateUserRemote(IRemoteObserver userRemote) {
		userRemotes.remove(userRemote);
		updateRemoteIsActive(userRemote.getRemote(), false);
	}
	
	private void updateRemoteIsActive(Remote remote, boolean isActive) {
		remote.setIsActive(isActive);
		entityDAO.updateRemote(remote);
	}
}
