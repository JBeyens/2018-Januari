package model.business;

import java.sql.Date;
import java.time.LocalDate;

import org.fluttercode.datafactory.impl.DataFactory;

import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;

public class TestUser {

	public static void main(String[] args) {
		DataFactory factory = new DataFactory();
		Administrator admin = new Administrator();
		
		System.out.println("Administrator created");
		System.out.println("Administrator has " + admin.getListeners().size() +" observers");
		
		Person person = new Person();
		
		person.setFirstname(factory.getFirstName());
		person.setLastname(factory.getLastName());
		person.setAdress(new Address("Kersenlaan", 10, 1, 3300, "Leuven", "Ijsland"));
		person.setRemote(new Remote("123456789", 100));
		person.setEndOfContract(Date.valueOf(LocalDate.of(2018, 7, 1)));
		
		EntityDAO.PERSON_DAO.create(person);
		System.out.println("Person saved to database!");
		
		System.out.println("Remote becomes active");
		System.out.println("Admin gets new observer");
		
		person.getRemote().setIsActive(true);
	}

}
