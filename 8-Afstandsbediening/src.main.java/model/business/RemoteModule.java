package model.business;

import model.business.interfaces.IGateModule;
import model.business.interfaces.IRemoteModule;
import model.entities.Remote;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel This is the software used by the remote
 */
public class RemoteModule implements IRemoteModule {
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
	@Override
	public void setFrequency(long frequency) {
		remote.setFrequency(frequency);
	}
	
	/**
	 *  Will return true if the remote is allowed to open the gate. Will return false otherwise.
	 **/
	public boolean askOpenGate(IGateModule gate) {
		gate.verifyAndUpdateFrequencyRemote(this);
		return remote.getFrequency() == gate.getFrequency();
	}
	
	/** 
	 * Returns the serial Id number of the remote.
	 **/
	@Override
	public String getSerialNumber() {
		return remote.getSerialNumber();
	}
}
