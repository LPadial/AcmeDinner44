package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Supermarket extends Actor {
	// Constructors -----------------------------------------------------------

	public Supermarket() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String brand;

	// Getters
	@NotBlank
	@Column(unique = true)
	public String getBrand() {
		return brand;
	}

	// Setters
	public void setBrand(String brand) {
		this.brand = brand;
	}

}
