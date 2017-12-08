package modelPersistent;

import java.io.Serializable;

public class Remote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String serialNumber;
	private long frequency;
	
	public Remote(String serialNumber, long frequency) {
		super();
		this.serialNumber = serialNumber;
		this.frequency = frequency;
	}
	
	public Remote(){
		
	}
	
	/** Getter & Setter for 'id' **/
	public int getId() {
		return id;	}
	public void setId(int id) {
		this.id = id;	}
	
	/** Getter & Setter for 'serialNumber' **/
	public String getSerialNumber() {
		return serialNumber;	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;	}
	
	/** Getter & Setter for 'frequency' **/
	public long getFrequency() {
		return frequency;	}
	public void setFrequency(long frequency) {
		this.frequency = frequency;	}
	
}
