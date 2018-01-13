package database;

import org.apache.log4j.Logger;

import net.bytebuddy.asm.Advice.This;
import utility.Utility;

/**
 * @Author Ben Vandevorst & Jef Beyens
 * @Datum 03/12/2017
 * @Project Afstandsbediening
 * @Doel Clears all data from Database
 */
public final class DataDeleter {
	private static Logger logger;

	public static void main(String[] args) {
		performDeletion();
		System.exit(0);
	}
	
	public static boolean performDeletion() {
		logger = Utility.getLogger(This.class.getSimpleName());
		try {
			logger.info("Starting to delete database... ");
			removePerson();	
			removeAddress();	
			removeRemote();
			logger.info("Deleting data -> DONE!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void removeRemote() {
		logger.info("Deleting content from table 'Remote' ...");
		EntityDAO.REMOTE_DAO.deleteAll();
	}

	private static void removeAddress() {
		logger.info("Deleting content from table 'Address' ...");
		EntityDAO.ADDRESS_DAO.deleteAll();
	}

	private static void removePerson() {
		logger.info("Deleting content from table 'Person' ...");
		EntityDAO.PERSON_DAO.deleteAll();
	}

}
