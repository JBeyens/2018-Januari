package model.business;

import java.sql.Date;
import java.time.LocalDate;

import org.fluttercode.datafactory.impl.DataFactory;

import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import values.DefaultSettings;

public class TestUser {

	public static void main(String[] args) {
		DataFactory factory = new DataFactory();
		Administrator admin = new Administrator();
		
		System.out.println("Administrator created!");
		System.out.println("Administrator has " + admin.getListeners().size() +" observers");
		
		Person person = new Person();		
		System.out.println("New person created!");
		
		person.setFirstname(factory.getFirstName());
		person.setLastname(factory.getLastName());
		person.setAdress(new Address("Kersenlaan", 10, DefaultSettings.RANDOM.nextInt(), 3300, "Leuven", "Ijsland"));
		person.setRemote(new Remote(Long.toString( DefaultSettings.RANDOM.nextLong()), 100));
		person.setEndOfContract(Date.valueOf(LocalDate.of(2018, 7, 1)));

		System.out.println("Registering this person to administrator... (person will also be saved to database in process)");
		User remote = new User(person, person.getRemote(), admin);
		System.out.println(admin.registerUser(new User(person, person.getRemote(), admin)).toString());
		
		System.out.println("Made new remote and registered person '" + person.toString() + "' to it.");
		System.out.println("Trying to open gate with this remote...");
		if (remote.openGate())
			System.out.println("Access granted!");
		else 
			System.out.println("Access denied");

		
		remote.getPerson().getRemote().setIsActive(false);
		System.out.println("Remote set inactive!");
		System.out.println("Trying to open gate with this remote...");
		if (remote.openGate())
			System.out.println("Access granted!");
		else 
			System.out.println("Access denied");
		
		remote.getPerson().getRemote().setIsActive(true);
		remote.getPerson().setEndOfContract(Date.valueOf(LocalDate.of(2000, 1, 1)));
		System.out.println("Contract person has expired!");
		System.out.println("Trying to open gate with this remote...");
		if (remote.openGate())
			System.out.println("Access granted!");
		else 
			System.out.println("Access denied");

		
		System.exit(0);
		
	}

}
