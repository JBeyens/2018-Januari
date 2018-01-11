package model.business.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.EntityDAO;
import model.business.Administrator;
import model.business.PersonWrapper;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

/**
 * 	@Author Jef Beyens & Ben Vandevorst
	@Datum 02/01/2018
	@Project Afstandsbediening
	@Doel Test of RemoteModule
 */
public class AdministratorTest {
	private Administrator admin;
	private Person person;
	private PersonWrapper user;
	
	@Before
	public void setUp(){
		admin = new Administrator();
		person = createPersonMock();
		user = new PersonWrapper(person, admin);
		
		EntityDAO.PERSON_DAO.create(person);
	}
	
	@Test
	public void Register_User_Succesfull() {
		String result = admin.registerUser(user).toString();
		
		assertEquals(result, "User was succesfully registered!");
	}
	
	@Test
	public void Register_Duplica_User_Except_Already_Exists(){
		admin.registerUser(user);
		String result = admin.registerUser(user).toString();
		
		assertEquals(result, "User could not be registered again, since it was already registered!");
	}
	

	
	@After
	public void tearDown(){
		EntityDAO.PERSON_DAO.delete(person);
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