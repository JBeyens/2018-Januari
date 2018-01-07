package view;

import model.business.User;
import values.DefaultSettings;

public final class ViewUserToLabels {
	public static void SetUserToEntranceLabels(View view, User user) 
	{		
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
