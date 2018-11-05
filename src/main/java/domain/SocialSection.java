package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialSection extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public SocialSection() {
		super();
	}

	// Attributes -------------------------------------------------------------
	
	private String title;
	private String network;
	private String nickname;
	private String link;
	

	// GETTERS
	
	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotBlank
	public String getNetwork() {
		return network;
	}

	@NotBlank
	public String getNickname() {
		return nickname;
	}

	@NotBlank
	@URL
	public String getLink() {
		return link;
	}

	// SETTERS
	
	public void setTitle(String title){
		this.title=title;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	// Relationships ----------------------------------------------------------

}
