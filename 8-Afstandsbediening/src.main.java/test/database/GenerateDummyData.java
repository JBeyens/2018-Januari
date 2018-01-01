package test.database;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.fluttercode.datafactory.impl.DataFactory;

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
	private static HashMap<Integer, Address> nrToAdress;

	private static Logger logger = DefaultSettings.getLogger("GenerateData");
	private static Date contractDate = getDate();	
	private static Integer amountOfAddresses = 30;
	private static DataFactory factory = new DataFactory();
	
	private static String street = "Bondgenotenlaan";
	private static Integer streetNumber = 150;
	private static Integer postalCode = 3000;
	private static String city = "Leuven";
	private static String country = "Belgi" + Character.toString((char)235);
	
	public static void main(String[] args) {
		try {
			createActivePersonsRemotesAddresses();
			createInActiveRemote();
			createInActivePerson();
			createInActiveAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Finished creating dummy data!");
	}
	
	private static Date getDate() {		
		LocalDate date  = LocalDate.now();
		date.plusYears(2);		
		return Date.valueOf(date);		
	} 
	
	private static void loadHashMapOfAddresses() {
		logger.debug("-> Loading in existing addresses");
		ArrayList<Address> addresses = (ArrayList<Address>) EntityDAO.ADDRESS_DAO.findAll();
		logger.debug("-> Creating HashMap for eisting addresses which would overlap with generation method");
		nrToAdress = new HashMap<>();
		for (Address xAddress : addresses) {
			if ( xAddress.getStreet().equals(street) &&
				 xAddress.getNumber() == streetNumber &&
				 xAddress.getPostalCode() == postalCode &&
				 xAddress.getCity().equals(city) &&
				 xAddress.getCountry().equals(country))
				nrToAdress.put(xAddress.getMailBox(), xAddress);
		}		
	}

	private static void createActivePersonsRemotesAddresses(){	
		logger.info("Creating linked persons, remotes and addresses...");

		loadHashMapOfAddresses();
		
		for (int i = 1; i <= amountOfAddresses; i++) {
			person = new Person(factory.getFirstName(), factory.getLastName(), contractDate);
			remote = new Remote(UUID.randomUUID().toString(), ThreadLocalRandom.current().nextLong(10000, 1000000));
			
			address = nrToAdress.get(i+1);
			if (address == null)
				address = new Address(street, streetNumber, i+1, postalCode, city, country);			
			
			remote.setIsActive(true);
			person.setRemote(remote);
			person.setAdress(address);
			
			EntityDAO.PERSON_DAO.create(person);
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

		loadHashMapOfAddresses();
		
		for (int i = 1; i <= 10; i++) {
			address = nrToAdress.get(i+1);
			if (address != null)
				continue;
			
			address = new Address(street, streetNumber, i+1, postalCode, city, country);			
			EntityDAO.ADDRESS_DAO.create(address);
		}
	}
}
