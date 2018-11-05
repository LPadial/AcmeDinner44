package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {
	// Constructors -----------------------------------------------------------

	public Sponsor() {
		super();
	}

	// Relationships ----------------------------------------------------------

	private Collection<Sponsorship> sponsorships;

	@Valid
	@OneToMany
	public Collection<Sponsorship> getSponsorships() {
		return sponsorships;
	}

	public void setSponsorships(Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
	
	public void addSponsorship(Sponsorship sponsorship) {
		sponsorships.add(sponsorship);
	}

	public void removeSponsorship(Sponsorship sponsorship) {
		sponsorships.remove(sponsorship);
	}

}
