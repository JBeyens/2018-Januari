package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.Administrator;
import model.business.DataManager;
import model.business.User;
import model.entities.Address;
import model.entities.Person;
import values.UserDeactivationResult;
import values.DefaultSettings;
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
		log = DefaultSettings.getLogger("Controller");
		gateAdmin = new Administrator();
		view = new View();		
	}
	
	public void start(){		
		view.entranceTabAddAskEntranceListener(new AskEntranceListener());
		view.entranceTabAddRegisterUserListener(new RegisterUserListener());
		view.entranceTabAddDeactivateUserListener(new DeactivateUserListener());
		view.entranceTabAddUserListItemListener(new SetEntranceLabelsToSelectedUser());
		view.addOVerViewUpdateListener(new RefreshOverViewListener());
		view.addAddPersonListener(new AddPersonListener());
		
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
	
	private void setUserToEntranceLabels() 
	{		
		User user = view.getUserForGate();
		view.setEntranceTabLblSerialNumberUser(	user.getRemote().getSerialNumber());
		view.setEntranceTabLblFrequencyUser( 	Double.toString( user.getRemote().getFrequency()));
		view.setEntranceTabLblRegisteredUser(	user.getRemote().getIsActive());
		view.setEntranceTabLblFirstNameUser(	user.getPerson().getFirstname());
		view.setEntranceTabLblLastNameUser(		user.getPerson().getLastname()); 
		view.setEntranceTabLblEndOfContractUser(DefaultSettings.DATE_FORMAT.format(user.getPerson().getEndOfContract()));
		view.setEntranceTabLblFrequencyGate(	Double.toString(user.getGate().getFrequency()));

		Address a = user.getPerson().getAdress();
		view.setEntranceTablLblAddressStreetUser( a.getStreet() + " " + a.getNumber() + "/" + a.getMailBox());
		view.setEntranceTablLblAddressCityUser(   a.getPostalCode() + " " + a.getCity());
		view.setEntranceTablLblAddressCountryUser(a.getCountry());
	}

	
	// Listener for register user button (Ask Entrance tab)
	private class RegisterUserListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			UserRegistrationResult result = gateAdmin.registerUser(view.getUserForGate());
			
			view.showMessage(result.toString());
			setUserToEntranceLabels();
		}		
	}		
	// Listener for deactivate user button (Ask Entrance tab)
	private class DeactivateUserListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			UserDeactivationResult result = gateAdmin.deactivateUser(view.getUserForGate());

			view.showMessage(result.toString());
			setUserToEntranceLabels();
		}		
	}	
	// Listener for ask entrance button (Ask Entrance tab)
	private class AskEntranceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			User selectedUser = view.getUserForGate();
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