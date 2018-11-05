package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Chirp() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Date dateCreation;
	private String topic;
	private String text;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@NotBlank
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<SpamWord> spamWords;
	
	
	@Valid
	@ManyToMany
	@NotNull
	public Collection<SpamWord> getSpamWords(){
		return spamWords;
	}
	
	public void setSpamWords(Collection<SpamWord> spamWords){
		this.spamWords= spamWords;
	}

}
