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
		
		
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Remote");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		Remote remote = new Remote("NieuwRemote", 12456789);
		
		manager.persist(remote);
		
		manager.getTransaction().commit();
		
		manager.close();
		factory.close();

	}

}
