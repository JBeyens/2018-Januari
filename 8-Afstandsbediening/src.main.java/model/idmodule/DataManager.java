package model.idmodule;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import database.EManagerFactory;
import model.entities.Address;
import model.entities.EntityDAO;
import model.entities.Person;
import model.entities.Remote;
import model.observer.IRemoteObserver;
import values.DefaultSettings;

/*
 * Business layer for retrieval & storage of data
 */
public class DataManager {
	// FIELDS
	private Logger log;
	// FIELDS
	private ArrayList<Address> addresses;
	private ArrayList<Person> persons;
	private ArrayList<Remote> remotes;
	private ArrayList<IRemoteObserver> activeRemotes;
	
	public DataManager() {
		log = DefaultSettings.getLogger("DataManager");
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
		return EntityDAO.ADDRESS_DAO.findOne(id);
	}
	
	public Person getPerson(Integer id) {
		log.trace("Asking datalayer to retrieve 'Person' with id = " + id);
		return  EntityDAO.PERSON_DAO.findOne(id);
	}
	
	public Remote getRemote(Integer id) {
		log.trace("Asking datalayer to retrieve 'Remote' with id = " + id);
		return  EntityDAO.REMOTE_DAO.findOne(id);
	}
	public void updateRemote(Remote remote) {
		EntityDAO.REMOTE_DAO.update(remote);
	}	
	public void updatePerson(Person person) {
		EntityDAO.PERSON_DAO.update(person);
	}	
}
