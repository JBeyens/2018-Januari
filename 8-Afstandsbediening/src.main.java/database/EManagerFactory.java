package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Creation of EMFactory
 */

public class EManagerFactory {
	private static EntityManagerFactory factory;
	
	public static EntityManagerFactory getFactory(){
		if(factory == null)
			factory = Persistence.createEntityManagerFactory("Afstandsbediening");
		
		return factory;
	}
}
