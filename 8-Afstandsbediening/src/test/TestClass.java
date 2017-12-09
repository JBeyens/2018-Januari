package test;


import java.sql.Date;
import java.time.LocalDate;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;


public class TestClass {

	public static void main(String[] args){
		
		Person person = null;
		
		Date date = Date.valueOf(LocalDate.now());
		
		GenericDAO<Person> PersonDAO = new GenericDAO<>(Person.class);
		GenericDAO<Remote> RemoteDAO = new GenericDAO<>(Remote.class);
		GenericDAO<Address> AddressDAO = new GenericDAO<>(Address.class);
		
		RemoteDAO.create(new Remote("Ben", 0));
		
		

	}

}
