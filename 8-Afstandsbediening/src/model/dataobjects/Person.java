package model.dataobjects;

import java.util.Date;

public class Person {
	private int id;
	private String firstname;
	private String lastname;
	private int adress;
	private boolean isOwner;
	private Date endOfContract;
	private String email;
	
	/** Getter & Setter for 'id' **/
	public int getId() {
		return id; 	}
	public void setId(int id) {
		this.id = id; 	}
	
	/** Getter & Setter for 'firstname' **/
	public String getFirstname() {
		return firstname; }
	public void setFirstname(String firstname) {
		this.firstname = firstname;	}
		
	/** Getter & Setter for 'lastname' **/
	public String getLastname() {
		return lastname; }
	public void setLastname(String lastname) {
		this.lastname = lastname; }
	
	/** Getter & Setter for 'adress' **/
	public int getAdress() {
		return adress;	}
	public void setAdress(int adress) {
		this.adress = adress; }
	
	/** Getter & Setter for 'isOwner' **/
	public boolean isOwner() {
		return isOwner;	}
	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner; }
	
	/** Getter & Setter for 'endOfContract' **/
	public Date getEndOfContract() {
		return endOfContract;	}
	public void setEndOfContract(Date endOfContract) {
		this.endOfContract = endOfContract;	}
	
	/** Getter & Setter for 'email' **/
	public String getEmail() {
		return email;	}
	public void setEmail(String email) {
		this.email = email;	}
}
