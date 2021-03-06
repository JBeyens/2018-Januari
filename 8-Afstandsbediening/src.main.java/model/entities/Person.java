package model.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 09/12/2017
	@Project Afstandsbediening
	@Doel JPA annotated POJO for database mapping
 */

@Entity
@NamedQueries({
	@NamedQuery(name ="Person.allPersonsWithActiveRemote", query= "select p from Person p where p.remote.isActive = true"),
	@NamedQuery(name ="Person.allPersonsWithRemote", query= "select p from Person p where p.remote is not null"),
})
@Table(name ="Person")
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String lastname;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "addressId")
	private Address address;
	private Date endOfContract;
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name= "remoteId")
	private Remote remote;
	
	public Person(String firstname, String lastname, Date endOfContract) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.endOfContract = endOfContract;
	}
	
	public Person(){
		
	}

	public int getId() {
		return id; } 
	public void setId(int id) {
		this.id = id; }

	public String getFirstname() {
		return firstname; } 
	public void setFirstname(String firstname) {
		this.firstname = firstname; }

	public String getLastname() {
		return lastname; } 
	public void setLastname(String lastname) {
		this.lastname = lastname; }

	public Address getAdress() {
		return address; } 
	public void setAdress(Address adress) {
		this.address = adress; }

	public Date getEndOfContract() {
		return endOfContract; } 
	public void setEndOfContract(Date endOfContract) {
		this.endOfContract = endOfContract; }
	
	public Remote getRemote() {
		return remote; } 
	public void setRemote(Remote remote) {
		this.remote = remote;
	}

	@Override
	public String toString() {
		return firstname + " "+ lastname;
	}	
}
