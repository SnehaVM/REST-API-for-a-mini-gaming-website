package pso.model;
import javax.persistence.*;

/**
 * Entity class for Sponsor
 *
 */
@Entity
public class Sponsor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sponsor_id", unique = true, nullable = false)
	private Long id;
	private String name;
	private String description;
	@Embedded
	private Address address;

	public Sponsor() {
	}

	public Sponsor(Long id, String name, String description, Address address) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
	}

	//get id
	public Long getId() {
		return id;
	}

	//set id
	public void setId(Long id) {
		this.id = id;
	}

	//get name
	public String getName() {
		return name;
	}

	//set name
	public void setName(String name) {
		this.name = name;
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
}
