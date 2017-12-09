package test.database;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;

public class ClearDummyData {
	private static GenericDAO<Remote> remoteDAO = new GenericDAO<>(Remote.class);
	private static GenericDAO<Address> addressDAO = new GenericDAO<>(Address.class);
	private static GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
	
	private static Iterator iterator;
	
	public static void main(String[] args) {
		try {
			removePerson();
			removeRemote();
			removeAddress();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void removeRemote(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Remote.class.getSimpleName());
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		manager.createNativeQuery("DELETE FROM Remote").executeUpdate();
		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
	
	private static void removeAddress(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Address.class.getSimpleName());
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		manager.createNativeQuery("DELETE FROM Address").executeUpdate();
		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}

	private static void removePerson(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Person.class.getSimpleName());
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		manager.createNativeQuery("DELETE FROM Person").executeUpdate();
		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
}
