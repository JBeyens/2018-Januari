package model.business.test;

import org.junit.Before;
import org.junit.Test;

import model.business.GateModule;
import model.business.RemoteModule;
import model.entities.Person;
import model.entities.Remote;
import model.interfaces.IRemoteModule;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * 	@Author Jef Beyens & Ben Vandevorst
	@Datum 02/01/2018
	@Project Afstandsbediening
	@Doel Test of GateModule
 */
public class GateModuleTest {
	private GateModule gateModule;
	private ArrayList<Person> personList;
	private long newFrequency;
	
	@Before
	public void setUp(){
		gateModule = new GateModule();
		personList = new ArrayList<>();
		newFrequency = 789;
	}
	
	@Test
	public void HandleNotification_Should_Update_Frequency_And_Persons() {
		personList.add(new Person());
		gateModule.handleNotification(newFrequency, personList);
		
		assertEquals(newFrequency, gateModule.getFrequency());
		assertEquals(personList, gateModule.getPersons());
	}
	
	@Test
	public void Verify_And_Update_Frequency_Remote_When_Remote_Is_Not_Null(){
		
	}
		// TODO: Test verifyAndUpdateFrequencyRemote
		/** 
		 * Will check the id of the remote and add it to the observers if verified. This is the IdModule of the gate.
		 *
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
		
		private class RemoteModuleMock GateModuleTest() {
			// TODO Auto-generated constructor stub
		}*/
	
		@SuppressWarnings("unused")
		private class RemoteModuleMock implements IRemoteModule{
			private Remote remote;
			
			public RemoteModuleMock(Remote remote){
				this.setRemote(remote);
			}
			@Override
			public String getSerialNumber() {
				return getRemote().getSerialNumber();
			}

			@Override
			public void setFrequency(long frequency) {
				getRemote().setFrequency(frequency);
			}
			
			public Remote getRemote() {
				return remote;
			}
			public void setRemote(Remote remote) {
				this.remote = remote;
			}
			
		}
	}

