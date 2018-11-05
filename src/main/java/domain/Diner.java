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

import org.hibernate.validator.constraints.NotEmpty;

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

	/*
	 * private List<Event> events; private List<Soiree> soirees; private
	 * List<Vote> votes; private List<ShoppingCart> shoppingCarts;
	 */
	private BussinessCard bussinessCard;
	private Collection<Event> events;

	/*
	 * @OneToMany public List<Event> getEvents() { return events; }
	 * 
	 * public void setEvents(List<Event> events) { this.events = events; }
	 * 
	 * @OneToMany public List<Soiree> getSoirees() { return soirees; }
	 * 
	 * public void setSoirees(List<Soiree> soirees) { this.soirees = soirees; }
	 * 
	 * @OneToMany public List<Vote> getVotes() { return votes; }
	 * 
	 * public void setVotes(List<Vote> votes) { this.votes = votes; }
	 * 
	 * @OneToMany public List<ShoppingCart> getShoppingCarts() { return
	 * shoppingCarts; }
	 * 
	 * public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
	 * this.shoppingCarts = shoppingCarts; }
	 */
	
	@NotNull
	@OneToOne(optional = false)
	@Valid
	public BussinessCard getBussinessCard() {
		return bussinessCard;
	}

	public void setBussinessCard(BussinessCard bussinessCard) {
		this.bussinessCard = bussinessCard;
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
