package test;

import java.sql.Date;
import java.time.LocalDate;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;

public class TestClass {

	public static void main(String[] args){
		Address address = new Address("Bondgenotenlaan", 150, 5, 3000, "Leuven", "België");
		Remote remote = new Remote("Test", 555555);
		
		LocalDate date  = LocalDate.now();
		date.plusYears(2);		
		Date contractDate = Date.valueOf(date);
		
		Person person = new Person("Ben", "Vdv", contractDate);
		person.setAdress(address);
		person.setRemote(remote);
		
		GenericDAO<Person> dao = new GenericDAO<>(Person.class);
		dao.create(person);

	}

}
