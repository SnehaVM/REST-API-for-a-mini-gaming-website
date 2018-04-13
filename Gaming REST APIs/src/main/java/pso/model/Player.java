package pso.model;
import javax.persistence.*;
import java.util.*;

/**
 * Entity class for Player
 *
 */
@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	private String firstname;
	private String lastname;
	@Column(unique = true)
	private String email;
	private String description;
	@Embedded
	private Address address;
	@Transient
	private List<Long> opponents = new ArrayList<Long>();

	@ManyToOne
	@JoinColumn(name = "sponsor_id")
	private Sponsor sponsorid;
	
	public Player() {
	}

	public Player(Long id, String firstname, String lastname, String email, String description, Address address) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.address = address;

	}

	//get player id
	public Long getId() {
		return id;
	}

	//set player id
	public void setId(Long id) {
		this.id = id;
	}

	//get first name
	public String getFirstname() {
		return firstname;
	}

	//set first name
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	//get last name
	public String getLastname() {
		return lastname;
	}

	//set last name
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	//get email
	public String getEmail() {
		return email;
	}

	//set email
	public void setEmail(String email) {
		this.email = email;
	}

	//get description
	public String getDescription() {
		return description;
	}

	//set description
	public void setDescription(String description) {
		this.description = description;
	}

	//get address
	public Address getAddress() {
		return address;
	}

	//set address
	public void setAddress(Address address) {
		this.address = address;
	}

	//get Sponsor
	public Sponsor getSponsor() {
		return sponsorid;
	}

	//set sponsor
	public void setSponsor(Sponsor sponsor) {
		this.sponsorid = sponsor;
	}

	//get opponents
	public List<Long> getOpponents() {
		return opponents;
	}

	//set opponents
	public void setOpponents(List<Long> opponents) {
		this.opponents = opponents;
	}
}
