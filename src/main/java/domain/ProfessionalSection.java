package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalSection extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public ProfessionalSection() {
		super();
	}

	// Attributes -------------------------------------------------------------
	
	private String title;
	private String company;
	private String position;
	
	

	// GETTERS
	
	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotBlank
	public String getPosition() {
		return position;
	}

	@NotBlank
	public String getCompany() {
		return company;
	}

	// SETTERS
	
	public void setTitle(String title){
		this.title=title;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	// Relationships ----------------------------------------------------------


}
