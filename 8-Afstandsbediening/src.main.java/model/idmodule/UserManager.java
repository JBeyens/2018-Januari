package model.idmodule;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.GenericDAO;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/11/2017
 * @Project Afstandsbediening
 * @Doel Manages user/remote authorization
 */
public class UserManager {
	private EntityManagerFactory emFactory;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;
	/**
	 * 
	 */
	public UserManager() {
		/*
		 * EntityManagerFactory thread safe/heavy resource
		 * Only 1 creating
		 */
		emFactory = Persistence.createEntityManagerFactory("Afstandsbediening");
		
		personDAO = new GenericDAO<>(Person.class, emFactory);
		remoteDAO = new GenericDAO<>(Remote.class, emFactory);
		addressDAO = new GenericDAO<>(Address.class, emFactory);
	}
	

	
}
