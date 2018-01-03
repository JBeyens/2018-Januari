package model.business.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import model.business.GateModule;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import model.interfaces.IRemoteModule;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 02/01/2018
 * @Project Afstandsbediening
 * @Doel Test of GateModule
 */
public class GateModuleTest {
	private Person person;
	private GateModule gateModule;
	private ArrayList<Person> personList;
	private long newFrequency;

	@Before
	public void setUp() {
		gateModule = new GateModule();
		personList = new ArrayList<>();

		person = createPersonMock();
		personList.add(person);
		gateModule.addPerson(person);

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
	public void Verify_And_Update_Frequency_Remote_When_Remote_Is_Not_Null() {
		RemoteModuleMock mock = new RemoteModuleMock(person.getRemote());
		gateModule.setFrequency(newFrequency);
		gateModule.verifyAndUpdateFrequencyRemote(mock);

		assertEquals(mock.getRemote().getFrequency(), 789);
	}

	// Test fails
	// Gives nullreference exception => throw statement should be added in
	// function
	// Change assert to assetTrue?? verifyAndUpdate...functie misschien boolean
	// als return geven?
	@Test
	public void Verify_And_Update_Frequency_Remote_When_Remote_Is_Null() {
		RemoteModuleMock mock = new RemoteModuleMock(person.getRemote());
		mock.setRemote(null);
		gateModule.setFrequency(newFrequency);
		gateModule.verifyAndUpdateFrequencyRemote(mock);

		assertNotEquals(mock.getRemote().getFrequency(), gateModule.getFrequency());
	}

	@Test
	public void Verify_And_Update_Frequency_Remote_When_Contract_Has_Expired() {
		RemoteModuleMock mock = new RemoteModuleMock(person.getRemote());
		person.setEndOfContract(Date.valueOf(LocalDate.of(200, 1, 1)));
		gateModule.setFrequency(newFrequency);
		gateModule.verifyAndUpdateFrequencyRemote(mock);

		assertNotEquals(mock.getRemote().getFrequency(), gateModule.getFrequency());
	}

	@Test
	public void Verify_And_Update_Frequency_Remote_When_Remote_Is_Not_Active() {
		RemoteModuleMock mock = new RemoteModuleMock(person.getRemote());
		mock.getRemote().setIsActive(false);
		gateModule.setFrequency(newFrequency);
		gateModule.verifyAndUpdateFrequencyRemote(mock);

		assertNotEquals(mock.getRemote().getFrequency(), gateModule.getFrequency());
	}

	//Test fails
	//Is het zelfs mogelijk om andere serial number te bekomen? 
	@Test
	public void Verify_And_Update_Frequency_Remote_When_Remote_Has_Different_Serial_Number() {
		RemoteModuleMock mock = new RemoteModuleMock(person.getRemote());
		mock.getRemote().setSerialNumber("NotValid");
		gateModule.setFrequency(newFrequency);
		gateModule.verifyAndUpdateFrequencyRemote(mock);

		assertNotEquals(mock.getRemote().getFrequency(), gateModule.getFrequency());
	}

	private Person createPersonMock() {
		Person person = new Person();
		person.setFirstname("Foo");
		person.setLastname("Bar");
		person.setAdress(new Address("Test", 10, 20, 3300, "Unknown", "Unknown"));
		person.setRemote(new Remote("123456789", 0));

		LocalDate date = LocalDate.now();
		date.plusYears(2);

		person.setEndOfContract(Date.valueOf(date));

		return person;
	}

	private class RemoteModuleMock implements IRemoteModule {
		private Remote remote;

		public RemoteModuleMock(Remote remote) {
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
