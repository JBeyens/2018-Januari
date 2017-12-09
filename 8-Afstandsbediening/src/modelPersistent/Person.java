package modelPersistent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@ManyToOne
	@JoinColumn(name = "addressId", nullable=false)
	private Address address;
	@ManyToOne
	@JoinColumn(name= "remoteId", nullable=false)
	private Remote remote;
	private Date endOfContract;
	private String email;
	
	public Person(String firstname, String lastname, Date endOfContract, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.endOfContract = endOfContract;
		this.email = email;
	}
	
	public Person(){
		
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

	public Address getAdress() {
		return address;
	}
	
	public Remote getRemote() {
		return remote;
	}

	public void setRemote(Remote remote) {
		this.remote = remote;
	}

	public void setAdress(Address adress) {
		this.address = adress;
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
