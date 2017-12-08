package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityMgrFactory {
	public static EntityManagerFactory getFactory(String simpleClassName){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(simpleClassName);
		return factory;
	}
}
