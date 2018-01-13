package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.apache.log4j.Logger;

import model.business.Administrator;
import model.business.DataManager;
import model.business.PersonWrapper;
import model.entities.Address;
import model.entities.Person;
import utility.DataDeleter;
import utility.DataGenerator;
import utility.Utility;
import values.UserDeactivationResult;
import values.UserRegistrationResult;
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
	
	public ControllerRemote(){
		log = Utility.getLogger("Controller");
		gateAdmin = new Administrator();
		view = new View();		
	}
	
	public void start(){		
		view.entranceTabAddRegisterUserListener(new RegisterUserListener());
		view.entranceTabAddDeactivateUserListener(new DeactivateUserListener());
		view.entranceTabAddUpdateFrequency(new UpdateGateFrequencyListener());
		view.entranceTabAddAskEntranceListener(new AskEntranceListener());
		view.entranceTabAddUserListItemListener(new SetEntranceLabelsToSelectedUser());
		view.addOverviewUpdateListener(new RefreshOverViewListener());
		view.addPersonTabBtnAddListener(new AddPersonListener());
		view.addPersonTabBtnClearDatabaseListener(new DataDeletionListener());
		view.addPersonTabBtnGenerateDataListener(new DataGenerationListener());
		
		resetEntranceTabLabels();
		loadDataToView();
		view.setVisible(true);
	}
	
	/**
	 * Loads data from database to view
	 **/
	private void loadDataToView() {
		setInactiveRemote();
		setUnusedAddress();
		setUsers();
	}
	
	/**
	 * Sets the labels in the entrance tab to standard strings
	 **/
	private void resetEntranceTabLabels() {
		view.setEntranceTabLblSerialNumberUser(	"...");
		view.setEntranceTabLblFrequencyUser(	"...");
		view.setEntranceTabLblRegisteredUser(	false);
		view.setEntranceTabLblFirstNameUser(	"...");
		view.setEntranceTabLblLastNameUser(		"...");
		view.setEntranceTabLblEndOfContractUser("...");
		view.setEntranceTablLblAddressStreetUser("...");
		view.setEntranceTablLblAddressCityUser(	"...");
		view.setEntranceTablLblAddressCountryUser("...");
		view.setEntranceTabLblFrequencyGate(	"...");
		
		view.setEntranceTabRequest(null);
	} 
	
	/*
	 * Creates list of al inactive Remotes => not given to a inhabitant
	 */
	private void setInactiveRemote(){	
		view.setAddPersonInactiveRemotes( DataManager.getUnusedRemotes() );
	}
	
	/*
	 * Add all persons to list for simulation
	 */	
	private void setUsers(){	
		view.setEntranceTabUsers( gateAdmin.getListeners() );
	}
	
	/*
	 * JPA Namedquery (Address class) => unused Addresses returned
	 */
	private void setUnusedAddress(){
		view.setAddPersonUnusedAddresses( DataManager.getUnusedAddress() );
	}
	
	/*
	 * Query list of all active inhabitants and pass them to view
	 */
	private void setOverView(){
		view.setOverviewTabTable( DataManager.getAllPersons() );
	} 
	
	private void setUserToEntranceLabels() 
	{		
		PersonWrapper user = view.getUserForGate();
		if (user == null) {
			System.out.println("User is null");
			resetEntranceTabLabels();
			return;
		}
		
		Person person = DataManager.getPerson(user.getId());
		
		view.setEntranceTabLblSerialNumberUser(person.getRemote().getSerialNumber());
		view.setEntranceTabLblFrequencyUser( Double.toString( user.getRemote().getFrequency())); // this gets updated on asking the correct frequency
		view.setEntranceTabLblRegisteredUser(person.getRemote().getIsActive());
		view.setEntranceTabLblFirstNameUser(person.getFirstname());
		view.setEntranceTabLblLastNameUser(	person.getLastname()); 
		view.setEntranceTabLblEndOfContractUser(Utility.DATE_FORMAT.format(person.getEndOfContract()));
		view.setEntranceTabLblFrequencyGate(Double.toString(user.getGate().getFrequency())); // this is the gate frequency

		Address a = person.getAdress();
		view.setEntranceTablLblAddressStreetUser( a.getStreet() + " " + a.getNumber() + "/" + a.getMailBox());
		view.setEntranceTablLblAddressCityUser(   a.getPostalCode() + " " + a.getCity());
		view.setEntranceTablLblAddressCountryUser(a.getCountry());
	}

	
	// Listener for register user button (Ask Entrance tab)
	private class RegisterUserListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			PersonWrapper user = view.getUserForGate();
			if (user == null)
				return;
			
			UserRegistrationResult result = gateAdmin.registerUser(user);
			
			view.showMessage(result.toString());
			setUserToEntranceLabels();
		}		
	}		
	// Listener for deactivate user button (Ask Entrance tab)
	private class DeactivateUserListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			PersonWrapper user = view.getUserForGate();
			if (user == null)
				return;

			UserDeactivationResult result = gateAdmin.deactivateUser(user);

			view.showMessage(result.toString());
			setUserToEntranceLabels();
		}		
	}	
	// Listener for button to update gate frequency (Ask Entrance tab)
	private class UpdateGateFrequencyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			gateAdmin.setRandomFrequency();
			view.setEntranceTabLblFrequencyGate( Long.toString(gateAdmin.getFrequency()) ); 
		}		
	}	
	// Listener for ask entrance button (Ask Entrance tab)
	private class AskEntranceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			PersonWrapper selectedUser = view.getUserForGate();
			if (selectedUser == null)
				return;
			
			boolean isGateOpening = selectedUser.openGate();
			log.info("-> The entrance was " + (isGateOpening?"":"not ") + "granted!");
			view.setEntranceTabRequest(isGateOpening);
			setUserToEntranceLabels();
		}		
	}	
	// Listener for user list combo box (Ask Entrance tab)
	private class SetEntranceLabelsToSelectedUser implements ItemListener {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			setUserToEntranceLabels();
		}
	}
	
	// Listeren for adding a new Person to the database (Add Person tab)	 
	private class AddPersonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {				
				Person person = new Person();
				
				person.setFirstname(view.getAddPersonTabFirstName());
				person.setLastname(view.getAddPersonTabLastName());
				person.setEndOfContract(view.getAddPersonTabDate());
				
				if (person.getFirstname().trim().isEmpty() || person.getLastname().trim().isEmpty() || person.getEndOfContract() == null) {
					view.showMessage("Data is missing, cannot add person!");
					return;
				}
				
				if(view.getAddPersonTabChosenAddress() != null)
					person.setAdress(view.getAddPersonTabChosenAddress());
				
				if(view.getAddPersonTabChosenRemote() != null)
					person.setRemote(view.getAddPersonTabChosenRemote());
				
				DataManager.updatePerson(person);
				
				view.setAddPersonTabFirstName("");
				view.setAddPersonTabLastName("");
				view.setAddPersonTabDate(null);
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
				setUsers();
				gateAdmin.refreshUsersFromDB();
			}
		}	
	}
	// Listeren for clearing the database (Add Person tab)
	private class DataDeletionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isSuccesfull = DataDeleter.performDeletion();
			gateAdmin.refreshUsersFromDB();
			loadDataToView();
			view.showMessage(isSuccesfull ? "Data deletion successful!" : "Deletion of data failed...");
		}
		
	}
	// Listeren for clearing the database (Add Person tab)
	private class DataGenerationListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isSuccesfull = DataGenerator.performDataGeneration();
			gateAdmin.refreshUsersFromDB();
			loadDataToView();
			view.showMessage(isSuccesfull ? "Data generation successful!" : "Generation of data failed...");
		}
		
	}
	
	// Listener for update the overview table (Overview tab)
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