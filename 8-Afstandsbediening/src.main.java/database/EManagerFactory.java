package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EManagerFactory {
	private static EntityManagerFactory factory;
	
	public static EntityManagerFactory getFactory(){
		if(factory == null)
			factory = Persistence.createEntityManagerFactory("Afstandsbediening");
		
		return factory;
	}
}
