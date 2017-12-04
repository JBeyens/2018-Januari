package model.dataobjects;

import java.util.Date;

public class Person {
	private int id;
	private String firstname;
	private String lastname;
	private Adres adress;
	private Date endOfContract;
	private String email;
	
	public Person(String firstname, String lastname, Adres adress, Date endOfContract, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.adress = adress;
		this.endOfContract = endOfContract;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Adres getAdress() {
		return adress;
	}

	public void setAdress(Adres adress) {
		this.adress = adress;
	}

	public Date getEndOfContract() {
		return endOfContract;
	}

	public void setEndOfContract(Date endOfContract) {
		this.endOfContract = endOfContract;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
