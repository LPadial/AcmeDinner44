package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String actorName;
	private String surname;
	private String email;
	private UserAccount userAccount;	

	@NotBlank
	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Relationships ----------------------------------------------------------
	
	private List<Actor> followers;
	private List<Chirp> chirps;
	private Finder finder;

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@ManyToMany
	public List<Actor> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Actor> followers) {
		this.followers = followers;
	}

	@NotNull
	@OneToMany
	public List<Chirp> getChirps() {
		return chirps;
	}

	public void setChirps(List<Chirp> chirps) {
		this.chirps = chirps;
	}
	
	public void addChirp(Chirp chirp) {
		chirps.add(chirp);
	}
	
	public void removeChirp(Chirp chirp) {
		chirps.remove(chirp);
	}
	
	@NotNull
	@OneToOne(optional=false)
	public Finder getFinder() {
		return finder;
	}

	public void setFinder(Finder finder) {
		this.finder = finder;
	}

}
