package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.Administrator;
import model.business.DataManager;
import model.business.User;
import model.entities.Person;
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
	private Administrator gateAdmin;
	private View view;
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public ControllerRemote(){
		log = DefaultSettings.getLogger("Controller");
		gateAdmin = new Administrator();
		view = new View();		
	}
	
	public void start(){		
		view.entranceTabAddAskEntranceListener(new AskEntranceListener());
		view.addOVerViewUpdateListener(new RefreshOverViewListener());
		view.addAddPersonListener(new AddPersonListener());
		view.entranceTabAddUserListItemListener(new UserLabelsItemListener());
		
		setInactiveRemote();
		setUnusedAddress();
		setUsers();
		view.setVisible(true);
	}
	
	/*
	 * Creates list of al inactive Remotes => not given to a inhabitant
	 */
	private void setInactiveRemote(){	
		view.setInactiveRemote( DataManager.getUnusedRemotes() );
	}
	
	/*
	 * Add all persons to list for simulation
	 */	
	private void setUsers(){
		ArrayList<Person> persons = DataManager.getAllPersonsWithRemote();
		ArrayList<User> users = new ArrayList<>();
		for (Person person : persons) {
			users.add(new User(person, person.getRemote(), gateAdmin));
		}
		
		view.addUsers( users );
	}
	
	/*
	 * JPA Namedquery (Address class) => unused Addresses returned
	 */
	private void setUnusedAddress(){
		view.setUnusedAddress( DataManager.getUnusedAddress() );
	}
	
	/*
	 * Query list of all active inhabitants and pass them to view
	 */
	private void setOverView(){
		view.setOverview( DataManager.getAllPersons() );
	} 
	
	/*
	 * Listener for ask entrance button (Ask Entrance tab)
	 */
	private class AskEntranceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			User selectedUser = view.getUserForGate();
			boolean isGateOpening = selectedUser.openGate();
			log.info("-> The entrance was " + (isGateOpening?"":"not ") + "granted!");
			view.setEntranceTabRequest(isGateOpening);
		}		
	}	
	
	/*
	 * Listener for user list combo box (Ask Entrance tab)
	 */
	private class UserLabelsItemListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			User user = view.getUserForGate(); 
			
			view.setEntranceTabLblSerialNumberUser(	user.getRemote().getSerialNumber());
			view.setEntranceTabLblFrequencyUser( 	Double.toString( user.getRemote().getFrequency()));
			view.setEntranceTabLblRegisteredUser(	user.getRemote().getIsActive());
			view.setEntranceTabLblFirstNameUser(	user.getPerson().getFirstname());
			view.setEntranceTabLblLastNameUser(		user.getPerson().getLastname()); 
			view.setEntranceTabLblEndOfContractUser(dateFormat.format(user.getPerson().getEndOfContract()));
			view.setEntranceTabLblAddressUser(		user.getPerson().getAdress().toString());
			view.setEntranceTabLblFrequencyGate(	Double.toString(user.getGate().getFrequency()));
		}
	}
	
	/*
	 * Listeren for addPerson button (2nd tab)
	 */
	private class AddPersonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {				
				Person person = new Person();
				person.setFirstname(view.getFirstNamePersonTab());
				person.setLastname(view.getLastName());
				person.setEndOfContract(view.getDate());
				
				if(view.getAddress() != null)
					person.setAdress(view.getAddress());
				
				if(view.getRemote() != null)
					person.setRemote(view.getRemote());
				
				DataManager.updatePerson(person);
				
				view.setFirstNamePersonTab("");
				view.setLastName("");
				view.setDate(null);
			} 
			catch (Exception e1) {
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
}