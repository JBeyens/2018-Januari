package model.business.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import model.business.Administrator;
import model.business.User;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.sql.Date;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 02/01/2018
 * @Project Afstandsbediening
 * @Doel Test of GateModule
 */
public class UserTest {
	private Person person;
	private User user;
	private Administrator admin;
	private long newFrequency = 111;

	@Before
	public void setUp() {
		admin = new Administrator();
		admin.setFrequency(newFrequency);

		person = createPersonMock();
		user = new User(person, person.getRemote(), admin);
	}


	@Test
	public void Verify_And_Update_Frequency_Remote_When_Remote_Is_Active_Contract_OK_Expect_Succesfull() {
		admin.addObserver(user);
		Boolean bool = user.openGate();

		
		assertEquals(user.getRemote().getFrequency(), admin.getFrequency());
		assertTrue(bool);
	}

	//@Test
	public void Verify_And_Update_Frequency_Remote_When_Contract_Has_Expired_Expect_Expired() {
		user.getPerson().setEndOfContract(Date.valueOf(LocalDate.of(2000, 1, 1)));

		admin.notifyAllObservers();

		assertNotEquals(user.getPerson().getRemote().getFrequency(), admin.getFrequency());
	}

	//@Test
	public void Verify_And_Update_Frequency_Remote_When_Remote_Is_Not_Active() {
		user.getPerson().getRemote().setIsActive(false);
		Boolean bool = user.openGate();

		assertNotEquals(user.getRemote().getFrequency(), admin.getFrequency());
		assertFalse(bool);
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

}
