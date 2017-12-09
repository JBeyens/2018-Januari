package test.database;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;

public class GenerateDummyData {
	private static Remote remote;
	private static Address address;
	private static Person person;
	
	private static GenericDAO<Remote> remoteDAO = new GenericDAO<>(Remote.class);
	private static GenericDAO<Address> addressDAO = new GenericDAO<>(Address.class);
	private static GenericDAO<Person> PersonDAO = new GenericDAO<>(Person.class);
	
	private static Random random;
	
	public static void main(String[] args) {
		try {
			createRemote();
			createAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void createRemote(){
		for (int i = 0; i < 35; i++) {
			String serialNumber = UUID.randomUUID().toString();
			long frequency = ThreadLocalRandom.current().nextLong(10000, 1000000);
			remote = new Remote(serialNumber, frequency);
			
			remoteDAO.create(remote);
		}
	}
	
	private static void createAddress(){
		for (int i = 1; i < 31; i++) {
			address = new Address("Bondgenotenlaan", 150, i, 3000, "Leuven", "België");
			addressDAO.create(address);
		}
	}

}
