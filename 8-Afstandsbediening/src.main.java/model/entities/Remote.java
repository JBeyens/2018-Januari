package model.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 09/12/2017
	@Project Afstandsbediening
	@Doel JPA annotated POJO for database mapping
 */

@Entity
@Table(name ="Remote")
public class Remote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String serialNumber;
	private Boolean isActive;
	private long frequency;
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name= "personId")
	private Person person;
	
	public Remote(String serialNumber, long frequency) {
		super();
		this.serialNumber = serialNumber;
		this.frequency = frequency;
		this.isActive = false;
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

	/** Getter & Setter for 'isActive' **/
	public Boolean getIsActive() {
		return isActive;}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;}
	
	public Person getPerson() {
		return person; } 
	public void setPerson(Person person) {
		this.person = person;
		/*
		 * Remote becomes active when given to person
		 */
		setIsActive(true);
	}

	@Override
	public String toString() {
		return "Id=" + id + " / serialNumber=" + serialNumber;
	}
}