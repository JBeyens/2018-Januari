package test.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;

public class ClearDummyData {
	
	
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
