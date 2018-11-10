package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Vote extends DomainEntity {
	// Constructors -----------------------------------------------------------

	public Vote() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Integer points;
	private Collection<String> comments;
	private Collection<String> pictures;

	// GETTERS

	@Range(min = 0, max = 10)
	public Integer getPoints() {
		return points;
	}
	
	@NotNull
	@ElementCollection(targetClass = String.class)
	public Collection<String> getComments() {
		return comments;
	}
	
	@NotNull
	@ElementCollection(targetClass = String.class)
	public Collection<String> getPictures() {
		return pictures;
	}

	// SETTERS

	public void setPoints(Integer points) {
		this.points = points;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	public void addComments(String comment) {
		this.comments.add(comment);
	}

	public void removeComments(String comment) {
		this.comments.remove(comment);
	}
	
	public void addPictures(String picture) {
		this.pictures.add(picture);
	}

	public void removePictures(String picture) {
		this.pictures.remove(picture);
	}

	// Relationships ----------------------------------------------------------
	private Diner voter;
	private Soiree soiree;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Diner getVoter() {
		return voter;
	}

	public void setVoter(Diner voter) {
		this.voter = voter;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Soiree getSoiree() {
		return soiree;
	}

	public void setsoiree(Soiree soiree) {
		this.soiree = soiree;
	}

}
