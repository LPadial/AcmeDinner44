package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Diner extends Actor {

	// Constructors -----------------------------------------------------------

	public Diner() {
		super();
		events = new ArrayList<Event>();
	}

	// Attributes -------------------------------------------------------------
	private Double avgScore;

	public Double getAvgScore() {
		return avgScore;
	}
	
	public void setAvgScore(Double avgScore){
		this.avgScore = avgScore;
	}

	// Relationships ----------------------------------------------------------

	
	private BusinessCard businessCard;
	private Collection<Event> events;

	
	
	@NotNull
	@OneToOne(optional = false)
	@Valid
	public BusinessCard getBusinessCard() {
		return businessCard;
	}

	public void setBusinessCard(BusinessCard businessCard) {
		this.businessCard = businessCard;
	}
	
	@Valid
	@ManyToMany
	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}


}
