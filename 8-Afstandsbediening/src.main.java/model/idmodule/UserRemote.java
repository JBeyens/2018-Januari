package model.idmodule;

import model.entities.Remote;
import model.observer.IRemoteObserver;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel This is the software used by the remote
 */
public class UserRemote implements IRemoteObserver{
	// FIELDS
	private Remote remote;
	
	// CONSTRUCTOR
	public UserRemote(Remote remote) {
		this.remote = remote;
	}
	
	// METHODS
	public void UpdateFrequency(long frequency) {
		remote.setFrequency(frequency);
	}
	

	public boolean AskOpenGate()
	{
		return true;
	}
	
	public void SendId()
	{
		
	}
}
