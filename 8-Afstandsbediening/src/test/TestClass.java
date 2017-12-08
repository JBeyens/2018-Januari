package test;

import java.time.LocalDate;
import java.sql.Date;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;


public class TestClass {

	public static void main(String[] args){
		GenericDAO<Person> mgr = new GenericDAO<>(Person.class);
		
		String fname = "Ben";
		String lname = "Test";
		Address adres = new Address("Bondgenotenlaan", 10, 5, 3000, "Leuven", "België");
		Remote remote = new Remote("NieuwRemote", 12456789);
		String email = "email";
		Date date = Date.valueOf(LocalDate.now());
		
		Person p = new Person(fname, fname, adres, remote, date, email);
		
		mgr.create(p);
		
		

	}

}
