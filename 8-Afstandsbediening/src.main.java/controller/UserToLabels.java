package controller;

import model.business.User;
import model.entities.Address;
import values.DefaultSettings;
import view.View;

public final class UserToLabels {
	public static void SetUserToEntranceLabels(View view, User user) 
	{		
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
}
