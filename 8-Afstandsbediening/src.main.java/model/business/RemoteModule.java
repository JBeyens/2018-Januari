package model.business;

import model.entities.Remote;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel This is the software used by the remote
 */
public class RemoteModule  {
	// FIELDS
	private Remote remote;
	
	// CONSTRUCTOR
	public RemoteModule(Remote remote) {
		this.remote = remote;
	}
	
	// METHODS
	/** 
	 * Updates the frequency in the remote
	 **/
	public void setFrequency(long frequency) {
		remote.setFrequency(frequency);
	}
	
	/**
	 *  Will return true if the remote is allowed to open the gate. Will return false otherwise.
	 **/
	public boolean askOpenGate() {
		//gate.verifyAndUpdateFrequencyRemote(this);
		return remote.getFrequency() == 0;
	}
	
	/** 
	 * Returns the serial Id number of the remote.
	 **/
	public String getSerialNumber() {
		return remote.getSerialNumber();
	}
}
