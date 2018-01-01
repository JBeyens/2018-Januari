package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import model.entities.Address;
import model.entities.Person;
import model.entities.Remote;
import model.idmodule.GateModule;
import model.idmodule.RemoteModule;
import model.idmodule.DataManager;
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
	private View view;
	private GateModule gateModule;
	private DataManager dataManager;
	
	public ControllerRemote(){
		log = DefaultSettings.getLogger("Controller");
		gateModule = new GateModule();
		dataManager = new DataManager();
		view = new View();
		
		view.addAskEntranceListener(new AskEntranceListener());
		view.addOVerViewUpdateListener(new RefreshOverViewListener());
		view.addAddPersonListener(new AddPersonListener());
		
		setInactiveRemote();
		setUnusedAddress();
		setRemotes();
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
		ArrayList<Remote> inactiveRemotes = new ArrayList<>();
		
		for (Remote remote : gateModule.getAllRemotes()) {
			if(!remote.getIsActive())
				inactiveRemotes.add(remote);
		}

		view.setInactiveRemote(inactiveRemotes);
	}
	
	/*
	 * Add all persons to list for simulation
	 */	
	private void setRemotes(){
		view.addRemotes( gateModule.getAllRemotes() );
	}
	
	/*
	 * JPA Namedquery (Address class) => unused Addresses returned
	 */
	private void setUnusedAddress(){
		view.setUnusedAddress(dataManager.getUnusedAddress());
	}
	
	/*
	 * Query list of all active inhabitants and pass them to view
	 */
	private void setOverView(){
		view.setOverview(gateModule.getAllPersons());
	} 
	
	/*
	 * Listeren for addPerson button (2nd tab)
	 */
	private class AddPersonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {					
				Address address = dataManager.getAddress(view.getAddress().getId());
				Remote remote = dataManager.getRemote(view.getRemote().getId());

				Person person = new Person();
				person.setFirstname(view.getFirstName());
				person.setLastname(view.getLastName());
				person.setEndOfContract(view.getDate());

				//person.setAdress(addressDAO.findOne(view.getAddress().getId()));
				//person.setRemote(remoteDAO.findOne(view.getRemote().getId()));

				person.setAdress(address);
				remote.setPerson(person);
				
				dataManager.updateRemote(remote);
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
			boolean isGateOpening = remoteModule.askOpenGate(gateModule);
			log.info("-> The entrance was " + (isGateOpening?"":"not ") + "granted!");
			view.setRequest(isGateOpening);
		}
		
	}
}
