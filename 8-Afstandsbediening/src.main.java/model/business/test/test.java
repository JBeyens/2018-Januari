package model.business.test;

import java.sql.Date;
import java.time.LocalDate;

import database.EntityDAO;
import model.business.Administrator;
import model.business.PersonWrapper;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

public class test {
	private static Person createPersonMock() {
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
	
	public static void main(String[] args) {
		Administrator admin = new Administrator();
		Person person = createPersonMock();
		
		EntityDAO.PERSON_DAO.create(person);
		
		PersonWrapper wrap = new PersonWrapper(person, admin);
		
		admin.registerUser(wrap);
		Boolean first = wrap.openGate();
		
		admin.deactivateUser(wrap);
		Boolean second = wrap.openGate();
		
		System.out.println(first);
		System.out.println(second);

	}

}
