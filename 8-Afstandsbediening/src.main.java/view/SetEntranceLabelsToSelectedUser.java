package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import model.business.User;
import values.DefaultSettings;

/**
 * 	@Author Jef Beyens & Ben Vandevorst
	@Datum 15/12/2017
	@Project Afstandsbediening
	@Doel Single controller for handling interaction model<>view
 */
// Listener for user list combo box (Ask Entrance tab)
public class SetEntranceLabelsToSelectedUser implements ItemListener {
	View view;
	
	public SetEntranceLabelsToSelectedUser(View view) {
		this.view = view;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		User user = view.getUserForGate(); 
			
		view.setEntranceTabLblSerialNumberUser(	user.getRemote().getSerialNumber());
		view.setEntranceTabLblFrequencyUser( 	Double.toString( user.getRemote().getFrequency()));
		view.setEntranceTabLblRegisteredUser(	user.getRemote().getIsActive());
		view.setEntranceTabLblFirstNameUser(	user.getPerson().getFirstname());
		view.setEntranceTabLblLastNameUser(		user.getPerson().getLastname()); 
		view.setEntranceTabLblEndOfContractUser(DefaultSettings.DATE_FORMAT.format(user.getPerson().getEndOfContract()));
		view.setEntranceTabLblAddressUser(		user.getPerson().getAdress().toString());
		view.setEntranceTabLblFrequencyGate(	Double.toString(user.getGate().getFrequency()));
	}
}