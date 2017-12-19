package test.database;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.fluttercode.datafactory.impl.DataFactory;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 03/12/2017
	@Project Afstandsbediening
	@Doel Generates dummy data for database
 */

public class GenerateDummyData {
	private static Remote remote;
	private static Address address;
	private static Person person;
	
	private static GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
	private static GenericDAO<Remote> remoteDAO = new GenericDAO<>(Remote.class);
	private static GenericDAO<Address> addressDAO = new GenericDAO<>(Address.class);
	
	private static DataFactory factory = new DataFactory();
	
	public static void main(String[] args) {
		try {;
			createActivePerson();
			createInActiveRemote();
			createInActiveAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createActivePerson(){
		LocalDate date  = LocalDate.now();
		date.plusYears(2);		
		Date contractDate = Date.valueOf(date);
		
		for (int i = 0; i < 30; i++) {
			person = new Person(factory.getFirstName(), factory.getLastName(), contractDate);
			address = new Address("Bondgenotenlaan", 150, i+1, 3000, "Leuven", "België");
			remote = new Remote(UUID.randomUUID().toString(), ThreadLocalRandom.current().nextLong(10000, 1000000));
			
			person.setAdress(address);
			person.setRemote(remote);
			person.getRemote().setIsActive(true);
			
			personDAO.create(person);
		}
	}
	
	private static void createInActiveRemote(){
		for (int i = 0; i < 10; i++) {
			remote = new Remote(UUID.randomUUID().toString(), ThreadLocalRandom.current().nextLong(10000, 1000000));
			
			remoteDAO.create(remote);
		}
	}
	
	private static void createInActiveAddress(){
		for (int i = 0; i < 10; i++) {
			address = new Address("Bondgenotenlaan", 150, i+31, 3000, "Leuven", "België");
			
			addressDAO.create(address);
		}
	}
}
