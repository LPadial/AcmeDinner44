package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingCart extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public ShoppingCart() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Date dateCreation;
	private Boolean isOrdered;
	private String deliveryAddress;
	private Double priceTotal;
	

	// Getters

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getDateCreation() {
		return dateCreation;
	}	

	public Boolean getIsOrdered() {
		return isOrdered;
	}
	
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	
	@Digits(integer = 7, fraction = 2 )
	public Double getPriceTotal() {
		return priceTotal;
	}

	// Setters
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}
	
	// Relationships ----------------------------------------------------------
	
	private Diner owner;
	private CreditCard creditCard;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Diner getOwner(){
		return owner;
	}
	
	public void setOwner(Diner owner){
		this.owner = owner;
	}
	
	@Valid
	@OneToOne(optional = true)
	public CreditCard getCreditCard(){
		return creditCard;
	}
	
	public void setCreditCard(CreditCard creditCard){
		this.creditCard = creditCard;
	}
	

}
