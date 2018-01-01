package test.database;

import org.apache.log4j.Logger;

import model.entities.EntityDAO;
import values.DefaultSettings;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 03/12/2017
	@Project Afstandsbediening
	@Doel Clears all data from Database
 */
public class ClearDummyData {
	private static Logger logger;
	
	public static void main(String[] args) {
		logger = DefaultSettings.getLogger();
		try {
			removeRemote();
			removePerson();
			removeAddress();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void removeRemote(){
		logger.info("Deleting cotent from table 'Remote' ...");
		EntityDAO.REMOTE_DAO.deleteAll();
	}
	
	private static void removeAddress(){
		logger.info("Deleting cotent from table 'Address' ...");
		EntityDAO.ADDRESS_DAO.deleteAll();
	}

	private static void removePerson(){
		logger.info("Deleting cotent from table 'Person' ...");
		EntityDAO.PERSON_DAO.deleteAll();
	}
}
