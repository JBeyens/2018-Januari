package model.business.test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.EntityDAO;
import model.business.Administrator;
import model.business.PersonWrapper;
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
 * @Doel Test of Users
 */ 
public class PersonWrapperTest {
	private Person person;
	private PersonWrapper user;
	private Administrator admin;
	private long newFrequency = 111;

	@Before
	public void setUp() {
		admin = new Administrator();
		admin.setFrequency(newFrequency);

		person = EntityDAO.PERSON_DAO.update(createPersonMock());
		user = new PersonWrapper(person, admin);
		
		admin.registerUser(user);
	}
	
	@Test
	public void Update_Frequency_Should_Be_Set_In_Remote() {
		user.update(123456);

		assertEquals(123456, user.getRemote().getFrequency());
	}


	@Test
	public void Open_Gate_When_Remote_Is_Active_Access_Granted() {
		Boolean bool = user.openGate();
		
		Remote found = EntityDAO.REMOTE_DAO.findOne(user.getRemote().getId());

		assertTrue(found.getIsActive());
		assertEquals(user.getRemote().getFrequency(), admin.getFrequency());
		assertTrue(bool);
	}

	@Test
	public void Open_Gate_When_Remote_Is_Not_Active_Access_Denied() {
		admin.deactivateUser(user);
		
		Boolean bool = user.openGate();

		Remote found = EntityDAO.REMOTE_DAO.findOne(user.getRemote().getId());

		assertFalse(found.getIsActive());
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
	
	//Clean up file
	@After
	public void tearDown(){
		admin.deactivateUser(user);
		EntityDAO.PERSON_DAO.delete(person);
	}
}
