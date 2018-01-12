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

		person = createPersonMock();
		user = new PersonWrapper(person, admin);
		
		EntityDAO.PERSON_DAO.create(person);
		
		admin.registerUser(user);
	}


	@Test
	public void Open_Gate_When_Remote_Is_Active_Contract_OK_Acces_Granted() {
		Boolean bool = user.openGate();
		
		Remote found = EntityDAO.REMOTE_DAO.findOne(user.getRemote().getId());

		assertTrue(found.getIsActive());
		assertEquals(user.getRemote().getFrequency(), admin.getFrequency());
		assertTrue(bool);
	}

	@Test
	public void Open_Gate__When_Contract_Has_Expired_Acces_Denied() {
		user.setEndOfContract(Date.valueOf(LocalDate.of(2000, 1, 1)));

		Boolean bool = user.openGate();
		
		Remote found = EntityDAO.REMOTE_DAO.findOne(user.getRemote().getId());

		assertTrue(found.getIsActive());
		assertFalse(bool);
	}

	@Test
	public void Open_Gate_When_Remote_Is_Not_Active_Acces_Denied() {
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
