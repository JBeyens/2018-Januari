package model.idmodule;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.entities.EntityDAO;
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
	// FIELDS
	private Logger log;
	private long gateFrequency;
	private ArrayList<Person> persons;
	private ArrayList<Remote> remotes;
	private ArrayList<IRemoteObserver> activeRemotes;
	
	
	// CONSTRUCTOR
	public GateModule() {
		//log = DefaultSettings.getLogger();
		persons = new ArrayList<>();
		remotes = new ArrayList<>();
		activeRemotes = new ArrayList<>();
		
		loadAllRemotes();
		loadAllPersons();
	}	
	

	// METHODS	
	
	/** Getter & Setter for 'frequency' **/
	public long getFrequency() {
		return gateFrequency; }
	public void setFrequency(long frequency) {
		this.gateFrequency = frequency; }
	
	/**  Loads all remotes from database **/	
	private boolean loadAllRemotes() {
		remotes = (ArrayList<Remote>) EntityDAO.REMOTE_DAO.findAll();
		for (Remote remote : remotes) {
			if (remote.getPerson() != null) {
				persons.add(remote.getPerson());
			}
			if(remote.getIsActive()) {
				registerUserRemote(new RemoteModule(remote));
			}
		}
		return remotes == null ? false : true;
	}
		
	/**  Loads all users from database **/	
	private boolean loadAllPersons() {
		ArrayList<Person> allPersons = (ArrayList<Person>) EntityDAO.PERSON_DAO.findAll();
		
		if (allPersons == null)
			return false;
		
		if (persons == null || persons.size() == 0) {
			persons = allPersons;
			return true;
		}
		
		ArrayList<Integer> personIds = new ArrayList<Integer>();
		for(Person person : persons) {
			personIds.add(person.getId());
		}
		
		for (Person person : allPersons) {
			if(!personIds.contains(person.getId())) {
				persons.add(person);
			}
		}
		return true;
	}
	
	/** Returns ArrayList of Persons. Will load first from database if this list is null. **/	
	public ArrayList<Person> getAllPersons() {
		if (persons == null)
			loadAllPersons();
		
		return persons;
	}	
	
	/** Returns ArrayList of Persons. Will load first from database if this list is null. **/	
	public ArrayList<Remote> getAllRemotes() {
		if (remotes == null)
			loadAllRemotes();
		
		return remotes;
	}
	
	/** Adds new user **/
	public void addNewUser(Person person) {
		EntityDAO.PERSON_DAO.create(person);
	}
	
	/** Will check the id of the remote and add it to the observers if verified **/
	public void idModule(IRemoteObserver userRemote)
	{		
		log.info("Remote asking access to gate: " + userRemote.toString());
		for(IRemoteObserver remote : activeRemotes) {
			if (userRemote.sendSerialId().equals(remote.sendSerialId())) {
				log.trace("Checking... " + userRemote.sendSerialId() + " NOT EQUALS " + remote.sendSerialId());
				continue; }

			log.debug("Checking... " + userRemote.sendSerialId() + " EQUALS " + remote.sendSerialId());
			log.debug("-> setIsActive = true");
			userRemote.getRemote().setIsActive(true);
			log.debug("-> updating frequency");
			userRemote.updateFrequency(getFrequency());
			return;
		}
		
		log.debug("Checking...  Serial Id not found in registered serial ids");
		log.debug("-> setIsActive = false");
		userRemote.getRemote().setIsActive(false);
	}
	
	/** Observer pattern - Updates all remotes who have subscribed to UserManager. **/
	public void SendFrequencyToRemotes()
	{
		long freq = getFrequency();
		for(IRemoteObserver userRemote : activeRemotes)
		{
			userRemote.updateFrequency(freq);
		}
	}


	/**
	 * Observer pattern - Add- & Remove observers
	 **/
	@Override
	public void registerUserRemote(IRemoteObserver userRemote) {
		activeRemotes.add(userRemote);
		updateRemoteIsActive(userRemote.getRemote(), true);
	}	
	@Override
	public void deactivateUserRemote(IRemoteObserver userRemote) {
		activeRemotes.remove(userRemote);
		updateRemoteIsActive(userRemote.getRemote(), false);
	}
	
	private void updateRemoteIsActive(Remote remote, boolean isActive) {
		remote.setIsActive(isActive);
		EntityDAO.REMOTE_DAO.update(remote);
	}
}
