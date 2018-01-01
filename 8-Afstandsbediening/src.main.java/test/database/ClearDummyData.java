package test.database;

import model.entities.EntityDAO;
import values.DefaultLogger;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 03/12/2017
	@Project Afstandsbediening
	@Doel Clears all data from Database
 */
public class ClearDummyData {
	
	
	public static void main(String[] args) {
		DefaultLogger.configureLogger();
		try {
			removeRemote();
			removePerson();
			removeAddress();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void removeRemote(){
		EntityDAO.REMOTE_DAO.deleteAll();
	}
	
	private static void removeAddress(){
		EntityDAO.ADDRESS_DAO.deleteAll();
	}

	private static void removePerson(){
		EntityDAO.PERSON_DAO.deleteAll();
	}
}
