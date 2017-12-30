package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;

import database.GenericDAO;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import view.View;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Single controller for handling interaction model<>view
 */

public class ControllerRemote {
	private View view;
	private EntityManagerFactory emFactory;
	private GenericDAO<Person> personDAO;
	private GenericDAO<Remote> remoteDAO;
	private GenericDAO<Address> addressDAO;
	
	public ControllerRemote(){
		view = new View();
		
		/*
		 * EntityManagerFactory thread safe/heavy resource
		 * Only 1 creating
		 */
		emFactory = Persistence.createEntityManagerFactory("Afstandsbediening");
		
		personDAO = new GenericDAO<>(Person.class, emFactory);
		remoteDAO = new GenericDAO<>(Remote.class, emFactory);
		addressDAO = new GenericDAO<>(Address.class, emFactory);
		
		view.addAskEntranceListener(new AskEntranceListener());
		view.addOVerViewUpdateListener(new RefreshOverViewListener());
		view.addAddPersonListener(new AddPersonListener());
		setInactiveRemote();
		setUnusedAddress();
		setPersons();
	}
	
	public void start(){
		this.view.setSize(600, 500);
		this.view.setResizable(false);
		this.view.setVisible(true);
		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*
	 * Creates list of al inactive Remotes => not given to a inhabitant
	 */
	private void setInactiveRemote(){
		ArrayList<Remote> list = new ArrayList<>();
		
		for (Remote remote : remoteDAO.findAll()) {
			if(!remote.getIsActive())
				list.add(remote);
		}

		view.setInactiveRemote(list);
	}
	
	/*
	 * Add all persons to list for simulation
	 */
	
	private void setPersons(){
		view.addPersons((ArrayList<Person>) personDAO.findAll());
	}
	
	/*
	 * JPA Namedquery (Address class) => unused Addresses returned
	 */
	private void setUnusedAddress(){
		try {
			EntityManager manager = emFactory.createEntityManager();
			
			ArrayList<Address> list = (ArrayList<Address>) manager.createNamedQuery("findUnusedAddress", Address.class).getResultList();
			
			view.setUnusedAddress(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Query list of all active inhabitants and pass them to view
	 */
	private void setOverView(){
		view.setOverview((ArrayList<Person>)personDAO.findAll());
	} 
	
	/*
	 * Listeren for addPerson button (2nd tab)
	 */
	private class AddPersonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Person person = new Person();
				person.setFirstname(view.getFirstName());
				person.setLastname(view.getLastName());
				person.setEndOfContract(view.getDate());
				person.setAdress(addressDAO.findOne(view.getAddress().getId()));
				person.setRemote(remoteDAO.findOne(view.getRemote().getId()));
				
				personDAO.create(person);
			} catch (Exception e1) {
				e1.printStackTrace();
				view.showMessage("Input parameters not correct!");
			}
			finally{
				/*
				 * Refresh lists after new person is added
				 */
				setInactiveRemote();
				setUnusedAddress();
			}
		}	
	}
	
	/*
	 * Listener for update button (3rd tab)
	 */
	private class RefreshOverViewListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				setOverView();
			} catch (Exception e1) {
				e1.printStackTrace();
				view.showMessage("Loading from database failed!");
			}
		}	
	}
	
	/*
	 * Listener for ask entrance button (1st tab)
	 */
	private class AskEntranceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
