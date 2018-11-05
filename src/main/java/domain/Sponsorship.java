package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {
	// Constructors -----------------------------------------------------------

	public Sponsorship() {
		super();
		
	}

	// Attributes -------------------------------------------------------------

	private Date dateCreation;
	private String description;
	private Collection<String> banners;
	private Decision decision;

	// Getters

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getDateCreation() {
		return dateCreation;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	@NotEmpty
	@ElementCollection(targetClass = String.class)
	public Collection<String> getBanners() {
		return banners;
	}

	@Valid
	public Decision getDecision() {
		return decision;
	}

	// Setters
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBanners(Collection<String> banners) {
		this.banners = banners;
	}
	
	public void addBanners(String banner) {
		this.banners.add(banner);
	}

	public void removeBanners(String banner) {
		this.banners.remove(banner);
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}
	
	// Relationships ----------------------------------------------------------
		private CreditCard creditCard;
		private Soiree soiree;
		
		@Valid
		@OneToOne(optional=true)
		public CreditCard getCreditCard(){
			return creditCard;
		}
		
		public void setCreditCard(CreditCard creditCard){
			this.creditCard=creditCard;
		}
		
		@Valid
		@ManyToOne(optional=false)
		public Soiree getSoiree(){
			return soiree;
		}
		
		public void setSoiree(Soiree soiree){
			this.soiree=soiree;
		}

}
