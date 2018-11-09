package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Event() {
		super();		
		soirees = new HashSet<Soiree>();
	}

	// Attributes -------------------------------------------------------------

	private String ticker;
	private String title;
	private String city;
	private String description;

	@NotBlank
	@Pattern(regexp = "^(1[8-9]|2[0-9])(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])-(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])-[A-Z]{4}$", message = "This ticker is wrong")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Relationships ----------------------------------------------------------
	private Collection<Soiree> soirees;
	private Diner organizer;
	

	@Valid
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	public Collection<Soiree> getSoirees() {
		return soirees;
	}

	public void setSoirees(Collection<Soiree> soirees) {
		this.soirees = soirees;
	}
	
	public void addSoiree(Soiree soiree) {
		soirees.add(soiree);
		soiree.setEvent(this);
	}

	public void removeSoiree(Soiree soiree) {
		soirees.remove(soiree);
		soiree.setEvent(null);
	}

	
	@NotNull
	@Valid
	@ManyToOne
	public Diner getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Diner organizer) {
		this.organizer = organizer;
	}
	
	
}
