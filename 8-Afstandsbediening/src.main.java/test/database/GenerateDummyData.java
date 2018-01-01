package test.database;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.fluttercode.datafactory.impl.DataFactory;

import antlr.collections.List;
import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import values.DefaultSettings;


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

	private static Logger logger = DefaultSettings.getLogger();
	private static Date contractDate = getDate();	
	private static Integer amountOfAddresses = 30;
	private static DataFactory factory = new DataFactory();
	
	public static void main(String[] args) {
		try {
			createActiveRemotesPersonsAddresses();
			createInActiveRemote();
			createInActivePerson();
			createInActiveAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static Date getDate() {		
		LocalDate date  = LocalDate.now();
		date.plusYears(2);		
		return Date.valueOf(date);		
	} 

	private static void createActiveRemotesPersonsAddresses(){	
		logger.info("Creating linked remotes, persons and addresses...");	
		
		
		for (int i = 1; i <= amountOfAddresses; i++) {
			remote = new Remote(UUID.randomUUID().toString(), ThreadLocalRandom.current().nextLong(10000, 1000000));
			person = new Person(factory.getFirstName(), factory.getLastName(), contractDate);
			address = new Address("Bondgenotenlaan", 150, i+1, 3000, "Leuven", "Belgi" + Character.toString((char)235));
			
			remote.setIsActive(true);
			remote.setPerson(person);
			person.setAdress(address);
			
			EntityDAO.REMOTE_DAO.create(remote);
		}
	}
	
	private static void createInActiveRemote(){		
		logger.info("Creating remotes not linked to persons...");
		for (int i = 1; i < 10; i++) {
			remote = new Remote(UUID.randomUUID().toString(), ThreadLocalRandom.current().nextLong(10000, 1000000));
			
			EntityDAO.REMOTE_DAO.create(remote);
		}
	}
	
	private static void createInActivePerson(){
		logger.info("Creating persons not linked by remotes...");
		for (int i = 1; i <= 10; i++) {
			person = new Person(factory.getFirstName(), factory.getLastName(), contractDate);
			
			EntityDAO.PERSON_DAO.create(person);
		}
	}
	
	private static void createInActiveAddress(){
		logger.info("Creating addresses not linked by persons...");
		for (int i = 1; i <= 10; i++) {
			address = new Address("Bondgenotenlaan", 150, amountOfAddresses + i, 3000, "Leuven", "Belgi" + Character.toString((char)235));
			
			EntityDAO.ADDRESS_DAO.create(address);
		}
	}
}
