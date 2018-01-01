package model.idmodule;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import database.EManagerFactory;
import database.EntityDAO;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import values.DefaultSettings;

/*
 * Business layer for retrieval & storage of data
 */
public class DataManager {
	// FIELDS
	private Logger log;
	private EntityDAO entityDAO;
	
	public DataManager() {
		log = DefaultSettings.getLogger();
		entityDAO = EntityDAO.createEntityDAO();
	}

	/*
	 * JPA Namedquery (Address class) => unused Addresses returned
	 */
	public ArrayList<Address> getUnusedAddress(){
		try {
			EntityManager manager = EManagerFactory.getFactory().createEntityManager();
			
			ArrayList<Address> list = (ArrayList<Address>) manager.createNamedQuery("findUnusedAddress", Address.class).getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	public Address getAddress(Integer id) {
		log.trace("Asking datalayer to retrieve 'Address' with id = " + id);
		return entityDAO.readAddress(id);
	}
	
	public Person getPerson(Integer id) {
		log.trace("Asking datalayer to retrieve 'Person' with id = " + id);
		return entityDAO.readPerson(id);
	}
	
	public Remote getRemote(Integer id) {
		log.trace("Asking datalayer to retrieve 'Remote' with id = " + id);
		return entityDAO.readRemote(id);
	}
	public void updateRemote(Remote remote) {
		entityDAO.updateRemote(remote);
	}	
}
