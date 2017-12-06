package modelPersistent;

import java.io.Serializable;

public class Adres implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String street;
	private int number;
	private int mailBox;
	private int postalCode;
	private String city;
	private String country;
	
	public Adres(){
		
	}
	
	public Adres(String street, int number, int mailBox, int postalCode, String city, String country) {
		super();
		this.street = street;
		this.number = number;
		this.mailBox = mailBox;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getMailBox() {
		return mailBox;
	}

	public void setMailBox(int mailBox) {
		this.mailBox = mailBox;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
}
