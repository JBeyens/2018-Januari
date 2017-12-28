 package model.idmodule;


/**
 * @Author Jef Beyens & Ben Vandevorst
 * @Datum 27/11/2017
 * @Project Afstandsbediening
 * @Doel This is the software used by the remote
 */
public class GateModule {
	private long frequency;

	
	/**
	 * Getter & setter for Frequency of the receiver module of the gate
	 **/
	public long getFrequency() {
		return frequency;	}
	public void setFrequency(long frequency) {
		this.frequency = frequency;	}

	
}
