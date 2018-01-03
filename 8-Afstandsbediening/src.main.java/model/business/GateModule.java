package model.business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.entities.Person;
import model.interfaces.IGateModule;
import model.interfaces.IGateObserver;
import model.interfaces.IRemoteModule;
import values.DefaultSettings;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel Manages users and their remotes. Will control for who the gate opens
 */
public class GateModule implements IGateObserver, IGateModule {
	// FIELDS
	private Logger log = DefaultSettings.getLogger("GateModule");
	private long gateFrequency;
	private ArrayList<Person> persons;
	
	
	// CONSTRUCTOR
	public GateModule() {
		gateFrequency = DefaultSettings.RANDOM.nextLong();
		persons = new ArrayList<>();
	}	
	

	// METHODS	
	
	/**
	 *  Getter & Setter for 'frequency' 
	 **/
	@Override // IGateModule
	public long getFrequency() {
		return gateFrequency; }
	public void setFrequency(long frequency) {
		this.gateFrequency = frequency; }
	
	/**
	 *  Getter & Setter for 'persons' 
	 **/
	public ArrayList<Person> getPersons() {
		return persons; }
	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons; }	
	
	/*
	 * Add & Remove from list
	 */
	public void addPerson(Person person){
		persons.add(person);
	}
	
	public void removePerson(Person person){
		persons.remove(person);
	}

	@Override // IGateObserver
	public void handleNotification(long frequency, ArrayList<Person> persons) {
		setFrequency(frequency);
		setPersons(persons);
	}
	
	/** 
	 * Will check the id of the remote and add it to the observers if verified. This is the IdModule of the gate.
	 **/
	@Override // IGateModule
	public void verifyAndUpdateFrequencyRemote(IRemoteModule userRemote)
	{		
		LocalDate date  = LocalDate.now();	
		Date now = Date.valueOf(date);	
		
		log.info("Remote asking access to gate has serial number: " + userRemote.getSerialNumber());
		for(Person person : persons) {
			if (person.getRemote() == null)
				log.trace("- No remote registered to " + person.toString());			
			else if (!person.getRemote().getSerialNumber().equals(userRemote.getSerialNumber()))
				log.trace("- Different serial number for remote registered to " + person.toString() + ". Serial number is " + userRemote.getSerialNumber());	
			else if (!person.getRemote().getIsActive()) 
				log.trace("- Remote registered to " + person.toString() + ", but not activated!");
			else if (person.getEndOfContract().before(now))
				log.trace("- Remote registered to " + person.toString() + ", but user contract has expired!");
			else {
				log.trace("- Remote registered to " + person.toString() + ", contract valid so updating frequency");
				userRemote.setFrequency(getFrequency());
			}				
		}
	}
}
