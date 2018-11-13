package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Soiree extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Soiree() {
		super();
		dishes = new HashSet<Dish>();
	}

	// Attributes -------------------------------------------------------------

	private Date date;
	private String address;
	private Collection<String> pictures;


	// Getters

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getDate() {
		return date;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	@NotEmpty
	@ElementCollection(targetClass = String.class)
	public Collection<String> getPictures() {
		return pictures;
	}

	// Setters
	public void setDate(Date date) {
		this.date = date;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	public void addPictures(String picture) {
		this.pictures.add(picture);
	}

	public void removePictures(String picture) {
		this.pictures.remove(picture);
	}

	
	
	// Relationships ----------------------------------------------------------
	private Diner organizer;
	private Event event;
	private Collection<Dish> dishes;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Diner getOrganizer() {
		return organizer;
	}
	
	public void setOrganizer(Diner organizer) {
		this.organizer = organizer;
	}
	
	@NotNull	
	@Valid
	@ManyToOne(optional = false)
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Valid
	@OneToMany(mappedBy = "soiree", cascade = CascadeType.ALL)
	public Collection<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Collection<Dish> dishes) {
		this.dishes = dishes;
	}
	
	public void addDish(Dish dish) {
		dishes.add(dish);
		dish.setSoiree(this);
	}

	public void removeDish(Dish dish) {
		dishes.remove(dish);
		dish.setSoiree(null);
	}
	
	


}
