package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class BussinessCard extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public BussinessCard() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	
	private PersonalSection personalSection;
	private Collection<ProfessionalSection> professionalSections;
	private Collection<SocialSection> socialSections;
	
	@Valid
	@OneToMany
	@NotNull
	public Collection<ProfessionalSection> getProfessionalSections() {
		return professionalSections;
	}

	public void setProfessionalSections(Collection<ProfessionalSection> professionalSections) {
		this.professionalSections = professionalSections;
	}
	
	public void addProfessionalSection(ProfessionalSection professionalSection) {
		professionalSections.add(professionalSection);
	}

	public void removeProfessionalSection(ProfessionalSection professionalSection) {
		professionalSections.remove(professionalSection);
	}
	
	@Valid
	@OneToMany
	@NotNull
	public Collection<SocialSection> getSocialSections() {
		return socialSections;
	}

	public void setSocialSections(Collection<SocialSection> socialSections) {
		this.socialSections = socialSections;
	}
	
	public void addSocialSection(SocialSection socialSection) {
		socialSections.add(socialSection);
	}

	public void removeSocialSection(SocialSection socialSection) {
		socialSections.remove(socialSection);
	}
	
	@Valid
	@OneToOne(optional = false)
	public PersonalSection getPersonalSection() {
		return personalSection;
	}

	public void setPersonalSection(PersonalSection personalSection) {
		this.personalSection = personalSection;
	}
	
}
