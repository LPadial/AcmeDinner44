
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Entidad extends DomainEntity {
//PLANTILLA
	// Attributes -------------------------------------------------------------

	private String	ticker;
	private String	title;
	private String	description;
	private int		gauge;
	private Date	moment;
	private boolean	isSaveFinalMode;


	// Constructors -----------------------------------------------------------

	public Entidad() {
		super();
	}

	@Pattern(regexp = "^\\w{5}-\\d{5}$")
	@NotNull
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@NotBlank
	@Length(min = 0, max = 140)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	
	@NotNull
	@NotBlank
	@Length(min = 0, max = 140)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(1)
	@Max(3)
	@NotNull
	public Integer getGauge() {
		return this.gauge;
	}

	public void setGauge(final Integer gauge) {
		this.gauge = gauge;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public boolean getIsSaveFinalMode() {
		return this.isSaveFinalMode;
	}

	public void setIsSaveFinalMode(final boolean isSaveFinalMode) {
		this.isSaveFinalMode = isSaveFinalMode;
	}


	// Relationships -----------------------------------------------------------

	private Administrator	admin;
	private Diner			diner;


	@Valid
	@ManyToOne(optional = false)
	public Administrator getAdmin() {
		return this.admin;
	}

	public void setAdmin(final Administrator admin) {
		this.admin = admin;
	}

	@Valid
	@ManyToOne(optional = false)
	public Diner getDiner() {
		return this.diner;
	}

	public void setDiner(final Diner diner) {
		this.diner = diner;
	}

}
