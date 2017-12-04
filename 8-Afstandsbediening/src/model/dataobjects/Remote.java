package model.dataobjects;

public class Remote {
	private int id;
	private String serialNumber;
	private long frequency;
	
	public Remote(String serialNumber, long frequency) {
		super();
		this.serialNumber = serialNumber;
		this.frequency = frequency;
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
