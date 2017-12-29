package model.idmodule;

import model.entities.Remote;
import model.observer.IRemoteObserver;

/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/12/2017
 * @Project Afstandsbediening
 * @Doel This is the software used by the remote
 */
public class RemoteModule implements IRemoteObserver{
	// FIELDS
	private Remote remote;
	
	// CONSTRUCTOR
	public RemoteModule(Remote remote) {
		this.remote = remote;
	}
	
	// METHODS
	/** Updates the frequency in the remote **/
	public void updateFrequency(long frequency) {
		getRemote().setFrequency(frequency);
	}
	
	/** Will return true if the remote is allowed to open the gate. Will return false otherwise. **/
	public boolean askOpenGate(GateModule gate) {
		gate.idModule(this);
		return getRemote().getFrequency() == gate.getFrequency();
	}
	
	/** Returns the serial Id number of the remote. **/
	public String sendSerialId() {
		return getRemote().getSerialNumber();
	}
	
	/** Returns the remote object **/
	public Remote getRemote() {
		return remote;
	}
}
