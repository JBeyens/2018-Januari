package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords;

import database.EManagerFactory;
import database.EntityDAO;
import database.GenericDAO;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import model.idmodule.GateModule;
import model.idmodule.RemoteModule;
import values.DefaultSettings;
import view.View;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Single controller for handling interaction model<>view
 */

public class ControllerRemote {
	private Logger log;
	private GateModule gate;
	private View view;
	private EntityDAO entityDAO;
	
	public ControllerRemote(){
		log = DefaultSettings.getLogger();
		gate = new GateModule();
		view = new View();
		entityDAO = EntityDAO.createEntityDAO();
		
		view.addAskEntranceListener(new AskEntranceListener());
		view.addOVerViewUpdateListener(new RefreshOverViewListener());
		view.addAddPersonListener(new AddPersonListener());
		
		setInactiveRemote();
		setUnusedAddress();
		setRemotes();
		view.repaint();
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
		GenericDAO<Remote> remoteDAO = new GenericDAO<>(Remote.class);
		
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
	
	private void setRemotes(){
		view.addRemotes( entityDAO.readAllRemotes());
	}
	
	/*
	 * JPA Namedquery (Address class) => unused Addresses returned
	 */
	private void setUnusedAddress(){
		try {
			EntityManager manager = EManagerFactory.getFactory().createEntityManager();
			
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
		GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
		
		view.setOverview((ArrayList<Person>)personDAO.findAll());
	} 
	
	/*
	 * Listeren for addPerson button (2nd tab)
	 */
	private class AddPersonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
				GenericDAO<Address> addressDAO = new GenericDAO<>(Address.class);
				GenericDAO<Remote> remoteDAO = new GenericDAO<>(Remote.class);
					
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
		public void actionPerformed(ActionEvent e) {
			RemoteModule remoteModule = new RemoteModule(view.getRemoteForGate());
			log.info("Remote " + remoteModule.getRemote() + " asked for entrance.");
			boolean isGateOpening = remoteModule.askOpenGate(gate);
			log.info("-> The entrance was " + (isGateOpening?"":"not ") + "granted!");
			view.drawGraphic(isGateOpening);
		}
		
	}
}
