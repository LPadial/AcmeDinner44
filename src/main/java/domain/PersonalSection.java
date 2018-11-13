package domain;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalSection extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public PersonalSection() {
		super();
	}

	// Attributes -------------------------------------------------------------
	
	private String title;
	private String fullName;
	private String photo;
	private Date birthdate;
	private Collection<String> likes;
	private Collection<String> dislikes;

	// GETTERS
	
	@NotBlank
	public String getTitle() {
		return title;
	}

	
	public String getFullName() {
		return fullName;
	}

	@URL
	public String getPhoto() {
		return photo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthdate() {
		return birthdate;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public Collection<String> getLikes() {
		return likes;
	}
	
	@NotNull
	@ElementCollection(targetClass = String.class)
	public Collection<String> getDislikes() {
		return dislikes;
	}

	// SETTERS
	
	public void setTitle(String title){
		this.title=title;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setLikes(Collection<String> likes) {
		this.likes = likes;
	}

	public void setDislikes(Collection<String> dislikes) {
		this.dislikes = dislikes;
	}
	
	public void addLike(String like) {
		this.likes.add(like);
	}

	public void removeLike(String like) {
		this.likes.remove(like);
	}
	
	public void addDislike(String dislike) {
		this.dislikes.add(dislike);
	}

	public void removeDislike(String dislike) {
		this.dislikes.remove(dislike);
	}

}
