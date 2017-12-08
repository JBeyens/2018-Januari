package test;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Date;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Person;
import modelPersistent.Remote;


public class TestClass {

	public static void main(String[] args){
		
		Address remote;
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Address.class.getSimpleName());
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		remote = new Address("NieuwRemote", 12456789, 5, 6, "hh", "gg");
		
		manager.persist(remote);
		
		manager.getTransaction().commit();
		
		manager.close();
		factory.close();

	}

}
