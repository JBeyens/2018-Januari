package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 09/12/2017
	@Project Afstandsbediening
	@Doel JPA annotated POJO for database mapping
 */

@Entity
@NamedQuery(name="Address.findUnusedAddress", query = "SELECT a FROM Address a WHERE a.id NOT IN (SELECT p.address.id FROM Person p where p.address.id IS NOT NULL)")
@Table(name = "address")
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String street;
	private int nr;
	private int mailBox;
	private int postalCode;
	private String city;
	private String country;
	
	public Address(){
		
	}
	
	public Address(String street, int number, int mailBox, int postalCode, String city, String country) {
		super();
		this.street = street;
		this.nr = number;
		this.mailBox = mailBox;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public int getId() {
		return id; } 
	public void setId(int id) {
		this.id = id; }

	public String getStreet() {
		return street; }
	public void setStreet(String street) {
		this.street = street; }
	
	public int getNumber() {
		return nr; }
	public void setNumber(int number) {
		this.nr = number; }

	public int getMailBox() {
		return mailBox; }
	public void setMailBox(int mailBox) {
		this.mailBox = mailBox; }

	public int getPostalCode() {
		return postalCode; }
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode; }

	public String getCity() {
		return city; }
	public void setCity(String city) {
		this.city = city; }

	public String getCountry() {
		return country; }
	public void setCountry(String country) {
		this.country = country; }

	@Override
	public String toString() {
		return "Id=" + id + ", street=" + street + ", nr=" + nr + ", mailBox=" + mailBox;
	}

	
}
