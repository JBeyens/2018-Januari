package model.dataobjects;

public class Remote {
	private int id;
	private int serialNumber;
	private long frequency;
	private int bewoner;
	
	/** Getter & Setter for 'id' **/
	public int getId() {
		return id;	}
	public void setId(int id) {
		this.id = id;	}
	
	/** Getter & Setter for 'serialNumber' **/
	public int getSerialNumber() {
		return serialNumber;	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;	}
	
	/** Getter & Setter for 'frequency' **/
	public long getFrequency() {
		return frequency;	}
	public void setFrequency(long frequency) {
		this.frequency = frequency;	}
	
	/** Getter & Setter for 'bewoner' **/
	public int getBewoner() {
		return bewoner;	}
	public void setBewoner(int bewoner) {
		this.bewoner = bewoner;	}
}
