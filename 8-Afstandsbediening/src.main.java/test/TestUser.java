package test;

import java.sql.Date;
import java.time.LocalDate;

import org.fluttercode.datafactory.impl.DataFactory;

import database.DataDeleter;
import database.DataGenerator;
import model.business.Administrator;
import model.business.PersonWrapper;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import utility.Utility;

public class TestUser {

	public static void main(String[] args) {
		System.out.println("Deleting all the data in the database...");
		DataDeleter.performDeletion();
		System.out.println("--> Done!");
		System.out.println("Generating data in the database...");
		DataGenerator.performDataGeneration();
		System.out.println("--> Done!");
		
		DataFactory factory = new DataFactory();
		Administrator admin = new Administrator();
		
		System.out.println("Administrator created!");
		System.out.println("Administrator has " + admin.getListeners().size() +" observers");
		
		Person person = new Person();		
		System.out.println("New person created!");
		
		person.setFirstname(factory.getFirstName());
		person.setLastname(factory.getLastName());
		person.setAdress(new Address("Kersenlaan", 10, Utility.RANDOM.nextInt(), 3300, "Leuven", "Ijsland"));
		person.setRemote(new Remote(Long.toString( Utility.RANDOM.nextLong()), 100));
		person.setEndOfContract(Date.valueOf(LocalDate.of(2018, 7, 1)));

		System.out.println("Registering this person to administrator... (person will also be saved to database in process)");
		PersonWrapper user = new PersonWrapper(person, admin);
		System.out.println(admin.registerUser(new PersonWrapper(person, admin)).toString());
		
		System.out.println("Made new remote and registered person '" + person.toString() + "' to it.");
		System.out.println("Trying to open gate with this remote...");
		if (user.openGate())
			System.out.println("Access granted!");
		else 
			System.out.println("Access denied");

		
		user.getRemote().setIsActive(false);
		System.out.println("Remote set inactive!");
		System.out.println("Trying to open gate with this remote...");
		if (user.openGate())
			System.out.println("Access granted!");
		else 
			System.out.println("Access denied");
		
		user.getRemote().setIsActive(true);
		user.setEndOfContract(Date.valueOf(LocalDate.of(2000, 1, 1)));
		System.out.println("Contract person has expired!");
		System.out.println("Trying to open gate with this remote...");
		if (user.openGate())
			System.out.println("Access granted!");
		else 
			System.out.println("Access denied");

		
		System.exit(0);
		
	}

}
